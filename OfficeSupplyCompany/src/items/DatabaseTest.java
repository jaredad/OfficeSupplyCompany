package items;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

class DatabaseTest {

	@Test
	void testAddingItem() throws ClassNotFoundException, SQLException {
		Database database = new Database();
		Item new_item = new Item("fake item", "0.00", "1A", "Technology", "5", database.createSKUSetup(),"a fake item","fake,item");
		database.addItem(new_item);
		HashMap<String, String> search = new HashMap<String,String>();
		search.put("name", "'fake item'");
		ObservableList<Item> matches = database.search(search);
		assertTrue(new_item.getName().equals(matches.get(0).getName()));
		database.deleteItem(new_item);
	}
	
	@Test 
	void testDeleteItem() throws ClassNotFoundException, SQLException {
		Database database = new Database();
		Item new_item = new Item("fake item", "0.00", "1A", "Technology", "5", database.createSKUSetup(),"a fake item","fake,item");
		database.addItem(new_item);
		HashMap<String, String> search = new HashMap<String,String>();
		search.put("name", "'fake item'");
		ObservableList<Item> matches = database.search(search);
		assertTrue(matches.size()>0);
		database.deleteItem(matches.get(0));
		matches = database.search(search);
		assertTrue(matches.size()==0);
	}
	
	@Test
	void testEditItem() throws ClassNotFoundException, SQLException {
		Database database = new Database();
		String sku = database.createSKUSetup();
		Item new_item = new Item("fake item", "0.00", "1A", "Technology", "5", sku,"a fake item","fake,item");
		database.addItem(new_item);
		new_item = new Item("fake item updated", "0.00", "1A", "Technology", 
				"5", sku,"a fake item","fake,item");
		List<String> info = new ArrayList<String>(Arrays.asList("fake item updated", "0.00", "1A", "Technology", 
				"5", sku,"a fake item","fake,item"));
		database.update(info);
		HashMap<String, String> search = new HashMap<String,String>();
		search.put("sku", "'"+sku+"'");
		ObservableList<Item> matches = database.search(search);
		assertTrue(matches.get(0).getName().equals("fake item updated"));
		database.deleteItem(new_item);
	}

}
