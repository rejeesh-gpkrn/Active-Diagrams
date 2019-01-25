package contract;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RichDialogContainer {
	
	private RichDialog m_childScene;
	
	private int m_width = 200;
	
	private int m_height = 200;

	public void setSize(int width, int height) {
		this.m_width = width;
		this.m_height = height;
	}
	
	public void show(RichDialog richDialogScene) {
		if (richDialogScene == null) {
			throw new IllegalArgumentException("Content is not specified.");
		}
		
		m_childScene = richDialogScene;
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showFX();
            }
        });
	}
	
	public void showFX() {
		JFrame frame = new JFrame("Rich Dialog");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(m_width, m_height);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tryCreateScene(fxPanel);
            }
       });
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		/*Platform.exit();
        		frame.removeAll();*/

        		super.windowClosing(e);
        	}
        });
	}
	
	public void tryCreateScene(JFXPanel fxPanel) {

		Scene currentScene = m_childScene.createScene();
		if (currentScene == null) {
			throw new NullPointerException("Scene not defined.");
		}
		
		fxPanel.setScene(currentScene);
	}
	
}
