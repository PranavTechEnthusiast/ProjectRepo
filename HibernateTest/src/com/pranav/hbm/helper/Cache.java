package com.pranav.hbm.helper;

import java.util.HashMap;
import java.util.Map;

public class Cache {

	//Cache Map for real time cache
	private static Map<String,Map> cache = new HashMap<String,Map>();

	public static Map<String, Map> getCache() {
		return cache;
	}

	
}
