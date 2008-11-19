import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;



class Process 

{
	private	int	pid;			// process ID
	private int CPU_BurstTime;	// CPU burst time 
	private int IO_BurstTime;	// IO burst time 
	private int	CPU_Bursts;		// number of CPU bursts
	private int	IO_Bursts;		// number of I/O bursts
	private int finishTime;
	private int	leftTime;
	private int	turnAroundTime;	// will be incremented with the clock , and stopped when the process finishes
	private boolean sequence [];

	
	/** 
	Process constructor
	**/
	public Process(){
		this(0,0,0,0);
	}
	public Process(int cpuBurstsNum, int cpuBurstTime, int ioBurstsNum, int ioBurstTime){
		this.CPU_BurstTime = cpuBurstTime;
		this.IO_BurstTime = ioBurstTime;
		this.CPU_Bursts = cpuBurstsNum;
		this.IO_Bursts = ioBurstsNum;
	}
	
	
	public int getTotalCPUTime()
	{
		return CPU_BurstTime*CPU_Bursts;
	}
	
	public String toString(){
		return "" + getTotalCPUTime();
	}
	
	public int getRemainingTime()
	{
		return leftTime;
	}
	public void servedOneClock() 
	{ 
		TimeLeft--;
	}
	
	public void finishedAt(int t) 
	{
      finishTime=t;
     // Tq = FinishTime-ArrivalTime;
     // Tqs = Math.round (Tq / ServiceTime);
	} 
	
	
	public void generateSequence() 
	{
		int cpu = 1;
		int io = 0;
		int rand;
		sequence[0] = true;
		for( int i = 1; cpu < this.CPU_Bursts ||  io < his.IO_Bursts ; i++) 
		{
			rand = (Math.random() / 2) ;
			if(rand)
			{
				sequence[i] = true;
				cpu++;
			}
			else 
			{
				if(sequence[i-1] == false ) // to insure no two IO bursts after each other..
					sequence[i] = true;
				io++;
			}
		
		}
	}
	
	/*
	public void setCPU_BurstTime(int max, int min)	{
		this.CPU_BurstTime=(int) (Math.random() * (max - min + 1) ) + min;
		System.out.println("Random CPU : " + this.CPU_BurstTime);
	}
	
	public void setIO_BurstTime(int max, int min)	{
		this.IO_BurstTime=(int) (Math.random() * (max - min + 1) ) + min;
		System.out.println("Random IO : " + this.IO_BurstTime);
	}

	public static void generateProcesses(){
		int rawRandomNumber;
        int ct_min = 5;
        int ct_max = 50;
		int iot_min = 500;
        int iot_max = 1000;
		
		
		ArrayList ProcessList = new ArrayList();
		Process process = new Process();

		int i =0;
		while( i < 5 ) {
			
			process.setCPU_BurstTime(ct_max,ct_min);
			process.setIO_BurstTime(iot_max,iot_min);

		
			i++;
		}
	
	}
	*/
}