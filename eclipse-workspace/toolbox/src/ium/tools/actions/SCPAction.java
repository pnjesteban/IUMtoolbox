package ium.tools.actions;


import ium.tools.config.DefaultConfigDataset;
import ium.tools.config.ConfigEntry;
import ium.tools.config.info.Host;
import ium.tools.console.ConsoleManager;
import ium.tools.data.GlobalScopeManager;

public class SCPAction extends DefaultConfigDataset implements Action {

    @Override
    public void setData(ConfigEntry data) {
        // TODO Auto-generated method stub

    }
   
    public String getRemoteFile(){
        return (String)getPropertyValue("remoteFile");
    }
    
    public String getDestination(){
        return (String)getPropertyValue("destination");
    }

    @Override
    public void execute() {
        
        String dest=getDestination();
        String orig=getRemoteFile();
        if(dest.endsWith("/")){
            int index=orig.lastIndexOf("/")+1;
            String file=orig.substring(index);
            dest+=file;
        }
        
        
        Host host=(Host)GlobalScopeManager.instance().getConfig("Host");
        

        if(ConsoleManager.instance().openSession(host)){
            
            System.out.println("SCPAction.execute: "+getRemoteFile()+" to "+dest);
            
            ConsoleManager.instance().transfer(orig, dest);            
            ConsoleManager.instance().closeSession();    
            
        }    
        
        System.out.println("done");
        
    }

    @Override
    public boolean isGlobal() {
        // TODO Auto-generated method stub
        return false;
    }

}
