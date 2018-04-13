package home;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import items.Database;
import items.ErrorHandler;
import items.Item;
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
	private Button add, delete, search, viewEdit, reset;

	@FXML
	private HBox topHbox, bottomHbox;

	@FXML
	private VBox vbox;

	@FXML
	private TextField searchField;

	@FXML
	private ChoiceBox<String> searchType;

	@FXML
	private AnchorPane pane;

	@FXML
	private TableView<Item> table;

	@FXML
	private TableColumn<Item, String> skuColumn, nameColumn, locationColumn, priceColumn, categoryColumn,
			quantityColumn;

	private AutoCompletionBinding<String> searchSuggestions;

	private List<String> categories = new ArrayList<String>(
			Arrays.asList("Mail/Printing", "Office Furniture", "Office Supplies", "Technology"));

	private Database database = new Database();

	private HashMap<String, String> searchMap = new HashMap<String, String>();

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		searchTypeSetup();
		tableSetup();
	}

	public void tableSetup() {
		skuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		table.setPlaceholder(new Label("Welcome to the Office Supply Company Database"));
	}

	public void searchTypeSetup() {
		ObservableList<String> types = FXCollections.observableArrayList("Name", "Price", "Categories", "SKU",
				"Keywords", "Location");
		searchType.setItems(types);
		searchType.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldType, String newType) -> {
					try {
						changeSuggestionsList();
					} catch (ClassNotFoundException e) {
						ErrorHandler.errorAlert("An unusual error has occured");
					} catch (SQLException e) {
						ErrorHandler.errorAlert("An unusual error has occured");
					}
				});
	}

	public void changeSuggestionsList() throws ClassNotFoundException, SQLException {
		clearSuggestions();
		if (searchType.getValue().equals("Name")) {
			searchSuggestions =  TextFields.bindAutoCompletion(searchField, database.getSuggestions("name"));
		} else if (searchType.getValue().equals("Price")) {
			searchSuggestions = TextFields.bindAutoCompletion(searchField, database.getSuggestions("price"));
		} else if (searchType.getValue().equals("Categories")) {
			searchSuggestions = TextFields.bindAutoCompletion(searchField, categories);
		} else if (searchType.getValue().equals("SKU")) {
			searchSuggestions = TextFields.bindAutoCompletion(searchField, database.getSuggestions("sku"));
		} else if (searchType.getValue().equals("Keywords")) {
			searchSuggestions = TextFields.bindAutoCompletion(searchField, removeCopies(database.getKeywords()));
		} else if (searchType.getValue().equals("Location")) {
			searchSuggestions = TextFields.bindAutoCompletion(searchField, database.getSuggestions("location"));
		}
	}
	
	public List<String> removeCopies(List<String> keywords) {
		List<String> copylessList = new ArrayList<String>();
		for (int i = 0; i < keywords.size(); i++) {
			if (!(keywords.subList(0, i).contains(keywords.get(i)))){
				copylessList.add(keywords.get(i));
			}
		}
		
		return copylessList;
	}

	public void clearSuggestions() {
		if (!(searchSuggestions == null)) {
			searchSuggestions.dispose();
		}
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

		for (int i = 0; i < table.getItems().size(); i++) {
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
		if (ErrorHandler.isSearchable(searchType.getValue()) && (ErrorHandler.inDatabase(searchField.getText()))) {
			clearTable();
			String search = searchField.getText();
			if (search.equals("")) {
				ObservableList<Item> items = database.getAllObservableItems();
				table.getItems().addAll(items);
			} else {
				searchByType(search);
				if(table.getItems().isEmpty()) {
					ErrorHandler.errorAlert("This item is not searchable by this search type");
					reset();
				}
			}
		}
	}

	public void searchByType(String search) throws ClassNotFoundException, SQLException {
		if (searchType.getValue().equals("Name")) {
			searchMap.put("name", "'" + search + "'");
			ObservableList<Item> items = database.search(searchMap);
			table.getItems().addAll(items);
		} else if (searchType.getValue().equals("Price")) {
			searchMap.put("price", "'" + search + "'");
			ObservableList<Item> items = database.search(searchMap);
			table.getItems().addAll(items);
		} else if (searchType.getValue().equals("Categories")) {
			searchMap.put("category", "'" + search + "'");
			ObservableList<Item> items = database.search(searchMap);
			table.getItems().addAll(items);
		} else if (searchType.getValue().equals("SKU")) {
			searchMap.put("sku", "'" + search + "'");
			ObservableList<Item> items = database.search(searchMap);
			table.getItems().addAll(items);
		} else if (searchType.getValue().equals("Keywords")) {
			searchMap.put("keywords", "'" + search + "'");
			ObservableList<Item> items = database.search(searchMap);
			table.getItems().addAll(items);
		} else if (searchType.getValue().equals("Location")) {
			searchMap.put("location", "'" + search + "'");
			ObservableList<Item> items = database.search(searchMap);
			table.getItems().addAll(items);
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
		for (int i = 0; i < table.getItems().size(); i++) {
			table.getItems().clear();
		}
		searchField.setText("");
		searchMap.clear();
	}

	public void clearTable() {
		for (int i = 0; i < table.getItems().size(); i++) {
			table.getItems().clear();
		}
	}

}
