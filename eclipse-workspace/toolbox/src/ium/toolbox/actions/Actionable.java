package ium.toolbox.actions;

import java.util.Map;

public interface Actionable {
    
    void setProperties(Map<String,String> prop);
    
    String getProperty(String prop);
    
    void execute();
}
