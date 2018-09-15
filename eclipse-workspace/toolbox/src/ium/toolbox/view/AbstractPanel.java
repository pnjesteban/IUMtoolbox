package ium.toolbox.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;



public abstract class AbstractPanel extends AbstractRenderable {

    List<Renderable> controls=new ArrayList<Renderable>();
    
    protected Composite innerPanel;
    
    @Override
    public final Control draw(Composite parent, int style) {
        
        innerPanel=new Composite(parent, style );
        
        //layout
        
        /***
         *  BEGINNING = 1;
            CENTER = 2;
            END = 3;
            FILL = 4;
            VERTICAL_ALIGN_END = 8;
         */
        
        
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = getIntProperty("alignmentH", 4);
        gridData.verticalAlignment = getIntProperty("alignmentV", 4);
        gridData.grabExcessHorizontalSpace = getBooleanProperty("grabH", true);
        gridData.grabExcessVerticalSpace = getBooleanProperty("grabV", true);
        gridData.horizontalSpan = getIntProperty("span",1);
        innerPanel.setLayoutData(gridData);
        
        
        String layout=getProperty("layout");
        if(layout!=null && layout.equals("fill")){
            innerPanel.setLayout(new FillLayout());
        }else if(layout!=null && layout.equals("horizontal")){
            innerPanel.setLayout(new RowLayout());
            //innerPanel.setLayout(new FillLayout(SWT.HORIZONTAL));
        }else if(layout!=null && layout.equals("vertical")){
            innerPanel.setLayout(new FillLayout(SWT.VERTICAL));
        } else {
            GridLayout gridLayout = new GridLayout();
            gridLayout.numColumns = getIntProperty("columns",1);
            innerPanel.setLayout(gridLayout);
        }
        
        innerDraw(innerPanel,style);
        
        return innerPanel;
    }
    
    private int getIntProperty(String propName, int defaultValue){
        
        int val=defaultValue;
        try{
            val=Integer.parseInt( getProperty(propName) );
        }catch(NumberFormatException e){
            System.out.println("WARNING missing parameter '"+propName+"' on view "+getClass()+" default:"+defaultValue);
        }
        
        System.out.println(getClass()+"."+propName+"="+val);
        
        return val;
    }
    
    private boolean getBooleanProperty(String propName, boolean defaultValue){
        
        if(getProperty(propName)==null){            
            System.out.println("WARNING missing parameter '"+propName+"' on view "+getClass()+" default:"+defaultValue);
            return defaultValue;
        }    
        
        return Boolean.getBoolean( getProperty(propName) );
        
    }
    
    
    
    protected abstract void innerDraw(Composite innerPanel, int style);
    
    @Override
    public void setControls(List<Renderable> controls) {
        this.controls=controls;
    }
    
    
    
    

}
