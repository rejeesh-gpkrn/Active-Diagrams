/**
 * 
 */
package contract;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Rejeesh G.
 *
 */
public abstract class RichDialogController implements EventHandler<ActionEvent>{
	
	private RichDialog m_view;
	
	protected RichDialog getView() {
		return m_view;
	}

	public RichDialogController(RichDialog view) {
		if (view == null) {
			throw new IllegalArgumentException("View is null");
		}
		
		m_view = view;
	}
}
