package ium.tools.console;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.jcraft.jsch.UserInfo;

public class RemoteUser implements UserInfo {

	String user;
	String pass;
	
	public Component optionalParent;
	
	public RemoteUser(String user, String pass)
	{
		super();
		this.user=user;
		this.pass=pass;
	}
	
	
	@Override
	public String getPassphrase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return pass;
	}

	@Override
	public boolean promptPassphrase(String str) {
		System.out.println(str+" ...Not OK");
		return false;
	}

	@Override
	public boolean promptPassword(String str) {
		System.out.println(str+" ...OK");
		return true;
	}

	@Override
	public boolean promptYesNo(String str) {
		
		/*System.out.println(str+" ...OK");
		return true;*/
		
		Object[] options={ "yes", "no" };
	    int ret=JOptionPane.showOptionDialog(optionalParent, str, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	    return ret==0;
	}

	@Override
	public void showMessage(String str) {
		
		//JOptionPane.showMessageDialog(optionalParent,str);
		
		System.out.println(str);
	}

}
