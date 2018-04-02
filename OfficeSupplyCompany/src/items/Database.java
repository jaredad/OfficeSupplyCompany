package items;

import java.sql.*;
import java.util.Scanner;

public class Database {
	
	private Statement stat;
	
	public Database() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:testdb");
        stat = con.createStatement();
	}
	
    public void addItem(Item item) throws ClassNotFoundException, SQLException {
    	String cmd = "INSERT INTO items (name, price, location, category, quantity, sku, description)" + " VALUES(" + item.getName()+ ", " + item.getPrice()+
    			", " + item.getLocation() + ", " + item.getCategory() + ", " + item.getQuantity() + ", " + item.getSKU() + ", "+ item.getDescription() + ")";
    	stat.execute(cmd);
        }
    }