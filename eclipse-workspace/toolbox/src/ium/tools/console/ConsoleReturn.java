package ium.tools.console;

import java.util.ArrayList;

import ium.tools.data.GlobalDataSet.DataLine;
import ium.tools.data.GlobalDataSet.DataValue;
import ium.tools.data.GlobalDataSet.Dataset;

public class ConsoleReturn implements Dataset{

    class ReturnValue implements DataValue{
        String value;
        public ReturnValue(String value){
            this.value=value;
        }
        
        @Override
        public String name() {
            return "line";
        }

        @Override
        public String value() {
            return value;
        }
        @Override
        public String toString() {
            return print();
        }
    } 
    class ReturnLine implements DataLine{
        DataValue line;

        public ReturnLine(String s){
            
            line =new ReturnValue(s);
            
        }
        
        public String getValue(){
            return getValue("line");
        }
        
        @Override
        public void add(DataValue value) {
            line=value;
        }
        
        @Override
        public Iterable<DataValue> get() {
            ArrayList<DataValue> ret=new ArrayList<DataValue>();
            ret.add(line);
            return ret;
        }  
        
        @Override
        public String toString() {
            return print();
        }
    }
    
	int exitCode;
	ArrayList<DataLine> returnData=new ArrayList<DataLine>();
	
	public int getExitCode() {
		return exitCode;
	}
	public void setReturnValue(int returnValue) {
		this.exitCode = returnValue;
	}
	
	public void addReturnLine(String line)
	{
		returnData.add(new ReturnLine(line));
	}
	/*
	public List<String> getReturnData() {
		return returnData;
	}
	*/
	
    @Override
    public void add(DataLine line) {
        
        returnData.add(line);
        
    }
    @Override
    public Iterable<DataLine> get() {
        return returnData;
    }
}
