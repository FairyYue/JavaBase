package apiFramework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Use synchronized for method.
 * Others: volatile, or currentHashMap
 */
public class DWRServiceCache {
	
	private static HashMap<String, DWRBaseService> map = new HashMap<String, DWRBaseService>();
	
	public synchronized static DWRBaseService get(String identifier) 
	{
		if (map.containsKey(identifier))
		{
			return map.get(identifier);
		}
		
		return null;
	}
	
	public synchronized static void put(String identifier, DWRBaseService service) 
	{
		map.put(identifier, service);
	}
	
	/**
	 * This is to remove all cached user credentials by a company
	 * Typically it is called in before class of a test base to make cache only visible to the test case scope
	 * @param company
	 */
	public synchronized static void clearCacheByCompany(String company)
	{
		if (company==null || company.equalsIgnoreCase(""))
		{
			return;
		}
		
		List<String> keysToRemove = new ArrayList<String>();
		
		for(String key : map.keySet())
		{
			if (key.endsWith("_" + company))
			{
				keysToRemove.add(key);
			}
		}
		
		for (String item : keysToRemove)
		{
			map.remove(item);
		}
	}
 
}