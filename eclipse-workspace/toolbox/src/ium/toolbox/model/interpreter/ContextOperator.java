package ium.toolbox.model.interpreter;

import ium.toolbox.config.XMLParser;

public class ContextOperator extends OperatorExpresion {
    
    //public static final String ORIGIN_CONFIG="Config";
    
    public ContextOperator(Expression left, Expression right) {
        super(left, right);
    }    
    
    @Override
    public void eval() {

//        if(leftExpresion.getExpresion().equals(ORIGIN_CONFIG))        
            data=XMLParser.instance().getConfigDataList(rightExpresion.getExpresion());
    }

    @Override
    String getOperator() {
        return "@";
    }

    

}
