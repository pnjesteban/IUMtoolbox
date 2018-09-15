package ium.tools.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import ium.tools.LaunchShell;
import ium.tools.config.ConsoleListener;
import ium.tools.config.info.Host;
import ium.tools.console.ConsoleManager;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.data.GlobalScopeManager;
import ium.tools.parser.ConfigNode;

public class HostView implements Renderable, ConsoleListener {

    Label lblIUMVer;
    Label lblHost;
    StyledText styledText;
        
    public HostView() {
     
        ConsoleManager.instance().addListener(this);
        
    }
    
    @Override
    public void update(ConfigNode node) {

        ConfigNode configServerNode=node.getChild("ConfigServer");
        
        if(configServerNode!=null)
        {
            for(String s:configServerNode.getContentLines()){
                if(s.startsWith("IumVer"))
                    lblIUMVer.setText(s);
            }
        }
        
        Object obj=GlobalScopeManager.instance().getConfig("Host");
        
        if(obj!=null){
            Host host=((Host)obj);
            
            lblHost.setText(host.getName()+"="+host.getUserName()+"@"+host.getHostname()+":"+host.getPort());
        }

    }

    @Override
    public Composite draw(Composite parent, int style) {
                       
        Composite panel=new Composite(parent, style);
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 4;
        
        panel.setLayout(gridLayout);
        
        
        Button boton1=new Button(panel, SWT.NONE);
        boton1.setText("Cargar configuracion");
        Button boton2=new Button(panel, SWT.NONE);
        boton2.setText("formatear maquina remota");
        Button boton3=new Button(panel, SWT.NONE);
        boton3.setText("purgar base de datos");
        Button boton4=new Button(panel, SWT.NONE);
        
        Group grpIum = new Group(panel, SWT.NONE);
        grpIum.setText("ium");
        grpIum.setLayout(new FillLayout());
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalSpan = 2;
        grpIum.setLayoutData(gridData);

        lblIUMVer = new Label(grpIum, SWT.NONE);
        lblIUMVer.setText("IumVer");
        
        Group grpHost = new Group(panel, SWT.NONE);
        grpHost.setText("Host");
        grpHost.setLayout(new FillLayout());
        
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalSpan = 2;
        grpHost.setLayoutData(gridData);
        
        lblHost = new Label(grpHost, SWT.NONE);
        lblHost.setText("Host");
        
        Group grpConsole = new Group(panel, SWT.NONE);
        grpConsole.setText("Console");
        grpConsole.setLayout(new FillLayout());
        
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan = 4;
        grpConsole.setLayoutData(gridData);
        
        styledText = new StyledText(grpConsole, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
       
        return panel;
    }
    
    public void addText(String text, int type){

        LaunchShell.shell.getDisplay().syncExec(new Runnable() {
            public void run() {
        
                StyleRange style=null;
                
                if(type==ConsoleListener.ERROR){
                    style=new StyleRange(styledText.getText().length(), 
                            text.length(),
                            Display.getCurrent().getSystemColor(SWT.COLOR_RED) , 
                            Display.getCurrent().getSystemColor(SWT.COLOR_WHITE),
                            SWT.BOLD );
                }
                
                else if(type==ConsoleListener.BOLD){
                    style=new StyleRange(styledText.getText().length(), 
                            text.length(),
                            Display.getCurrent().getSystemColor(SWT.COLOR_BLACK) , 
                            Display.getCurrent().getSystemColor(SWT.COLOR_WHITE),
                            SWT.BOLD );
                }

                styledText.append(text+"\n");

                if(type!=ConsoleListener.INFO)
                    styledText.setStyleRange(style);
        
            }
        });
    }

    @Override
    public void setConnect(int status) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void initProgress(int min, int max) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setProgress(int actual) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addControls(Renderable[] controls) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setMaster(Renderable master) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Dataset o) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getPanel() {
        // TODO Auto-generated method stub
        return 0;
    }

}
