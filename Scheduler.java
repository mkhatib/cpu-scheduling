//package .Users.mkhatib.Documents.os.cpu-scheduling;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import javax.swing.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public class Scheduler extends Object{
	// Almost 
	private int MULTIPROGRAMMING_DEGREE = 20;
	protected MLFQueue readyQueue; ;
	protected ArrayList<Process> doneQueue = new ArrayList<Process>();
	private static Lock lock = new ReentrantLock();
	private static Condition spaceAvailable = lock.newCondition();
	private static Condition processAvailable = lock.newCondition();
	protected JTextArea resultTextArea;
	
	int clock;
	Process P, T;
	boolean idle;
	public Thread thread;
	
	public Scheduler(){
		this(0);
	}
	
	
	public Scheduler( int c) {
      	clock = c-1; 
      	idle = true;
		readyQueue = new MLFQueue();
   	}
	
	public Scheduler(int quantum0, int quantum1, int context, int degree){
		readyQueue = new MLFQueue(quantum0,quantum1,context,degree);
		MULTIPROGRAMMING_DEGREE = degree;
	}
	
	
	public void insertProcess(Process process) {
		lock.lock();
		try {
			// If there's no space in the system this method have to wait until it is be signaled
			//System.out.println(readyQueue.getNumOfElements());
			
			while(readyQueue.getNumOfElements() >= MULTIPROGRAMMING_DEGREE)
				spaceAvailable.await();
			readyQueue.add(process); // add new process to first RRqueue
			resultTextArea.append("Process " +process.getPID() + " has entered the ready queue at " + clock + "\n");
			process.setArrivalTime(clock); 
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
			//while(readyQueue.getNumOfElements() <= 0)
			//	processAvailable.await();
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
			resultTextArea.append("Process " +p.getPID() + " has finished at " + clock + "\n");
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
