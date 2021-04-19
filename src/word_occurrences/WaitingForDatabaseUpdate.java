package word_occurrences;

/**
 * Creates a thread that will display some symbols (**) until
 * the database used by the program is updated 
 * @author octavian
 *
 */
public class WaitingForDatabaseUpdate extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub	
		System.out.print("*");
		while(DatabaseConnector.running) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("**");
		}		
	}

}
