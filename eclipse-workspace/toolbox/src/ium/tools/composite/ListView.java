package ium.tools.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ium.tools.data.GlobalDataSet.DataLine;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.data.GlobalScopeManager;
import ium.tools.parser.ConfigNode;

public class ListView implements Renderable {
    
    ConfigNode collectors=null;
    Image collectorIconOff;
    Image collectorIconOn;
    Table collectorTable;
    
    Dataset dataset;
    
    Renderable[] controls={};
    
    
    @Override
    public Composite draw(Composite parent, int style) {
     
        Composite panel=new Composite(parent, SWT.NONE);
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 4;
        
        panel.setLayout(gridLayout);
        
        Composite controlsPanel=new Composite(panel, SWT.BORDER);
        controlsPanel.setLayout(new RowLayout());
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;        
        gridData.horizontalSpan = 4;
        controlsPanel.setLayoutData(gridData);
        
        
        for(Renderable control:controls){
            System.out.println(control);
            control.setMaster(this);
            control.draw(controlsPanel, SWT.NONE);
        }
        
        collectorTable = new Table(panel, SWT.BORDER | SWT.FULL_SELECTION |SWT.V_SCROLL);        
        collectorTable.setHeaderVisible(true);
        collectorTable.setLinesVisible(true);
        
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan = 4;
        collectorTable.setLayoutData(gridData);
        
        TableColumn tblclmnColl = new TableColumn(collectorTable, SWT.NONE);
        tblclmnColl.setWidth(25);
                
        tblclmnColl = new TableColumn(collectorTable, SWT.NONE);
        tblclmnColl.setWidth(100);
        tblclmnColl.setText("Colector");
        
        tblclmnColl = new TableColumn(collectorTable, SWT.NONE);
        tblclmnColl.setWidth(80);
        tblclmnColl.setText("pid");
        
        setTableMenus();               

        collectorIconOff = new Image(parent.getDisplay(),"icons/1x/sharp_portable_wifi_off_black_18dp.png");
        collectorIconOn = new Image(parent.getDisplay(),"icons/1x/sharp_wifi_tethering_black_18dp.png");
        
        return panel;
    }
    
    
    public void setTableMenus(){
        
        collectorTable.addMenuDetectListener(new MenuDetectListener()
        {
            @Override
            public void menuDetected(MenuDetectEvent e)
            {                
               int index = collectorTable.getSelectionIndex();
               if (index == -1) 
                 return; //no row selected

               TableItem item = collectorTable.getItem(index);
               item.getData(); //use this to identify which row was clicked.
               //The popup can now be displayed as usual using table.toDisplay(e.x, e.y)              
            }
        });

    }
    
    String filter="";
    public void setFilter(String text){
        filter=text;        
        loadCollectorsTable(collectors);
    }
    
    @Override
    public void addControls(Renderable[] controls) {
        this.controls=controls;
    }
    
    //@Override
    public void setRootNode(ConfigNode node) {        
                        
        collectors=node.getChild("collectors");
        
        loadCollectorsTable(collectors);
        
    }
    
    private void loadCollectorsTable(ConfigNode collectors){
        
        collectorTable.removeAll();
        
        if(collectors!=null){
            
            for(ConfigNode cn:collectors.getChilds()){
    
                if(filter.isEmpty() || cn.toString().contains(filter)){
                    TableItem tableItem = new TableItem(collectorTable, SWT.NONE);
                    tableItem.setImage(0,collectorIconOff);                
                    tableItem.setText(1,cn.toString());
                    tableItem.setData(cn);
                                        
                    if(dataset!=null){
                        for(DataLine line:dataset){                        
                            String collName=line.getValue("collector");
                            if(collName.equals(cn.toString())){
                                tableItem.setText(2,line.getValue("pid"));
                                tableItem.setImage(0,collectorIconOn);
                                break;
                            }
                        }
                    }
                }
            }   
        }
    }

    @Override
    public void setMaster(Renderable master) {
        
    }


    @Override
    public void update(Dataset o) {
     
        dataset=o;
        loadCollectorsTable(collectors);
        
        System.out.println("update!!");
        
    }


    @Override
    public void update(ConfigNode o) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public int getPanel() {
        // TODO Auto-generated method stub
        return 0;
    }
}
