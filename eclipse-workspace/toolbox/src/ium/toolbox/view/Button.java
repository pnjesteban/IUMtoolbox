package ium.toolbox.view;


import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class Button extends AbstractControl {
        
    @Override
    protected Control innerDraw(Composite parent, int style) {

        org.eclipse.swt.widgets.Button b=new org.eclipse.swt.widgets.Button(parent,style);
        
        b.setText(getProperty("title"));
        
        b.addSelectionListener(new SelectionListener() {
            
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                getAction().execute();
            }
            
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        
        
        return b;
    }
    
}
