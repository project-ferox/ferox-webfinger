package com.tantaman.ferox.webfinger.resource_provider.blank;

import java.util.Map;

import com.tantaman.ferox.webfinger.IResourceProvider;
import com.tantaman.ferox.webfinger.entry.IWebfingerEntry;

public class BlankResourceProvider implements IResourceProvider {
	@Override
	public void setConfiguration(Map<Object, Object> configuration) {
		
	}

	@Override
	public IWebfingerEntry getIdentity(String identity) {
		return new WebfingerEntry(identity);
	}
}
