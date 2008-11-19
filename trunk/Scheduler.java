//package .Users.mkhatib.Documents.os.cpu-scheduling;
import java.io.*;
import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public class Scheduler extends Object{
	// Almost 
	protected MLFQueue readyQueue = new MLFQueue();
	protected ArrayList<Processes> doneQueue = new ArrayList<Processes>();
	int clock;
	Process P, T;
	boolean idle;
	public Thread thread;
	
	public Scheduler( int c) {
     
      clock = c-1; //  for run loop
      idle = true;
         
   }
	
	
	

 
}
