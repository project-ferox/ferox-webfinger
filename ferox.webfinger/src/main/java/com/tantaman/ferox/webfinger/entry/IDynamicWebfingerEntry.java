package com.tantaman.ferox.webfinger.entry;

import java.util.List;
import java.util.Map;

public interface IDynamicWebfingerEntry extends IWebfingerEntry {
	public String getSubject();
	public void setSubject(String subject);
	public List<String> getAliases();
	public Map<String, Object> getProperties();
	public List<Map<String,Object>> getLinks();
}
