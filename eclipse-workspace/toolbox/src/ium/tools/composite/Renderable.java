package ium.tools.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ium.tools.data.GlobalScopeListener;

public interface Renderable extends GlobalScopeListener{
    
    public Control draw(Composite parent, int style);
    
    public void addControls(Renderable[] controls);
    
    public void setMaster(Renderable master);
    
    public int getPanel();

}
