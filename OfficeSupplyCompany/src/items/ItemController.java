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
	private VBox MiddleUpperMiddleVbox;

	@FXML
	private VBox LeftUpperMiddleVbox;

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

	@FXML
	private Label lowerTopRightLabel;

	@FXML
	private TextField keywordsField;

	String updateSku;
	Database data;
	boolean wait;

	public void initialize() throws ClassNotFoundException, SQLException {
		ObservableList<String> categories = FXCollections.observableArrayList("Mail/Printing", "Office Furniture",
				"Office Supplies", "Technology");
		categoryField.setItems(categories);
		categoryField.getSelectionModel().selectFirst();
		data = new Database();
		button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
					addItem();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
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
		}
	}

	public void addItem() throws ClassNotFoundException, SQLException {
		if (button.getText().equals("Edit")) {
			nameField.setEditable(true);
			priceField.setEditable(true);
			locationField.setEditable(true);
			categoryField.setDisable(false);
			quantityField.setEditable(true);
			descriptionField.setEditable(true);
			keywordsField.setEditable(true);
			button.setText("Finish");
		} else if (button.getText().equals("Finish")) {
			if (ErrorHandler.passErrorCheck(nameField.getText(), priceField.getText(), locationField.getText(),
					quantityField.getText(), descriptionField.getText())) {
				List<String> newInfo = new ArrayList<String>();
				newInfo.add(nameField.getText());
				newInfo.add(priceField.getText());
				newInfo.add(locationField.getText());
				newInfo.add(categoryField.getValue());
				newInfo.add(quantityField.getText());
				newInfo.add(updateSku);
				newInfo.add(descriptionField.getText());
				newInfo.add(keywordsField.getText());
				data.update(updateSku, newInfo);
				System.out.println(newInfo.get(5));
				data.deleteAllItems("communicator");
				infoAlert("Your edit was successful!");
				closeWindow();
			}
		} else {
			if (ErrorHandler.passErrorCheck(nameField.getText(), priceField.getText(), locationField.getText(),
					quantityField.getText(), descriptionField.getText())) {
				Item new_item = new Item(nameField.getText(), priceField.getText(), locationField.getText(),
						categoryField.getValue(), quantityField.getText(), data.createSKUSetup(),
						descriptionField.getText(), keywordsField.getText());
				data.addItem(new_item);
				String addAlert = nameField.getText() + " has been added to the database.";
				infoAlert(addAlert);
				closeWindow();
			}
		}
		data.deleteAllItems("communicator");
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
