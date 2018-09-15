package ium.toolbox.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ium.toolbox.model.DataManager;

public abstract class DefaultAction implements Actionable {

    Map<String, String> properties=new HashMap<String,String>();
    
    @Override
    public void setProperties(Map<String, String> props) {
        properties=props;
    }

    @Override
    public String getProperty(String prop) {
        
        return DataManager.instance().translate(properties.get(prop));
        
    }
    
    public String getName(){
        return getProperty("name");
        
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

}
