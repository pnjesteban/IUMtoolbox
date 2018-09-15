package ium.toolbox.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import ium.toolbox.model.DataLeaf;


public class ParseIumConfig {

    
    public static void main(String[] args){
        
        try{
            IUMConfigNode root=new ParseIumConfig().parse(new File("files/file.config"));
            
            System.out.println(root.printNode());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();            
        }
    }
    
    
    public IUMConfigNode parse(File f) throws IOException{
        
        long t0=System.currentTimeMillis();
        
        Reader reader=new InputStreamReader(new FileInputStream(f));       
        BufferedReader buff=new BufferedReader(reader);     

        String line="";
        int totalNodes=0;
        String header="";
        
        IUMConfigNode lastNode=null;
        
        List<IUMConfigNode> nodeList=new ArrayList<IUMConfigNode>();
        
        while((line=buff.readLine())!=null)
        {
            line=line.trim();
            
            if(line.isEmpty())
                continue;
            
            if(line.startsWith(IUMConfigNode.OPEN_CONFIG_ENTRY) && line.endsWith(IUMConfigNode.CLOSE_CONFIG_ENTRY)){                
                
                lastNode=new IUMConfigNode(line.replace(IUMConfigNode.OPEN_CONFIG_ENTRY, "").replace(IUMConfigNode.CLOSE_CONFIG_ENTRY, ""));
                
                nodeList.add(lastNode);
                
                totalNodes++;
            }
            else
                lastNode.addContentLine(line);
        }
        long t1=System.currentTimeMillis();
        
        
        IUMConfigNode root=compactNodeList(nodeList);
        
        System.out.println(totalNodes+" nodos leidos en "+(t1-t0)+"ms");
        
        return root;
    }
    
    private IUMConfigNode compactNodeList(List<IUMConfigNode> nodeList)
    {   
        IUMConfigNode rootNode=new IUMConfigNode("/",null);
        
        for(IUMConfigNode cn: nodeList)
        {
            String[] pathDecomp=cn.getPath().split(IUMConfigNode.PATH_SEPARATOR);
                        
            
            //System.out.println(">>"+cn.getPath());
            
            IUMConfigNode lastNode=rootNode;
            
            for(String subPath:pathDecomp)
            {
                if(subPath.isEmpty())
                    continue;
                
                IUMConfigNode actualNode=lastNode.getNode(subPath);
                
                if(actualNode==null){
                    actualNode=new IUMConfigNode(subPath,lastNode);
                    lastNode.addNode(actualNode);
                }
                
                lastNode=actualNode;
                
                //System.out.println(actualNode);
            }
            
            lastNode.setPath(cn.getPath());
            
            for(DataLeaf l:cn.getLeafs()){
                lastNode.addData(l);
                
            }
            
            /*for(String s:cn.getContentLines())   
                lastNode.addContentLine(s);*/
        }
        
        return rootNode;
    }
    
    
    
}
