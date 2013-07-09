package com.tantaman.ferox.webfinger.entry;


public interface IStaticWebfingerEntry extends IWebfingerEntry {
	
	public String getContents();
	
	public static final IWebfingerEntry ILLEGAL = new IStaticWebfingerEntry() {
		public String getContents() {
			return "{\"status\": \"ILLEGAL\"}";
		}
	};
}
