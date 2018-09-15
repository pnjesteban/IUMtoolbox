package ium.toolbox.model.interpreter;

import ium.toolbox.model.Data;


public class TerminalExpresion implements Expression{

    String exp;
    Data data;
    
    public TerminalExpresion(String exp){
        this.exp=exp;
    }
    
    @Override
    public void eval() { }
    
    @Override
    public boolean isTerminal(){
        return true;
    }
    
    @Override
    public String toString(){
        return "["+exp+"]";
    }

    @Override
    public String getExpresion() {
        return exp;
    }

    @Override
    public Data getData() {
        return data;
    }

    @Override
    public void setData(Data data) {
        this.data=data;
        
    }

}
