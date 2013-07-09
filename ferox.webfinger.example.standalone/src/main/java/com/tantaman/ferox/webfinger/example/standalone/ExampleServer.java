package com.tantaman.ferox.webfinger.example.standalone;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class ExampleServer {
	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		Configuration config;
		try {
			config = configAdmin.getConfiguration("ferox.webfinger.SimpleFilesystemResourceProvider");
			Dictionary<String, Object> dict = config.getProperties();
			
			if (dict == null)
				dict = new Hashtable<>();
			
			dict.put("identityRoot", "resources/webfinger/identities");
			
			config.update(dict);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void activate() {
		
	}
}