package ium.tools.config.info;


import java.util.Arrays;

import ium.tools.actions.Action;
import ium.tools.config.DefaultConfigDataset;
import ium.tools.config.ConfigEntry;
import ium.tools.config.XMLParser;

public class MenuItem  extends DefaultConfigDataset{

    String titlePropName="title";    
    String actionPropName="action";
    String loadPropName="load";
    String typePropName="type";
    
    public String getTitle() 
    {
        return (String)getPropertyValue(titlePropName);
    }
     
    public Action getAction()
    {   
        String actionStr=(String)getPropertyValue(actionPropName);
        return XMLParser.instance().getActionByName(actionStr);
    }
    
    public String getType() 
    {   
        String s=(String)getPropertyValue(typePropName);
        if(s==null)
            s="item";
        
        return s;
    }
    
    public ConfigEntry[] getload()
    {   
        String load=(String)getPropertyValue(loadPropName);
        
        if(load!=null)
        {
            ConfigEntry[] st=XMLParser.instance().getStorableType(load);
            return st;
        }
        return null;
    }
    
    
    public MenuItem[] getSubMenus(){
        
        ConfigEntry[] o=getChildrens();
        
        if(o!=null)        
            return Arrays.copyOf(o, o.length, MenuItem[].class);
        
        return null;
    }
    
    

}
