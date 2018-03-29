package home;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OfficeSupplyController {

    @FXML
    private Button add, search, changeQuantity, viewEdit, delete;

    @FXML
    private HBox topHbox, bottomHbox;

    @FXML
    private VBox vbox;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<?> searchType;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<?> table;
    
    public void addItem() {
    	//open ItemGUI
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
