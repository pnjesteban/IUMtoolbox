package ium.tools.data;

import java.util.Iterator;

public class GlobalDataSet {
    
    
    public interface GlobalizedData<T>{
        
        public String getGlobalName();
        
        
        public T getData();
        
    }
    
    public interface DataValue
    {
        public String name();
        public String value();
        
        
        public default String print()
        {
            return name()+"="+value();
        }
    }
    
    public interface DataLine extends Iterable<DataValue>
    {
        public default String getValue(String name)
        {
            for(DataValue val:get())
            {
                if(val.name().equals(name))
                    return val.value();   
            }           
            return null;
        }
        
        public void add(DataValue value);
        
        public Iterable<DataValue> get();
        
        public default String print()
        {
            String ret="";
            for(DataValue val:get())
            {
                ret+=val+" ";
            }
            return ret;
        }
        
        @Override
        public default Iterator<DataValue> iterator() {
            return get().iterator();
        }
    }
    
    public interface Dataset extends Iterable<DataLine>
    {
        public void add(DataLine line);
        public Iterable<DataLine> get();
        
        public default String print()
        {   
            String ret="";
            for(DataLine line:get())
            {
                ret+="["+line+"]\n";
            }
            return ret;
        }                
                
        @Override
        public default Iterator<DataLine> iterator() {            
            
            return get().iterator();
        }
    }
    
    
}
