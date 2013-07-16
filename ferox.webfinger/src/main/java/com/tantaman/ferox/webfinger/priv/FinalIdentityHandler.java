package com.tantaman.ferox.webfinger.priv;

import com.tantaman.ferox.api.request_response.IHttpContent;
import com.tantaman.ferox.api.request_response.IRequestChainer;
import com.tantaman.ferox.api.request_response.IResponse;
import com.tantaman.ferox.api.router.RouteHandlerAdapter;
import com.tantaman.ferox.webfinger.Serializer;
import com.tantaman.ferox.webfinger.entry.IDynamicWebfingerEntry;

public class FinalIdentityHandler extends RouteHandlerAdapter {
	private final Serializer serializer = new Serializer();
	
	@Override
	public void lastContent(IHttpContent content, IResponse response,
			IRequestChainer next) {
		IDynamicWebfingerEntry r = response.getUserData();
		response.send(serializer.serialize((IDynamicWebfingerEntry)r), "application/json"); //"application/jrd+json" remotestorage client is barfing on this type?
	}
}
