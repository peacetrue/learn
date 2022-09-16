package com.github.peacetrue.learn.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

/**
 * @author peace
 **/
@Slf4j
@RestController
@RequestMapping("/echo")
public class EchoController {

    @DubboReference
    private EchoService echoService;

    @GetMapping
    public String echo(@RequestParam(defaultValue = "empty") String input) {
        log.info("echo {}", input);
        return echoService.echo(input);
    }

    @PostMapping
    public EchoRes echo(EchoReq input) {
        log.info("echo {}", input);
        return echoService.echo(input);
    }

}
