package com.zj.vert.x;

import org.junit.internal.runners.statements.Fail;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class HelloVert extends AbstractVerticle {

	@Override
	public void start(Future<Void> future) throws Exception {
		// TODO Auto-generated method stub
		HttpServer server = vertx.createHttpServer();
		server.requestHandler(req -> {
			HttpServerResponse response = req.response();
			response.putHeader("content-type", "text/html");
			response.end("hello vert.x");
		});
		server.listen(8881,result -> {
			if(result.succeeded()){
				future.complete();
			}else{
				future.fail(result.cause());
			}
		});
	}


}
