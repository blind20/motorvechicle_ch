package com.vechicle.util;

import java.io.InputStream;
import java.util.Properties;

public class MyPropertiesUtil {

	public static Properties getProperties(){
		Properties pros = new Properties();
		try {
			InputStream in = MyPropertiesUtil.class.
					getResourceAsStream("/assets/settings.properties");
			pros.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pros;
	}
	
}
