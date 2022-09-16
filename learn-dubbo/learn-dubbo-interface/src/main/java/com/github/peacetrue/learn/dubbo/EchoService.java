package com.github.peacetrue.learn.dubbo;

/**
 * 回声服务。
 *
 * @author peace
 **/
public interface EchoService {

    /**
     * 返回输入内容。
     *
     * @param input 输入内容
     * @return 输入内容
     */
    String echo(String input);

    /**
     * 返回输入内容。
     *
     * @param input 输入内容
     * @return 输入内容
     */
    EchoRes echo(EchoReq input);
}
