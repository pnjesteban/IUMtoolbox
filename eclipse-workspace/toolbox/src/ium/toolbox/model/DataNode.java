package ium.toolbox.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface DataNode extends Data {
     
    List<Data> getData(); 
    
    
    public default Data get(String name){
        
        return getData()
                .stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .get();
    }
    
    public default List<Data> getDataList(String name){
        
        return getData()
                .stream()
                .filter(d -> d.getName().equals(name))                
                .collect(Collectors.toList());
    }
    
    public default List<DataNode> getNodes(){
        
        return getData()
                .stream()
                .filter(DataNode.class::isInstance)
                .map(DataNode.class::cast)
                .collect(Collectors.toList());
    }
    
    public default List<DataLeaf> getLeafs(){
        
        return getData()
                .stream()
                .filter(DataLeaf.class::isInstance)
                .map(DataLeaf.class::cast)
                .collect(Collectors.toList());
    }
    
    public default String getDataValue(String name){
        try{
        return getData()
                .stream()
                .filter(DataLeaf.class::isInstance)
                .filter(d -> d.getName().equals(name))
                .map(DataLeaf.class::cast)
                .findFirst()
                .get()
                .getValue();
        }catch(NoSuchElementException e){
            
            System.out.println("WARNING data not found "+this.getName()+"::"+name);
            return null;
        }
    }
    
    public default void addData(Data d){
        getData().add(d);
    }
    
    public default String print(){
        
        if(getPrint()==null)
            return getName();
        
        try {
            return ""+getClass().getMethod(getPrint()).invoke(this);
        } catch (Exception e) {
            
        }
        //se intenta el accesor
        try {
            String accesor="get"+getPrint().substring(0, 1).toUpperCase() + getPrint().substring(1);
            return ""+getClass().getMethod(accesor).invoke(this);
        } catch (Exception e2) {
            e2.printStackTrace();            
        }
        
        return getName();
    }
    
    
}
