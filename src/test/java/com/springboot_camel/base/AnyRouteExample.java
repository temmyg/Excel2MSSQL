package com.springboot_camel.base;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class AnyRouteExample extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        from("file:data?fileName=sampledata.xlsx&noop=true")
                .to("bean:excelConverterBean")
                .to("bean:jdbcTemplateBean");
    }
}

