package com.tantaman.ferox.webfinger.resource_provider.fs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import com.tantaman.ferox.webfinger.IResource;
import com.tantaman.ferox.webfinger.IResourceProvider;

public class SimpleFilesystemResourceProvider implements IResourceProvider {
	private String identityRoot;
	private IResource meta;
	private String contentType;
	
	public SimpleFilesystemResourceProvider() {
	}
	
	public void setConfiguration(Map<Object, Object> configuration) {
		String metaRoot = (String)configuration.get("metaRoot");
		contentType = (String)configuration.get("contentType");
		identityRoot = (String)configuration.get("identityRoot");
		
		File f = new File(metaRoot);
		String m = readFully(f);
		if (m == null) m = "";
		
		meta = new StringResource(contentType, m);
	}
	
	@Override
	public IResource getIdentity(String path) {
		path = path.replace("..", "");
		File f = new File(identityRoot + "/" + path);
		if (!f.getAbsolutePath().startsWith(identityRoot))
			return IResource.ILLEGAL;
		
		String ident = readFully(f);
		
		if (ident == null) {
			return null;
		} else {
			return new StringResource(contentType, ident);
		}
	}
	
	private String readFully(File f) {
		Scanner scan = null;
		try {
			scan = new Scanner(f);
			scan.useDelimiter("\\Z");
			return scan.next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (scan != null)
				scan.close();
		}
	}
	
	@Override
	public IResource getMeta() {
		return meta;
	}
}
