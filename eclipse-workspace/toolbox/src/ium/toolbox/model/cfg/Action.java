package ium.toolbox.model.cfg;

import ium.toolbox.actions.Actionable;

public class Action extends ConfigDataNode{
    
    public Actionable instance(){
        
        String actionClass=getDataValue("actionClass");
        
        try{
            Actionable o=(Actionable)Class.forName(actionClass).newInstance();
            
            o.setProperties(getProperties());
            
            return o;
        }catch(Exception ex){
            ex.printStackTrace();        
        }
        
        return null;
        
    }    
    
}
