package word_occurrences;

/**
 * Represents objects that include a String and an integer that will
 * be used to store pairs of words and numbers of occurrences in a 
 * poem
 */
public class PairOfWordsAndOccurrences {
	
	public String word;
	public int occurrence;
/**
 * The constructor that will create a pair of a word and the times
 * it appears in a text	
 * @param theWord String that represent a word in a poem
 * @param theOccurrence int that represent the number of times 
 * a word appear in a text
 */
	public PairOfWordsAndOccurrences (String theWord, int theOccurrence){
		this.word = theWord;
		this.occurrence = theOccurrence;
	}

}

