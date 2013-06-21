package com.tantaman.ferox.webfinger.priv;

import com.tantaman.ferox.api.IHttpContent;
import com.tantaman.ferox.api.IRequestChainer;
import com.tantaman.ferox.api.IResponse;
import com.tantaman.ferox.api.RouteHandlerAdapter;

public class IdentityHandler extends RouteHandlerAdapter {
	@Override
	public void lastContent(IHttpContent content, IResponse response,
			IRequestChainer next) {
		
	}
}
