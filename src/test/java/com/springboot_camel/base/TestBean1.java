package com.springboot_camel.base;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

public class TestBean1 {
    public String process(Exchange exchange) throws Exception{
        throw new Exception("Error 1");
    }
}
