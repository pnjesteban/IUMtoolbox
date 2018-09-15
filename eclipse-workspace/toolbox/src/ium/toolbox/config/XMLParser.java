package ium.toolbox.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import ium.toolbox.model.ContainerData;
import ium.toolbox.model.Data;
import ium.toolbox.model.DataLeaf;
import ium.toolbox.model.DataNode;
import ium.toolbox.model.StringData;
import ium.toolbox.model.cfg.Action;


public class XMLParser {

	public final String FILE_DEPOT="cfg/appConfig.xml";
	public final String ROOT_TAG="vf";
	public final String CLASS_TAG="className";
	public final String NAME_TAG="name";
	public final String TYPE_TAG="type";
	
	public final String STRING_CLASS_TYPE="ium.toolbox.model.StringData";
	
	
	Document doc=null;
	
	private static XMLParser singleton=null;
	
	private XMLParser() throws IOException,JDOMException{
	    doc=openDoc();
	}
	
	public static XMLParser instance() {
		if(singleton==null)
            try {
                singleton=new XMLParser();
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JDOMException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		
		return singleton;
	}
	
	
	private Document openDoc() throws JDOMException, IOException {
		Document doc=null;
		
		File f=new File(FILE_DEPOT);
		if(!f.exists())
		    throw new FileNotFoundException("FATAL: no configuration file "+FILE_DEPOT);
			
		SAXBuilder builder = new SAXBuilder();
		doc = builder.build(FILE_DEPOT);
		
		return doc;
	}	
	
	public Action getActionData(String name){
      
        List<DataNode> acciones=getConfigDataList("actions").getNodes();
        
        for(DataNode a:acciones){
            if(a.getName().equals(name))
                return (Action)a;
        }
        
        return null;     
    }
	
	public <T extends DataNode> T getConfigDataList(String name){
	    
        Element subType=doc.getRootElement().getChild(name);
        List<Element> elementos=subType.getChildren();  
        
        ContainerData cd=new ContainerData();        
        
        for(Element subElement:elementos){
            Data data=createConfigData(subElement);
            cd.addData(data);
        }
        
        return (T)cd;
	}
	
	private Data createConfigData(Element node) {
	    
	    Data configData=null;
        
	    //se busca una clase forzada
	    String className=node.getAttributeValue(CLASS_TAG);
	    //si no se busca con el nombre del tag
	    if(className==null){
	        String pack="ium.toolbox.model.cfg";//TODO hacerlo configurable
	        String classname=node.getName();
	        try{
	            className=pack+"."+classname;
	            Class.forName(className);
	        }catch(Exception ex){
	            //si todo falla se instancia por defecto
	            className=STRING_CLASS_TYPE;
	        }
	    }
	    try{
	        configData = (Data)Class.forName(className).newInstance();
	    }catch(Exception ex){
	        ex.printStackTrace();
	        return null;
	    }
	    
	    if(configData instanceof DataLeaf){
	        ((DataLeaf)configData).setName(node.getName());
	        ((DataLeaf)configData).setValue(node.getText());
	    }
	    if(configData instanceof DataNode){
    	    //basic info
    	    //((DataNode)configData).addData(new StringData(node.getName(), node.getText().trim()));

            //carga de atributos
            List<Attribute> atributos=node.getAttributes();
            for(Attribute att:atributos){
                StringData d=new StringData();
                d.setName(att.getName());
                d.setValue(att.getValue());
                ((DataNode)configData).addData(d);
            }
            //carga de hijos
            List<Element> childs=node.getChildren();
            for(Element c:childs){
                ((DataNode)configData).addData(createConfigData(c));
            }
	    }
        return configData;
	}
	
	public static void main(String[] args){
	    
	    try{
	        DataNode cfg=XMLParser.instance().getConfigDataList("MainPanel");
	        
	        cfg.getNodes().forEach(System.out::println);
	    }
	    catch(Exception ex){
	        
	        ex.printStackTrace();
	    }
	    
	    
	    
	}
	
}
