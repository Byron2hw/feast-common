package com.feast.common.model;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Byron
 * @date 2022/12/16 3:34 下午
 */
@Slf4j
public record ObjectId(int machineId, short processId,
                       LongAdder adder) implements Comparable<ObjectId> {
    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 2<sup>24</sup>-1.
     */
    private static final int MAX_VALUE = 16777215;
    private static final int LENGTH = 24;
    private static final int SHIFT = 16;
    private static final int BIT_SHIFT = 8;

    public static ObjectId get() {
        LongAdder longAdder = new LongAdder();
        long randomValue = new SecureRandom().nextLong();
        longAdder.add(randomValue & MAX_VALUE);
        return new ObjectId(getMachineId(), getProcessId(), longAdder);
    }

    public String id() {
        char[] chars = new char[LENGTH];
        byte[] bytes = this.toByte();
        int length = CHARS.length - 1;
        int i = 0;
        for (byte aByte : bytes) {
            chars[i++] = CHARS[aByte >> 4 & length];
            chars[i++] = CHARS[aByte & length];
        }
        return new String(chars);
    }

    /**
     *
     * @return id
     */
    public static String getId() {
        return get().id();
    }

    private byte[] toByte() {
        int capacity = LENGTH >> 1;
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        long now = System.currentTimeMillis();
        buffer.put((byte)(now >> LENGTH));
        buffer.put((byte)(now >> SHIFT));
        buffer.put((byte)(now >> BIT_SHIFT));
        buffer.put((byte)(now));
        buffer.put((byte)(machineId >> SHIFT));
        buffer.put((byte)(machineId >> BIT_SHIFT));
        buffer.put((byte)(machineId));
        buffer.put((byte) (processId >> BIT_SHIFT));
        buffer.put((byte) (processId));
        adder.increment();
        long adderValue = adder.longValue();
        buffer.put((byte)(adderValue >> SHIFT));
        buffer.put((byte)(adderValue >> BIT_SHIFT));
        buffer.put((byte)(adderValue));
        return buffer.array();
    }

    private static int getMachineId() {
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
                    sb.append(bb.getChar());
                    sb.append(bb.getChar());
                    sb.append(bb.getChar());
                }
            }

            machinePiece = sb.toString().hashCode();
        } catch (Throwable var8) {
            machinePiece = (new SecureRandom()).nextInt();
            log.warn("Failed to get machine identifier from network interface, using random number instead", var8);
        }

        return machinePiece & MAX_VALUE;
    }

    private static short getProcessId() {
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

    @Override
    public int compareTo(final ObjectId other) {
        if (other == null) {
            throw new NullPointerException();
        } else {
            byte[] byteArray = this.toByte();
            byte[] otherByteArray = other.toByte();

            for(int i = 0; i < 12; ++i) {
                if (byteArray[i] != otherByteArray[i]) {
                    return (byteArray[i] & 255) < (otherByteArray[i] & 255) ? -1 : 1;
                }
            }

            return 0;
        }
    }
}