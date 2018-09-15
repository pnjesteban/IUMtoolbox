package ium.tools.console.commands;

import ium.tools.config.info.Host;

public interface Command {

	public String getCommand();
	
	public String translateCommand(Host host,String[] params);
}
