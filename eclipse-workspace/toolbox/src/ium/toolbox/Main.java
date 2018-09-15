package ium.toolbox;



import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ium.toolbox.config.XMLParser;
import ium.toolbox.model.Data;
import ium.toolbox.model.cfg.Panel;
import ium.toolbox.view.Renderable;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;


public class Main extends Shell {
	
    
    public static Main shell;
    
    //private Hashtable<String, Renderable> renderableObjects=new Hashtable<String,Renderable>();
    
    
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new Main(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static Main getInstance() {
        return shell;
    }


    private void createShell(Display display){
	    
	    try{
	        
	        GridLayout gridLayout = new GridLayout();
            gridLayout.numColumns = 1;
            setLayout(gridLayout);
            
            XMLParser.instance().getConfigDataList("MainPanel")
                .getNodes()
                .stream()
                .map(Panel.class::cast)
                .forEach(p -> p.instance().draw(this, SWT.NONE));
    	    

	    }catch(Exception ex){
	        
	        ex.printStackTrace();
	    }
	}
		
	/**
	 * Create the shell.
	 * @param display
	 */
	public Main(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		createShell(display);

		createContents();
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("IUM Explorer Mark 3");
		setSize(639, 481);

	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
