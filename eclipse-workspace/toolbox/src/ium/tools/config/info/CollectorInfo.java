package ium.tools.config.info;

import ium.tools.config.DefaultConfigDataset;

@Deprecated
public class CollectorInfo extends DefaultConfigDataset{
	
	String pidPropName="pid";
	String userPropName="user";
	String topPidPropName;
	String timePropName;
	String commandLinePropName;
	

	public CollectorInfo(String name) {
		super(name);
	}

	public String getPid() {
		return (String)getPropertyValue(pidPropName);
	}

	public void setPid(String pid) {
		addPropertyValue(pidPropName, pid);
	}

	public String getUser() {
		return (String)getPropertyValue(userPropName);
	}

	public void setUser(String user) {
		addPropertyValue(userPropName,user);
	}

	public String getTopPid() {
		return (String)getPropertyValue(topPidPropName);
	}

	public void setTopPid(String topPid) {
		addPropertyValue(topPidPropName,topPid);
	}

	public String getTime() {
		return (String)getPropertyValue(timePropName);
	}

	public void setTime(String time) {
		addPropertyValue(timePropName,time);
	}

	public String getCommandLine() {
		return (String)getPropertyValue(commandLinePropName);
	}

	public void setCommandLine(String commandLine) {
		addPropertyValue(commandLinePropName, commandLine);		
	}

	public String toExtendString()
	{
		String ret=getName()+"\n";		
		ret+="User:"+getUser()+"\n";
		ret+="PID: "+getPid()+"\n";
		ret+="PPID: "+getTopPid()+"\n";
		ret+="time: "+getTime()+"\n";
		ret+="commandLine: "+getCommandLine()+"\n";
		
		return ret;
	}

	public void cloneFrom(CollectorInfo coll)
	{
		setPid(coll.getPid());
		setUser(coll.getUser());
		setTopPid(coll.getTopPid());
		setTime(coll.getTime());
		setCommandLine(coll.getCommandLine());
	}
}
