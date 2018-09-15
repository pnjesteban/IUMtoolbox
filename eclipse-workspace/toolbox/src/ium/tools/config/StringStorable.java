package ium.tools.config;

public class StringStorable implements ConfigEntry {

    String name;
    String value;
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setName(String name) {
        this.name=name;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNodeType() {
        return "String";
    }

    @Override
    public void addPropertyValue(String name, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getPropertyValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addChildren(ConfigEntry st) {
        // TODO Auto-generated method stub

    }

    @Override
    public ConfigEntry[] getChildrens() {
        // TODO Auto-generated method stub
        return null;
    }

}
