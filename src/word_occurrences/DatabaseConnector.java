package word_occurrences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * This is a class that connects the program with a database where the words 
 * from a text document are stored and then read from. It uses a Java Database Connector 
 * to help connecting to the database
 * @author octavian
 * @see TextAnalyzer
 * @see PairOfWordsAndOccurrences
 */
public class DatabaseConnector {
	/**
	 * The data fields below store the words, then just a single occurrence
	 * of every word, and finally the pair of words - times they appear
	 */
	private static List<String> listOfUniqueWords = new ArrayList<>();
	private static List<PairOfWordsAndOccurrences> listOfPairs = new ArrayList<>();
	/**
	 * It is used to count how many times a words occur in the file provided
	 */
	public static int occurrenceCounter;
	private static Connection conn;
	/**
	 * this variable will dictate when the waiting thread (used to
	 * control the database update, will stop
	 */
	public static boolean running;
	/**
	 * The main method of the Main class that calls all the methods
	 * that allows the program to interact with a server database
	 * @param args String array arguments
	 * @throws Exception to catch any Exception that may occur
	 */
	public static void connectionEstablishment() throws Exception {
		// TODO Auto-generated method stub
		ArrayList <String> allWords = TextAnalyzer.trimPunctuation(
				TextAnalyzer.cleanArrayNoTags(TextAnalyzer.fileManager()));
		for(String eachWord : allWords){
			if(!listOfUniqueWords.contains(eachWord)){
				listOfUniqueWords.add(eachWord);
			}
		}
		int[] occurrenceCounter = new int[allWords.size() - 1];
		for(int j = 0; j < allWords.size() - 1; j++){
			occurrenceCounter[j] = 0;
		}
		for(String uniqueWord : listOfUniqueWords){
			for(int i = 0; i < allWords.size() - 1; i++){
				if(uniqueWord.compareTo(allWords.get(i)) == 0){
					occurrenceCounter[listOfUniqueWords.indexOf(uniqueWord)] ++;
				}
			}
			PairOfWordsAndOccurrences newPair = new PairOfWordsAndOccurrences(uniqueWord, 
					occurrenceCounter[listOfUniqueWords.indexOf(uniqueWord)]);
			listOfPairs.add(newPair);			
		}		
		conn = getConnection();
		createATable();
		dataPosting(listOfPairs);
		conn.close();
		System.out.println("Disconnected from the database...");	
	}
	/**
	 * Creates a connection between the program and a database server
	 * @return Connection object 
	 * @throws Exception to catch any Exception that may occur
	 */
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver"; 
			String url = "jdbc:mysql://107.180.41.146:3306/wordOccurrences";
			String user = "octavian";
			String password = "passwordsdlcclass";
			Class.forName(driver);		
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("Successfully connected to the database!");
			return conn;
		}catch (Exception e) {
			System.out.println(e);
		}		
		return null;		
	}
	/**
	 * Creates a table in the database if that table doesn't already exists
	 * @throws Exception to catch any Exception that may occur
	 */
	public static void createATable() throws Exception{
		try {
		System.out.println("Creating table if it doesn't exist");
		PreparedStatement createTable = conn.prepareStatement(
				"CREATE TABLE IF NOT EXISTS word(word VARCHAR(45) NOT NULL UNIQUE, occurrences INT "
				+ "NOT NULL)");
		createTable.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * 
	 * @param withApostrophe the String that will be checked for apostrophes 
	 * @return a String where the apostrophe, if exists, is converted into
	 * "`" to prevent errors in the SQL statements executions
	 */
	public static String noApostrophe(String withApostrophe) {
		 return withApostrophe.replace('\'', '`');		
	}
	/**
	 * Executes the INSERT statement into the "word" table in the database
	 * that the program is connected to
	 * @param anotherPair a PairOfWordsAndOccurrences object that stores
	 * the unique word in the poem and the occurrences of it 
	 * @throws Exception to catch any Exception that may occur
	 */
	public static void dataPosting(List<PairOfWordsAndOccurrences> anotherPair) 
			throws Exception{			
		System.out.println("Updating the database (please wait)...");
		running = true;
		WaitingForDatabaseUpdate waitingThread = new WaitingForDatabaseUpdate();
		waitingThread.join();
		waitingThread.start();
		if(checkIfUpdated() < 443) {
		for(int k = 0; k < anotherPair.size() - 1; k++){			
				String sql = noApostrophe(anotherPair.get(k).word.toString());	
				int occ = anotherPair.get(k).occurrence;
			try {
				PreparedStatement insertToDb = conn.prepareStatement(
				"INSERT INTO word (word, occurrences) VALUES('"
				+ sql + "' , " + occ +")");
				insertToDb.executeUpdate();
			}catch(Exception e) {				
				continue;
		}
		}	
	}
		running = false;
		System.out.println("\nDatabase is updated!");			
	}
	/**
	 * Displays all the data in the database
	 * @return an ArrayList of the type that stores pairs of words and occurrences
	 * @throws Exception used to catch all possible exception that
	 * may occur 
	 */
	public static ArrayList<PairOfWordsAndOccurrences> dataDisplay() throws Exception{
		conn = getConnection();
		ArrayList<PairOfWordsAndOccurrences> listOfPairs = new ArrayList<>();			
		try {
			PreparedStatement displayFromDb = conn.prepareStatement(
					"SELECT * FROM word ORDER BY occurrences DESC");
			ResultSet selection = displayFromDb.executeQuery();
			while(selection.next()) {
				listOfPairs.add(new PairOfWordsAndOccurrences(selection.getString("word"), selection.getInt("occurrences")));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		conn.close();
		return listOfPairs;
	}
	/**
	 * Check to see if the database is already updated, before
	 * attempting inserting all the values into the table 'word'
	 * @return the number of rows that the table in the database has
	 * @throws Exception all the possible exceptions
	 */
	public static int checkIfUpdated() throws Exception {
		int updateChecker = 0;		
		try {
			PreparedStatement countFromDb = conn.prepareStatement(
					"SELECT COUNT(word) FROM word");
			ResultSet selection = countFromDb.executeQuery();
			selection.next();
			updateChecker = selection.getInt(1);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return updateChecker;
	}
	

}
