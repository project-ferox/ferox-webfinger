package com.tantaman.ferox.webfinger.priv;

import io.netty.handler.codec.http.HttpResponseStatus;

import com.tantaman.ferox.api.IHttpContent;
import com.tantaman.ferox.api.IRequestChainer;
import com.tantaman.ferox.api.IResponse;
import com.tantaman.ferox.api.RouteHandlerAdapter;
import com.tantaman.ferox.webfinger.IResource;
import com.tantaman.ferox.webfinger.IResourceProvider;

public class IdentityHandler extends RouteHandlerAdapter {
	private final IResourceProvider resourceProvider;
	
	public IdentityHandler(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}

	@Override
	public void lastContent(IHttpContent content, IResponse response,
			IRequestChainer next) {
		String resource = content.getQueryParam("resource").get(0);
		
		try {
			IResource r = resourceProvider.getIdentity(resource);
			if (r == null) {
				response.send("Not found", HttpResponseStatus.NOT_FOUND);
			} else {
				response.send(r.getContents(), r.getContentType());
			}
		} catch (Exception e) {
			response.send("Internal server error", HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
