package dialog;

import java.util.ArrayList;
import java.util.List;

import contract.RichDialog;
import contract.RichDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @author Rejeesh G.
 *
 */
public class LvPropertyDialog extends RichDialog {
	
	private final String NAME = "Properties";
	
	// TODO: Set through public method.
	Text statusText;
	TextField totalField;
	TextField orderNumberField;
	TableView<LvPropertyInfo> table;
	
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
			
		// TODO: Create to match with property bag styles.
		GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        pane.getColumnConstraints().addAll(col1,col2);
       
        pane.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(pane, getWidth(), getHeight());
        
        table = new TableView<LvPropertyInfo>();
        properties = prepareProperties();
        table.setItems(properties);
        table.setEditable(true);
        
        TableColumn<LvPropertyInfo, String> titleColumn = new TableColumn<LvPropertyInfo, String>("Property");
        titleColumn.setCellValueFactory(new PropertyValueFactory<LvPropertyInfo, String>("name"));
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
        		
        		
        		/*(CellEditEvent<LvPropertyInfo, String> event)->{
        	TablePosition<LvPropertyInfo, String> pos = event.getTablePosition();
        	String newValue = event.getNewValue();
        	 int row = pos.getRow();
        	 LvPropertyInfo property = event.getTableView().getItems().get(row);
        	 property.setValue(newValue);
        	 
        });*/
        
        table.getColumns().setAll(titleColumn, valueColumn);
        
        table.prefWidth(getWidth() - 25);
        table.prefHeight(getHeight());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pane.add(table, 0, 0, 2, 1);
        

        Button saveButton = new Button("Save");        
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(saveButton);
        pane.add(hbox, 1, 4);
        
        RichDialogController controller = getController();
        saveButton.setOnAction(controller);	

        return (scene);
	}
	
	//TODO: Move to data. Call should be; this > controller > data.
	private ObservableList<LvPropertyInfo> prepareProperties() {
		List<LvPropertyInfo> properties = new ArrayList<LvPropertyInfo>();
		
		properties.add(new LvPropertyInfo("Name"));
		properties.add(new LvPropertyInfo("Order"));
		properties.add(new LvPropertyInfo("Blink"));
		
		ObservableList<LvPropertyInfo> obsProperties = FXCollections.observableList(properties);
		return obsProperties;
	}
	
}
