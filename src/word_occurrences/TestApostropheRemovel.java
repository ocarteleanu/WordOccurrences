package word_occurrences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestApostropheRemovel {

	@Test
	void test() {
		String testString = "home's";
		assertEquals("home`s", DatabaseConnector.noApostrophe(testString));
	}

}
