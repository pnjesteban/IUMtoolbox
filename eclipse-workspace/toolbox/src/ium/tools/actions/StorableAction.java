package ium.tools.actions;

import java.util.function.Function;

import ium.tools.config.DefaultConfigDataset;
import ium.tools.config.ConfigEntry;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.data.GlobalScopeManager;

public abstract class StorableAction extends DefaultConfigDataset implements Action {

    ConfigEntry data=null;
    
    @Override
    public void setData(ConfigEntry data) {
        this.data=data;
    }
    
    protected void globalizeObject(Dataset o){
        
        if(isGlobal()){
            GlobalScopeManager.instance().setObject(getName(),o,isNotify() );
        }
    }
    
    
    protected Dataset readDataset(){
        //TODO todos los objetos globales deben ser dataset        
        return (Dataset)GlobalScopeManager.instance().getDataset(getInput());
    }
    
    public void executeAsync(Function<Void , Void> func){
        
        new Thread(new Runnable() {            
            @Override
            public void run() {
                try {
                    func.apply(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    
    @Override
    public final boolean isGlobal() {

        String scope=(String)getPropertyValue("scope");
        
        if(scope==null)
            return true;
        
        return (!scope.equals("private"));
    }
    
    public final String getMode(){
        return (String)getPropertyValue("mode");
    }
    
    public final boolean isNotify(){
        String notify=(String)getPropertyValue("notify");
        
        if(notify==null)
            return true;
        
        return (!notify.equals("no"));
    }
    
    public final String getInput(){
        String input=(String)getPropertyValue("input");
        //TODO parse pipe
        return input;
    }

}
