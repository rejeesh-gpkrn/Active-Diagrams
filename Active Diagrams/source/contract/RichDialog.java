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
	
	public abstract String getTitle();

	public abstract Scene createScene();
	
	public RichDialog() {
		m_dialogContainer = new RichDialogContainer();
	}

	public void show() {
		m_dialogContainer.show(this);
	}
}
