package ium.tools.config.info;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ium.tools.composite.Renderable;
import ium.tools.composite.RenderableControl;
import ium.tools.config.DefaultConfigDataset;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.parser.ConfigNode;

public class List extends DefaultConfigDataset implements RenderableControl {

    Renderable master=null;
    
    org.eclipse.swt.widgets.List list;
    
    public String getViewMethod(){
        return (String)getPropertyValue("viewMethod");
    }
    
    
    public int getPanel(){
        String s=(String)getPropertyValue("panel");
        
        if(s.isEmpty())
            return 0;
        
        return Integer.parseInt(s);
    }
    
    public String getField(){
        return (String)getPropertyValue("field");        
    }
    
    public String getTitle(){
        return (String)getPropertyValue("title");        
    }
    
    @Override
    public Control draw(Composite parent, int style) {

        list = new org.eclipse.swt.widgets.List(parent, style);        
        return list;
    }
    
    @Override
    public void addControls(Renderable[] controls) {}
    
    @Override
    public void setMaster(Renderable master){
        this.master=master;
    }

    @Override
    public void update(Dataset o) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(ConfigNode o) {
        // TODO Auto-generated method stub
    }


    @Override
    public void putText(String text) {
        
        list.add(text);
        list.pack();
        
    }
}
