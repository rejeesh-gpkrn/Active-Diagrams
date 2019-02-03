package dialog;

import java.util.ArrayList;
import java.util.List;

import contract.RichDialog;
import contract.RichDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Rejeesh G.
 *
 */
public class LvPropertyDialog extends RichDialog {
	
	private final String NAME = "Properties";
	
	// TODO: Set through public method.
	Text statusText;
	TableView<LvPropertyInfo> propertyTable;
	
	ObservableList<LvPropertyInfo> properties;

	public LvPropertyDialog() {
		setController(new LvPropertyDialogController(this));
	}

	@Override
	public String getTitle() {
		return NAME;
	}
	
	@Override
	public Scene createScene() {
		
		LvPropertyDialogController controller = (LvPropertyDialogController)getController();
		
		GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        ColumnConstraints propertyColConstraints = new ColumnConstraints();
        propertyColConstraints.setPercentWidth(25);
        ColumnConstraints valueColConstraints = new ColumnConstraints();
        valueColConstraints.setPercentWidth(75);
        pane.getColumnConstraints().addAll(propertyColConstraints,valueColConstraints);
       
        pane.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(pane, getWidth(), getHeight());
        
        propertyTable = new TableView<LvPropertyInfo>();
        properties = controller.getProperties(); //prepareProperties();
        propertyTable.setItems(properties);
        propertyTable.setEditable(true);
        
        TableColumn<LvPropertyInfo, String> nameColumn = new TableColumn<LvPropertyInfo, String>("Property");
        nameColumn.setCellValueFactory(new PropertyValueFactory<LvPropertyInfo, String>("name"));
        TableColumn<LvPropertyInfo, String> valueColumn = new TableColumn<LvPropertyInfo, String>("Value");
        valueColumn.setEditable(true);
        valueColumn.setCellValueFactory(new PropertyValueFactory<LvPropertyInfo, String>("value"));
        valueColumn.setCellFactory(TextFieldTableCell.<LvPropertyInfo>forTableColumn());
        
        // TODO: Move this to table's controller.
        valueColumn.setOnEditCommit((CellEditEvent<LvPropertyInfo, String> event)->{
        	TablePosition<LvPropertyInfo, String> pos = event.getTablePosition();
        	String newValue = event.getNewValue();
        	 int row = pos.getRow();
        	 LvPropertyInfo property = event.getTableView().getItems().get(row);
        	 property.setValue(newValue);
        });
        
        propertyTable.getColumns().setAll(nameColumn, valueColumn);
        
        propertyTable.prefWidth(getWidth() - 25);
        propertyTable.prefHeight(getHeight());
        propertyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.add(propertyTable, 0, 0, 2, 1);
        

        Button saveButton = new Button("Save");        
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(saveButton);
        pane.add(hbox, 1, 4);

        saveButton.setOnAction(controller);	

        return (scene);
	}
	
	@Override
	public void handleNotification(Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isReady() {
		// TODO Check state and return value.
		return true;
	}

	@Override
	public String getName() {
		return "LvPropertyDialog";
	}
	
}
