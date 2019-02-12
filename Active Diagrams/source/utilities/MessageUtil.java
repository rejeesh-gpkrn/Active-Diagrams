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
	
	public static void showMessageBox(String message) {
		showMessageBox(message, null);
	}
	
	public static void showMessageBox(String message, String title) {
		if (message == null || "".equals(message)) {
			return;
		}
		
		if (title == null || "".equals(title)) {
			title = "Active Diagrams";
		}
		
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
