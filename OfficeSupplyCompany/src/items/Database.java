package items;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

	private Statement stat;
	private String sku = "0";

	public Database() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:testdb");
		stat = con.createStatement();
		getTableValues();
	}

	public void addItem(Item item) throws ClassNotFoundException, SQLException {
		updateSKUSetup();
		String cmd = "INSERT INTO items (name, price, location, category, quantity, sku, description)" + " VALUES("
				+ item.getName() + ", " + item.getPrice() + ", " + item.getLocation() + ", " + item.getCategory() + ", "
				+ item.getQuantity() + ", " + sku + ", " + item.getDescription() + ")";
		stat.execute(cmd);
	}

	public void updateSKUSetup() throws ClassNotFoundException, SQLException {
		List<String> skuList = new ArrayList<>();
		String cmd = "SELECT sku from items;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		while (results.next()) {
			for (int c = 1; c <= 1; ++c) {
				skuList.add(results.getString(c));
			}
		}
		updateSKU(skuList);
	}
	
	public void updateSKU(List<String> skuList) {
		if (skuList.size() == 0) {
			sku = "1";
		} else {
			String currentSKU = skuList.get(skuList.size() - 1);
			int newSKU = Integer.parseInt(currentSKU);
			newSKU = newSKU + 1;
			sku = Integer.toString(newSKU);
		}
	}
	
	public void deleteAllItems() throws SQLException {
		String cmd = "DELETE FROM items;";
		stat.execute(cmd);
	}
	
	public void getTableValues() throws SQLException {
		String cmd = "Select * From items;";
		stat.execute(cmd);
		ResultSet results = stat.getResultSet();
		while (results.next()) {
			for (int c = 1; c <= 7; ++c) {
				System.out.println(results.getString(c));
			}
			System.out.println();
		}
	}
}
