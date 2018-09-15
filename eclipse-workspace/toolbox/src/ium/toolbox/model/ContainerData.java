package ium.toolbox.model;

import java.util.ArrayList;
import java.util.List;

public class ContainerData implements DataNode {

    String name;
    List<Data> childs=new ArrayList<>();
    String defaultPrint;
    
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Data> getData() {
        return childs;
    }

    @Override
    public void setPrint(String att) {
        defaultPrint=att;
    }

    @Override
    public String getPrint() {
        return defaultPrint;
    }

}
