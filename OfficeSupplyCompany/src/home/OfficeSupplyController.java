package home;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import items.Database;
import items.ErrorHandler;
import items.Item;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<Item> table;

	@FXML
	private TableColumn<Item, String> skuColumn, nameColumn, locationColumn, priceColumn, categoryColumn,
			quantityColumn;

	@FXML
	private HBox bottomHbox;

	@FXML
	private Button viewEdit;
	
	@FXML
	private Button resetButton;

	private List<String> suggestions = new ArrayList<String>();
	List<String> categories = new ArrayList<String>(
			Arrays.asList("Mail/Printing", "Office Furniture", "Office Supplies", "Technology"));
	private Database database;
	private HashMap<String,String> searchMap = new HashMap<String, String>();

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		ObservableList<String> types = FXCollections.observableArrayList("Name", "Price", "Categories", "SKU",
				"Keywords", "Location");
		searchType.setItems(types);
		searchType.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldType, String newType) -> {
					try {
						changeSuggestionsList();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		database = new Database();
		skuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		table.setPlaceholder(new Label("Welcome to the Office Supply Company Database"));
	}

	public void changeSuggestionsList() throws ClassNotFoundException, SQLException {
		if (searchType.getValue().equals("Name")) {
			suggestions = database.getSuggestions("name");
		} else if (searchType.getValue().equals("Price")) {
			suggestions = database.getSuggestions("price");
		} else if (searchType.getValue().equals("Categories")) {
			suggestions = (ArrayList<String>) categories;
		} else if (searchType.getValue().equals("SKU")) {
			suggestions = database.getSuggestions("sku");
		} else if (searchType.getValue().equals("Keywords")) {
			suggestions = database.getKeywords();
		} else if (searchType.getValue().equals("Location")) {
			suggestions = database.getSuggestions("location");
		}
		TextFields.bindAutoCompletion(searchField, suggestions);
	}

	public void openItemWindow() {
		reset();
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
			e.printStackTrace();
		}
	}

	public void deleteItem() throws ClassNotFoundException, SQLException {
		try {
			Item selected = table.getSelectionModel().getSelectedItem();
			if (confirmAlert("Are you sure you want to delete " + selected.getName() + " from the database")) {
				database.deleteItem(selected);
			}
		} catch (NullPointerException e) {
			ErrorHandler.errorAlert("Please select an item from the table to view/edit.");
		} 
		
		for ( int i = 0; i<table.getItems().size(); i++) {
		    table.getItems().clear();
		}
	}

	public void editViewItemInfo() throws ClassNotFoundException, SQLException {
		searchField.setText("");
		try {
			Item selected = table.getSelectionModel().getSelectedItem();
			database.addToCommunicator(selected);
			reset();
			openItemWindow();
		} catch (NullPointerException e) {
			ErrorHandler.errorAlert("Please select an item in the table to view/edit.");
		}
	}

	public void searchItem() throws ClassNotFoundException, SQLException {
		if (ErrorHandler.isSearchable(searchType.getValue())
				&& ErrorHandler.inDatabase(searchField.getText())) {
			for ( int i = 0; i<table.getItems().size(); i++) {
			    table.getItems().clear();
			}
			String search = searchField.getText();
			if (searchType.getValue().equals("Name")) {
				searchMap.put("name", "'"+search+"'");
				ObservableList<Item> items = database.search(searchMap);
				table.getItems().addAll(items);
			} else if (searchType.getValue().equals("Price")) {
				searchMap.put("price", "'"+search+"'");
				ObservableList<Item> items = database.search(searchMap);
				table.getItems().addAll(items);
			} else if (searchType.getValue().equals("Categories")) {
				searchMap.put("category", "'"+search+"'");
				ObservableList<Item> items = database.search(searchMap);
				table.getItems().addAll(items);
			} else if (searchType.getValue().equals("SKU")) {
				searchMap.put("sku", "'"+search+"'");
				ObservableList<Item> items = database.search(searchMap);
				table.getItems().addAll(items);
			} else if (searchType.getValue().equals("Keywords")) {
				searchMap.put("keywords", "'"+search+"'");
				ObservableList<Item> items = database.search(searchMap);
				table.getItems().addAll(items);
			} else if (searchType.getValue().equals("Location")) {
				searchMap.put("location", "'"+search+"'");
				ObservableList<Item> items = database.search(searchMap);
				table.getItems().addAll(items);
			} 
			
			if (searchField.getText().equals("")) {
				ObservableList<Item> items = database.getAllObservableItems();
				table.getItems().addAll(items);
			}
		}
	}

	public boolean confirmAlert(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			return true;
		}
		return false;
	}
	
	public void reset() {
		for ( int i = 0; i<table.getItems().size(); i++) {
		    table.getItems().clear();
		}
		
		searchMap.clear();
	}

}
