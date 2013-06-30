package com.tantaman.ferox.webfinger.priv;

import java.util.logging.Level;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

import com.tantaman.ferox.api.request_response.IHttpContent;
import com.tantaman.ferox.api.request_response.IRequestChainer;
import com.tantaman.ferox.api.request_response.IResponse;
import com.tantaman.ferox.api.router.RouteHandlerAdapter;
import com.tantaman.ferox.webfinger.IResource;
import com.tantaman.ferox.webfinger.IResourceProvider;
import com.tantaman.ferox.webfinger.WebfingerInitializer;

public class IdentityHandler extends RouteHandlerAdapter {
	private final IResourceProvider resourceProvider;
	
	public IdentityHandler(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}

	@Override
	public void lastContent(IHttpContent content, IResponse response,
			IRequestChainer next) {
		String resource = content.getQueryParam("resource").get(0);
		WebfingerInitializer.logger.log(Level.INFO, "Retrieving identity: " + resource);
		response.headers().set(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		try {
			if (resourceProvider == null) {
				send404(response);
				return;
			}
			IResource r = resourceProvider.getIdentity(resource);
			if (r == null) {
				send404(response);
			} else {
				response.send(r.getContents(), r.getContentType());
			}
		} catch (Exception e) {
			response.send("Internal server error", HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void send404(IResponse response) {
		response.send("Not found", HttpResponseStatus.NOT_FOUND);
	}
}
