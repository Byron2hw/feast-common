package com.feast.common.model;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Byron
 * @date 2022/12/16 3:34 下午
 */
@Slf4j
public class ObjectId {
    /**
     * 2<sup>24</sup> - 1
     */
    private static final int LOW_ORDER_THREE_BYTES = 0xffffff;
    private static final long MACHINE_IDENTIFIER;
    private static final short PROCESS_IDENTIFIER;
    private static final AtomicLong NEXT_COUNTER = new AtomicLong((new SecureRandom()).nextLong());
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final long timestamp;
    private final long machineIdentifier;
    private final short processIdentifier;
    private final long counter;

    public static ObjectId get() {
        return new ObjectId();
    }

    public static String getString() {
        return (new ObjectId()).toHexString();
    }

    public static boolean isValid(final String hexString) {
        if (hexString == null) {
            throw new IllegalArgumentException();
        } else {
            int len = hexString.length();
            if (len != 24) {
                return false;
            } else {
                for(int i = 0; i < len; ++i) {
                    char c = hexString.charAt(i);
                    if ((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public ObjectId() {
        this(System.currentTimeMillis());
    }

    public ObjectId(final Long mills) {
        this(mills, MACHINE_IDENTIFIER, PROCESS_IDENTIFIER, NEXT_COUNTER.getAndIncrement(), false);
    }
    private ObjectId(final long timestamp, final long machineIdentifier, final short processIdentifier, final long counter, final boolean checkCounter) {
        if ((machineIdentifier & -16777216) != 0) {
            throw new IllegalArgumentException("The machine identifier must be between 0 and 16777215 (it must fit in three bytes).");
        } else if (checkCounter && (counter & -16777216) != 0) {
            throw new IllegalArgumentException("The counter must be between 0 and 16777215 (it must fit in three bytes).");
        } else {
            this.timestamp = timestamp;
            this.machineIdentifier = machineIdentifier;
            this.processIdentifier = processIdentifier;
            this.counter = counter & LOW_ORDER_THREE_BYTES;
        }
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        this.putToByteBuffer(buffer);
        return buffer.array();
    }

    public void putToByteBuffer(final ByteBuffer buffer) {
        notNull("buffer", buffer);
        isTrueArgument("buffer.remaining() >=12", buffer.remaining() >= 12);
        buffer.put(long3(this.timestamp));
        buffer.put(long2(this.timestamp));
        buffer.put(long1(this.timestamp));
        buffer.put(long0(this.timestamp));
        buffer.put(long2(this.machineIdentifier));
        buffer.put(long1(this.machineIdentifier));
        buffer.put(long0(this.machineIdentifier));
        buffer.put(short1(this.processIdentifier));
        buffer.put(short0(this.processIdentifier));
        buffer.put(long2(this.counter));
        buffer.put(long1(this.counter));
        buffer.put(long0(this.counter));
    }

    private static int createMachineIdentifier() {
        int machinePiece;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration e = NetworkInterface.getNetworkInterfaces();

            while(e.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface)e.nextElement();
                sb.append(ni.toString());
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    ByteBuffer bb = ByteBuffer.wrap(mac);
                    try {
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                    } catch (BufferUnderflowException var7) {
                    }
                }
            }

            machinePiece = sb.toString().hashCode();
        } catch (Throwable var8) {
            machinePiece = (new SecureRandom()).nextInt();
            log.warn("Failed to get machine identifier from network interface, using random number instead", var8);
        }

        machinePiece &= LOW_ORDER_THREE_BYTES;
        return machinePiece;
    }

    private static short createProcessIdentifier() {
        short processId;
        try {
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            if (processName.contains("@")) {
                processId = (short)Integer.parseInt(processName.substring(0, processName.indexOf(64)));
            } else {
                processId = (short)ManagementFactory.getRuntimeMXBean().getName().hashCode();
            }
        } catch (Throwable var2) {
            processId = (short)(new SecureRandom()).nextInt();
            log.warn("Failed to get process identifier from JMX, using random number instead", var2);
        }

        return processId;
    }

    private static byte long3(final long x) {
        return (byte)(x >> 32);
    }
    private static byte long2(final long x) {
        return (byte)(x >> 16);
    }
    private static byte long1(final long x) {
        return (byte)(x >> 8);
    }
    private static byte long0(final long x) {
        return (byte)(x);
    }

    private static byte short1(final short x) {
        return (byte)(x >> 8);
    }

    private static byte short0(final short x) {
        return (byte)(x);
    }

    private static <T> T notNull(String name, T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " can not be null");
        } else {
            return value;
        }
    }
    public String toHexString() {
        char[] chars = new char[24];
        int i = 0;
        byte[] var3 = this.toByteArray();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            chars[i++] = HEX_CHARS[b >> 4 & 15];
            chars[i++] = HEX_CHARS[b & 15];
        }
        return new String(chars);
    }

    private static void isTrueArgument(String name, boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException("state should be: " + name);
        }
    }

    static {
        try {
            MACHINE_IDENTIFIER = createMachineIdentifier();
            PROCESS_IDENTIFIER = createProcessIdentifier();
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }
}