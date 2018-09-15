package ium.tools.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class ParseIumConfig {

    
    public static void main(String[] args){
        
        try{
            new ParseIumConfig().parse(new File("files/200_templates_ericssonR4.config"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();            
        }
    }
    
    
    public ConfigNode parse(File f) throws IOException{
        
        long t0=System.currentTimeMillis();
        
        Reader reader=new InputStreamReader(new FileInputStream(f));       
        BufferedReader buff=new BufferedReader(reader);     

        String line="";
        int totalNodes=0;
        String header="";
        
        ConfigNode lastNode=null;
        
        List<ConfigNode> nodeList=new ArrayList<ConfigNode>();
        
        while((line=buff.readLine())!=null)
        {
            line=line.trim();
            
            if(line.isEmpty())
                continue;
            
            if(line.startsWith(ConfigNode.OPEN_CONFIG_ENTRY) && line.endsWith(ConfigNode.CLOSE_CONFIG_ENTRY)){                
                
                lastNode=new ConfigNode(line.replace(ConfigNode.OPEN_CONFIG_ENTRY, "").replace(ConfigNode.CLOSE_CONFIG_ENTRY, ""));
                
                nodeList.add(lastNode);
                
                totalNodes++;
            }
            else
                lastNode.addContentLine(line);
        }
        long t1=System.currentTimeMillis();
        
        
        ConfigNode root=compactNodeList(nodeList);
        
        System.out.println(totalNodes+" nodos leidos en "+(t1-t0)+"ms");
        
        return root;
    }
    
    private ConfigNode compactNodeList(List<ConfigNode> nodeList)
    {   
        ConfigNode rootNode=new ConfigNode("/",null);
        
        for(ConfigNode cn: nodeList)
        {
            String[] pathDecomp=cn.getPath().split(ConfigNode.PATH_SEPARATOR);
                        
            
            //System.out.println(">>"+cn.getPath());
            
            ConfigNode lastNode=rootNode;
            
            for(String subPath:pathDecomp)
            {
                if(subPath.isEmpty())
                    continue;
                
                ConfigNode actualNode=lastNode.getChild(subPath);
                
                if(actualNode==null){
                    actualNode=new ConfigNode(subPath,lastNode);
                    lastNode.addChild(actualNode);
                }
                
                lastNode=actualNode;
                
               // System.out.println(actualNode);
            }
            
            lastNode.setPath(cn.getPath());
            
            for(String s:cn.getContentLines())   
                lastNode.addContentLine(s);
        }
        
        return rootNode;
    }
    
    
    
}
