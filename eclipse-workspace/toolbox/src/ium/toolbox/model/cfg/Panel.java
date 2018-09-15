package ium.toolbox.model.cfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ium.toolbox.config.XMLParser;
import ium.toolbox.model.DataChangeListener;
import ium.toolbox.model.DataManager;
import ium.toolbox.view.Renderable;


public class Panel extends ConfigDataNode {
    
    public String getViewClass(){   
        
        return getDataValue("view");
        /*
        return getChildsType(StringConfig.class).stream()
                //solo puede haber una vista por panel
                .findFirst()
                .map( v -> v.getPropertyValue("View") )
                .get();
         */
    }
    
    public List<String> getListenTo(){
        
        if(getDataValue("listenTo")==null)
            return new ArrayList<String>();
        
        return Arrays
                .stream(getDataValue("listenTo")
                .split("\\|"))
                .collect(Collectors.toList());
    }
    
    public Renderable instance(){
        
        try{
            
            System.out.println(getName()+" instance "+getViewClass());
            Renderable o=(Renderable)Class.forName(getViewClass()).newInstance();
            List<Renderable> controlList=getChildsType(Panel.class)
                    .stream()
                    .map( Panel::instance )
                    .collect( Collectors.toList() );
            
            o.setControls(controlList);
            
            o.setProperties(getProperties());
            
            if(o instanceof DataChangeListener )
                getListenTo().forEach(l -> DataManager.instance().addListener(l, (DataChangeListener)o ));
            
            
            String action=getProperties().get("action");
            if(action!=null){
                
                o.setAction(XMLParser.instance().getActionData(action).instance());
            }
            
            return o;
            
        }catch(Exception ex){
            ex.printStackTrace();        
        }
        return null;
    }
    
}
