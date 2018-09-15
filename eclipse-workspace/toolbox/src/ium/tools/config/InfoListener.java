package ium.tools.config;

public interface InfoListener {
	
	public static final String RELOAD_EVENT="RELOAD";
		
	public void onChange(String event);
	
}
