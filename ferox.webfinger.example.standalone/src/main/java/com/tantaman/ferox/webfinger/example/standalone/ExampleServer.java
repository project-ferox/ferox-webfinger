package com.tantaman.ferox.webfinger.example.standalone;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class ExampleServer {
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		Configuration config;
		try {
			config = configAdmin.createFactoryConfiguration("ferox.SimpleFilesystemResourceProvider");
			Dictionary<String, Object> dict = config.getProperties();
			
			if (dict == null)
				dict = new Hashtable<>();
			
			dict.put("contentType", "application/jrd+json");
			dict.put("identityRoot", "resources/webfinger/identities");
			
			config.update(dict);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void activate() {
		
	}
}