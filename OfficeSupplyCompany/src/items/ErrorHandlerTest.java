package items;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ErrorHandlerTest {

	@Test
	void testIsSearchable() {
		String category = null;
		assertFalse(ErrorHandler.isSearchable(category));
		category = "Mail/Shipping";
		assertTrue(ErrorHandler.isSearchable(category));
	}

}
