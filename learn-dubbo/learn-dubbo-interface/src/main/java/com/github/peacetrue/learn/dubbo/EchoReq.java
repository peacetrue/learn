package com.github.peacetrue.learn.dubbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author peace
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EchoReq implements Serializable {
    private static final long serialVersionUID = 8226943247048112247L;
    private String input;
}
