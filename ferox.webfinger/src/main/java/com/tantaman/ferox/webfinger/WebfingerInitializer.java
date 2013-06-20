package com.tantaman.ferox.webfinger;

import com.tantaman.ferox.api.IRouterBuilder;

public class WebfingerInitializer {
	private final IResourceProvider resourceProvider;
	
	public WebfingerInitializer(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}
	
	public void addWebfinger(String metaLocation, String identityRoot, IRouterBuilder routerBuilder) {
		
	}
}
