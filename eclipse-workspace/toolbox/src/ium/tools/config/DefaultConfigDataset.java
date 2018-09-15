package ium.tools.config;

import java.util.ArrayList;
import java.util.Hashtable;

import ium.tools.data.GlobalScopeManager;


public abstract class DefaultConfigDataset implements ConfigEntry{
    
    
	String name;
	private Hashtable<String, Object> properties=new Hashtable<String, Object>();
	
	private ArrayList<ConfigEntry> childrens=new ArrayList<ConfigEntry>();
    private String externalParams="";
	
	public void addPropertyValue(String name,Object value){
		properties.put(name, value);
	}
	
	public Object getPropertyValue(String name){
	    return GlobalScopeManager.instance().translate((String)properties.get(name),getExternalParams());
	}
	
	public ConfigEntry[] getChildrens(){	
		return childrens.toArray(new ConfigEntry[0]);
	}
	
	public void addChildren(ConfigEntry st){
        childrens.add(st);
    }	
		
	public DefaultConfigDataset(){}
	
	public DefaultConfigDataset(String name){
		this.name=name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName() {		
		return name;
	}
	
	public static ConfigEntry instanceInfo(String nodeType){
		try{
			return (ConfigEntry)Class.forName(nodeType).newInstance();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public String getNodeType(){
		return getClass().getSimpleName();
	}
	
	public String toString(){
		return getName();
	}
	
	public String[] getExternalParams() {
	    
	    if(externalParams!=null){
	    
	        String[] params=externalParams.split(".\\|");
	        return params;
	    }
	    return null;
	}


	public void setExternalParams(String externalParams) {
	    this.externalParams = externalParams;
	}
	
}
