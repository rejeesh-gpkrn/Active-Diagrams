/**
 * 
 */
package utilities;

import java.awt.TrayIcon.MessageType;

import javax.swing.JOptionPane;

/**
 * @author Rejeesh G.
 *
 */
public class MessageUtil {
	
	public static void showMessageBox(String message, String title) {
		showMessageBox(message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showMessageBox(String message) {
		showMessageBox(message, null, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showErrorBox(String message, String title) {
		showMessageBox(message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showErrorBox(String message) {
		showMessageBox(message, null, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showWarningBox(String message, String title) {
		showMessageBox(message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showWarningBox(String message) {
		showMessageBox(message, null, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showMessageBox(String message, String title, int messageType) {
		if (message == null || "".equals(message)) {
			return;
		}
		
		if (title == null || "".equals(title)) {
			title = "Active Diagrams";
		}
		
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

}
