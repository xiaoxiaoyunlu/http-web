package com.zj.server.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseEncoder;
import java.io.IOException;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;
import com.zj.server.http.endpoint.DefaultEndpointFactory;
import com.zj.server.http.endpoint.EndpointFactory;
import com.zj.server.http.handler.HttpRequestHandler;
import com.zj.server.transform.Receiver;
import com.zj.server.transform.Transformer;

/**
 * 项目出口类，启动netty服务
 * 加载启动信息
 * @author USER
 *
 */
@Slf4j
public class HttpAcceptor {
	
	//最大重试次数
	private static final int MAX_RETRY = 20;
	//默认ip
	private String acceptIp = "0.0.0.0";
	//默认启动端口
	private int acceptPort = 8080;
	
	private int idleTime = 30;
	
	private ServerBootstrap bootstrap;
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private Channel channel;
	private Transformer<FullHttpRequest, Object> requestDecoder = null;
	private Transformer<Object, HttpResponse> responseEncoder = null;
	private EndpointFactory endpointFactory = new DefaultEndpointFactory();
    
	private int maxContentLength = 104857600;

	public HttpAcceptor() {
		this.bossGroup=new NioEventLoopGroup();
		this.workerGroup= new NioEventLoopGroup();
		this.bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
	}
	
	public void start()throws IOException{
		this.bootstrap.channel(NioServerSocketChannel.class)
		    .childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					
				   ChannelPipeline pipeline = ch.pipeline();
				   pipeline.addLast("encoder",new HttpResponseEncoder());
				   pipeline.addLast("decoder",new HttpRequestDecoder());
					pipeline.addLast("aggregator", new HttpObjectAggregator(maxContentLength));
					pipeline.addLast("handler",
							new HttpRequestHandler(endpointFactory,requestDecoder,responseEncoder));
				}
			})
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.option(ChannelOption.SO_BACKLOG, 10240)
			.childOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true))
			.childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true))
//			.childOption(ChannelOption.SO_TIMEOUT, Integer.valueOf(this.idleTime))
			.childOption(ChannelOption.SO_LINGER, Integer.valueOf(-1));
//			.childOption(ChannelOption.SO_SNDBUF, Integer.valueOf(-1));
		
		int retryCount = 0;
		boolean isBind = false;
		ChannelFuture future = null;
		do {
			try {
				future = bootstrap.bind(new InetSocketAddress(this.acceptIp, this.acceptPort)).sync();
//				 future.addListener(new ChannelFutureListener() {
//					
//					@Override
//					public void operationComplete(ChannelFuture future) throws Exception {
//                        if(future.isSuccess()){
//                        	log.info(" HttpAccept Channel bound success");
//                        }else{
//                        	log.error(" HttpAccept Channel attempt failed");
//                        	future.cause().printStackTrace();
//                        }						
//					}
//				});
				// 当通道关闭时继续向后执行，这是一个阻塞方法
				 channel = future.channel();
				// 监听服务器关闭监听
//				 future.channel().closeFuture().sync();
				isBind = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.warn("start failed : " + e + ", and retry...");
				
				try {
					Thread.sleep(30000L);
					retryCount++;
					if(retryCount>=MAX_RETRY){
						throw new Exception(e.getMessage());
					}
				} catch ( Exception e1) {
					e1.printStackTrace();
				}
				
			}finally{
				if (future != null && future.isSuccess()) {
	                log.info("Netty server listening " + acceptIp + " on port " + acceptPort + " and ready for connections...");
	            } else {
	            	log.error("Netty server start up Error!");
	            }

			}
		} while (!isBind);
		
		log.info("start succeed in " + this.acceptIp + ":" + this.acceptPort);
		
	}
	
	public void stop(){
		log.info("Shutdown Netty Server...");
        if(channel != null) { channel.close();}
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
		
	}
	
	

	public String getAcceptIp() {
		return acceptIp;
	}

	public void setAcceptIp(String acceptIp) {
		this.acceptIp = acceptIp;
	}

	public int getAcceptPort() {
		return acceptPort;
	}

	public void setAcceptPort(int acceptPort) {
		this.acceptPort = acceptPort;
	}

	public Transformer<FullHttpRequest, Object> getRequestDecoder() {
		return requestDecoder;
	}

	public void setRequestDecoder(Transformer<FullHttpRequest, Object> requestDecoder) {
		this.requestDecoder = requestDecoder;
	}

	public Transformer<Object, HttpResponse> getResponseEncoder() {
		return responseEncoder;
	}

	public void setResponseEncoder(Transformer<Object, HttpResponse> responseEncoder) {
		this.responseEncoder = responseEncoder;
	}

	public int getMaxContentLength() {
		return maxContentLength;
	}

	public void setMaxContentLength(int maxContentLength) {
		this.maxContentLength = maxContentLength;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}
	
	public void setMessageClosure(Receiver messageClosure) {
		this.endpointFactory.setCourse(messageClosure);
	}
}
