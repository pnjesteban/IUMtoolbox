package ium.toolbox.view;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ium.toolbox.actions.Actionable;

public interface Renderable {
    
    public Control draw(Composite parent, int style);
    
    public void setControls(List<Renderable> controls);
    
    public void setProperties(Map<String,String> properties);
    
    public String getProperty(String name);
    
    public void setAction(Actionable action);
    
}
