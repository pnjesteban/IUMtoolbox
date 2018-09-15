package ium.toolbox.model.cfg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ium.toolbox.model.Data;
import ium.toolbox.model.DataLeaf;
import ium.toolbox.model.DataNode;


public abstract class ConfigDataNode implements DataNode {
    
    List<Data> data=new ArrayList<Data>();
    String defaultPrint;
    
    @Override
    public String getName() {
        return getDataValue("name");
    }
    
    @Override
    public List<Data> getData(){
        return data;
    }
        
    public <T extends Data> List<T> getChildsType(Class<T> t){
        
        return data
                .stream()
                .filter(c -> c.getClass().equals(t))
                .map(c -> (T) c)
                .collect(Collectors.toList());
    }
    
    public Map<String, String> getProperties(){
        
         return data
             .stream()
             .filter(DataLeaf.class::isInstance)
             .map(DataLeaf.class::cast)
             .collect(Collectors.toMap( DataLeaf::getName, DataLeaf::getValue ));
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
