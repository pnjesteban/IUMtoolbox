package ium.toolbox.parser;


import java.util.ArrayList;
import java.util.List;

import ium.toolbox.model.Data;
import ium.toolbox.model.DataLeaf;
import ium.toolbox.model.DataNode;

public class IUMConfigNode implements DataNode{

    public static final String OPEN_CONFIG_ENTRY="[";
    public static final String CLOSE_CONFIG_ENTRY="]";
    public static final String PATH_SEPARATOR="/";
    
    public static final String EQUALS="=";
    public static final String NODE_CLASS_KEY="ClassName";
    
    
    String nodePath="";
    
    String nodeName="";
    
    String defaultPrint;
    
    //List<String> nodeContent=new ArrayList<String>();
    
    IUMConfigNode parent=null;
    List<Data> nodes=new ArrayList<Data>();

    
    public IUMConfigNode(String nodePath){
        
        this.nodePath=nodePath;
        
        String[] nodes=nodePath.split(PATH_SEPARATOR);
        if(nodes.length>0)
            nodeName=nodes[nodes.length-1];
    }
    
    public IUMConfigNode(String nodeName,IUMConfigNode parent){
        
        this.nodeName=nodeName;
        this.parent=parent;
    }
    
    
    public void addContentLine(String line){
        
        nodes.add(new IUMNodeLeaf(line));
        
    }
    
    /*
    public List<DataLeaf> getContentLines(){
        
        getLeafs()
        
        return nodeContent.toArray(new String[0]);
    }*/
    
    public String getNodeClass()
    {
        
        for(DataLeaf l:getLeafs()){
            
            if(l.getName().equals(NODE_CLASS_KEY))
                return l.getValue();
            
        }
        
        return "";
        
    }
    
    public void addNode(IUMConfigNode node){
        nodes.add(node);
    }
    
    
    public IUMConfigNode getNode(String nodeName){
        
        return getNodes()
                .stream()                
                .map(IUMConfigNode.class::cast)
                .filter(n -> n.getNodeName().equals(nodeName))
                .findFirst()
                .orElse(null);
    }
    
    void setParent(IUMConfigNode node){
        parent=node;
    }
    
    IUMConfigNode getParent(){
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
    
    
    /*public List<IUMConfigNode> getNodes(){
        return nodes
                .stream()
                .map(n -> (IUMConfigNode) n)
                .collect(Collectors.toList());
    }*/
    
    public String getCompoundPath(){
        if(parent!=null)
            return parent.getCompoundPath()+nodeName+"/";
        return nodeName;
    }
    
    public String toString(){        
        return print();
    }
    
    public String printNode(){
        
        String ret="["+getPath()+"]\n";
        
        for(DataLeaf s: getLeafs())
        {
            ret+="   "+s;
        }
        return ret;
    }

    //from data
    
    @Override
    public String getName() {
        return nodeName;
    }

    @Override
    public List<Data> getData() {
        return nodes;
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
