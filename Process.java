import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;



class Process 

{
	private	int	pID;			// process ID
	private int CPU_BurstTime;	// CPU burst time 
	private int IO_BurstTime;	// IO burst time 
	private int	CPU_Bursts;		// number of CPU bursts
	private int	IO_Bursts;		// number of I/O bursts
	
	
	/** 
	Process constructor
	**/
	public Process(){
		this.CPU_BurstTime = 0;
		this.IO_BurstTime = 0;
		this.CPU_Bursts = 0;
		this.IO_Bursts = 0;
		this.arrivalTime = 0;
	
	}
	
	
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
}