package ium.toolbox.model;

public interface DataLeaf extends Data {

    public void setName(String name);
    public void setValue(String value);
    public String getValue();
        
        
    public default String print(){
            
        if("name".equals(getPrint()))
            return getName();
        
        if("value".equals(getPrint()))
            return getValue();
        
        //return getName()+"="+getValue()+"\n";
        return getValue();
    }
    
}
