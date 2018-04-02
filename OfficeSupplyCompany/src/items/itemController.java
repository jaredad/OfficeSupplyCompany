package items;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class itemController {
	
	String sku = "00000001";

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
    private TextArea descriptionField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField priceField;

    @FXML
    private HBox UpperMiddleHbox;

    @FXML
    private VBox bottomVbox;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField quantityField;

    @FXML
    private HBox bottomHbox;

    @FXML
    private Label nameLabel;

    @FXML
    private HBox middleHbox;

    @FXML
    private VBox LeftUpperMiddleHbox;

    @FXML
    private Label locationLabel;

    @FXML
    private VBox upperMiddleVbox;

    @FXML
    private VBox mainVbox;

    @FXML
    private Button doneButton;

    @FXML
    private Label topMiddleLabel;

    @FXML
    private Label lowerTopMiddleLabel;

    @FXML
    private VBox middleVbox;

    @FXML
    private VBox topMiddleVbox;

    @FXML
    private VBox middleVbox2;

    @FXML
    private Label lowerTopLeftLabel;

    @FXML
    private Label descriptionLabel;
    
    public void addItem() throws ClassNotFoundException, SQLException {
    	String name = nameField.getText();
    	String price = priceField.getText();
    	String location = locationField.getText();
    	String category = categoryField.getText();
    	String quantity = quantityField.getText();
    	String sku_num = sku;
    	String description = descriptionField.getText();
    	increaseSKU();
    	Item new_item = new Item(name, price, location, category, quantity, sku_num, description);
    	Database new_data = new Database();
    	new_data.addItem(new_item);
    }
    
    public void increaseSKU() {
    	return;
    }

}
