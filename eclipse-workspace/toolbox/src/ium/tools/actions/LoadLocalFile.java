package ium.tools.actions;


import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import ium.tools.LaunchShell;
import ium.tools.data.GlobalScopeManager;
import ium.tools.parser.ConfigNode;
import ium.tools.parser.ParseIumConfig;

public class LoadLocalFile extends StorableAction {
    
    @Override
    public void execute() {

        if(getMode().equals("interactive"))
            executeInteractive();
        else
            parseFile(getFile());
    }

    
    private void parseFile(String file){
        
        ParseIumConfig parser=new ParseIumConfig();
        ConfigNode rootNode=null;
        try{
            rootNode=parser.parse(new File(file));                
        }catch (IOException ex){
            ex.printStackTrace();
        }
        
        GlobalScopeManager.instance().setIUMConfig(getName(),rootNode);
    }
    
    
    public String getFile(){
        return (String)getPropertyValue("file");
        
    }
    
    public void executeInteractive() {
        
        FileDialog dialog = new FileDialog(LaunchShell.getInstance(), SWT.OPEN);
        dialog.setFilterExtensions(new String [] {"*.config"});
        dialog.setFilterPath("file");
        String result = dialog.open();
        
        if(result!=null)
            parseFile(result);
    }    
}
