package ium.toolbox.model.interpreter;

import java.util.StringTokenizer;

import ium.toolbox.model.Data;
import ium.toolbox.model.StringData;

public class Evaluator {
        
    
    final String parseExp=":.";
    final String initExp="@";
    final String configExp="CONFIG";
    
    
    /***
     * expresiones:
     * @CONFIG:<value>
     * @<VAR>[:<ATRIBUTE>[.<METHOD>]]
     * 
     * 
     * @param exp
     * @return
     */
    public Data evaluate(String exp){
        
        String[] expresiones=exp.split("\\|");
        
        if(expresiones.length==1)
            return evaluateSingleExp(exp);
        
        //si hay datos concatenados se deben tratar antes de devolverlos
        String strComp="";
        for(String e:expresiones){
        
            Data d=evaluateSingleExp(e);
            strComp+=d;
        }
        
        StringData ret=new StringData();
        
        ret.setName("literal");
        ret.setValue(strComp);
        
        return ret;
    }
    
    private Data evaluateSingleExp(String exp){

        
        if(!exp.startsWith(initExp)){
            //es un literal
            StringData sd=new StringData();
            sd.setValue("literal");
            sd.setValue(exp.replaceAll("\\\\", ""));//se quitan los caracteres de escape
            return sd;
            
        }
        
        Expression ex=new Evaluator().parseExp(exp.substring(1));
        
        ex.eval();
        
        return ex.getData();
        
    }
        
    private Expression parseExp(String exp){
                       
        StringTokenizer strTnk=new StringTokenizer(exp, parseExp,true);
        
        if(strTnk.countTokens()==1){            
            return new TerminalExpresion(exp);
        }else{
            
            int index=strTnk.nextToken().length();
            
            String left=exp.substring(0,index);
            String operator=exp.substring(index,index+1);
            String right=exp.substring(index+1);

            //if(operator.equals("@")){
            if(left.equals(configExp)){
                return new ContextOperator(parseExp(left), parseExp(right));
            }
            if(operator.equals(":")){
                return new DataOperator(parseExp(left), parseExp(right));
            }
            if(operator.equals(".")){
                return new FieldOperator(parseExp(left), parseExp(right));
            }
        }
        return null;
    }
}
