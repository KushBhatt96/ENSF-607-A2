/** 
 * Started by M. Moussavi
 * March 2015
 * Completed by: Kush Bhatt
 * 				 Matthew Vanderway
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     *  opens an ObjectInputStream using a FileInputStream
     */
    
    private void readObjectsFromFile(String name)
    {
        MusicRecord record ;
        
        try
        {
            input = new ObjectInputStream(new FileInputStream( name ) );
        }
        catch ( IOException ioException )
        {
            System.err.println( "Error opening file." );
        }
        
        /* The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several records.
         * Loop should terminate when an EOFException is thrown.
         */
        
        try
        {
            while ( true )
            {
            	// TO BE COMPLETED BY THE STUDENTS
                record = (MusicRecord) input.readObject();
                		System.out.println(record.getYear()+"\n"+ record.getSongName() 
                		+ "\n" + record.getSingerName() + "\n" + record.getPurchasePrice() + "\n----------------");
                
            }   // END OF WHILE
            
         // ADD NECESSARY catch CLAUSES HERE    
        }catch(EOFException e) {
        	System.out.println("\nReached end of file.");
        }catch(ClassNotFoundException e1) {
        	System.out.println("Unable to locate the object." + e1.getMessage());
        }catch(IOException e2) {
        	System.out.println("Error in opening the file." + e2.getMessage());
        }finally{
        	try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }           // END OF METHOD 
    
    
    public static void main(String [] args)
    {
        ReadRecord d = new ReadRecord();
        d.readObjectsFromFile("mySongs.ser");
    }
}