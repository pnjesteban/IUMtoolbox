package ium.tools.config.info;

import ium.tools.config.DefaultConfigDataset;


public class Host extends DefaultConfigDataset{

	String hostnamePropName="hostname";
	String portPropName="port";
	String usernamePropName="username";
	String passwordPropName="password";
	String adminAgentPidPropName="adminAgentPid";
	String IUMInstancePropName="IUMInstance";
	String CollectorPrefixPropName="CollectorPrefix";
	String homePropName="home";
	
	
	//String collectorPrefix="a";
	
	String IUMHome="/opt/${IUMInstance}/bin/";
	String defaultAppHome="${HOME}/.medctrl/";

	//SortedMap<String, CollectorInfo> collectors = new TreeMap<String, CollectorInfo>();
	
	public Host() {}
	
	public Host(String name) {
		super(name);
	}
		
	public void load(String hostname, String port, String username,String password,String IUMInstance) 
	{
		setHostname(hostname);
		setPort(port);
		setUsername(username);
		setPassword(password);
		setIUMInstance(IUMInstance);
	}

	protected void setHostname(String hostName) 
	{
		addPropertyValue(hostnamePropName, hostName);
	}
	
	public String getHostname() 
	{
		return (String)getPropertyValue(hostnamePropName);
	}

	protected void setPort(String port) 
	{
		addPropertyValue(portPropName, port);
	}
	
	public int getPort() 
	{
		return Integer.parseInt((String)getPropertyValue(portPropName));
	}
	
	protected void setUsername(String userName) 
	{
		addPropertyValue(usernamePropName, userName);
	}
	
		
	public String getUserName()
	{
		return (String)getPropertyValue(usernamePropName);
	}
	
	protected void setPassword(String password) 
	{
		addPropertyValue(passwordPropName, password);
	}
	
	public String getPassword()
	{
		return (String)getPropertyValue(passwordPropName);
	}
	/*
	public User getUser()
	{
		return new User(getUserName(),getPassword());
	}*/
	
	
	public String getHome() 
    {
        return (String)getPropertyValue(homePropName);
    }
	
	public String getIUMInstance() {
		return (String)getPropertyValue(IUMInstancePropName);
	}

	public void setIUMInstance(String iUMInstance) {
		addPropertyValue(IUMInstancePropName,iUMInstance);		
	}
	
	public String getDefaultAppHome() {
		return defaultAppHome;
	}

	public String getIUMHome() {
		return "/opt/"+getIUMInstance()+"/bin/";
	}
	
	public void setDefaultAppHome(String defaultAppHome) {		
		this.defaultAppHome = defaultAppHome;
	}
	
	public String getAdminAgentPid() {
		return (String)getPropertyValue(adminAgentPidPropName);
	}

	public void setAdminAgentPid(String adminAgentPid) {
		addPropertyValue(adminAgentPidPropName, adminAgentPid);		
	}
	
	
	public String getCollectorPrefix() {
		return (String)getPropertyValue(CollectorPrefixPropName);
	}

	public void setCollectorPrefix(String collectorPrefix) {		
		addPropertyValue(CollectorPrefixPropName, collectorPrefix);
	}
}
