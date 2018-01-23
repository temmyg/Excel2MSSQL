package com.springboot_camel.base;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class Excel2DBRouter extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("log:output?level=ERROR&" +
                "showCaughtException=true&multiline=true&showStackTrace=true"));
        onException(java.lang.Exception.class).handled(true);
        from("file:data" + "?noop=true") //idempotent=false&charset=utf-8
                .routeId("subscriber")
                //--.aggregate(simple("${file:ext} == 'csv'"),new GroupedExchangeAggregationStrategy())
                //--.completionSize(2)
                //TODO: what should be the timeout period?
                //.completionTimeout(1000)
                //.pollEnrich().simple("file:data?include=mostspace.txt")
                .to("bean:excelConverterBean");


        //from("file:data?fileName=sampledata.xlsx&noop=true")
//        from("file:data?fileName=sampledata.xlsx")
//        //        from("direct:start")
//                .to("bean:excelConverterBean");
              //  .to("bean:jdbcTemplateBean");
    }
}
