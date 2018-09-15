package ium.tools.config.info;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ium.tools.composite.Renderable;
import ium.tools.config.DefaultConfigDataset;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.parser.ConfigNode;

public class Text extends DefaultConfigDataset implements Renderable {

    Renderable master=null;
    
    
    public String getViewMethod(){
        return (String)getPropertyValue("viewMethod");
    }
    
    @Override
    public Control draw(Composite parent, int style) {

        org.eclipse.swt.widgets.Text text = new org.eclipse.swt.widgets.Text(parent, SWT.BORDER);
        
        text.addModifyListener(new ModifyListener() {
            
            @Override
            public void modifyText(ModifyEvent arg0) {
                
                try {
                    master.getClass().getMethod(getViewMethod(), String.class).invoke(master, text.getText());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        return text;
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
    public int getPanel() {
        // TODO Auto-generated method stub
        return 0;
    }
}
