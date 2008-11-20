import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;



class Process 

{
	public static final boolean CPU_BURST=true, IO_BURST=false;
	private	int	pid;			// process ID
	private int CPU_BurstTime;	// CPU burst time 
	private int IO_BurstTime;	// IO burst time 
	private int	CPU_Bursts;		// number of CPU bursts
	private int	IO_Bursts;		// number of I/O bursts
	private int finishTime;
	private int	leftTime;
	private int burstLeftTime;
	private int ioBurstLeftTime;
	private int ioLeftBursts;
	private int leftBursts;
	private int	turnAroundTime;	// will be incremented with the clock , and stopped when the process finishes
	private int burstIndex=0;
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
		burstLeftTime = CPU_BurstTime;
		ioBurstLeftTime = IO_BurstTime;
		leftBursts = CPU_Bursts;
		ioLeftBursts = IO_Bursts;
		leftTime = getTotalCPUTime();
		sequence = new boolean[CPU_Bursts+IO_Bursts];
		generateSequence();
		//printSequence();
	}
	
	
	public int getTotalCPUTime()
	{
		return CPU_BurstTime*CPU_Bursts;
	}
	
	public int getBurstLeftTime(){
		return burstLeftTime;
	}
	
	
	
	
	public int getLeftBursts(){
		return leftBursts;
	}
	public int getIOBurstLeftTime(){
		return ioBurstLeftTime;
	}
	public void servedOneIOClock(){
		ioBurstLeftTime--;
	}
	public int getIOBurstsLeft(){
		return ioLeftBursts;
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
		burstLeftTime--;
	}
	
	public void servedOneIOBurst(){
		ioLeftBursts--;
		ioBurstLeftTime = IO_BurstTime;
		burstIndex++;
	}
	public void servedOneBurst(){
		leftBursts--;
		burstLeftTime = CPU_BurstTime;
		burstIndex++;
	}
	
	public boolean getBurstType(){
		return sequence[burstIndex-1];
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
	
	public int getTotalBursts(){
		return sequence.length;
	}
	
	public int getBurstIndex(){
		return burstIndex;
	}
	
	public void printSequence(){
		System.out.print("[" );
		for(int i=0;i<sequence.length-1; i++){
			System.out.print(sequence[i] + ",");
		}
		System.out.println(sequence[sequence.length-1] + "]");
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