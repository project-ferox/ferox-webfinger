package com.tantaman.ferox.webfinger.example.standalone;

import org.osgi.service.component.ComponentContext;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import com.tantaman.ferox.api.IChannelHandlerFactory;
import com.tantaman.ferox.api.IFeroxFactories;
import com.tantaman.ferox.api.IFeroxServer;
import com.tantaman.ferox.api.IFeroxServerBuilder;
import com.tantaman.ferox.api.IFeroxServerFactories;
import com.tantaman.ferox.api.IRouterBuilder;
import com.tantaman.ferox.webfinger.IResourceProvider;
import com.tantaman.ferox.webfinger.WebfingerInitializer;
import com.tantaman.lo4j._;

public class ExampleServer {
	private IFeroxFactories feroxFactories;
	private IFeroxServerFactories serverFactories;
	private IResourceProvider webfingerResourceProvider;
	
	public void setFeroxFactories(IFeroxFactories feroxFactories) {
		this.feroxFactories = feroxFactories;
	}
	
	public void setServerFactories(IFeroxServerFactories serverFactories) {
		this.serverFactories = serverFactories;
	}
	
	public void setWebfingerResourceProvider(IResourceProvider resourceProvider) {
		webfingerResourceProvider = resourceProvider;
	}

	public void activate(ComponentContext context) {
		System.out.println("ACTIVATED");
		IFeroxServerBuilder b = serverFactories.createServerBuilder();

		b.port(8082);
		b.use("decoder", new IChannelHandlerFactory() {

			@Override
			public ChannelHandler create() {
				return (ChannelHandler) new HttpRequestDecoder();
			}
		});

		b.use("encoder", new IChannelHandlerFactory() {

			@Override
			public ChannelHandler create() {
				return (ChannelHandler) new HttpResponseEncoder();
			}
		});

		// now add Ferox
		IRouterBuilder routerBuilder = feroxFactories.createRouterBuilder();
		
		webfingerResourceProvider.setConfiguration(_.createMap(new String [] {
				"contentType", "application/json",
				"metaRoot", "/webfingertest/.well-known",
				"indentityRoot", "/webfingertest/identities"
		}));
		WebfingerInitializer init = new WebfingerInitializer(webfingerResourceProvider);
		init.addWebfinger("/.well-known", "/identities", routerBuilder);
		b.use("ferox", feroxFactories.createFeroxChannelHandlerFactory(routerBuilder.build()));
		
		IFeroxServer server = b.build();
		try {
			server.runInCurrentThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void deactivate(ComponentContext context) {
		
	}
}
