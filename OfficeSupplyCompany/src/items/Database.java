package items;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

	private Statement stat;
	private Connection con;
	private String sku = "0";

	public void addItem(Item item) throws ClassNotFoundException, SQLException {
		openConnection();
		String cmd = "INSERT INTO items (name, price, location, category, quantity, sku, description, keywords)"
				+ " VALUES('" + item.getName() + "', '" + item.getPrice() + "', '" + item.getLocation() + "', '"
				+ item.getCategory() + "', '" + item.getQuantity() + "', '" + sku + "', '" + item.getDescription()
				+ "', '" + item.getKeywords() + "')";
		stat.execute(cmd);
		con.close();
	}

	public void addToCommunicator(Item item) throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "INSERT INTO communicator (name, price, location, category, quantity, sku, description, keywords)"
				+ " VALUES('" + item.getName() + "', '" + item.getPrice() + "', '" + item.getLocation() + "', '"
				+ item.getCategory() + "', '" + item.getQuantity() + "', '" + item.getSku() + "', '"
				+ item.getDescription() + "', '" + item.getKeywords() + "')";
		stat.execute(cmd);
		con.close();
	}

	public String createSKUSetup() throws ClassNotFoundException, SQLException {
		openConnection();
		List<String> skuList = new ArrayList<>();
		String cmd = "SELECT sku from items;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		while (results.next()) {
			for (int c = 1; c <= 1; ++c) {
				skuList.add(results.getString(c));
			}
		}
		con.close();
		return createSKU(skuList);
	}

	public String createSKU(List<String> skuList) {
		if (skuList.size() == 0) {
			sku = "1";
		} else {
			String currentSKU = skuList.get(skuList.size() - 1);
			int newSKU = Integer.parseInt(currentSKU);
			newSKU = newSKU + 1;
			sku = Integer.toString(newSKU);
		}
		
		return sku;
	}

	public void deleteAllItems(String table) throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "DELETE FROM " + table + ";";
		stat.execute(cmd);
		con.close();
	}

	public List<String> getTableValues() throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "Select * From communicator;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		List<String> item = new ArrayList<String>();
		while (results.next()) {
			for (int c = 1; c <= 8; ++c) {
				item.add(results.getString(c));
			}
		}
		con.close();
		return item;
		
	}

	public List<String> getSuggestions(String type) throws ClassNotFoundException, SQLException {
		openConnection();
		List<String> typeList = new ArrayList<>();
		String cmd = "SELECT " + type + " FROM items;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		while (results.next()) {
			typeList.add(results.getString(1));
		}
		con.close();
		return typeList;
	}

	public List<String> getKeywords() throws ClassNotFoundException, SQLException {
		openConnection();
		List<String> keywordsList = new ArrayList<>();
		String cmd = "SELECT keywords FROM items;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		while (results.next()) {
			keywordsList.add(results.getString(1));
		}

		List<String> finalKeywordsList = new ArrayList<String>();
		for (int i = 0; i < keywordsList.size(); i++) {
			finalKeywordsList.addAll(Arrays.asList(keywordsList.get(i).split(",")));
		}
		con.close();
		return finalKeywordsList;
	}

	public ObservableList<Item> search(HashMap<String, String> searchMap) throws SQLException, ClassNotFoundException {
		openConnection();
		String subcmd = "";
		for (int i = 0; i < searchMap.size();i++) {
			if (searchMap.keySet().toArray()[i] == "keywords") {
				subcmd = subcmd + " " + searchMap.keySet().toArray()[i] + " like '%" + 
			searchMap.get(searchMap.keySet().toArray()[i]).substring(1, searchMap.get(searchMap.keySet().toArray()[i]).length()-1)+"%' AND ";
			} else {
			subcmd = subcmd + " " + searchMap.keySet().toArray()[i] + " = " + searchMap.get(searchMap.keySet().toArray()[i])+" AND ";
			}
		}
		subcmd = subcmd.substring(0, subcmd.length()-5);
		System.out.println(subcmd);
		String cmd = "SELECT * FROM items WHERE " + subcmd + ";";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		ObservableList<Item> finalList = FXCollections.observableArrayList();
		while (results.next()) {
			ObservableList<String> item = FXCollections.observableArrayList();
			for (int i = 1; i < 9; i++) {
				item.add(results.getString(i));
			}
			Item newItem = new Item(item.get(0), item.get(1), item.get(2), item.get(3), item.get(4), item.get(5),
					item.get(6), item.get(7));
			finalList.add(newItem);
		}
		con.close();
		return finalList;
	}

	public boolean checkEmptyCommunicator() throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "SELECT COUNT(*) FROM communicator";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		if (results.getInt(1) == 0) {
			con.close();
			return true;
		}
		return false;
	}

	public void update(String updateSku, List<String> info) throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "UPDATE items SET name = '" + info.get(0) + "', price = '" + info.get(1) + "', location = '"
				+ info.get(2) + "', category = '" + info.get(3) + "', quantity = '" + info.get(4) + "', description = '"
				+ info.get(6) + "', keywords = '" + info.get(7) + "' WHERE sku = '" + info.get(5) + "';";
		stat.execute(cmd);
		con.close();
	}

	public void deleteItem(Item selected) throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "DELETE FROM items WHERE sku = '" + selected.getSku()+"';";
		stat.execute(cmd);
		List<Item> allItems = getAllItems();
		for (int i = 0; i < allItems.size(); i++) {
			if(allItems.get(i).checkGreaterSku(selected.getSku())) {
				decreaseSKU(allItems.get(i));
			}
		}
		con.close();
	}
	
	public void decreaseSKU(Item i) throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "UPDATE items SET sku = '" +i.decreaseSku()+"' WHERE name = '" + i.getName()+"';";
		stat.execute(cmd);
		con.close();
	}

	public List<Item> getAllItems() throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "SELECT * FROM items;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		List<Item> itemList = new ArrayList<Item>();
		while (results.next()) {
			List<String> item = new ArrayList<String>();
			for (int i = 1; i < 9; i++) {
				item.add(results.getString(i));
			}
			Item newItem = new Item(item.get(0), item.get(1), item.get(2), item.get(3), item.get(4), item.get(5),
					item.get(6), item.get(7));
			itemList.add(newItem);
		}
		con.close();
		return itemList;
	}
	
	public ObservableList<Item> getAllObservableItems() throws SQLException, ClassNotFoundException {
		openConnection();
		String cmd = "SELECT * FROM items;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		ObservableList<Item> itemList = FXCollections.observableArrayList();
		while (results.next()) {
			List<String> item = new ArrayList<String>();
			for (int i = 1; i < 9; i++) {
				item.add(results.getString(i));
			}
			Item newItem = new Item(item.get(0), item.get(1), item.get(2), item.get(3), item.get(4), item.get(5),
					item.get(6), item.get(7));
			itemList.add(newItem);
		}
		con.close();
		return itemList;
	}
	
	public void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:testdb");
		stat = con.createStatement();
	}
}
