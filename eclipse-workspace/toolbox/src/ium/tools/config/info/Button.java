package ium.tools.config.info;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ium.tools.actions.Action;
import ium.tools.composite.Renderable;
import ium.tools.config.DefaultConfigDataset;
import ium.tools.config.XMLParser;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.parser.ConfigNode;

public class Button extends DefaultConfigDataset implements Renderable {
       
    String actionPropName="action";
    String viewMethodPropName="viewMethod";
    
    Renderable master=null;
   
    public Action getAction()
    {   
        String actionStr=(String)getPropertyValue(actionPropName);
        return XMLParser.instance().getActionByName(actionStr);
    }

    public String getViewMethod(){
        return (String)getPropertyValue("viewMethod");
    }
        
    @Override
    public Control draw(Composite parent, int style) {

        org.eclipse.swt.widgets.Button b=new org.eclipse.swt.widgets.Button(parent,style);
        
        b.setText(getName());
        
        b.addSelectionListener(new SelectionListener() {
            
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                getAction().execute();
                /*try {
                    
                    master.getClass().getMethod(getViewMethod(), String.class).invoke(master, getAction().getName());
                    
                } catch (Exception e) {

                    e.printStackTrace();
                }*/
            }
            
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        
        return b;
    }
    
    @Override
    public void setMaster(Renderable master){
        this.master=master;
    }

    @Override
    public void addControls(Renderable[] controls){
        // TODO Auto-generated method stub
        
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
    public int getPanel(){
        String s=(String)getPropertyValue("panel");
        
        if(s==null || s.isEmpty())
            return 0;
        
        return Integer.parseInt(s);
    }

}
