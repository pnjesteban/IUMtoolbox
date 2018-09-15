package ium.tools.config;


public interface ConfigEntry{
	
	public void setName(String name);
	public String getName();
	
	public String getNodeType();
	
	public void addPropertyValue(String name,Object value);
	
	public Object getPropertyValue(String name);
		
	public void addChildren(ConfigEntry st);
	public ConfigEntry[] getChildrens();
	
}
