package com.tantaman.ferox.webfinger;

public interface IResource {
	public String getContentType();
	public String getContents();
	
	public static final IResource ILLEGAL = new IResource() {
		
		@Override
		public String getContents() {
			return "Illegal";
		}
		
		@Override
		public String getContentType() {
			return "application/json; charset=UTF-8";
		}
	};
}
