package word_occurrences;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
/**
 * This class verifies if the trimPunctuation method removes the punctuation at the end
 * of each word in the poem
 *
 */
class TestTrimPunctuation {
	static ArrayList <String> noPunctuationArray = new ArrayList<>();
	static ArrayList <String> arrayToTest = new ArrayList<>();
	@Test
	void test() {
		arrayToTest.add("stringOne");
		arrayToTest.add("stringt,");
		arrayToTest.add("stringThree:");
		noPunctuationArray = TextAnalyzer.trimPunctuation(arrayToTest);
		int countPunctuation = 0;
		for(String eachString : noPunctuationArray) {
			for(int i = 0; i < eachString.length(); i ++) {
				if(!Character.isLetter(eachString.charAt(i)))
					countPunctuation ++;
			}
		}
		assertEquals(0, countPunctuation);
	}

}
