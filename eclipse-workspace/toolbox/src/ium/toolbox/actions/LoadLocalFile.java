package ium.toolbox.actions;


import java.io.File;
import java.io.IOException;

import ium.toolbox.model.DataManager;
import ium.toolbox.parser.IUMConfigNode;
import ium.toolbox.parser.ParseIumConfig;


public class LoadLocalFile extends DefaultAction {
    
    @Override
    public void execute() {

        if("interactive".equals(getProperty("mode")))
            executeInteractive();
        else
            parseFile(getProperty("file"));
    }
    
    private void parseFile(String file){
        
        ParseIumConfig parser=new ParseIumConfig();
        IUMConfigNode rootNode=null;
        try{
            rootNode=parser.parse(new File(file));
            
            DataManager.instance().put(getName(), rootNode);
            
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void executeInteractive() {
      /*  
        FileDialog dialog = new FileDialog(LaunchShell.getInstance(), SWT.OPEN);
        dialog.setFilterExtensions(new String [] {"*.config"});
        dialog.setFilterPath("file");
        String result = dialog.open();
        
        if(result!=null)
            parseFile(result);
      */
    }    
}
