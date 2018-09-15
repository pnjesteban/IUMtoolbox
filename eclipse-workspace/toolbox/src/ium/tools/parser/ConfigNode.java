package ium.tools.parser;


import java.util.ArrayList;
import java.util.List;

public class ConfigNode {

    public static final String OPEN_CONFIG_ENTRY="[";
    public static final String CLOSE_CONFIG_ENTRY="]";
    public static final String PATH_SEPARATOR="/";
    
    public static final String EQUALS="=";
    public static final String NODE_CLASS_KEY="ClassName";
    
    
    String nodePath="";
    
    String nodeName="";
    
    List<String> nodeContent=new ArrayList<String>();
    
    ConfigNode parent=null;
    List<ConfigNode> childs=new ArrayList<ConfigNode>();
    
    public ConfigNode(String nodePath){
        
        this.nodePath=nodePath;
        
        String[] nodes=nodePath.split(PATH_SEPARATOR);
        if(nodes.length>0)
            nodeName=nodes[nodes.length-1];
        
    }
    
    public ConfigNode(String nodeName,ConfigNode parent){
        
        this.nodeName=nodeName;
        this.parent=parent;
    }
    
    
    public void addContentLine(String line){
        nodeContent.add(line);
    }
    
    public String[] getContentLines(){
        return nodeContent.toArray(new String[0]);
    }
    
    public String getNodeClass()
    {
        for(String line:getContentLines())
        {
            if(line.startsWith(NODE_CLASS_KEY))
                return line.substring(line.indexOf(EQUALS));
        }
        return "";
        
    }
    
    public void addChild(ConfigNode node){
        childs.add(node);
    }
    
    public ConfigNode getChild(String nodeName){
        for(ConfigNode n:childs){    
            if(n.getNodeName().equals(nodeName))
                return n;
        }
        return null;
    }
    
    void setParent(ConfigNode node){
        parent=node;
    }
    
    ConfigNode getParent(){
        return parent;
    }
    
    void setPath(String path)
    {
        nodePath=path;
        
    }
    
    String getPath(){
        return nodePath;
    }
    
    String getNodeName(){        
        return nodeName;
    }
    
    
    public ConfigNode[] getChilds(){    
        return childs.toArray(new ConfigNode[0]);
    }
    /*Iterator<ConfigNode> iteratorChild(){
        return childs.iterator();
    }*/
    
    public String getCompoundPath(){
        if(parent!=null)
            return parent.getCompoundPath()+nodeName+"/";
        
        
        return nodeName;
    }
    
    public String toString(){        
        return nodeName;
    }
    
    public String toStringFull(){
        
        String ret="["+getPath()+"]\n\n";
        
        for(String s: nodeContent)
        {
            ret+="   "+s+"\n";
        }
        return ret;
    }
    
}
