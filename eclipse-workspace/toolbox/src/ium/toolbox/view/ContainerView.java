package ium.toolbox.view;


import org.eclipse.swt.widgets.Composite;

public class ContainerView extends AbstractPanel {
    
    public ContainerView() {   
        
    }
    
    @Override
    protected void innerDraw(Composite parent, int style) {
        
        for(Renderable r:controls){
            
            r.draw(parent, style);
        }
    }    
}
