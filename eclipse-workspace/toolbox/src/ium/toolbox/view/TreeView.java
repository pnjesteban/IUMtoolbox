package ium.toolbox.view;




import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ium.toolbox.model.Data;
import ium.toolbox.model.DataManager;
import ium.toolbox.model.DataNode;


public class TreeView extends AbstractPanel {
    
    private Tree treeConfig;
    
    public TreeView() {   
        
    }
    
    @Override
    protected void innerDraw(Composite parent, int style) {
        
        treeConfig = new Tree(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL );

        String storeAs=getProperty("storeAs");
        
        treeConfig.addListener(SWT.Selection, new Listener() {
             public void handleEvent(Event event) {
                 if(event.item instanceof TreeItem){
                     DataManager.instance().put(storeAs,(Data)event.item.getData() );                     
                 }
             }
        });
    }
    
    @Override
    public void update(Data o) {
        treeConfig.removeAll();
        createNodes(null,(DataNode)o);
    }
    
    
    private void createNodes(TreeItem top,DataNode nodeObject) {

        TreeItem node;
        for(DataNode n:nodeObject.getNodes()){
            if(top==null)
                node=new TreeItem(treeConfig,0);
            else
                node=new TreeItem(top,0);
            
            node.setText(n.getName());
            node.setData(n);
            node.setExpanded(true);
            createNodes(node,n);
        }
    }
    
}
