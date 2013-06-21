package com.tantaman.ferox.webfinger;

import com.tantaman.ferox.api.IHttpContent;
import com.tantaman.ferox.api.IRequestChainer;
import com.tantaman.ferox.api.IResponse;
import com.tantaman.ferox.api.IRouteHandler;
import com.tantaman.ferox.api.IRouteHandlerFactory;
import com.tantaman.ferox.api.IRouterBuilder;
import com.tantaman.ferox.api.RouteHandlerAdapter;
import com.tantaman.ferox.webfinger.priv.IdentityHandler;

public class WebfingerInitializer {
	private final IResourceProvider resourceProvider;
	
	public WebfingerInitializer(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}
	
	public void addWebfinger(IRouterBuilder routerBuilder) {
		final RouteHandlerAdapter metaHandler = new RouteHandlerAdapter() {
			@Override
			public void lastContent(IHttpContent content,
					IResponse response, IRequestChainer next) {
				IResource meta = resourceProvider.getMeta();
				response.send(meta.getContents(), meta.getContentType());
			}
		};
		
		routerBuilder.get("/.well-known/host-meta", new IRouteHandlerFactory() {
			@Override
			public IRouteHandler create() {
				return metaHandler;
			}
		});
		
		routerBuilder.get("/.well-known/webfinger", new IRouteHandlerFactory() {
			@Override
			public IRouteHandler create() {
				return new IdentityHandler(resourceProvider);
			}
		});
	}
}
