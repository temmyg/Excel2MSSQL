package com.springboot_camel.base;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Excel2DBRouter extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("log:output?level=ERROR&" +
                "showCaughtException=true&multiline=true&showStackTrace=true"));

        //from("file:data?fileName=sampledata.xlsx&noop=true")
        //from("file:data?fileName=sampledata.xlsx")
                from("direct:start")
                .to("bean:excelConverterBean");
              //  .to("bean:jdbcTemplateBean");
    }
}
