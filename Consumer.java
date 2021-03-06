import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import javax.swing.*;
public final class Consumer extends Scheduler implements Runnable{
	
	private int numOfProcesses;
	private int cpuIdleTime=0;
	private int contextSwitchingClocks;
	private int throughputTime=1000;
	private double throughput=0;
	private boolean allFinished = false;
	private double cpuUtilization=0;
	private double waitingTime=0;
	private double turnAroundTime=0;
	private ArrayList<Integer> numOfProcessesIn1000 = new ArrayList<Integer>();
	private ArrayList<Process> ioQueue = new ArrayList<Process>();
	
	public Consumer(int quantum0, int quantum1, int context, int degree, int numOfProcesses,JTextArea resultTextArea){
		this(quantum0,quantum1,context,degree,numOfProcesses);
		this.resultTextArea = resultTextArea;
	}
	
	public Consumer(int quantum0, int quantum1, int context, int degree, int numOfProcesses){
		super(quantum0,quantum1,context,degree);
		this.numOfProcesses = numOfProcesses;
		
	}
	public Consumer(int numOfProcesses){
		this(8,16,2,20,numOfProcesses);	
	}

	public boolean isFinished(){
		return allFinished;
	}
	
	
	
	public void run(){
		int processesIn1000=0, n=0;
      	boolean preempted = false, finished=false, finishedBurst=false;
		int processedClocks=0;
		System.out.println("NumOfProcesses: " + numOfProcesses);
		
      	while (doneQueue.size() < numOfProcesses) {
         	clock++;
			performIO();
			P = getProcess();
			if (P == null) {
				resultTextArea.append("CPU Is Idle at "+ clock +"\n" );
				cpuIdleTime++;
				continue;
			}
			System.out.println(P + " - " + readyQueue.getNumOfElements() + " - " + doneQueue.size());
			int queueNumber = readyQueue.getLastProcessQueue();
        	idle = false; // Start Processing
			P.servedOneClock();
			
			if(P.getFlag() == 1)
				P.setItsWait(clock); // 1 for first burst

			
        	processedClocks++;
			throuputHelper();
			if(readyQueue.getLastProcessQueue() == 0){
				while(processedClocks < readyQueue.getQueue1Quantum() )
				{
					if (P.getBurstLeftTime() <= 0){
						finishedBurst = true;
						P.servedOneBurst();
						// Should we continue with a new burst if there's more quantum and if the next burst is cpu??
						break;
					}
					processedClocks++;
					clock++;
					performIO();
					throuputHelper();
					P.servedOneClock();
					//System.out.println("Left Bursts: "+P.getLeftBursts());
				}
				if(!(P.getLeftBursts() <= 0)) {
					if(!finishedBurst){
						readyQueue.add(P,1);
						contextSwitchingClocks += 2;
						clock++;
						throuputHelper();
						clock++;
						performIO();
						performIO();
						throuputHelper();
					}
					else{
						//P.servedOneBurst();
						System.out.println("Left Bursts: "+P.getLeftBursts() );
						System.out.println("Total Burst For the process: " + P.getTotalBursts() + " - Index now: " + P.getBurstIndex());
						
						
						if(P.getBurstType() == Process.CPU_BURST){
							readyQueue.add(P);
							contextSwitchingClocks += 2;
							clock++;
							throuputHelper();
							performIO();
							clock++;
							throuputHelper();
						}
						else //if(P.nextBurst() == Process.IO_BURST)
						{
							ioQueue.add(P);
							performIO();
						}
						finishedBurst = false;
					}
				}
				else 
				{ 
					P.finishedAt(clock+1);
					doneQueue.add(P);
					processFinished(P);
				}
			}
			else if(readyQueue.getLastProcessQueue() == 1){
				while(processedClocks < readyQueue.getQueue2Quantum() )
				{
					if (P.getBurstLeftTime() <= 0){
						finishedBurst = true;
						P.servedOneBurst();
						// Should we continue with a new burst if there's more quantum and if the next burst is cpu??
						break;
					}
					processedClocks++;
					clock++;
					throuputHelper();
					performIO();
					throuputHelper();
					P.servedOneClock();
				}
				if(!(P.getLeftBursts() <= 0)) {
					if(!finishedBurst){
						readyQueue.add(P,2);
						contextSwitchingClocks += 2;
						clock++;
						throuputHelper();
						//throuputHelper();
						clock++;
						throuputHelper();
						performIO();
						performIO();
						//throuputHelper();
					}
					else{
						System.out.println("Left Bursts: "+P.getLeftBursts());
						System.out.println("Total Burst For the process: " + P.getTotalBursts());
						
						//P.servedOneBurst();
						if(P.getBurstType() == Process.CPU_BURST){
							readyQueue.add(P);
							P.setTempArrivalTime(clock);
							
							contextSwitchingClocks += 2;
							clock++;
							throuputHelper();
							performIO();
							clock++;
							throuputHelper();
						}
						else //if(P.nextBurst() == Process.IO_BURST)
						{
							ioQueue.add(P);
							performIO();
						}
						finishedBurst = false;
					}
				}
				else 
				{ 
					P.finishedAt(clock+1);
					doneQueue.add(P);
					processFinished(P);
				}
			}
			
			else //if(readyQueue.getLastProcessQueue() == 2)
			{
				while(processedClocks < P.getBurstLeftTime() )
				{
					processedClocks++;
					clock++;
					throuputHelper();
					performIO();
					throuputHelper();
					P.servedOneClock();
				}
				P.servedOneBurst();
				finishedBurst = true;
				if(!(P.getLeftBursts() <= 0)) {
					//P.servedOneBurst();
					// Finished Burst is True
					System.out.println("Left Bursts: "+P.getLeftBursts());
					System.out.println("Total Burst For the process: " + P.getTotalBursts());
					
					if(P.getBurstType() == Process.CPU_BURST){
						readyQueue.add(P);
						performIO();
						clock++;
						throuputHelper();
						clock++;
						throuputHelper();
						contextSwitchingClocks += 2;
					}
					else //if(P.nextBurst() == Process.IO_BURST)
					{
						ioQueue.add(P);
						performIO();
					}
					finishedBurst = false;
				}
				else 
				{ 
					P.finishedAt(clock+1);
					doneQueue.add(P);
					processFinished(P);
				}
			}
			
			finished = false;
			finishedBurst = false;
			idle = true; // End Processing
			processedClocks = 0;
			
			//an.upstatus("Algorithm finished.");
      		//st.report(finishQ,"Feedback,q = 1");
      		//in.resetGUI();
		}
		
		// Calculate Throughput
		int sum=0;
		for(int i=0; i < numOfProcessesIn1000.size(); i++)
			sum += numOfProcessesIn1000.get(i);
		throughput = (sum*1.0)/(numOfProcessesIn1000.size());
		// Only Context Switching, When the CPU is Idle?
		cpuUtilization = (calculateTotalCPUWork()/(clock*1.0))*100;
		turnAroundTime = calculateAvgTurnAroundTime();
		waitingTime = calculateAvgWaitingTime();
		
		System.out.println("Idle Time = (" + (contextSwitchingClocks+cpuIdleTime) + ") Out of total (" + clock +  ") time Unit" );
		// CPU Utilization = clock/(All Processes Time)
		//System.out.println("CPU Utilization: " + (1-((contextSwitchingClocks+cpuIdleTime)/(clock*1.0)))*100 + "%");
		System.out.println("CPU Utilization: " + (( calculateTotalCPUWork() )/(clock*1.0))*100 + "%");
		System.out.println("Throughput = " + throughput + " Procesess Per 1000 Time Unit");
		System.out.println("Average Turn Around Time = " + calculateAvgTurnAroundTime() );
		System.out.println("Average Waiting Time = " + calculateAvgWaitingTime() );

		System.out.println("CPU Idle Time: " + cpuIdleTime);
		
		allFinished = true;
	}
	
	private void throuputHelper(){
		int nn;
		if(clock % throughputTime == 0){
			
			
			nn = doneQueue.size();
			
			for(int i=0; i<numOfProcessesIn1000.size() ; i++)
				nn = nn - numOfProcessesIn1000.get(i);
			//System.out.println("Clock("+clock+")Throughput #"+ numOfProcessesIn1000.size() +" :  "+nn);
			numOfProcessesIn1000.add(nn);
		}
	}
	
	
	private void performIO(){
		for(int i=0; i<ioQueue.size(); i++){
			Process p = ioQueue.get(i);
			p.servedOneIOClock();
		
			if(p.getIOBurstLeftTime() <= 0){
				p.servedOneIOBurst();
				System.out.println("IO Bursts Left: " + p.getIOBurstsLeft());
				resultTextArea.append("Process " +p.getPID() + " is performing IO at " + clock + "\n");
				ioQueue.remove(i);
				readyQueue.add(p);
			}
		}
		
	}

	public double getCpuUtilization(){
		return cpuUtilization;
	}
	
	public double getThroughput(){
		return throughput;
	}
	
	public double getTurnAroundTime(){
		return turnAroundTime;
	}
	public double getWaitingTime(){
		return waitingTime;
	}
	
	public int calculateTotalCPUWork() {
	
		Process p = null;
		int totalTime =0;
		for(int i = 0; i < doneQueue.size() ; i++) 
		{
			p = doneQueue.get(i);
			totalTime += p.getTotalCPUTime();
		}
		return totalTime;
	}
	
	public int calculateAvgTurnAroundTime() {
	
		Process p = null;
		int avgTAT =0;
		for(int i = 0; i < doneQueue.size() ; i++) 
		{
			p = doneQueue.get(i);
			avgTAT += p.getTurnAroundTime();
		}
		return (avgTAT/numOfProcesses);
	}
	
	/* public int calculateWaitingTime() {
		Process p = null;
		int wainting =0;
		for(int i = 0; i < doneQueue.size() ; i++) 
		{
			p = doneQueue.get(i);
			wainting += ( p.getTurnAroundTime() - p.getArrivalTime() );
		}
		return (wainting/numOfProcesses);
	} */
	
	public int calculateAvgWaitingTime() {
	
		Process p = null;
		int waiting =0;
		for(int i = 0; i < doneQueue.size() ; i++) 
		{
			p = doneQueue.get(i);
			waiting += p.getItsWait();
		}
		return (waiting/numOfProcesses);
	
	}
	
}

