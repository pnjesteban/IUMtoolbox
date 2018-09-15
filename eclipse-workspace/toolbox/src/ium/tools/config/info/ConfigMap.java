package ium.tools.config.info;

import ium.tools.config.DefaultConfigDataset;

public class ConfigMap extends DefaultConfigDataset{
    
    
    public String getPath(){
        return (String)getPropertyValue("Path");
    }
    
    public String getRuleClass(){
        return (String)getPropertyValue("RuleClass");
    }
    
    public String getClassName(){
        return (String)getPropertyValue("ClassName");
    }
    
    
    
    public String getParam(String param){
        return (String)getPropertyValue(param);
    }
    

}
