package ium.toolbox.view;




import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ium.toolbox.model.Data;
import ium.toolbox.model.interpreter.Evaluator;

public class Label extends AbstractControl {
        
    org.eclipse.swt.widgets.Label l=null;
    
    @Override
    protected Control innerDraw(Composite parent, int style) {

        l=new org.eclipse.swt.widgets.Label(parent,style);
        l.setText(getProperty("title"));
        
        return l;
    }
    
    @Override
    public void update(Data o) {

        String field=getProperty("field");
        
        Data d=new Evaluator().evaluate(field);
        
        l.setText(getProperty("title")+d);
        l.getParent().pack();
    }
}
