package ium.toolbox.actions;

import ium.toolbox.model.cfg.Action;


/**
 * TODO
 * 
 */
public class ChainAction extends DefaultAction {

    
    @Override
    public void execute() {
        
        if("async".equals(getProperty("mode"))) {
            
            executeAsync(  this::chain );
            
        } else {
            
            chain(null);
        }
    }
    
    public Void chain(Void v){
        
        for(Action act:getActions()){
            act.instance().execute();
        }
        return null;
    }
    
    
    public Action[] getActions(){        
        
        /*        
        ConfigEntry[] list=getChildrens();
        return Arrays.copyOf(list, list.length, Action[].class);
        */
    	return null;
    }
}
