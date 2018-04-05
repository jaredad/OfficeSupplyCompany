package items;

public class Item {
	
	String name;
	String price;
	String location;
	String category;
	String quantity;
	String description;
	
	public Item(String n, String p, String l, String c, String q, String d) {
		name = n;
		price = p;
		location = l;
		category = c;
		quantity = q;
		description = d;
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
	
	
	public String getDescription() {
		return "'" + description + "'";
	}

}
