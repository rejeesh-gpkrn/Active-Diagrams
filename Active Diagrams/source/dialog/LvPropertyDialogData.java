/**
 * 
 */
package dialog;

import java.util.ArrayList;
import java.util.List;

import contract.ChangeListener;
import contract.CommonData;

/**
 * @author Rejeesh G.
 *
 */
public class LvPropertyDialogData implements CommonData {

	private List<LvPropertyInfo> m_properties;
	
	public LvPropertyDialogData() {
		this.m_properties = new ArrayList<LvPropertyInfo>();
	}

	public List<LvPropertyInfo> getProperties() {
		return m_properties;
	}

	public void addProperty(LvPropertyInfo property) {
		this.m_properties.add(property);
	}

	@Override
	public void setNotificationListener(ChangeListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}
