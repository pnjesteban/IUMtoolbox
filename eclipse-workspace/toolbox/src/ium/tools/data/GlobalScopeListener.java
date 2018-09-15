package ium.tools.data;

import ium.tools.data.GlobalDataSet.Dataset;
import ium.tools.parser.ConfigNode;

public interface GlobalScopeListener {

    
    public void update(Dataset o);
    
    public void update(ConfigNode o);
    
    
}
