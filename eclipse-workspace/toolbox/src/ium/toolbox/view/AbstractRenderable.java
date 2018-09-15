package ium.toolbox.view;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ium.toolbox.actions.Actionable;
import ium.toolbox.model.Data;
import ium.toolbox.model.DataChangeListener;

public abstract class AbstractRenderable implements Renderable,DataChangeListener {

    Map<String, String> properties;
       
    private Actionable action;
    
    @Override
    public void setProperties(Map<String,String> properties){
        
        this.properties=properties;
        
    }
    
    @Override
    public String getProperty(String name){
        
        return properties.get(name);
        
    }
    
    public List<String> getProperyList(String name){
        
        if(getProperty(name)==null)
            return new ArrayList<String>();
            
        return Arrays
                .stream(getProperty(name)
                .split("\\|"))
                .collect(Collectors.toList());
        
        
    }
    
    @Override
    public void update(Data o) {

        System.out.println(o);
    }
    
    @Override
    public void setAction(Actionable action){
        this.action=action;
    }
    public Actionable getAction(){
        return action;
    }
}
