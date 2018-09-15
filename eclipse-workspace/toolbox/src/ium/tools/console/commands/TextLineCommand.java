package ium.tools.console.commands;

import java.lang.reflect.Method;
import java.util.StringTokenizer;

import ium.tools.config.DefaultConfigDataset;
import ium.tools.config.info.Host;


public class TextLineCommand extends DefaultConfigDataset implements Command {
	
	public String getCommand(){
		return (String)getPropertyValue("command");
	}
	
	public String translateCommand(Host host,String[] params){
		String cmd=getCommand();
		String ret="";
		
		StringTokenizer strTnk=new StringTokenizer(cmd, "#{}",true);
		
		boolean findScape=false;
		boolean findGroup=false;
		while (strTnk.hasMoreTokens()){
			String token=strTnk.nextToken();
			
			if(token.equals("#")){
				findScape=true;
				continue;
			}
			if(findScape && token.equals("{")){
				findGroup=true;
				continue;
			}
			if(findGroup && token.equals("}")){
				findGroup=false;
				continue;
			}
			
			
			if(findScape){
				if(token.startsWith("Host.")){
					String member=token.substring("Host.".length());
					System.out.println(member);
					try{
						Method method=host.getClass().getMethod("get"+member);
						String s=(String)method.invoke(host);
						ret+=s;
					}catch(Exception ex){
						ret+=token;
						ex.printStackTrace();
					}
				}else{
					try{  
						int i = Integer.parseInt(token);						
						ret+=params[i-1];
					}catch(NumberFormatException nfe){  
						nfe.printStackTrace();
					}
				}
				findScape=false;
				continue;
			}
			ret+=token;
		}
		return ret;
	}
	
	
}
