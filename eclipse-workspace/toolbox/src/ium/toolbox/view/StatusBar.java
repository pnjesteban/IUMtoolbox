package ium.toolbox.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.TabItem;

public class StatusBar extends AbstractPanel{

    Label lblNewLabel;
    Label lblConnect;
    Label lblProgressLabel;
    ProgressBar progressBar;
    Image connectIcon;
    Image workIcon;
    
    boolean isWorking=false;

    
    @Override
    protected void innerDraw(Composite innerPanel, int style) {
        
        

        
        for(Renderable r:controls){
            
            r.draw(innerPanel, style);
        }
        
        /*
       Label lblNewLabel_1 = new Label(innerPanel, SWT.BORDER);
       
        
        lblNewLabel = new Label(innerPanel, SWT.BORDER);
        lblNewLabel.setText("");
                
        progressBar = new ProgressBar(innerPanel, SWT.NONE);
        initProgress(0,100);
        
        lblProgressLabel = new Label(innerPanel, SWT.BORDER);
        lblProgressLabel.setText("  0%");
        */
        
        connectIcon = new Image(innerPanel.getDisplay(),"icons/1x/baseline_cast_black_18dp.png");
        workIcon = new Image(innerPanel.getDisplay(),"icons/1x/baseline_cast_connected_black_18dp.png");
        
        lblConnect = new Label(innerPanel,  SWT.NONE);
        //lblConnect.setText("XX");
        lblConnect.setImage(connectIcon);
        lblConnect.setEnabled(false);
        
        /*
        RowData data = new RowData();
        data.exclude=true;
        lblConnect.setLayoutData(data);*/
        
        /*
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_END;
       // gridData.verticalAlignment = GridData.VERTICAL_ALIGN_END;
       // gridData.grabExcessHorizontalSpace = true;
        //gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan=4;
        
        lblConnect.setLayoutData(gridData);*/
        
        
    }
    

    
    
    private void updateActionIcon(){
        
        //LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
         //   public void run() {
        
        lblConnect.setImage(workIcon);
        lblConnect.update();
        try{
            
            Thread.sleep(300);
        }catch(InterruptedException ex){}
        
        
       lblConnect.setImage(connectIcon);
       lblConnect.update();
                   
        //lblConnect.redraw();
        
        isWorking=!isWorking;
        
      //      }
       // });
        
        
    }

    public void setText(String s){
        
        lblNewLabel.setText(s);
        
    }
/*

    public void addText(String text, int type) {
        
        LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
            public void run() {
        
        updateActionIcon(); 
        
            }
        });
    }
    
    

    public void setConnect(int status) {
      
        LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
            public void run() {
        
                    lblConnect.setEnabled(status==ConsoleListener.STATUS_CONNECTED);
      
            }
        });
    }


    public void setProgress(int actual) {
        
        LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
            public void run() {
                if(progressBar.getSelection()!=actual){
                    lblProgressLabel.setText(actual+"%");
                    progressBar.setSelection(actual);
                    updateActionIcon();
                }
            }
        });
    }

*/
    public void initProgress(int min, int max) {
        
        innerPanel.getDisplay().asyncExec(new Runnable() {
            public void run() {        
                progressBar.setMinimum(min);
                progressBar.setMaximum(max);
                progressBar.setSelection(min);
            }
        });
    }


}
