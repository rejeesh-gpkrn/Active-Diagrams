/**
 * 
 */
package contract;

import javafx.scene.Scene;

/**
 * @author Rejeesh G.
 * Base class for creating pop-up dialogs.
 */
public abstract class RichDialog {
	
	private RichDialogContainer m_dialogContainer;
	
	private RichDialogController m_controller;
	
	private int m_width;
	
	private int m_height;

	public abstract String getTitle();

	public abstract Scene createScene();
	
	public RichDialog() {
		m_dialogContainer = new RichDialogContainer();
	}

	/**
	 * Bring up pop-up dialog.
	 * Default style is Medeless.
	 */
	public void show() {
		m_dialogContainer.setSize(m_width, m_height);
		m_dialogContainer.show(this);
	}
	
	public RichDialogController getController() {
		return m_controller;
	}

	protected void setController(RichDialogController controller) {
		this.m_controller = controller;
	}

	public int getWidth() {
		return m_width;
	}

	public void setWidth(int width) {
		this.m_width = width;
	}

	public int getHeight() {
		return m_height;
	}

	public void setHeight(int height) {
		this.m_height = height;
	}
	
	
}
