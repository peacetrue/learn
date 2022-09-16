package com.github.peacetrue.learn.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String input) {
        return input;
    }

    @Override
    public EchoRes echo(EchoReq input) {
        return new EchoRes(input.getInput());
    }

}
