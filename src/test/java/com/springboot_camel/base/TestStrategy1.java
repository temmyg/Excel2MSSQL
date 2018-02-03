package com.springboot_camel.base;


import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class TestStrategy1 implements AggregationStrategy {
    public Exchange aggregate(Exchange original, Exchange resource) {
        Object originalBody = original.getIn().getBody();
        Object resourceResponse = resource.getIn().getBody();
//        Object mergeResult = null;
//        // combine original body and resource response
//        if (original.getPattern().isOutCapable()) {
//            original.getOut().setBody(mergeResult);
//        } else {
//            original.getIn().setBody(mergeResult);
//        }
        return original;
    }
}

