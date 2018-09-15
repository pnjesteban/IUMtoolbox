package ium.tools.config;

public interface ConsoleListener {

    
    public static final int INFO=0;
    public static final int ERROR=1;
    public static final int WARNING=2;
    public static final int BOLD=3;    
    public static final int PROGRESS=4;
    
    public static final int STATUS_CONNECTED=0;    
    public static final int STATUS_DISCONNECTED=1;
    public static final int STATUS_WORKING=2;
    
    
    public void addText(String text, int type);
    
    public void setConnect(int status);
    
    public void initProgress(int min, int max);
    public void setProgress(int actual);
    
}
