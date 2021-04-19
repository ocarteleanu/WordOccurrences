package word_occurrences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * TextAnalyzer class is used to carry out the tasks that are
 * meant to read a HTML document, select only the words that 
 * appear in the poem included in that HTML file (ignoring HTML 
 * tags), and display all the words together with a number that
 *  represent the times each word "occurs" in the poem "The Raven"
 *  by E. Poe
 * @author octavian
 * @version 1.0
 * @see ArrayList
 * @see File
 * @see Scanner
 */
public class TextAnalyzer {
	//create the lists that will store all the words in the poem, unique words 
	//and the pairs of one word and its frequency
	/**
	 * The data fields below store the words, then just a single occurrence
	 * of every word, and finally the pair of words - times they appear
	 * There are also the data fields that are used to create a scanner object
	 * and an integer to count the number of times each word appear
	 */
	private static ArrayList<String> listOfWords = new ArrayList<>();
	private static ArrayList<String> listOfUniqueWords = new ArrayList<>();
	private static ArrayList<PairOfWordsAndOccurrences> listOfPairs = new ArrayList<>();
	private static Scanner wordScanner;
	/**
	 * It is used to count how many times a words occur in the file provided
	 */
	public static int occurrenceCounter;
	/**
	 * By using a Scanner object, it reads a HTML file, looks
	 * for the beginning of the poem, then reads all the words 
	 * in the poem, ignores the HTML tags, and stops after the 
	 * last word of the poem 
	 * @return a list of strings with all the words in the poem
	 * @throws FileNotFoundException if the file is not found based 
	 * on the data provided
	 * @throws NoSuchElementException if the element being requested
	 * does not exist
	 */
	public static ArrayList<String> fileManager(){			
		try {
			//create a file object that will read the initial document
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream is = classLoader.getResourceAsStream("1065-h.htm");			
			wordScanner = new Scanner(is);
			boolean controlOfStart = false; 
			while(wordScanner.hasNextLine()){
				String newWord = wordScanner.next();
				//set the point where the reading will actually start
				if(newWord.compareTo("<H1") == 0){
					controlOfStart = true;
				}
				//set the point where the reading will stop
				if(newWord.compareTo("<BR><BR><BR><BR>") == 0){
					break;
				}
				//ignore HTML tags
				if(controlOfStart && (! newWord.contains("STYLE")) && (newWord.charAt(0) != '<')
						&& !(newWord.contains("<") ^ newWord.contains(">"))){
					if(newWord.contains("&mdash;")){
						removeAmpersand(newWord);
						if(removeAmpersand(newWord)[0].compareTo("<BR>") != 0){																				
							listOfWords.add(removeAmpersand(newWord)[0]);						
						}
						if(removeAmpersand(newWord)[1].compareTo("<BR>") != 0){	
							;														
							listOfWords.add(removeAmpersand(newWord)[1]);
						}
					}
					else{
						if(newWord.compareTo("<BR>") != 0){													
							listOfWords.add(newWord);
						}
					}
				}
			}
			
			wordScanner.close();
			
		/*} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			System.out.println(listOfWords.get(listOfWords.size() - 1));
		}
		
		return listOfWords;
	}
	//this method will remove the dashes and create two words from the part 
	//before and after the dash
	/**
	 * Takes the words that contains dashes, represented in HTML with 
	 * an expression that include the ampersand. The dashes are then 
	 * removed and two words are generated from the initial one 
	 * @param initialStr the initial word that contains a dash, 
	 * represented in HTML with an expression that includes the
	 * ampersand
	 * @return two strings representing the words generated after the 
	 * dash is removed from the initial String
	 */
	public static String[] removeAmpersand (String initialStr){
		String[] theTwoStrings = {"", ""};		
		theTwoStrings[0] = initialStr.substring(0, initialStr.indexOf('&')) ;
		theTwoStrings[1] = initialStr.substring(initialStr.indexOf(';') + 1);
		return theTwoStrings;
	}
	//this method will remove any remaining HTML tags not removed in the previous steps
	/**
	 * Removes the HTML tags from the file that is read within the TextAnalyzer 
	 * class. 
	 * @param someArray the array of Strings that represent words in the poem and also
	 * HTML tags
	 * @return an array of Strings that excludes HTML tags
	 */
	public static ArrayList<String> cleanArrayNoTags(ArrayList<String> someArray){
		ArrayList<String> finalArray = new ArrayList<>();
		for(String someString : someArray){
			if(someString.contains("<")){
				finalArray.add(someString.substring(0, someString.indexOf('<')));
			}
			else 
			{
				finalArray.add(someString);
			}
		}
		return finalArray;
	}
	//this method will remove punctuation such as commas and quotation marks
	/**
	 * As some of the words in the poem are ended with punctuation signs, this
	 * method removes all the final punctuation that exists at the end of any 
	 * word that is in this situation
	 * @param beginingArray the initial Array of Strings that possibly includes
	 * words ended with punctuation signs 
	 * @return an array of Strings where no words ends with punctuation signs 
	 */
	public static ArrayList<String> trimPunctuation(ArrayList<String> beginingArray){
		ArrayList<String> finalArray = new ArrayList<>();
		for(String someString : beginingArray){
			if(someString.length() > 2){
			if(!Character.isLetter(someString.charAt(someString.length() -1))){
				if(!Character.isLetter(someString.charAt(someString.length() -2 ))){
					if(!Character.isLetter(someString.charAt(0))){
						finalArray.add(someString.substring(1, someString.length() - 2));
					}
					else
					{
						finalArray.add(someString.substring(0, someString.length() - 2));
					}
				}
				else if(!Character.isLetter(someString.charAt(0)))
				{
					finalArray.add(someString.substring(1, someString.length() - 1));
				}
				else 
				{
					finalArray.add(someString.substring(0, someString.length() - 1));
				}
			}
			else if(!Character.isLetter(someString.charAt(0)))
			{
				finalArray.add(someString.substring(1));
			}
			else
			{
				finalArray.add(someString);
			}
		}
		
		else {
			finalArray.add(someString);
		}
		}
		return finalArray;
		
	}
	
}

