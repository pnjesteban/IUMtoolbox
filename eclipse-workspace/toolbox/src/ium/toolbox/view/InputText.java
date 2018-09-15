package ium.toolbox.view;


import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import ium.toolbox.model.Data;
import ium.toolbox.model.DataManager;
import ium.toolbox.model.DataNode;
import ium.toolbox.model.interpreter.Evaluator;



public class InputText extends AbstractControl {
        
    @Override
    protected Control innerDraw(Composite parent, int style) {
        
        Control ret=null;
        
        String type=getProperty("type");

        if(type.equals("combo")){
            final Combo c=new Combo(parent, style | SWT.READ_ONLY);
            String input=getProperty("input");
            String storeAs=getProperty("storeAs");
            
            DataNode node=(DataNode)new Evaluator().evaluate(input);
            
            
            List<DataNode> list=node.getNodes();
            list.forEach(l -> c.add( l.getName() )  );
            
            c.addSelectionListener(new SelectionListener() {
                
                @Override
                public void widgetSelected(SelectionEvent arg0) {
                    
                    DataManager.instance().put(storeAs,list.get(c.getSelectionIndex()));
                    /*

                    String onSelect=getProperty("onSelect");
                    Action a=XMLParser.instance().getActionData(onSelect);
                    a.instance().execute();    */                
                }
                @Override
                public void widgetDefaultSelected(SelectionEvent arg0) {}
            });
            ret=c;
            
        }
        else if(type.equals("text")){
            ret=new Text(parent, style);
        }
                
        return ret;
    }

}
