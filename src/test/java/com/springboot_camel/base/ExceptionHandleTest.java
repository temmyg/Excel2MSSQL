package com.springboot_camel.base;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.camel.util.jndi.JndiContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.Context;

public class ExceptionHandleTest extends ContextTestSupport {

    private ThrowExceptionBean throwExceptionBean = new ThrowExceptionBean();

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            // START SNIPPET: e1
            public void configure() throws Exception {

                errorHandler(deadLetterChannel("log:deadletterchannel?level=INFO"));
                // tell Camel to handle and continue when this exception is thrown
                //onException(IndexOutOfBoundsException.class).continued(true);

                from("direct:start")
                        .to("mock:middle")
                        //.to("bean:throwExceptionBean?method=throwException");
                        .throwException(new IllegalArgumentException("Forced"))
                        .to("mock:result");
            }
            // END SNIPPET: e1
        };
    }

    public void testErrorCallerMessage() throws Exception{
        MockEndpoint ep = getMockEndpoint("mock:result");
        ep.setExpectedCount(0);
       Object result =  template.requestBody("direct:start", "Hello World");

       assertMockEndpointsSatisfied();
       assertIsInstanceOf(IllegalArgumentException.class, result);
    }

    @Override
    protected Context createJndiContext() throws Exception {
        JndiContext answer = new JndiContext();
        answer.bind("throwExceptionBean", throwExceptionBean);
        return answer;
    }

    public static class ThrowExceptionBean {
        public void throwException(Exchange exchange) throws IllegalArgumentException{
            throw new IllegalArgumentException();

        }
    }
}
