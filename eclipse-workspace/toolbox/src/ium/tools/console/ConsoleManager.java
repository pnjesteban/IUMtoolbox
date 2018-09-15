package ium.tools.console;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import ium.tools.config.ConsoleListener;
import ium.tools.config.info.Host;
import ium.tools.console.commands.Command;
import ium.tools.data.GlobalScopeManager;

/*
 * http://www.jcraft.com/jsch/examples/ 
 */
public class ConsoleManager{

    Host host = null;
    Session session = null;
    ChannelShell channel = null;

    List<ConsoleListener> listeners = new ArrayList<ConsoleListener>();

    private static ConsoleManager singleton = null;

    private ConsoleManager() {
    }

    public static ConsoleManager instance() {
        if (singleton == null)
            singleton = new ConsoleManager();

        return singleton;
    }

    public void addListener(ConsoleListener listener) {

        listeners.add(listener);

    }
    
    
    private void notifyListeners(String data, int type){
        
        for(ConsoleListener listener: listeners){
            listener.addText(data,type);
        }
    }
    
    
    private void notifyInitProgress(int min,int max){
        
        for(ConsoleListener listener: listeners){
            listener.initProgress(min,max);
        }
    }
    
    private void notifyProgress(int actual){
        
        for(ConsoleListener listener: listeners){
            listener.setProgress(actual);
        }
    }
    
    private void notifyConnect(int status){
        
        for(ConsoleListener listener: listeners){
            listener.setConnect(status);
        }
    }

            
    public boolean openSession(Host host) {
        this.host = host;

        try{
        if (session == null) {
            JSch jsch = new JSch();
            session = jsch.getSession(host.getUserName(), host.getHostname(), host.getPort());
            RemoteUser ui = new RemoteUser(host.getUserName(), host.getPassword());
            // ui.optionalParent=parent;
            session.setUserInfo(ui);

            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            notifyListeners("CONNECTING to " + host.getName() + " [" + host.getUserName() + "@" + host.getHostname() + ":" + host.getPort() + "]",ConsoleListener.BOLD);
            //System.err.println("CONNECTING to " + host.getName() + " [" + host.getUserName() + "@" + host.getHostname() + ":" + host.getPort() + "]");

            session.connect();
            notifyConnect(ConsoleListener.STATUS_CONNECTED);
        }
        }catch(Exception ex){
            
            notifyListeners("ERROR intentando conectar: "+ex.getMessage(),ConsoleListener.ERROR);
            return false;
        }
        
        return true;
    }

    public void closeSession() {
        if (session != null) {
            notifyListeners("DISCONECT " + session.getHost(),ConsoleListener.BOLD);
            session.disconnect();
            session = null;
            notifyConnect(ConsoleListener.STATUS_DISCONNECTED);
        }
    }

    public ConsoleReturn exec(Command command, String[] params) {
        
        ConsoleReturn ret = new ConsoleReturn();
        String retStr = "";

        try {
            if (session != null) {

                ChannelExec channel = (ChannelExec) session.openChannel("exec");

                InputStream in = channel.getInputStream();

                channel.setCommand(command.translateCommand(host, params));
                notifyListeners("> " + command.translateCommand(host, params),ConsoleListener.INFO);

                // X Forwarding
                // channel.setXForwarding(true);

                channel.connect();

                byte[] tmp = new byte[1024];
                while (true) {
                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        if (i < 0)
                            break;
                         
                        String s=new String(tmp, 0, i);
                        notifyListeners(s,ConsoleListener.INFO);
                        retStr += s;
                    }
                    if (channel.isClosed()) {
                        if (in.available() > 0)
                            continue;
                        ret.setReturnValue(channel.getExitStatus());
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                    }
                }
                channel.disconnect();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ret.setReturnValue(-500);
        }

        String[] returnLines = retStr.split("\n");
        for (String line : returnLines)
            ret.addReturnLine(line);    
        
        return ret;
    }

    public void transfer(String remoteFile, String localFile) {

        FileOutputStream fos = null;

        try {

            // exec 'scp -f rfile' remotely
            String command = "scp -C -f " + remoteFile;

            notifyListeners("transfer command: " + command,ConsoleListener.INFO);

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] buf = new byte[1024];

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            while (true) {
                int c = checkAck(in);
                if (c != 'C') {
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        break;
                    }
                    if (buf[0] == ' ')
                        break;
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }

                String file = null;
                for (int i = 0;; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }
                               
                notifyListeners("filesize=" + filesize + ", file=" + file,ConsoleListener.INFO);
                
                long total=filesize;
                
                notifyInitProgress(0,100);
                
                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();

                // read a content of lfile
                fos = new FileOutputStream(localFile);
                int foo;
                while (true) {
                    if (buf.length < filesize)
                        foo = buf.length;
                    else
                        foo = (int) filesize;
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize -= foo;
   
                    long progress=total-filesize;
                    
                    notifyProgress( (int) (progress*100l/total));
                    
                    if (filesize == 0L)
                        break;
                }
                fos.close();
                fos = null;

                if (checkAck(in) != 0) {
                    // System.exit(0);
                    return;
                }

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }

            // session.disconnect();

            notifyListeners("transfer complete: " + localFile,ConsoleListener.INFO);
            // System.exit(0);
        } catch (Exception e) {
            System.out.println(e);
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception ee) {
            }
        }
    }

    int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        // 1 for error,
        // 2 for fatal error,
        // -1
        if (b == 0)
            return b;
        if (b == -1)
            return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

    

}
