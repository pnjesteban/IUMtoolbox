package ium.toolbox.model;

public class StringData implements DataLeaf {

    String name=null;
    String value=null;
    String defaultPrint;
       
    @Override 
    public String toString(){
        return print();
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
    public void setPrint(String att) {
        defaultPrint=att;
    }

    @Override
    public String getPrint() {
        return defaultPrint;
    }
    /*
    @Override
    public String print(){
        return value;
        
    }*/
}
