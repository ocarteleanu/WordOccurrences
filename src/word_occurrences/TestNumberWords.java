package word_occurrences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * This class tests if the program counts all the words in the poem
 * The words in the poem were counted with a word document
 */
class TestNumberWords {

	@Test
	void test() {
		TextAnalyzer textAnalyzer = new TextAnalyzer();
		int numberOfWordsInPoem = textAnalyzer.fileManager().size();
		assertEquals(1091, numberOfWordsInPoem);
	}

}
