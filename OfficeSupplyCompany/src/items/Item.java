package items;

public class Item {
	
	String name;
	String price;
	String location;
	String category;
	String quantity;
	String sku;
	String description;
	
	public Item(String n, String p, String l, String c, String q, String s, String d) {
		name = n;
		price = p;
		location = l;
		category = c;
		quantity = q;
		sku = s;
		description = d;
	}
	
	public void addToDatabase() {
		
	}
	
	public String getName() {
		return "'" + name + "'";
	}
	
	public String getPrice() {
		return "'" + price + "'";
	}
	
	public String getLocation() {
		return "'" + location + "'";
	}
	
	public String getCategory() {
		return "'" + category + "'";
	}
	
	public String getQuantity() {
		return "'" + quantity + "'";
	}
	
	public String getSKU() {
		return "'" + sku + "'";
	}
	
	public String getDescription() {
		return "'" + description + "'";
	}

}
