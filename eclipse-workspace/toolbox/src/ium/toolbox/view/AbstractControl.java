package ium.toolbox.view;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractControl extends AbstractRenderable {

        
    @Override
    public final Control draw(Composite parent, int style) {
    
        return innerDraw(parent,style);
        
    }
    
    protected abstract Control innerDraw(Composite parent, int style);
    
    @Override
    public void setControls(List<Renderable> controls) {
        //no hay controles en un control....
        
    }

}
