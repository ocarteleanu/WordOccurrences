package word_occurrences;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * This is the JUnit Test Suite for the WordOccurrences project
 * @author octavian
 *
 */
@RunWith(Suite.class)
@SuiteClasses({TestNoTagsMethod.class, TestNumberWords.class, TestRemoveAmpersandSeq.class,
	TestTrimPunctuation.class, TestApostropheRemovel.class})
public class AllTests {

}
