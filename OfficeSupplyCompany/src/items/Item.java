package items;

public class Item {
	
	String name;
	String price;
	String location;
	String category;
	String quantity;
	String description;
	String keywords;
	String sku;
	
	public Item(String n, String p, String l, String c, String q, String s, String d, String k) {
		name = n;
		price = p;
		location = l;
		category = c;
		quantity = q;
		sku = s;
		description = d;
		keywords = k;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getPrice() {
		return price;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	
	public String getDescription() {
		return description;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String s) {
		sku = s;
	}
	
	public boolean checkGreaterSku(String check) {
		if (Integer.parseInt(sku)>Integer.parseInt(check)) {
			return true;
		}
		
		return false;
	}
	
	public String decreaseSku() {
		int i = Integer.parseInt(sku);
		i = i-1;
		sku = Integer.toString(i);
		return sku;
	}
}
