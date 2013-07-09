package com.tantaman.ferox.webfinger;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tantaman.ferox.api.router.IRouteHandler;
import com.tantaman.ferox.api.router.IRouteHandlerFactory;
import com.tantaman.ferox.api.router.IRouteInitializer;
import com.tantaman.ferox.api.router.IRouterBuilder;
import com.tantaman.ferox.webfinger.priv.IdentityHandler;

public class WebfingerInitializer implements IRouteInitializer {
	public static final Logger logger = Logger.getLogger(WebfingerInitializer.class.getName());
	
	private volatile IResourceProvider resourceProvider;
	
	public WebfingerInitializer() {
		logger.log(Level.INFO, "Initialized");
	}
	
	public void setResourceProvider(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}
	
	public void unsetResourceProvider() {
		this.resourceProvider = null;
	}
	
	public void addRoutes(IRouterBuilder routerBuilder) {
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
