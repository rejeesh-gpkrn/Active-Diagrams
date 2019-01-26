package dialog;

import java.util.List;

import contract.RichDialog;
import contract.RichDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class LvPropertyDialogController extends RichDialogController {
	
	private LvPropertyDialogData m_data;

	public LvPropertyDialogController(RichDialog view) {
		super(view);
		
		m_data = new LvPropertyDialogData();
		prepareProperties();
	}

	/**
	 * @return the m_data
	 */
	public LvPropertyDialogData getData() {
		return m_data;
	}
	
	private void prepareProperties() {
		m_data.addProperty(new LvPropertyInfo("Name"));
		m_data.addProperty(new LvPropertyInfo("Order"));
		m_data.addProperty(new LvPropertyInfo("Blink"));
		m_data.addProperty(new LvPropertyInfo("Enabled"));
	}
	
	public ObservableList<LvPropertyInfo> getProperties(){
		List<LvPropertyInfo> enabledProperties = m_data.getProperties();
		if (enabledProperties == null) {
			return null;
		}
		
		ObservableList<LvPropertyInfo> observableProperties = 
						FXCollections.observableList(enabledProperties);
		return observableProperties;
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		LvPropertyDialog propertyDialog = (LvPropertyDialog)getView();
		LvPropertyInfo prp = (LvPropertyInfo)propertyDialog.properties.get(0);
		System.out.println(prp.getValue());
		/*propertyDialog.statusText.setText("Name [" + propertyDialog.totalField.getText() + 
											" ] Order [" + propertyDialog.orderNumberField.getText() + " ]");*/
	}

}
