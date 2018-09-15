package ium.tools;



import java.util.Hashtable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import ium.tools.actions.Action;
import ium.tools.composite.Renderable;
import ium.tools.composite.StatusBar;
import ium.tools.config.ConfigEntry;
import ium.tools.config.XMLParser;
import ium.tools.config.info.Host;
import ium.tools.config.info.Panel;
import ium.tools.data.GlobalScopeManager;




public class LaunchShell extends Shell {
	
    
    public static LaunchShell shell;
    
    private Hashtable<String, Renderable> renderableObjects=new Hashtable<String,Renderable>();
    
    
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new LaunchShell(display);
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

	
	public static LaunchShell getInstance() {
        return shell;
    }


    private void createShell(Display display){
	    
	    
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        setLayout(gridLayout);
        
	    loadMenus();
        
        DropTarget dropTarget = new DropTarget(this, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
        
        final FileTransfer fileTransfer = FileTransfer.getInstance();
        dropTarget.setTransfer(new Transfer[] {fileTransfer});
        /*
        dropTarget.addDropListener(new DropTargetListener() {
            @Override
            public void drop(DropTargetEvent event) {                
                if (fileTransfer.isSupportedType(event.currentDataType)){
                    String[] files = (String[])event.data;
                    loadFile(files[0]);
                }
            }
            @Override
            public void dropAccept(DropTargetEvent arg0) {}
            @Override
            public void dragOver(DropTargetEvent arg0) {}
            @Override
            public void dragOperationChanged(DropTargetEvent arg0) {}
            @Override
            public void dragLeave(DropTargetEvent arg0) {}
            @Override
            public void dragEnter(DropTargetEvent arg0) {}
        });
        */
        TabFolder tabFolder = new TabFolder(this, SWT.NONE);
        tabFolder.setBounds(10, 10, 422, 253);
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;        
        tabFolder.setLayoutData(gridData);
        
        
	    Panel[] panels=XMLParser.instance().getPanelsList();
	    Composite innerPanel;
	    for(Panel panel:panels){

	       TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
	       tabItem.setText(panel.getName());
	       
	       Renderable r=panel.instance();
	       
	       innerPanel=(Composite)r.draw(tabFolder,SWT.NONE);
	       
	       tabItem.setControl(innerPanel);
	       
	       renderableObjects.put(panel.getName(),r);
	    }
	    
	    StatusBar stBar=new StatusBar(this, SWT.NONE);
	    
	    gridData = new GridData();
        gridData.verticalAlignment = GridData.END;
        gridData.horizontalAlignment=GridData.VERTICAL_ALIGN_END;
        //gridData.grabExcessHorizontalSpace = true;
        stBar.setLayoutData(gridData);
	    
	}
	
	protected void executeAction(Action act,MenuItem item){
	    if(act!=null){
	        
	        act.setData((ConfigEntry)item.getData());
	        act.execute();
	    }   
	}
		
	private void loadMenus(){
	    
	    Menu menuBar = new Menu(this, SWT.BAR);
        setMenuBar(menuBar);
        
        
        ium.tools.config.info.MenuItem[] menus=XMLParser.instance().getMenuList();
        
        for( ium.tools.config.info.MenuItem item:menus){
            
            MenuItem menu = new MenuItem(menuBar, SWT.CASCADE);
            menu.setText(item.getName());
            
            Menu menuList = new Menu(menu);
            menu.setMenu(menuList);
            
            ium.tools.config.info.MenuItem[] subItems=item.getSubMenus();
            
            if(subItems!=null){                
                for( ium.tools.config.info.MenuItem subitem:subItems){                
                    
                    
                    int menuType=SWT.NONE;
                    
                    if(subitem.getType().equals("cascade"))
                    {
                        menuType=SWT.CASCADE;
                    }
                    
                    MenuItem subMenu = new MenuItem(menuList, menuType);
                    subMenu.setText(subitem.getName());
                    subMenu.setData(subitem);

                    ConfigEntry[] st=subitem.getload();
                    
                    if(st!=null){
                    
                        Menu submenuList = new Menu(subMenu);
                        subMenu.setMenu(submenuList);
                    
                        for(ConfigEntry s:st){
                            MenuItem subsubMenu = new MenuItem(submenuList, SWT.NONE);
                            subsubMenu.setText(s.getName());
                            subsubMenu.setData(s);
                            subsubMenu.addSelectionListener(new SelectionListener() {
                                
                                @Override
                                public void widgetSelected(SelectionEvent arg0) {
                                    
                                    System.out.println("menu action! "+s+": "+s.getClass());
                                    
                                    GlobalScopeManager.instance().setObject(s.getNodeType(), s,false);
                                    
                                    executeAction(subitem.getAction(),subsubMenu);
                                }
                                @Override
                                public void widgetDefaultSelected(SelectionEvent arg0) {}
                            });
                        }
                    }
                    else{
                        subMenu.addSelectionListener(new SelectionListener() {
                            
                            @Override
                            public void widgetSelected(SelectionEvent arg0) {
                                executeAction(subitem.getAction(),subMenu);
                            }
                            @Override
                            public void widgetDefaultSelected(SelectionEvent arg0) {}
                        });
                    }
                }
            }
        }
	}
	/*
	public void loadFile(String file){
    
	    if(file!=null){
	        
	       // setCursor(new Cursor(getDisplay(), SWT.CURSOR_WAIT));
	        
	        LaunchShell.shell.getDisplay().asyncExec(new Runnable() {
	            public void run() {
	        
        	        ParseIumConfig parser=new ParseIumConfig();
        	        ConfigNode rootNode=null;
        	        try{
        	            rootNode=parser.parse(new File(file));                
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
        	        
        	        Set<String> keys = renderableObjects.keySet();
        	        for(String key: keys){
        	            
        	            Object o=renderableObjects.get(key);
        	            
        	            if(o instanceof IUMConfigListener){
        	                ((IUMConfigListener)o).setRootNode(rootNode);
        	            }
        	        }
	        
	            }
	        });
	    }
	    //setCursor(new Cursor(getDisplay(), SWT.CURSOR_ARROW));
	            
	}
	*/
	
	public void loadRemote(Host host){
	    System.out.println("Cargando "+host);
	    
	    System.out.println(XMLParser.instance().getGlobal("remoteWork"));
	    
	}
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public LaunchShell(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		createShell(display);

		createContents();
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("IUM Explorer");
		setSize(639, 481);

	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
