import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public final class Consumer extends Scheduler implements Runnable{
	
	private int numOfProcesses;
	
	

	public Consumer(int numOfProcesses){
		this.numOfProcesses = numOfProcesses;
		//thread = new Thread(this);
		//thread.start();
	}

	
	
	
	
	public void run(){
		try{
			
		System.out.println("Consumer Run()");
		
      	boolean preempted = false, finished=false;
		int processedClocks=0;
      	while (doneQueue.size() < numOfProcesses) {
         	clock++;
			P = getProcess();
			System.out.println(P + " - " + readyQueue.getNumOfElements() + " - " + doneQueue.size());
			
			int queueNumber = readyQueue.getLastProcessQueue();
        	idle = false; // Start Processing
			P.servedOneClock();
        	processedClocks++;

			if(readyQueue.getLastProcessQueue() == 0){
				while(processedClocks < readyQueue.getQueue1Quantum())
				{
					if (P.getRemainingTime()==0){
		            	P.finishedAt(clock+1);
						finished = true;
						break;
					}
					processedClocks++;
					clock++;
					P.servedOneClock();
				}
				if(!finished) readyQueue.add(P,1);
				else 
				{ 
					doneQueue.add(P);
					processFinished(P);
					finished = false;
				}
			}
			else if(readyQueue.getLastProcessQueue() == 1){
				while(processedClocks < readyQueue.getQueue2Quantum())
				{
					if (P.getRemainingTime()==0){
		            	P.finishedAt(clock+1);
						finished = true;
						break;
					}
					processedClocks++;
					clock++;
					P.servedOneClock();
				}
				if(!finished) readyQueue.add(P,2);
				else 
				{ 
					doneQueue.add(P);
					processFinished(P);
					finished = false;
				}
			}
			else //if(readyQueue.getLastProcessQueue() == 2)
			{
				while(P.getRemainingTime()!=0)
				{
					clock++;
					P.servedOneClock();
				}
            	P.finishedAt(clock+1); 
				doneQueue.add(P);
				removeProcess(P);
			//	spaceAvailable.signalAll();
				
				
			}
			finished = false;
			idle = true; // End Processing
			processedClocks = 0;
			
			//an.upstatus("Algorithm finished.");
      		//st.report(finishQ,"Feedback,q = 1");
      		//in.resetGUI();
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			//lock.unlock();
		}
	}
	
}