package ium.tools.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class PrbShell extends Shell {
    private Text text;
    private Table table;
    
    
    /**
     * Launch the application.
     * @param args
     */
    public static void main(String args[]) {
        try {
            Display display = Display.getDefault();
            PrbShell shell = new PrbShell(display);
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

    /**
     * Create the shell.
     * @param display
     */
    public PrbShell(Display display) {
        super(display, SWT.SHELL_TRIM);
        setSize(450, 387);
        setLayout(new FillLayout(SWT.HORIZONTAL));
        
        Composite panel=new Composite(this, SWT.NONE);
        
        Composite composite = new Composite(panel, SWT.H_SCROLL | SWT.V_SCROLL);
        composite.setBounds(10, 25, 152, 167);
        
        text = new Text(panel, SWT.BORDER);
        text.setBounds(10, 0, 76, 19);
        
        List list = new List(panel, SWT.BORDER);
        list.setBounds(91, 217, 71, 68);
        
        Label lblNewLabel = new Label(panel, SWT.NONE);
        lblNewLabel.setBounds(10, 221, 49, 13);
        lblNewLabel.setText("New Label");
        
        table = new Table(panel, SWT.BORDER | SWT.FULL_SELECTION);
        table.setBounds(178, 25, 164, 84);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        TableColumn tblclmnColl = new TableColumn(table, SWT.NONE);
        tblclmnColl.setWidth(100);
        tblclmnColl.setText("coll");
        
        TableItem tableItem = new TableItem(table, SWT.NONE);
        tableItem.setText("New TableItem");
        
        TableItem tableItem_1 = new TableItem(table, SWT.NONE);
        tableItem_1.setText("New TableItem");
        
        Menu menu = new Menu(this, SWT.BAR);
        setMenuBar(menu);
        
        MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
        mntmNewSubmenu.setText("New SubMenu");
        
        Menu menu_1 = new Menu(mntmNewSubmenu);
        mntmNewSubmenu.setMenu(menu_1);
        
        MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
        mntmNewItem.setText("New Item");
        
        MenuItem mntmNewItem_1 = new MenuItem(menu_1, SWT.NONE);
        mntmNewItem_1.setText("New Item");
        
        MenuItem mntmNewSubmenu_1 = new MenuItem(menu_1, SWT.CASCADE);
        mntmNewSubmenu_1.setText("New SubMenu");
        
        Menu menu_2 = new Menu(mntmNewSubmenu_1);
        mntmNewSubmenu_1.setMenu(menu_2);
    }

    /**
     * Create contents of the shell.
     */
    protected void createContents() {
        
        setText("SWT Application");
        setSize(450, 300);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
