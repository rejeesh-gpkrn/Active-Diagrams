package dialog;

import contract.RichDialog;
import contract.RichDialogController;
import javafx.event.ActionEvent;

public class LvPropertyDialogController extends RichDialogController {

	public LvPropertyDialogController(RichDialog view) {
		super(view);
		// TODO Auto-generated constructor stub
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
