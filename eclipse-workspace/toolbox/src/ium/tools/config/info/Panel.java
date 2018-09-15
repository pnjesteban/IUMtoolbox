package ium.tools.config.info;

import java.util.ArrayList;

import ium.tools.composite.Renderable;
import ium.tools.config.DefaultConfigDataset;
import ium.tools.data.GlobalScopeManager;
import ium.tools.config.ConfigEntry;

public class Panel extends DefaultConfigDataset{

    String titlePropName="title";    
    String viewPropName="View";
    String listenToPropName="listenTo";
    
    public String getTitle() 
    {
        return (String)getPropertyValue(titlePropName);
    }
    
    public String getClassName() 
    {   
        return (String)getPropertyValue(viewPropName);
    }
    
    
    public String[] getListenTo() 
    {
        String listeners = (String)getPropertyValue(listenToPropName);
        
        if(listeners!=null)        
            return listeners.split("\\|");
        
        return new String[]{};
        
    }
    
    public Renderable instance(){
        
        try{
            Renderable o=(Renderable)Class.forName(getClassName()).newInstance();
            
            ArrayList<Renderable> controlList=new ArrayList<Renderable>();
            
            for(ConfigEntry st:getChildrens()){
                
                if(st instanceof Renderable){
                    controlList.add((Renderable)st);
                } 
            }
            o.addControls(controlList.toArray(new Renderable[0]));
            
            
            for(String l:getListenTo()){
                GlobalScopeManager.instance().addListener(l, o);
            }
            
            
            return o;
        }catch(Exception ex){
            ex.printStackTrace();        
        }
        return null;
    }
}
