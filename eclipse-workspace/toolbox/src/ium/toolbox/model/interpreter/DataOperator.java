package ium.toolbox.model.interpreter;

import ium.toolbox.model.DataManager;
import ium.toolbox.model.DataNode;

public class DataOperator extends OperatorExpresion {
    
    public DataOperator(Expression left, Expression right) {
        super(left, right);
    }
    
    @Override
    public  void eval() {

        if(data==null)
            data=DataManager.instance().get( leftExpresion.getExpresion() );
        else
            data=((DataNode)data).get(leftExpresion.getExpresion());
        
        if(data==null)
            return;
                
        if(rightExpresion.isTerminal()){
            data=((DataNode)data).get(rightExpresion.getExpresion());
        }else{            
            rightExpresion.setData(data);
            rightExpresion.eval();
            data=rightExpresion.getData();
        }
    }


    @Override
    String getOperator() {
        return ":";
    }

}
