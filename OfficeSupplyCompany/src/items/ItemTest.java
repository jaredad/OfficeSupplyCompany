package items;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ItemTest {

	@Test
	void testItemCreation() {
		Item item = new Item("Dell Inspiron 15 5000 Series", "499.99", "5A", "Technology", "4", "1", "15 inch laptop with the latest Intel processors, "
				+ "a glossy finish and options like an infrared camera and FHD touch display,"
				+ " so you can create a PC that reflects what matters to you.", "Dell, Inspiron, laptop, intel, pc");
		assertTrue(item.getName().equals("Dell Inspiron 15 5000 Series"));
		assertTrue(item.getPrice().equals("499.99"));
		assertTrue(item.getLocation().equals("5A"));
		assertTrue(item.getCategory().equals("Technology"));
		assertTrue(item.getQuantity().equals("4"));
		assertTrue(item.getSku().equals("1"));
		assertTrue(item.getDescription().equals("15 inch laptop with the latest Intel processors, "
				+ "a glossy finish and options like an infrared camera and FHD touch display,"
				+ " so you can create a PC that reflects what matters to you."));
		assertTrue(item.getKeywords().equals("Dell, Inspiron, laptop, intel, pc"));
	}
	
	@Test
	void testGreaterSkuCheck() {
		Item originalItem = new Item("Dell Inspiron 15 5000 Series", "499.99", "5A", "Technology", "4", "1", "15 inch laptop with the latest Intel processors, "
				+ "a glossy finish and options like an infrared camera and FHD touch display,"
				+ " so you can create a PC that reflects what matters to you.", "Dell, Inspiron, laptop, intel, pc");
		Item newItem = new Item("Macbook Pro ", "1299.99", "5A", "Technology", "4", "2", "It’s razor thin, feather light, and even faster and more powerful than before. It has the brightest, most colorful Mac notebook display ever. And it features the Touch Bar — a Multi-Touch enabled strip of glass built "
				+ "into the keyboard for instant access to the tools you want, right when you want them. "
				+ "MacBook Pro is built on groundbreaking ideas. And it’s ready for yours.", "Mac, Apple, laptop, intel");
		assertTrue(newItem.checkGreaterSku(originalItem.getSku()));
	}
	
	@Test
	void decreaseSku() {
		Item newItem = new Item("Macbook Pro ", "1299.99", "5A", "Technology", "4", "2", "It’s razor thin, feather light, and even faster and more powerful than before. It has the brightest, most colorful Mac notebook display ever. And it features the Touch Bar — a Multi-Touch enabled strip of glass built "
				+ "into the keyboard for instant access to the tools you want, right when you want them. "
				+ "MacBook Pro is built on groundbreaking ideas. And it’s ready for yours.", "Mac, Apple, laptop, intel");
		newItem.decreaseSku();
		assertTrue(newItem.getSku().equals("1"));
	}

}
