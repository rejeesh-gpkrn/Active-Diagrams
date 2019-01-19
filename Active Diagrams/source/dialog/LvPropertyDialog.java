package dialog;

import contract.RichDialog;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Rejeesh G.
 *
 */
public class LvPropertyDialog extends RichDialog {
	
	private final String NAME = "Properties";

	@Override
	public String getTitle() {
		return NAME;
	}

	@Override
	public Scene createScene() {
		Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root, Color.CADETBLUE);
        Text  text  =  new  Text();
        
        text.setX(40);
        text.setY(100);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX Property Bag");

        root.getChildren().add(text);
        
        Button x = new Button();
        x.setText("Close");
        x.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				System.out.println("Close clicked");
			}
        	
		});
        
        root.getChildren().add(x);

        return (scene);
	}
	
}
