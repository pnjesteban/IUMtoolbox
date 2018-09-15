package ium.tools.actions;

import ium.tools.config.ConfigEntry;

public interface Action {
        
    void setData(ConfigEntry data);
    
    void execute();
    
    public String getName();
    
    public boolean isGlobal();
        
}
