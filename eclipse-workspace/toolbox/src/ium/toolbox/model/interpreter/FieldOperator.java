package ium.toolbox.model.interpreter;

import ium.toolbox.model.DataManager;
import ium.toolbox.model.DataNode;

public class FieldOperator extends OperatorExpresion {
    
    public FieldOperator(Expression left, Expression right) {
        super(left, right);
    }

    public static final String ORIGIN_CONFIG="Config";
    public static final String ORIGIN_DATA="Data";
    
    @Override
    public void eval() {
        
        if(data==null)
            data=DataManager.instance().get( leftExpresion.getExpresion() );
        else
            data=((DataNode)data).get(leftExpresion.getExpresion());
        
        if(data==null)
            return;
        
        if(rightExpresion.isTerminal()){
            data.setPrint(rightExpresion.getExpresion());
            
            ((DataNode)data)
                .getLeafs()
                .forEach(l ->
                l.setPrint(rightExpresion.getExpresion())
            );
            
        }else{  
            rightExpresion.setData(data);
            rightExpresion.eval();
            data=rightExpresion.getData();
        }
        
    }


    @Override
    String getOperator() {
        return ".";
    }

}
