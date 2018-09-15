package ium.toolbox.parser;

import ium.toolbox.model.DataLeaf;

public class IUMNodeLeaf implements DataLeaf{

    String name;
    String value;
    String defaultPrint;
    
    public IUMNodeLeaf(String line){
        
        String[] parts=line.split("=");
        
        name=parts[0];
        
        if(parts.length>1)        
            value=parts[1];
        else
            value="";
        
    }
    
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
        
    }

    @Override
    public void setValue(String value) {
        this.value=value;
        
    }

    @Override
    public String getValue() {
        return value;
    }
    @Override
    public String toString(){
        return print();
        
    }
    
    @Override
    public void setPrint(String att) {
        defaultPrint=att;
    }

    @Override
    public String getPrint() {
        return defaultPrint;
    }

}
