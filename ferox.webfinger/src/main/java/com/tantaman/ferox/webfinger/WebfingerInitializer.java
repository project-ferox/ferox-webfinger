package com.tantaman.ferox.webfinger;

import io.netty.handler.codec.http.HttpHeaders;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tantaman.ferox.api.request_response.IHttpContent;
import com.tantaman.ferox.api.request_response.IRequestChainer;
import com.tantaman.ferox.api.request_response.IResponse;
import com.tantaman.ferox.api.router.IRouteHandler;
import com.tantaman.ferox.api.router.IRouteHandlerFactory;
import com.tantaman.ferox.api.router.IRouteInitializer;
import com.tantaman.ferox.api.router.IRouterBuilder;
import com.tantaman.ferox.api.router.RouteHandlerAdapter;
import com.tantaman.ferox.webfinger.priv.IdentityHandler;

public class WebfingerInitializer implements IRouteInitializer {
	public static final Logger logger = Logger.getLogger(WebfingerInitializer.class.getName());
	
	private final IResourceProvider resourceProvider;
	
	public WebfingerInitializer(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
		logger.log(Level.INFO, "Initialized");
	}
	
	public void addRoutes(IRouterBuilder routerBuilder) {
		final RouteHandlerAdapter metaHandler = new RouteHandlerAdapter() {
			@Override
			public void lastContent(IHttpContent content,
					IResponse response, IRequestChainer next) {
				logger.log(Level.INFO, "Received last content");
				IResource meta = resourceProvider.getMeta();
				response.headers().set(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
				response.send(meta.getContents(), meta.getContentType());
			}
		};
		
		routerBuilder.get("/.well-known/host-meta", new IRouteHandlerFactory() {
			@Override
			public IRouteHandler create() {
				logger.log(Level.INFO, "Creating host-meta handler");
				return metaHandler;
			}
		});
		
		routerBuilder.get("/.well-known/webfinger", new IRouteHandlerFactory() {
			@Override
			public IRouteHandler create() {
				logger.log(Level.INFO, "Returning identity handler");
				return new IdentityHandler(resourceProvider);
			}
		});
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
