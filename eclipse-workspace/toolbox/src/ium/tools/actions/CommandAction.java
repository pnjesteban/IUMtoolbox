package ium.tools.actions;


import ium.tools.config.DefaultConfigDataset;
import ium.tools.config.ConfigEntry;
import ium.tools.config.XMLParser;
import ium.tools.config.info.Host;
import ium.tools.console.ConsoleManager;
import ium.tools.console.ConsoleReturn;
import ium.tools.console.commands.Command;
import ium.tools.data.GlobalScopeManager;

public class CommandAction extends StorableAction  {

    @Override
    public void setData(ConfigEntry data) {

    }

    
    @Override
    public void execute() {

        
        System.out.println("CommandAction.execute "+getCommand());
        
        Host host=(Host)GlobalScopeManager.instance().getConfig("Host");

        if(ConsoleManager.instance().openSession(host)){
            
            ConsoleReturn ret = ConsoleManager.instance().exec(getCommand(), null);
            
            ConsoleManager.instance().closeSession();
            
            globalizeObject(ret);
        }
        else{
            System.err.println("NO SE PUEDE ABRIR SESION EN "+host);
            
        }
            
    }

    public Command getCommand(){
        
        String commStr=(String)getPropertyValue("command");
        
        Command c=XMLParser.instance().getCommand(commStr);
        
        if(c instanceof DefaultConfigDataset){
            
            String s=(String)getPropertyValue("param");
            
            ((DefaultConfigDataset)c).setExternalParams(s);
            
        }
        return c;
        
    }
}
