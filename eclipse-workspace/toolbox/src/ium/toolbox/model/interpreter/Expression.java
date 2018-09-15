package ium.toolbox.model.interpreter;

import ium.toolbox.model.Data;

public interface Expression {
    
    public String getExpresion();
    public Data getData();
    public void setData(Data data);
    public void eval();
    public boolean isTerminal();
    
}
