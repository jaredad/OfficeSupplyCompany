package items;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorHandler {

	public static boolean passErrorCheck(String name, String price, String location, String quantity,
			String description, String keywords, boolean isEdit) throws ClassNotFoundException, SQLException {
		if (!nameFieldCheck(name, isEdit)) {
			return false;
		} else if (!priceFieldCheck(price)) {
			return false;
		} else if (!locationFieldCheck(location)) {
			return false;
		} else if (!quantityFieldCheck(quantity)) {
			return false;
		} else if (!descriptionFieldCheck(description)) {
			return false;
		} else if (!keywordsFieldCheck(keywords)) {
			return false;
		}
		return true;
	}

	public static boolean nameFieldCheck(String name, boolean isEdit) throws ClassNotFoundException, SQLException {
		Database database = new Database();
		if (name.equals(null)) {
			String alertMessage = "Name field is blank!";
			errorAlert(alertMessage);
			return false;
		} else if (database.getSuggestions("name").contains(name) && !isEdit) {
			String alertMessage = "This item has already been added to the database!";
			errorAlert(alertMessage);
			return false;
		}
		for (int i = 0; i < name.length(); i++) {
			if (!(name.substring(i, i + 1).equals(" "))) {
				return true;
			}
		}
		String alertMessage = "Name field is blank!";
		errorAlert(alertMessage);
		return false;
	}

	public static boolean priceFieldCheck(String price) {
		try {
			Float.parseFloat(price);
			if ((price.length() - price.indexOf(".")) == 3) {
				return true;
			} else {
				String alertMessage = "Please only input two numbers after the decimal place in the price field.";
				errorAlert(alertMessage);
				return false;
			}
		} catch (NumberFormatException e) {
			String alertMessage = "Improper number format in the price field. Proper format examples: 10.73, 1100.20, 8.66";
			errorAlert(alertMessage);
			return false;
		}
	}

	public static boolean locationFieldCheck(String location) {
		List<String> rows = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H"));
		if (location.length() > 3 || location.length() < 2) {
			String alertMessage = "Improper format for location field. Please type aisle (1-32) then column (A-H). Examples: 1A, 5G, 14B.";
			errorAlert(alertMessage);
			return false;
		} else if (location.length() == 2) {
			try {
				Integer.parseInt(location.substring(0, 1));
				if (rows.contains(location.substring(1))) {
					return true;
				} else {
					String alertMessage = "Please input a column between A and H.";
					errorAlert(alertMessage);
					return false;
				}
			} catch (NumberFormatException e) {
				String alertMessage = "Improper format for location field. Please type aisle (1-32) then column (A-H). Examples: 3A, 12G, 8C.";
				errorAlert(alertMessage);
				return false;
			}
		} else if (location.length() == 3) {
			try {
				int aisle = Integer.parseInt(location.substring(0, 2));
				if (rows.contains(location.substring(2)) && aisle <= 32) {
					return true;
				} else {
					String alertMessage = "Improper format for location field. Please type aisle (1-32) then row (A-H). Examples: 3A, 12G, 8C.";
					errorAlert(alertMessage);
					return false;
				}
			} catch (NumberFormatException e) {
				String alertMessage = "Improper format for location field. Please type aisle (1-32) then row (A-H). Examples: 3A, 12G, 8C.";
				errorAlert(alertMessage);
				return false;
			}
		}

		return false;
	}

	public static boolean quantityFieldCheck(String quantity) {
		try {
			Integer.parseInt(quantity);
			return true;
		} catch (NumberFormatException e) {
			String alertMessage = "Please enter the whole number value of the quantity of items. Examples. 1, 14, 23.";
			errorAlert(alertMessage);
			return false;
		}
	}

	public static boolean descriptionFieldCheck(String description) {
		if (description.equals(null)) {
			String alertMessage = "Description field is blank!";
			errorAlert(alertMessage);
			return false;
		}
		for (int i = 0; i < description.length(); i++) {
			if (!(description.substring(i, i + 1).equals(" "))) {
				return true;
			}
		}
		String alertMessage = "Description field is blank!";
		errorAlert(alertMessage);
		return false;
	}

	public static boolean keywordsFieldCheck(String keywords) {
		if (keywords.startsWith(",") || keywords.endsWith(",")) {
			String alertMessage = "Keywords cannot start or end with ','";
			errorAlert(alertMessage);
			return false;
		} else if (keywords.contains(" ")) {
			String alertMessage = "Keywords cannot contain spaces. Each keyword must be an individual word";
			errorAlert(alertMessage);
			return false;
		}

		return true;
	}

	public static boolean inDatabase(String x) throws ClassNotFoundException, SQLException {
		Database database = new Database();
		List<String> names = database.getSuggestions("name");
		List<String> prices = database.getSuggestions("price");
		List<String> locations = database.getSuggestions("location");
		List<String> keywords = database.getSuggestions("keywords");
		List<String> categories = database.getSuggestions("category");
		List<String> skus = database.getSuggestions("sku");
		if (containsElementLike(x, names) || containsElementLike(x, prices) || containsElementLike(x, locations) || containsKeyword(x, keywords)
				|| containsElementLike(x, categories) || containsElementLike(x, skus) || x.equals("")) {
			return true;
		}

		errorAlert("This item is not in our database!");
		return false;
	}

	public static boolean containsKeyword(String search, List<String> keywords) {
		for (int i = 0; i < keywords.size(); i++) {
			if (keywords.get(i).toLowerCase().contains(search.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
		
	public static boolean containsElementLike(String search, List<String> dataList) {
		for (int i = 0; i < dataList.size();i++) {
			if (dataList.get(i).toLowerCase().equals(search.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSearchable(String category) {
		if (category == null) {
			errorAlert("Select a category to search by.");
			return false;
		}
		return true;
	}

	public static void errorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
