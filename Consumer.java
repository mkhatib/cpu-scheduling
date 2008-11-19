import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public final class Consumer extends Scheduler implements Runnable{

		private MLFQueue mlf_Queue = new MLFQueue();
		private ArrayList done = new ArrayList();
		
		private static Lock lock = new ReentrantLock();
		
		private static Condition finishedProcess = lock.getCondition();
		
		
		public Consumer(){
			
		}
		
		public simulation() {
			Consumer consumer = new Consumer();
			Scheduler schObject = new Scheduler("processes.txt", consumer);
		}
		
		public void insertProcess(Process process) {
			mlf_Queue.add(process); // add new process to first RRqueue
		}
	
		
		public void run(){
			while(mlf_Queue.getNumOfElements() > 0)
			{
				Process p = 
			}
		}
	
}