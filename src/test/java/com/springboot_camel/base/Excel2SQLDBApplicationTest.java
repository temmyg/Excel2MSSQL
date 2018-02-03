package com.springboot_camel.base;

import ch.qos.logback.classic.pattern.MethodOfCallerConverter;
import com.springboot_camel.base.excel.ExcelConverterBean;
import com.springboot_camel.base.excel.SheetHolder;
import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileConsumer;
import org.apache.camel.component.file.FileEndpoint;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
public class Excel2SQLDBApplicationTest
		 /* extends CamelTestSupport */ {

	public Excel2SQLDBApplicationTest(){
		int i = 0;
		System.out.println("From Test Class ..." + i);
	}

	@Autowired
	private CamelContext camelContext;

	@EndpointInject(uri = "mock:resultA")
	protected MockEndpoint mockEndpointA;

	@EndpointInject(uri = "mock:resultB")
	protected MockEndpoint mockEndpointB;

	@Produce(uri="mock:resultC", context = "camelContext")
	protected MockEndpoint mockEndpointC;

	@Produce(uri="mock:resultD", context = "camelContext")
	protected MockEndpoint mockEndpointD;

	@Produce(uri="direct:startA", context="camelContext")
	protected ProducerTemplate startEndpointA;

	@Produce(uri="file:data?fileName=sampledata.xlsx", context="camelContext")
	protected FileEndpoint startEndpointB;

	@Produce(uri="direct:startD")
	protected ProducerTemplate startD;

	@Test
	public void basicTest() throws Exception {
		mockEndpointA.setExpectedMessageCount(1);
	//	mockEndpointA.expectedBodiesReceived("HelloWorld");

		Object response = startEndpointA.requestBody("HelloWorld 1");

		//startEndpointA.sendBody("HelloWorld 1");
		// startEndpointA.sendBody("HelloWorld 2");
		//List<Exchange> ex = mockEndpointA.getExchanges();

		//MockEndpoint.assertIsSatisfied(camelContext);
		mockEndpointA.assertIsSatisfied();
	}

	@Test
	public void HandledResponseToCalledTest() throws Exception{

		//mockEndpointA.setExpectedMessageCount(1);
		camelContext.startRoute("expceptionHandledRoute");
		ProducerTemplate pt = camelContext.createProducerTemplate();
		Object response = pt.requestBody("direct:start1", "HelloWorld 1");

		assertEquals("Error Happened", response.toString());

	}

	@Test
	public void convertedExcelBodyTest_2() throws Exception {
		camelContext.startRoute("RouteD");

		File testFile = new File("data/sampledata.xlsx");
		FileInputStream fis = new FileInputStream(testFile);
		BufferedInputStream bis = new BufferedInputStream(fis);

		mockEndpointD.setExpectedCount(1);
		startD.sendBody(bis);

		mockEndpointD.assertIsSatisfied();

		Object body = mockEndpointD.getExchanges().get(0).getIn().getBody();

		assertThat(body, instanceOf(SheetHolder.class));
		assertNotNull(((SheetHolder)body).sheetData);
		assertThat(((SheetHolder)body).sheetData, instanceOf(ArrayList.class));
		ArrayList sheetData = ((SheetHolder)body).sheetData;
		assertEquals(2, sheetData.size());

		camelContext.stopRoute("RouteD");

		fis.close();
		bis.close();
	}

	/*
	 * RouteD
	 */
	@Test
	public void convertedExcelBodyTest_1() throws Exception {
		mockEndpointC.expectedMessageCount(1);

		SheetHolder expectedData = new SheetHolder();
		//expectedData.sheetData = new ArrayList<HashMap<String, Object>>();

		//mockEndpointC.expectedBodiesReceived(expectedData);
		camelContext.startRoute("RouteC");
		mockEndpointC.assertIsSatisfied();

		Object body = mockEndpointC.getExchanges().get(0).getIn().getBody();
		assertThat(body, instanceOf(SheetHolder.class));
		camelContext.stopRoute("RouteC");
	}

	/*
	 *	RouteB
	 */
	@Test
	public void excelFileReceivedTest() throws Exception {

		mockEndpointB.setExpectedMessageCount(2);
		mockEndpointB.expectedFileExists("data/sampledata.xlsx");

//		camelContext.getRouteDefinition("RouteB").adviceWith(camelContext, new AdviceWithRouteBuilder() {
//			@Override
//			public void configure() throws Exception {
//				interceptSendToEndpoint("mock:resultB").process(new MyProcessor());
//			}});

		camelContext.startRoute("RouteB");

//		camelContext.createProducerTemplate().sendBody("direct:startB", "Hello 123");

		mockEndpointB.assertIsSatisfied();

		List<Exchange> ex = mockEndpointB.getExchanges();
		camelContext.stopRoute("RouteB");
	}

	class MyProcessor implements Processor {
		@Override
		public void process(Exchange exchange) {
			int i;
			System.out.println("**********" + exchange.getIn());
			Message inMessage = exchange.getIn();
			exchange.setOut(inMessage);
			//exchange.getFromEndpoint().createProducer().process();
		}
	}


	//	@Override
//    protected RouteBuilder createRouteBuilder() throws Exception {
//        return new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("direct:start")
//                    .bean(ExcelConverterBean.class, "readExcelSheets");
//            }
//        };
//
}
