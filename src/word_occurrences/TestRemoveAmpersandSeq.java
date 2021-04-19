package word_occurrences;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 *This class tests if the method removeAmpersand removes the HTML sequences 
 *that start with the ampersand and end with semicolon, returning two words
 */
class TestRemoveAmpersandSeq {
	static String[] theTwoWords = {"", ""};
	
	@Test
	void test() {
		theTwoWords[0] = TextAnalyzer.removeAmpersand("us&mdash;by")[0];
		theTwoWords[1] = TextAnalyzer.removeAmpersand("us&mdash;by")[1];
		String stringOne = "us";
		String stringTwo = "by";
		assertTrue(stringOne.equals(theTwoWords[0]) && stringTwo.equals(theTwoWords[1]));
	}

}
