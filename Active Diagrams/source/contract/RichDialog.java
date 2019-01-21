/**
 * 
 */
package contract;

import dialog.RichDialogContainer;
import javafx.scene.Scene;

/**
 * @author Rejeesh G.
 *
 */
public abstract class RichDialog {
	
	private RichDialogContainer m_dialogContainer;
	
	private RichDialogController m_controller;

	public abstract String getTitle();

	public abstract Scene createScene();
	
	public RichDialog() {
		m_dialogContainer = new RichDialogContainer();
	}

	public void show() {
		m_dialogContainer.show(this);
	}
	
	public RichDialogController getController() {
		return m_controller;
	}

	protected void setController(RichDialogController controller) {
		this.m_controller = controller;
	}
	
}
