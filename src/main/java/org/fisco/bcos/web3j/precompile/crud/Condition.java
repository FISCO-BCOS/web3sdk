package org.fisco.bcos.web3j.precompile.crud;

import java.util.HashMap;
import java.util.Map;

public class Condition {
	
	private Map<String, Map<EnumOP, String>> conditions = new HashMap<>();
  private int offset = 0;
  private int count = 0;
	
	public void EQ(String key, String value)
	{
		HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
		map.put(EnumOP.eq, value);
		conditions.put(key, map);
	}
	
	public void NE(String key, String value)
	{
		HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
		map.put(EnumOP.ne, value);
		conditions.put(key, map);
	}
	
	public void GT(String key, String value)
	{
		HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
		map.put(EnumOP.gt, value);
		conditions.put(key, map);
	}
	
	public void GE(String key, String value)
	{
		HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
		map.put(EnumOP.ge, value);
		conditions.put(key, map);
	}
	
	public void LT(String key, String value)
	{
		HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
		map.put(EnumOP.lt, value);
		conditions.put(key, map);
	}
	
	public void LE(String key, String value)
	{
		HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
		map.put(EnumOP.le, value);
		conditions.put(key, map);
	}
	
	public void Limit(int count)
	{
		this.count = count;
	}
	
	public void Limit(int offset, int count)
	{
	  this.offset = offset;
		this.count = count;
	}

	public Map<String, Map<EnumOP, String>> getConditions() {
		return conditions;
	}

	public int getOffset() {
		return this.offset;
	}

	public int getCount() {
		return this.count;
	}

}
