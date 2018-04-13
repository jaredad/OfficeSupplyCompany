package items;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

class ErrorHandlerTest {
	
	//only able to test for assertTrue on many tests due to the error message box

	@Test
	void testIsSearchable() {
		String category = "Mail/Shipping";
		String name = "Asus Laptop";
		assertTrue(ErrorHandler.isSearchable(category));
		assertTrue(ErrorHandler.isSearchable(name));
	}
	
	@Test 
	void testIsInDatabase() throws ClassNotFoundException, SQLException {
		String keyword = "laptop";
		String price = "9.99";
		String location = "1A";
		assertTrue(ErrorHandler.inDatabase(keyword));
		assertTrue(ErrorHandler.inDatabase(price));
		assertTrue(ErrorHandler.inDatabase(location));
	}
	
	@Test 
	void testContainsElementLike() throws ClassNotFoundException, SQLException {
		String correct = "9.99";
		String incorrect = "pineapple";
		Database database = new Database();
		List<String> priceList = database.getSuggestions("price");
		assertTrue(ErrorHandler.containsElementLike(correct, priceList));
		assertFalse(ErrorHandler.containsElementLike(incorrect, priceList));
	}
	
	@Test
	void testContainsKeyword() throws ClassNotFoundException, SQLException {
		String realKeyword = "asus";
		String fakeKeyword = "pineapple";
		Database database = new Database();
		List<String> keywordsList = database.getSuggestions("keywords");
		assertTrue(ErrorHandler.containsKeyword(realKeyword, keywordsList));
		assertFalse(ErrorHandler.containsKeyword(fakeKeyword, keywordsList));
	}
	
	@Test
	void testKeywordsFieldCheck() {
		String keywords = "laptop,dell,intel";
		assertTrue(ErrorHandler.keywordsFieldCheck(keywords));
	}
	
	@Test
	void testDescriptionFieldCheck() {
		String description = "                             d";
		assertTrue(ErrorHandler.descriptionFieldCheck(description));
	}
	
	@Test
	void testQuantityFieldCheck() {
		String quantity1 = "5";
		String quantity2 = "22";
		String quantity3 = "34535345";
		assertTrue(ErrorHandler.quantityFieldCheck(quantity1));
		assertTrue(ErrorHandler.quantityFieldCheck(quantity2));
		assertTrue(ErrorHandler.quantityFieldCheck(quantity3));
	}
	
	@Test
	void testLocationFieldCheck() {
		String location1 = "1A";
		String location2 = "32H";
		assertTrue(ErrorHandler.locationFieldCheck(location1));
		assertTrue(ErrorHandler.locationFieldCheck(location2));
	}
	
	@Test
	void testPriceFieldCheck() {
		String price1 = "1.00";
		String price2 = "465464465161564565464646546484181982.22";
		assertTrue(ErrorHandler.priceFieldCheck(price1));
		assertTrue(ErrorHandler.priceFieldCheck(price2));
	}
	
	@Test
	void testNameFieldCheck() throws ClassNotFoundException, SQLException {
		String name = "Dell XPS 15";//actually in database
		assertTrue(ErrorHandler.nameFieldCheck(name,true));
		String name2 = "not in database";//not in database
		assertTrue(ErrorHandler.nameFieldCheck(name2,false));
	}
	
	
	

}
