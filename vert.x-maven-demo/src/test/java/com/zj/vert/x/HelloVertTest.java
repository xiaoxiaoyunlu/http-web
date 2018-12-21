package com.zj.vert.x;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class HelloVertTest {

	
	private Vertx vertx;
	
	@Before
	public void setUp(TestContext context){
		vertx = Vertx.vertx();
		vertx.deployVerticle(HelloVert.class.getName(),context.asyncAssertSuccess());
	}
	
	@After
	public void tearDown(TestContext context){
		vertx.close(context.asyncAssertSuccess());
	}
	
	@Test
	public void testHello(TestContext context){
		Async async = context.async();
		vertx.createHttpClient().getNow(8881,"localhost","/",response -> {
			response.handler(body -> {
				TestContext assertTrue = context.assertTrue(body.toString().contains("hello"));
				String result="";
				result = body.toString().contains("hello")?"111111111":"0000000000";
				System.out.println(result);
				async.complete();
			});
		});
	}
}
