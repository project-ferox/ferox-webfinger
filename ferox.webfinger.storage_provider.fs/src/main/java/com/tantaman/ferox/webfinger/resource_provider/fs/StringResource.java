package com.tantaman.ferox.webfinger.resource_provider.fs;

import com.tantaman.ferox.webfinger.entry.IStaticWebfingerEntry;

public class StringResource implements IStaticWebfingerEntry {
	private final String content;
	
	public StringResource(String content) {
		this.content = content;
	}

	@Override
	public String getContents() {
		return this.content;
	}
}
