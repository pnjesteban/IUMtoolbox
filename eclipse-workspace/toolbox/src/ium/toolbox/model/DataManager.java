package ium.toolbox.model;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import ium.toolbox.model.interpreter.Evaluator;

public class DataManager {
    
    Hashtable<String,Data> storedData = new Hashtable<String,Data>();
    
    Hashtable<String,ArrayList<DataChangeListener>> listeners = new Hashtable<String,ArrayList<DataChangeListener>>();
    
    private static DataManager singleton=null;    
    private DataManager(){}
        
    public static DataManager instance(){
        if(singleton==null)
            singleton=new DataManager();    
        
        return singleton;
    }
    
    /**
     * Sustituye variables con formato #{<var>}
     * @param s
     * @return
     */
    public String translate(String value){
        
        String ret="";
        
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
                
                Data d=new Evaluator().evaluate(token);
                
                ret+=d;
                
                System.out.println(d.getClass());
                
                findScape=false;
                continue;
            }
            ret+=token;
        }
        
        return ret;
        
    }
    
    public void addListener(String key, DataChangeListener listener){
        
        System.out.println(listener.getClass()+" listen to "+key);
        
        if(!listeners.containsKey(key)){
            listeners.put(key, new ArrayList<DataChangeListener>() );
        }
        
        listeners.get(key).add(listener);
    }
    
        
    public void notify(String key){
        if(listeners.containsKey(key) && storedData.containsKey(key))
            listeners.get(key).forEach(l -> l.update(storedData.get(key)));
    }
        
    public void put(String key,Data data){
        storedData.put(key,data);
        notify(key);
    }
    
    public Data get(String name){
        return storedData.get(name);
    }
}
