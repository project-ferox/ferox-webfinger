package com.tantaman.ferox.webfinger.resource_provider.blank;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tantaman.ferox.webfinger.entry.IDynamicWebfingerEntry;

public class WebfingerEntry implements IDynamicWebfingerEntry {
	private String subject;
	private final List<String> aliases;
	private final Map<String, String> properties;
	private final List<Map<String, Object>> links;
	
	public WebfingerEntry(String subject) {
		this.subject = subject;
		
		aliases = new ArrayList<>();
		properties = new LinkedHashMap<>();
		links = new ArrayList<>();
	}
	
	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public Map<String, String> getProperties() {
		return properties;
	}

	@Override
	public List<Map<String, Object>> getLinks() {
		return links;
	}

}
