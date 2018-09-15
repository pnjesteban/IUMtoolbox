package ium.tools.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ium.tools.data.GlobalDataSet.DataLine;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.parser.ConfigNode;
import ium.tools.parser.IUMConfigNavigator;

public class CollectorsView implements Renderable {
    
    ConfigNode collectors=null;
    Dataset dataset;
    
    Renderable[] controls={};
    
    Image collectorIconOff;
    Image collectorIconOn;
    Table collectorTable;
    
    Composite[] mainPanels=new Composite[2];
    Label collType;
    
    
    @Override
    public Composite draw(Composite parent, int style) {
     
        Composite panel=new Composite(parent, SWT.NONE);
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 4;
        
        panel.setLayout(gridLayout);
        
        mainPanels[0]=new Composite(panel, SWT.BORDER);
        mainPanels[0].setLayout(new RowLayout());
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;        
        gridData.horizontalSpan = 4;
        mainPanels[0].setLayoutData(gridData);
                       
        collectorTable = new Table(panel, SWT.BORDER | SWT.FULL_SELECTION |SWT.V_SCROLL);        
        collectorTable.setHeaderVisible(true);
        collectorTable.setLinesVisible(true);
        
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan = 2;
        collectorTable.setLayoutData(gridData);
        
        TableColumn tblclmnColl = new TableColumn(collectorTable, SWT.NONE);
        tblclmnColl.setWidth(25);
                
        tblclmnColl = new TableColumn(collectorTable, SWT.NONE);
        tblclmnColl.setWidth(100);
        tblclmnColl.setText("Colector");
        
        tblclmnColl = new TableColumn(collectorTable, SWT.NONE);
        tblclmnColl.setWidth(80);
        tblclmnColl.setText("pid");
        
        
        collectorTable.addSelectionListener(new SelectionListener() {
            
            @Override
            public void widgetSelected(SelectionEvent arg0) {

                if(arg0.getSource()==collectorTable){
                    updateCollector(collectorTable.getSelection()[0].getData().toString());
                    
                }
            }
            
            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        
        
        mainPanels[1]=new Composite(panel, SWT.BORDER);
        mainPanels[1].setLayout(new RowLayout(SWT.VERTICAL));
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan = 2;
        mainPanels[1].setLayoutData(gridData);
        
        for(Renderable control:controls){
            control.setMaster(this);
            control.draw(mainPanels[control.getPanel()], SWT.NONE);
        }
        
        
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
               item.getData();
              
            }
        });
        
        
        Menu popupMenu = new Menu(collectorTable);
        MenuItem newItem = new MenuItem(popupMenu, SWT.CASCADE);
        newItem.setText("Start");
        MenuItem refreshItem = new MenuItem(popupMenu, SWT.NONE);
        refreshItem.setText("Clean");
        MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
        deleteItem.setText("Stop");
/*
        Menu newMenu = new Menu(popupMenu);
        newItem.setMenu(newMenu);

        MenuItem shortcutItem = new MenuItem(newMenu, SWT.NONE);
        shortcutItem.setText("Shortcut");
        MenuItem iconItem = new MenuItem(newMenu, SWT.NONE);
        iconItem.setText("Icon");
*/
        collectorTable.setMenu(popupMenu);
    }
    
    
    public void setActiveCollectors(String objectName){
        
        
        System.out.println("setActiveCollectors>"+objectName);
        
        //dataset=GlobalScopeManager.instance().getDataset(objectName);
        loadCollectorsTable(collectors);
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
           
    private void loadCollectorsTable(ConfigNode collectors){
        
        collectorTable.removeAll();
        
        if(collectors!=null){
            
            for(ConfigNode cn:collectors.getChilds()){
    
                if(filter.isEmpty() || cn.toString().matches(filter)){
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
        
    }
    
    ConfigNode rootNode;
    
    @Override
    public void update(ConfigNode node) {        

        rootNode=node;
        collectors=node.getChild("collectors");
        loadCollectorsTable(collectors);
    }
    
    
    public void updateCollector(String coll){
        
        IUMConfigNavigator nav=new IUMConfigNavigator(rootNode);
        
        for(Renderable control:controls){
            
            if(control instanceof RenderableControl){
                RenderableControl l=(RenderableControl)control;
                l.putText(nav.getCollectorParam(coll, l.getField()));
            }
        }   
    }


    @Override
    public int getPanel() {
        return 0;
    }
    
}

