package ium.tools.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import ium.tools.config.ConfigEntry;
import ium.tools.config.StringStorable;
import ium.tools.config.XMLParser;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.parser.ConfigNode;

public class GlobalScopeManager {

    
    Hashtable<String,ConfigNode> loadedIUMConfig= new Hashtable<String,ConfigNode>();
    
    Hashtable<String,Dataset> loadedDataset=new Hashtable<String, Dataset>();
    
    Hashtable<String,ConfigEntry> loadedConfig=new Hashtable<String, ConfigEntry>();
    
    Hashtable<String,ArrayList<GlobalScopeListener>> listeners=new Hashtable<String,ArrayList<GlobalScopeListener>>();
    
    //Hashtable<String,ArrayList<IUMConfigListener>> IUMlisteners=new Hashtable<String,ArrayList<IUMConfigListener>>();
    
    
    private static GlobalScopeManager singleton=null;    
    private GlobalScopeManager(){}
        
    public static GlobalScopeManager instance(){
        if(singleton==null)
            singleton=new GlobalScopeManager();    
        
        return singleton;
    }
    
    
    public void addListener(String key, GlobalScopeListener listener){
        
        System.out.println(listener.getClass()+" listen to "+key);
        
        if(!listeners.containsKey(key)){
            listeners.put(key, new ArrayList<GlobalScopeListener>() );
        }
        
        listeners.get(key).add(listener);
    }
    
        
    public void notify(String key){
        
        System.out.println(" notificando "+key);
        
        //chapu: doble notificacion
        if(listeners.containsKey(key) && loadedIUMConfig.containsKey(key))
            listeners.get(key).forEach(l -> l.update(loadedIUMConfig.get(key)));
        
        if(listeners.containsKey(key) && loadedDataset.containsKey(key))
            listeners.get(key).forEach(l -> l.update(loadedDataset.get(key)));        
    }
    
    
    public ConfigNode getIUMConfig(String key){
        return loadedIUMConfig.get(key);
    }
    
    public void setIUMConfig(String key,ConfigNode config){
        loadedIUMConfig.put(key, config);
        
        notify(key);
    }
    
    public void setObject(String name,ConfigEntry object, boolean notify){
        loadedConfig.put(name,object);
    }
    
    
    public void setObject(String name,Dataset object, boolean notify){
        loadedDataset.put(name, object);
        notify(name);
    }
    
    public Dataset getDataset(String name){        
        return loadedDataset.get(name);
    }
    
    
    public ConfigEntry getConfig(String name){        
        return loadedConfig.get(name);
    }
    
    public String getObjectValue(String value){  

      StringTokenizer strTnk=new StringTokenizer(value, ".",false);
      String ObjName=strTnk.nextToken();
      Object o=loadedConfig.get(ObjName);
      if(o instanceof String){
          return (String)o;
      }
      else if(o instanceof ConfigEntry){
          String member=strTnk.nextToken();
          try{   
              Method method=o.getClass().getMethod("get"+member.substring(0, 1).toUpperCase() + member.substring(1));
              return (String)method.invoke(o);
          }
          catch(Exception ex){
              ex.printStackTrace();
          }
      }else{
          
          ConfigEntry[] list=XMLParser.instance().getStorableType(ObjName);
          String member=strTnk.nextToken();
          for(ConfigEntry st:list){
              if(st.getName().equals(member)){
                  //PELIGROSO
                  return translate( ((StringStorable)st).getValue() , null);
              }
          }
      }
      return "";
    }
    
    
public String translate(String value,String[] params){
        
        String ret="";
        
        if(value==null)
            return value;
        
        StringTokenizer strTnk=new StringTokenizer(value, "#{}",true);
        
        boolean findScape=false;
        boolean findGroup=false;
        while (strTnk.hasMoreTokens()){
            String token=strTnk.nextToken();
            
            if(token.equals("#")){
                findScape=true;
                continue;
            }
            if(findScape && token.equals("{")){
                findGroup=true;
                continue;
            }
            if(findGroup && token.equals("}")){
                findGroup=false;
                continue;
            }
            
            if(findScape){
                String s="";
                int index;   
                
                try {
                    index = Integer.parseInt(token);
                    s=params[index-1];
                } catch (NumberFormatException e) {
                     //si no es un numero de parametro es una variable
                    s=getObjectValue(token);
                }
                ret+=s;
                
                findScape=false;
                continue;
            }
            ret+=token;
        }
        return ret;
    }   

}
