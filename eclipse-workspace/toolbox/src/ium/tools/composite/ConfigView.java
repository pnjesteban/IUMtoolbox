package ium.tools.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ium.tools.LaunchShell;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.parser.ConfigNode;

public class ConfigView implements Renderable{
    
    private Composite panel;
    
    private StyledText text;
    private Tree treeConfig;
    
    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public ConfigView() {
        
    }
    
    private void createNodes(TreeItem top,ConfigNode nodeObject) {

        TreeItem node;
        for(ConfigNode n:nodeObject.getChilds()){
            if(top==null)
                node=new TreeItem(treeConfig,0);
            else
                node=new TreeItem(top,0);
            
            node.setText(n.toString());
            node.setData(n);
            node.setExpanded(true);
            createNodes(node,n);
        }
    }
    
    
    private void setText(Object o){
        
        if(o instanceof ConfigNode)
            text.setText(((ConfigNode)o).toStringFull());
        codeStyle(text);
    }
    
    private void codeStyle(StyledText text )
    {
        StyleRange styleRange = new StyleRange();
        
        int index0=text.getText().indexOf(ConfigNode.OPEN_CONFIG_ENTRY);
        int index1=text.getText().indexOf(ConfigNode.CLOSE_CONFIG_ENTRY,index0);
                
        styleRange.start = index0;
        styleRange.length = index1;
        styleRange.fontStyle = SWT.BOLD;
        styleRange.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA);

        text.setStyleRange(styleRange);
    }
    
    
    public Composite draw(Composite parent, int style)
    {
        panel=new Composite(parent, style);
        panel.setLayout(new FillLayout());

        SashForm sashForm = new SashForm(panel, SWT.NONE);
        
        treeConfig = new Tree(sashForm, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL );

        treeConfig.addListener(SWT.Selection, new Listener() {
             public void handleEvent(Event event) {
                 if(event.item instanceof TreeItem){
                     setText(event.item.getData());                     
                 }
             }
        });
        
        text = new StyledText(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
        
        return panel;
    }


    @Override
    public void update(ConfigNode rootNode) {
        
        LaunchShell.shell.getDisplay().syncExec(new Runnable() {
            public void run() {
        
        treeConfig.removeAll();
        text.setText("");
        createNodes(null,rootNode);
        
            }
        });
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
