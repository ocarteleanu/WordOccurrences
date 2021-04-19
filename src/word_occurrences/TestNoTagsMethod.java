package word_occurrences;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
/**
 * This class checks to see if the cleanArrayNoTags method removes the HTML tags from 
 * the poem, so that only the words in the poem will be counted by the TextAnalyzer class
 */
class TestNoTagsMethod {
	static ArrayList <String> noTagsArray = new ArrayList<>();
	static ArrayList <String> arrayToTest = new ArrayList<>();
	@Test
	void test() {
		arrayToTest.add("stringOne");
		arrayToTest.add("string<Two");
		arrayToTest.add("stringThree");
		noTagsArray = TextAnalyzer.cleanArrayNoTags(arrayToTest);
		int countTags = 0;
		for(String eachString : noTagsArray) {
			if(eachString.contains("<"))
				countTags ++;
		}
		assertEquals(0, countTags);
	}

}
