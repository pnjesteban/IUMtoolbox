package ium.toolbox.view;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;

import ium.toolbox.model.Data;
import ium.toolbox.model.interpreter.Evaluator;

public class TextPanel extends AbstractPanel {

    StyledText styledText=null;
    
    @Override
    protected void innerDraw(Composite innerPanel, int style) {
        
        styledText = new StyledText(innerPanel, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
     
    }
    
    @Override
    public void update(Data o) {
        
        String field=getProperty("field");
        
        Data d=new Evaluator().evaluate(field);
        
        styledText.setText(d.toString());
        
        
    }

}
