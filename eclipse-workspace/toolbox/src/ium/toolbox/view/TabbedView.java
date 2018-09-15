package ium.toolbox.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;



public class TabbedView extends AbstractPanel {
    
    @Override
    public void innerDraw(Composite parent, int style) {

        TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;        
        tabFolder.setLayoutData(gridData);
        
        controls.forEach( ( r ) -> {
            
            TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
            tabItem.setText(r.getProperty("title"));
            Composite innerPanel=(Composite)r.draw(tabFolder,SWT.NONE);            
            tabItem.setControl(innerPanel);
            
        } );

    }
}
