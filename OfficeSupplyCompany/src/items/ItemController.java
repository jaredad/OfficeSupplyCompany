package items;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private VBox MiddleUpperMiddleVbox, LeftUpperMiddleVbox, topleftVbox, topRightVbox, bottomVbox, LeftUpperMiddleHbox, upperMiddleVbox,
	mainVbox, middleVbox, topMiddleVbox, middleVbox2;

	@FXML
	private TextField locationField, nameField, priceField, quantityField;

	@FXML
	private HBox topVbox, UpperMiddleHbox, bottomHbox, middleHbox;

	@FXML
	private TextArea descriptionField;

	@FXML
	private ChoiceBox<String> categoryField;

	@FXML
	private AnchorPane pane;

	@FXML
	private Label nameLabel, locationLabel, topMiddleLabel, lowerTopMiddleLabel, lowerTopLeftLabel, descriptionLabel, lowerTopRightLabel;

	@FXML
	private Button button;

	@FXML
	private TextField keywordsField;

	private String updateSku;
	
	private Database data = new Database();

	public void initialize() throws ClassNotFoundException, SQLException {
		descriptionField.setWrapText(true);
		ObservableList<String> categories = FXCollections.observableArrayList("Mail/Printing", "Office Furniture","Office Supplies", "Technology");
		categoryField.setItems(categories);
		categoryField.getSelectionModel().selectFirst();
		setupGUI();
	}

	public void setupGUI() throws ClassNotFoundException, SQLException {
		if (!data.checkEmptyCommunicator()) {
			button.setText("Edit");
			List<String> itemList = data.getTableValues();
			nameField.setText(itemList.get(0));
			nameField.setEditable(false);
			priceField.setText(itemList.get(1));
			priceField.setEditable(false);
			locationField.setText(itemList.get(2));
			locationField.setEditable(false);
			categoryField.setValue(itemList.get(3));
			categoryField.setDisable(true);
			quantityField.setText(itemList.get(4));
			quantityField.setEditable(false);
			updateSku = itemList.get(5);
			descriptionField.setText(itemList.get(6));
			descriptionField.setEditable(false);
			keywordsField.setText(itemList.get(7));
			keywordsField.setEditable(false);
			data.deleteAllItems("communicator");
		}
	}

	public void editAddItemSetup() throws ClassNotFoundException, SQLException {
		if (button.getText().equals("Edit")) {
			setTextFieldsEditable();
			button.setText("Finish");
		} else if (button.getText().equals("Finish")) {
			if (ErrorHandler.passErrorCheck(nameField.getText(), priceField.getText(), locationField.getText(),
					quantityField.getText(), descriptionField.getText(),keywordsField.getText(), true)) {
				updateInfo();
				closeWindow();
			}
		} else {
			if (ErrorHandler.passErrorCheck(nameField.getText(), priceField.getText(), locationField.getText(),
					quantityField.getText(), descriptionField.getText(), keywordsField.getText(), false)) {
				addItem();
				closeWindow();
			}
		}
	}
	
	public void addItem() throws ClassNotFoundException, SQLException {
		sanitizeInput();
		Item new_item = new Item(nameField.getText(), priceField.getText(), locationField.getText(),
				categoryField.getValue(), quantityField.getText(), data.createSKUSetup(),
				descriptionField.getText(), keywordsField.getText().toLowerCase());
		data.addItem(new_item);
		String message = nameField.getText() + " has been added to the database.";
		infoAlert(message);
	}
	
	public void sanitizeInput() {
		nameField.setText(nameField.getText().replaceAll("'", ""));
		nameField.setText(nameField.getText().replaceAll(";", ""));
		priceField.setText(priceField.getText().replaceAll("'", ""));
		priceField.setText(priceField.getText().replaceAll(";", ""));
		locationField.setText(locationField.getText().replaceAll("'", ""));
		locationField.setText(locationField.getText().replaceAll(";", ""));
		quantityField.setText(quantityField.getText().replaceAll("'", ""));
		quantityField.setText(quantityField.getText().replaceAll(";", ""));
		descriptionField.setText(descriptionField.getText().replaceAll("'", ""));
		descriptionField.setText(descriptionField.getText().replaceAll(";", ""));
		keywordsField.setText(keywordsField.getText().replaceAll("'", ""));
		keywordsField.setText(keywordsField.getText().replaceAll(";", ""));
	}
	public void updateInfo() throws ClassNotFoundException, SQLException {
		List<String> newInfo = new ArrayList<String>();
		newInfo.add(nameField.getText());
		newInfo.add(priceField.getText());
		newInfo.add(locationField.getText());
		newInfo.add(categoryField.getValue());
		newInfo.add(quantityField.getText());
		newInfo.add(updateSku);
		newInfo.add(descriptionField.getText());
		newInfo.add(keywordsField.getText().toLowerCase());
		sanitizeInput();
		data.update(newInfo);
		infoAlert("Your edit was successful!");
	}
	
	public void setTextFieldsEditable() {
		nameField.setEditable(true);
		priceField.setEditable(true);
		locationField.setEditable(true);
		categoryField.setDisable(false);
		quantityField.setEditable(true);
		descriptionField.setEditable(true);
		keywordsField.setEditable(true);
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
