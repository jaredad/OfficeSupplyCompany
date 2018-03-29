package items;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class itemController {

    @FXML
    private HBox middleHbox;

    @FXML
    private TextField locationField;

    @FXML
    private HBox topVbox;

    @FXML
    private VBox topleftVbox;

    @FXML
    private TextField nameField;

    @FXML
    private VBox topRightVbox;

    @FXML
    private Label locationLabel;

    @FXML
    private TextArea descriptionField;

    @FXML
    private VBox mainVbox;

    @FXML
    private Button doneButton;

    @FXML
    private Label topMiddleLabel;

    @FXML
    private VBox middleVbox;

    @FXML
    private TextField priceField;

    @FXML
    private VBox bottomVbox;

    @FXML
    private VBox topMiddleVbox;

    @FXML
    private AnchorPane pane;

    @FXML
    private VBox middleVbox2;

    @FXML
    private Label descriptionLabel;

    @FXML
    private HBox bottomHbox;

    @FXML
    private Label nameLabel;
    
    public void test() {
    	System.out.println("hey");
    }
    
    public void addItem() {
    	//access the function that handles adding a new item in a database class
    }

}
