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
		this(0,0,0,0,0);
	}
	public Process(int pid,int cpuBurstsNum, int cpuBurstTime, int ioBurstsNum, int ioBurstTime){
		this.pid = pid;
		this.CPU_BurstTime = cpuBurstTime;
		this.IO_BurstTime = ioBurstTime;
		this.CPU_Bursts = cpuBurstsNum;
		this.IO_Bursts = ioBurstsNum;
		leftTime = getTotalCPUTime();
		sequence = new boolean[CPU_Bursts+IO_Bursts];
	}
	
	
	public int getTotalCPUTime()
	{
		return CPU_BurstTime*CPU_Bursts;
	}
	
	public String toString(){
		return (pid + " - " +getTotalCPUTime());
	}
	
	public int getRemainingTime()
	{
		return leftTime;
	}
	public void servedOneClock() 
	{ 
		leftTime--;
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
		sequence[0] = true;
		for( int i = 1; cpu < this.CPU_Bursts ||  io < this.IO_Bursts ; i++) 
		{
			if( (i%2==0) || (io == this.IO_Bursts) )
			{
				sequence[i] = true;
				cpu++;
			}
			else
			{
				sequence[i] = false;
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