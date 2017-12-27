package com.springboot_camel.base;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class Excel2DBRouter extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        from("file:data?fileName=sampledata.xlsx")
                .to("bean:excelConverterBean")
                .to("bean:jdbcTemplateBean");
    }
}
