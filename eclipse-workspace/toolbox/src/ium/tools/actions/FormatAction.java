package ium.tools.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ium.tools.data.GlobalDataSet.DataLine;
import ium.tools.data.GlobalDataSet.DataValue;
import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.data.GlobalDataSet.GlobalizedData;
import ium.tools.data.GlobalScopeManager;


public class FormatAction extends StorableAction {

    
    public class FormatValue implements DataValue
    {
        String name;
        String value;
        
        public FormatValue(String name,String value){
            this.name=name;
            this.value=value;
        }
        
        public String toString()
        {
            return print();
            //return name+"="+value;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String value() {
            return value;
        }
    }
    
    public class FormatLine implements DataLine
    {
        public List<DataValue> line=new ArrayList<DataValue>();
        
        public FormatLine(){}
        
        /*
        public String getValue(String name)
        {
            for(FormatValue val:line)
            {
                if(val.name.equals(name))
                    return val.value;   
            }           
            return null;
        }*/
        
        FormatLine(List<DataValue> line){
            this.line=line;
            
        }
        
        public String toString(){
            return print();
        }

        @Override
        public Iterable<DataValue> get() {
            return line;
        }

        @Override
        public void add(DataValue value) {
            line.add(value);
            
        }
    }
    
    public class FormatDataset implements Dataset, GlobalizedData<FormatDataset> 
    {
        List<DataLine> dataset=new ArrayList<DataLine>();
        
        public String toString(){   
            return print();
        }

        @Override
        public void add(DataLine line) {

            dataset.add(line);
        }

        @Override
        public Iterable<DataLine> get() {
            return dataset;
        }

        @Override
        public String getGlobalName() {
            return getName();
        }

        @Override
        public FormatDataset getData() {            
            return this;
        }
    }
    
    @Override
    public void execute() {

        Dataset inputData=GlobalScopeManager.instance().getDataset(getInput());
        
        FormatDataset fd=new FormatDataset();
        
        inputData.get().forEach(line -> fd.add(formatLine(line.getValue("line") )));
        
        globalizeObject(fd);

    }
        
    private FormatLine formatLine(String line){
        
        Pattern pattern=Pattern.compile(getRegex());
        
        FormatLine fl=new FormatLine();
        
        //se obtienen los grupos
        String[] groups=getGroups().split("\\|");
        Matcher matcher = pattern.matcher(line.trim());
        
        if (matcher.find()){
            int index=1;
            for(String grp:groups){               
                String val=matcher.group(index);
                fl.add(new FormatValue(grp,val));
                index++;
            }
        }
        
        return fl;
    }
    
    
    public String getObject(){
        return (String)getPropertyValue("object");
    }
    
    public String getRegex(){
        return (String)getPropertyValue("regex");
    }
    
    public String getGroups(){
        return (String)getPropertyValue("groups");
    }
}
