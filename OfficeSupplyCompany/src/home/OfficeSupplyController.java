package home;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.controlsfx.control.textfield.TextFields;

import items.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OfficeSupplyController {

    @FXML
    private Button add;

    @FXML
    private HBox topHbox;

    @FXML
    private VBox vbox;

    @FXML
    private Button search;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> searchType;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button delete;

    @FXML
    private TableView<?> table;

    @FXML
    private HBox bottomHbox;

    @FXML
    private Button viewEdit;

	private ArrayList<String> suggestions = new ArrayList<String>();
    
    @FXML
    public void initialize() throws ClassNotFoundException, SQLException {
    	suggestions.add("hello");
    	TextFields.bindAutoCompletion(searchField, suggestions); // controlsfx jar from mvnrepository.com
    	ObservableList<String> types = FXCollections.observableArrayList("Name", "Price", "Categories", "SKU", "Description");
		searchType.setItems(types);
		searchField.setDisable(true);
    }
    
    public void openItemWindow() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ItemGUI.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Item View/Edit");
			stage.setScene(new Scene(root, 515, 450));
			stage.show();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Cannot open this window.");
			alert.showAndWait();
		}
	}
    
    
    public void deleteItem() {
    	//ask for confirmation
    	//delete item from list
    }
    
    public void changeQuantity() {
    	//pop-up box for quantity change
    }
    
    public void editViewItemInfo() {
    	//open ItemGUI
    }
    
    public void searchItem() {
    	//update table based on items
    }

}
