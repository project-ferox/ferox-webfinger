package com.tantaman.ferox.webfinger.resource_provider.fs;

import com.tantaman.ferox.webfinger.entry.IStaticWebfingerEntry;

public class StringResource implements IStaticWebfingerEntry {
	private final String content;
	private final String contentType;
	
	public StringResource(String contentType, String content) {
		this.contentType = contentType;
		this.content = content;
	}

	@Override
	public String getContents() {
		return this.content;
	}
}
