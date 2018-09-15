package ium.tools.composite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import ium.tools.LaunchShell;
import ium.tools.config.ConsoleListener;
import ium.tools.console.ConsoleManager;

public class StatusBar extends Composite implements ConsoleListener{

    Label lblNewLabel;
    Label lblConnect;
    Label lblProgressLabel;
    ProgressBar progressBar;
    Image connectIcon;
    Image workIcon;
    
    boolean isWorking=false;
    
    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public StatusBar(Composite parent, int style) {
        
        super(parent, SWT.NONE);
        
        setLayout(new RowLayout());
        
        Label lblNewLabel_1 = new Label(this, SWT.BORDER);
        lblNewLabel_1.setText("");
        
        lblNewLabel = new Label(this, SWT.BORDER);
        lblNewLabel.setText("");
                
        progressBar = new ProgressBar(this, SWT.NONE);
        initProgress(0,100);
        lblProgressLabel = new Label(this, SWT.BORDER);
        lblProgressLabel.setText("  0%");
        
        connectIcon = new Image(getDisplay(),"icons/1x/baseline_cast_black_18dp.png");
        workIcon = new Image(getDisplay(),"icons/1x/baseline_cast_connected_black_18dp.png");
        
        lblConnect = new Label(this, SWT.BORDER | SWT.SMOOTH);
        lblConnect.setText("XX");
        lblConnect.setImage(connectIcon);
        lblConnect.setEnabled(false);
                
        ConsoleManager.instance().addListener(this);
        
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
    
    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

    @Override
    public void addText(String text, int type) {
        
        LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
            public void run() {
        
        updateActionIcon(); 
        
            }
        });
    }
    
    
    @Override
    public void setConnect(int status) {
      
        LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
            public void run() {
        
                    lblConnect.setEnabled(status==ConsoleListener.STATUS_CONNECTED);
      
            }
        });
    }

    @Override
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

    @Override
    public void initProgress(int min, int max) {
        
        if(LaunchShell.shell!=null){
        LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
            public void run() {
        
                progressBar.setMinimum(min);
                progressBar.setMaximum(max);
                progressBar.setSelection(min);
        
            }
         });
        }
    }
}
