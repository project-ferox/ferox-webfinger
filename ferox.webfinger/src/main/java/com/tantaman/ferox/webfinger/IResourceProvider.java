package com.tantaman.ferox.webfinger;

import java.util.Map;

public interface IResourceProvider {
	public void setConfiguration(Map<Object, Object> configuration);
	public IResource getMeta();
	public IResource getIdentity(String path);
}
