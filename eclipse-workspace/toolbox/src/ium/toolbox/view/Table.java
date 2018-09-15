package ium.toolbox.view;


import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ium.toolbox.model.Data;
import ium.toolbox.model.DataNode;
import ium.toolbox.model.interpreter.Evaluator;


public class Table extends AbstractPanel{

    org.eclipse.swt.widgets.Table t;
    
    @Override    
    protected void innerDraw(Composite innerPanel, int style) {
        
        t = new org.eclipse.swt.widgets.Table(innerPanel, SWT.BORDER | SWT.FULL_SELECTION |SWT.V_SCROLL);        
        t.setHeaderVisible(true);
        t.setLinesVisible(true);
        
        getProperyList("column").forEach( c -> {
        
            TableColumn column = new TableColumn(t, SWT.NONE);
            column.setWidth(100);
            column.setText(c);
            
        });
        
    }
    
    @Override
    public void update(Data o) {

        t.removeAll();
        
        int columnIndex=0;
        
        for(String field:getProperyList("field")){
            DataNode node=(DataNode)new Evaluator().evaluate(field);
            if(node!=null)                
                updateTable(node.getData(),columnIndex);
            columnIndex++;
        }

    }
    
    protected void updateTable(List<Data> data,int columnIndex){
        
        int rowIndex=0;
        for(Data d:data){        
            TableItem tableItem;
            
            if(columnIndex==0){
                tableItem = new TableItem(t, SWT.NONE);
            }else{
                tableItem=t.getItem(rowIndex);
            }
            
            //tableItem.setImage(0,collectorIconOff);                
            tableItem.setText(columnIndex,d.toString());
            tableItem.setData(d);
            rowIndex++;
            t.getColumn(columnIndex).pack();
        }
        
        
    }
}
