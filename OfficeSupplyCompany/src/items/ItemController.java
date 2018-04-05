package items;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ItemController {


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
	private ChoiceBox<String> categoryField;

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
	private Button button;

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
	
	public void initialize() {
		ObservableList<String> categories = FXCollections.observableArrayList("Mail/Shipping", "Office Furniture", "Office Supplies", "Technology");
		categoryField.setItems(categories);
		categoryField.getSelectionModel().selectFirst();
	}

	public void addItem() throws ClassNotFoundException, SQLException {
		if(ErrorHandler.passErrorCheck(nameField.getText(), priceField.getText(), locationField.getText(), quantityField.getText(), descriptionField.getText())) {
			Item new_item = new Item(nameField.getText(), priceField.getText(), locationField.getText(), categoryField.getValue(), quantityField.getText(), descriptionField.getText());
			Database database = new Database();
			database.addItem(new_item);
			String addAlert = nameField.getText() + " has been added to the database.";
			infoAlert(addAlert);
			closeWindow();
		}
	}
	
	public void closeWindow() {
		Stage stage1 = (Stage) button.getScene().getWindow();
		stage1.close();
	}
	
	public void infoAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
