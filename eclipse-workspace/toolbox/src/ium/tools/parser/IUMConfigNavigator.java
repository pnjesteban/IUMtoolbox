package ium.tools.parser;

import ium.tools.config.XMLParser;
import ium.tools.config.info.ConfigMap;

public class IUMConfigNavigator {

    ConfigNode rootNode;
    
    public final static String DEPLOYMENT_PATH="/deployment";
    
    public final static String LINK="Link";
    public final static String CLASS_NAME="ClassName";
    
    public IUMConfigNavigator(ConfigNode rootNode){
        this.rootNode=rootNode;
    }
    
    
    
    private String translate(String line,String collector){
        
        
        
        String[] par=line.split("\\.");
        
        String ruta=line;
        String parametro=null;
        
        String ret="";
        
        if(par.length==2){
            ruta=par[0];
            parametro=par[1];
        }
        
        ret=ruta;
        while(ret.indexOf("@")!=-1){
            
            String[] ss=ret.split("@");
            
            ret=translateUnit(collector, ss[1]);
            
        }
        
        if(ret.isEmpty())
            ret=ruta;
        
        if(parametro!=null)
            ret+="."+parametro;
        
        return ret;
    }
    
    
    private String translateUnit(String collector,String param){
        
        //System.out.println("translateUnit( "+param+","+ collector+")");
        
        String subParam=null;
        
        if(param.indexOf("::")!=-1){
            
            String[] ss=param.split("::");
            param=ss[0];
            subParam=ss[1];
        }
        
        ConfigMap[] configs=XMLParser.instance().getConfigMap(param);
        
        for(ConfigMap c:configs){

            String r=c.getPath()+c.getClassName();
            String paramClass=getCollectorParam(collector,r);

            if(paramClass.equals(c.getRuleClass()) || c.getRuleClass().equals("*")){
                if(subParam!=null)
                    return c.getPath()+c.getParam(subParam);
                else
                    return c.getPath();
            }
        }
        return param;
        
    }
    
    public String getCollectorParam(String collName, String param){
        
        String translateParam=translate(param,collName);
        
        System.out.println("getCollectorParam("+collName+","+param+")>>"+translateParam);
        
        String[] campos=translateParam.split("\\.");
        
        ConfigNode collectorNode=getNodeByPath(DEPLOYMENT_PATH+"/"+inferHost()+"/"+collName);

        String ret="NONE";
        
        if(campos.length==1){
            ret=getField(collectorNode, translateParam);
        }else{//con ruta
            
            System.out.println("---"+campos[0]);
            
            ConfigNode node=getNodeByPath(collectorNode, campos[0]);

            if(node!=null) {
            
                ret=getField(node,campos[1]);
            
                if(ret==null)
                    ret=getCollectorTemplateParam(collName,translateParam);
            }
            
        }
        
        //System.out.println("getCollectorParam>"+ret);
        return ret;
    }
    
    private ConfigNode getCollectorNode(String coll){
        
        return getNodeByPath(DEPLOYMENT_PATH+"/"+inferHost()+"/"+coll);
        
    }
    
    private String getCollectorTemplateParam(String collName, String param){
    
        ConfigNode templateNode=getNodeByPath(getCollectorTemplate(collName));
        
        String[] campos=param.split("\\.");
        

        if(campos.length==1){
            return getField(templateNode, param);
        }else{//con ruta
            
            ConfigNode node=getNodeByPath(templateNode, campos[0]);

            if(node==null) return "NONE";
            
            String ret=getField(node,campos[1]);
            
            return ret;
        }        
    }
    
    public String getField(ConfigNode node,String field){
        
        //System.out.println(" getField("+node+","+field+")");
        
        for(String s:node.getContentLines()){
            
            if(s.startsWith(field))
                return s.split("=")[1];
        }
        return null;
        
    }
    
    
    public String getCollectorTemplate(String collName ){
               
        String ret="";
        
        String path=DEPLOYMENT_PATH+"/"+inferHost()+"/"+collName;
                
        String[] lines=getNodeByPath(path).getContentLines();
        for(String s:lines){
            
            if(s.startsWith(LINK))
                ret=s.split("=")[1];
        }
        return ret;
    }
    
    public String inferHost(){
        
        ConfigNode node=getNodeByPath(DEPLOYMENT_PATH);
        return node.getChilds()[0].getNodeName();
    }
    
    public ConfigNode getLink(ConfigNode node){
        
        String s=getField(node,LINK);        
        if(s!=null)
            return getNodeByPath(s);
        return null;
    }
    
    public ConfigNode getNodeByPath(ConfigNode node,String path){
        
        String[] nodesStr=path.split("\\/");
        
        ConfigNode subNode=node;
        
        for(String n:nodesStr){
            
            if(n.length()>0){
               subNode=subNode.getChild(n);
            }
            
            if(subNode==null)
                return null;
        }
        return subNode;
    }
        
    public ConfigNode getNodeByPath(String path){
        return getNodeByPath(rootNode,path);
    }
    
    
}
