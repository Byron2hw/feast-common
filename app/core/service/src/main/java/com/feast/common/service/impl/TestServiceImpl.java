package com.feast.common.service.impl;

import com.feast.common.model.TestBO;
import com.feast.common.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author Byron
 * @date 2022/12/12 3:11 下午
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        TestBO testBO = new TestBO();
        testBO.setName("testBO");
        return testBO.toString();
    }
}
