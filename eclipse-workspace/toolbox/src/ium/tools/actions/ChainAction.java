package ium.tools.actions;

import java.util.Arrays;

import ium.tools.config.ConfigEntry;

public class ChainAction extends StorableAction {

    
    @Override
    public void execute() {
        
        if(getMode().equals("async")){
        
            executeAsync(  this::chain );
            
        }else{
            chain(null);
        }
    }
    
    public Void chain(Void v){
        
        for(Action act:getActions()){
            act.execute();
        }
        return null;
    }
    
    
    public Action[] getActions(){
        
        ConfigEntry[] list=getChildrens();

        return Arrays.copyOf(list, list.length, Action[].class);
    }
}
