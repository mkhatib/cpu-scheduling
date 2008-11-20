//package .Users.mkhatib.Documents.os.cpu-scheduling;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public class Scheduler extends Object{
	// Almost 
	private final int MULTIPROGRAMMING_DEGREE = 20;
	protected MLFQueue readyQueue = new MLFQueue();
	protected ArrayList<Process> doneQueue = new ArrayList<Process>();
	private static Lock lock = new ReentrantLock();
	private static Condition spaceAvailable = lock.newCondition();
	private static Condition processAvailable = lock.newCondition();
	
	int clock;
	Process P, T;
	boolean idle;
	public Thread thread;
	
	public Scheduler(){
		this(0);
	}
	
	
	public Scheduler( int c) {
     
      clock = c-1; //  for run loop
      idle = true;
         
   }
	
	
	public void insertProcess(Process process) {
		lock.lock();
		try {
			// If there's no space in the system this method have to wait until it is be signaled
			//System.out.println(readyQueue.getNumOfElements());
			
			while(readyQueue.getNumOfElements() >= MULTIPROGRAMMING_DEGREE)
				spaceAvailable.await();
			readyQueue.add(process); // add new process to first RRqueue
			processAvailable.signalAll();
			//System.out.println("InsertProcess");
			
		}
		catch(InterruptedException ex){
			ex.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}

	public Process getProcess(){
		lock.lock();
		Process p=null;
		try {
			// If there's no space in the system this method have to wait until it is be signaled
			while(readyQueue.getNumOfElements() <= 0)
				processAvailable.await();
			p = (Process)readyQueue.poll(); // Get Best Process
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			lock.unlock();
		}
		return p;
	}
	
	public void removeProcess(Process p){
		lock.lock();
		try{
			readyQueue.removeFromFCFS(p);
			spaceAvailable.signalAll();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
	public void processFinished(Process p){
		lock.lock();
		try{
			//readyQueue.removeFromFCFS(p);
			spaceAvailable.signalAll();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			lock.unlock();
		}	
	}
 
}
