package ium.toolbox.model.cfg;

public class Host extends ConfigDataNode{
        
    public String getPort() {
        return getDataValue("port");
    }
    
    public String getHostname() {
        return getDataValue("hostname");
    }
    
    public String getUsername() {
        return getDataValue("username");
    }
    
    public String getIUMInstance() {
        return getDataValue("IUMInstance");
    }
    
    
    
    public String toString(){
        
        return print();
    }
    
}

