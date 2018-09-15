package ium.tools.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import ium.tools.actions.Action;
import ium.tools.config.info.ConfigMap;
import ium.tools.config.info.Host;
import ium.tools.config.info.MenuItem;
import ium.tools.config.info.Panel;
import ium.tools.console.commands.Command;

public class XMLParser {

	public final String FILE_DEPOT="cfg/configuration.xml";
	public final String ROOT_TAG="vf";
	public final String CLASS_TAG="className";
	public final String NAME_TAG="name";
	public final String TYPE_TAG="type";
	
	//listas de objetos cargados
	Hashtable<String, ArrayList<ConfigEntry>> objectList=new Hashtable<String, ArrayList<ConfigEntry>>();
	
	Document doc=null;
	
	private static XMLParser singleton=null;
	
	private XMLParser(){
		doc=openDoc();
		
	}
	
	public static XMLParser instance(){
		if(singleton==null)
			singleton=new XMLParser();
		
		return singleton;
	}
	
	
	private Document openDoc(){
		Document doc=null;
		try{
			File f=new File(FILE_DEPOT);
			if(!f.exists())
				return createDoc();
			
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(FILE_DEPOT);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return doc;
	}
	private Document createDoc(){
		Element rootElement=new Element(ROOT_TAG);
		
		Document doc=new Document(rootElement);
		
		return doc;
	}
	
	private void writeDoc(Document doc){
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		try {
			FileOutputStream fout=new FileOutputStream(FILE_DEPOT);
			outputter.output(doc, fout);       
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public ConfigEntry getGlobal(String name){
		ConfigEntry[] list=getStorableType("global");
		
		for(ConfigEntry st:list){
			if(st.getName().equals(name)){
				return st;
			}
		}
		return null;
	}
	
	
	public ConfigMap[] getConfigMap(String name){
        ConfigEntry[] list=getStorableType("ConfigMap");
        List<ConfigMap> retEntry=new ArrayList<ConfigMap>();
        for(ConfigEntry st:list){
            if(st.getName().equals(name))                
                retEntry.add((ConfigMap)st);
        }
        return retEntry.toArray(new ConfigMap[0]);
    }
	
	
	
	public ConfigEntry[] getInfoTypeList(){	
		return getStorableType("info");
	}
	
	public Action getActionByName(String name){
        ConfigEntry[] list=getStorableType("actions");
        
        for(ConfigEntry st:list){
            if(st.getName().equals(name)){
                return (Action)st;
            }
        }
        return null;
    }
	
	public Host[] getHostsList(){
        ConfigEntry[] st=getStorableType("Hosts");
        
        return Arrays.copyOf(st, st.length, Host[].class);
    }
	
	public ConfigEntry[] getCommandTypeList(){
		return getStorableType("commands");
	}
	
	public Command getCommand(String name){   
	    for(ConfigEntry st:getCommandTypeList()){
	        if(st.getName().equals(name))
	            return (Command)st;
	    }
        return null;
    }
	
	public Panel[] getPanelsList(){
	    ConfigEntry[] st=getStorableType("MainPanels");
	    
        return Arrays.copyOf(st, st.length, Panel[].class);
    }
	
	public MenuItem[] getMenuList(){
        ConfigEntry[] st=getStorableType("Menu");
        
        return Arrays.copyOf(st, st.length, MenuItem[].class);
    }
	
	
	public ConfigEntry[] getStorableType(String type){
		ArrayList<ConfigEntry> ret=objectList.get(type);
		
		if(ret==null){
			//se obtiene y se carga en la lista
			ret=loadStorableType(type);
			objectList.put(type, ret);
		}	
		
		return ret.toArray(new ConfigEntry[0]);
	}

	
	private ArrayList<ConfigEntry> loadStorableType(String type){
		ArrayList<ConfigEntry> ret=new ArrayList<ConfigEntry>();
		
		try{
			Document doc=openDoc();
			
			Element subType=doc.getRootElement().getChild(type);
			
			//String pack=subType.getAttributeValue(PACKAGE_TAG);
			
			List<Element> elementos=subType.getChildren();	
			
			for(Element subElement:elementos){
			    ConfigEntry storable=createStorableByNode(subElement);
				
				ret.add(storable);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
	
	
	private ConfigEntry createStorableByNode(Element node){
	    
	    ConfigEntry storable=null;
	    
	    if(node.getAttributeValue(CLASS_TAG)!=null){
	        storable=DefaultConfigDataset.instanceInfo(node.getAttributeValue(CLASS_TAG));
	        storable.setName(node.getAttributeValue(NAME_TAG));
	    }else{
	        //objetos que son solo un string
	        storable=new StringStorable();        
	        ((StringStorable)storable).setValue(node.getValue());   
	        storable.setName(node.getName());
	    }
        
        
        //carga de atributos
        List<Attribute> atributos=node.getAttributes();
        for(Attribute att:atributos){
            storable.addPropertyValue(att.getName(),att.getValue());
        }
        
        //carga de hijos
        List<Element> parametros=node.getChildren();
        ArrayList<ConfigEntry> list=new ArrayList<ConfigEntry>();
        
        for(Element p:parametros){
            if(p.getAttributeValue(CLASS_TAG)!=null){
                storable.addChildren(createStorableByNode(p));
            }
            else{
                String name=p.getName();
                String param=p.getValue();
                
                storable.addPropertyValue(name, param);
            }
        }
        
        return storable;
	}
	
	
	//TODO
	public void saveFile(ConfigEntry obj)throws JDOMException,IOException{	/*
		Document doc=openDoc();
		
		Element e=new Element(obj.getNodeType());
		
		e.setAttribute("name", obj.getName());
		
		StorablePair[] campos=null;//obj.getFields();
				
		List<Element> elementos=doc.getRootElement().getChildren(obj.getNodeType());
		
		boolean found=false;
		
		for(Element subElement:elementos)
		{
			if(subElement.getAttributeValue("name").equals(obj.getName()))
			{
				subElement.removeContent();

				for(StorablePair c:campos)
				{
					//TODO tipo lista no soportado
					if(c instanceof StorablePairSingle)
					{
						Element ec=new Element(((StorablePairSingle)c).name);
						ec.addContent(((StorablePairSingle)c).value);
						subElement.addContent(ec);
					}
				}
				found=true;
				break;
			}
		}
		
		if(!found)
		{
			for(StorablePair c:campos)
			{
				//TODO tipo lista no soportado
				if(c instanceof StorablePairSingle)
				{
					Element ec=new Element(((StorablePairSingle)c).name);
					ec.addContent(((StorablePairSingle)c).value);
					e.addContent(ec);
				}
			}		
			doc.getRootElement().addContent(e);
		}
		
		writeDoc(doc);
		*/
	}
}
