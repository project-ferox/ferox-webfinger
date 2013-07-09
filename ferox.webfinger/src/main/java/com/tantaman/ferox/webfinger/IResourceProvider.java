package com.tantaman.ferox.webfinger;

import java.util.Map;

import com.tantaman.ferox.webfinger.entry.IWebfingerEntry;

public interface IResourceProvider {
	public void setConfiguration(Map<Object, Object> configuration);
	public IWebfingerEntry getMeta();
	public IWebfingerEntry getIdentity(String identity);
}
