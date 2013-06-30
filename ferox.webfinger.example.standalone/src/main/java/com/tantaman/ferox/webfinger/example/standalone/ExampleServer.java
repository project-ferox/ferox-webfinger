package com.tantaman.ferox.webfinger.example.standalone;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.service.component.ComponentContext;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import com.tantaman.ferox.api.IChannelHandlerFactory;
import com.tantaman.ferox.api.IFeroxFactories;
import com.tantaman.ferox.api.router.IRouterBuilder;
import com.tantaman.ferox.api.server.IFeroxServer;
import com.tantaman.ferox.api.server.IFeroxServerBuilder;
import com.tantaman.ferox.api.server.IFeroxServerFactories;
import com.tantaman.ferox.webfinger.IResourceProvider;
import com.tantaman.ferox.webfinger.WebfingerInitializer;
import com.tantaman.lo4j.Lo;

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
		IFeroxServerBuilder b = serverFactories.createServerBuilder();
		
		Logger logger = Logger.getLogger(WebfingerInitializer.class.getName());
		logger.setLevel(Level.INFO);

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
		
		webfingerResourceProvider.setConfiguration(Lo.createMap(new String [] {
				"contentType", "application/json",
				"metaPath", "resources/webfinger/meta.json",
				"identityRoot", "resources/webfinger/identities"
		}));
		WebfingerInitializer init = new WebfingerInitializer();
		init.setResourceProvider(webfingerResourceProvider);
		init.addRoutes(routerBuilder);
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
