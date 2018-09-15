package ium.toolbox.model.interpreter;

import ium.toolbox.model.Data;


public abstract class OperatorExpresion implements Expression {

    Expression leftExpresion;
    Expression rightExpresion;
    
    Data data;
    
    
    public OperatorExpresion(Expression left,Expression right){
        
        leftExpresion=left;
        rightExpresion=right;
    }
    
    @Override
    public boolean isTerminal(){
        return false;
    }
    
    
    abstract String getOperator();

    @Override
    public String toString(){
        return "Operator"+getOperator();
    }
    
    public void setData(Data d) {
        data=d;
    }
    
    public Data getData() {
        return data;
    }
    
    public String getExpresion() {     
        return getOperator();
    }
}
