package dialog;

import contract.RichDialog;
import contract.RichDialogController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(pane, 300, 275);

        statusText = new Text();
    	totalField = new TextField();
    	orderNumberField = new TextField();
        
        Text sceneTitle = new Text("Active Properties");
        sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL,20));
        pane.add(sceneTitle, 0, 0, 2, 1);
        Label total = new Label("Name:");
        pane.add(total, 0, 1);
        pane.add(totalField, 1, 1);
        Label percent = new Label("Order Number:");
        pane.add(percent,0,2);
        pane.add(orderNumberField, 1, 2);

        Button saveButton = new Button("Save");        
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(saveButton);
        pane.add(hbox, 1, 4);

        pane.add(statusText, 1, 6);
        
        RichDialogController controller = getController();
        saveButton.setOnAction(controller);

        /*saveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                statusText.setText("Name [" + totalField.getText() + " ] Order Number [" + orderNumberField.getText() + " ]");
            }
        });*/
		

        return (scene);
	}
	
}
