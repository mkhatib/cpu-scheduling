import java.lang.Math;
import java.io.*;

/*
	this component generates a sequence of processes with different properties and writes them to a file for future use in the simulation
	the file format: process ID CPU_Bursts CPU_BurstTime IO_Bursts IO_BurstTime
	
	************** NOTE: bursts number must be modified ************
*/

class ProcessGenerator

{

	public static void generateProcesses(int numOfProcesses){
		int	pID;			// process ID
		int CPU_BurstTime;	// CPU burst time 
		int IO_BurstTime;	// IO burst time 
		int	CPU_Bursts;		// number of CPU bursts
		int	IO_Bursts;		// number of I/O bursts
        int ct_min = 5;
        int ct_max = 50;
		int iot_min = 500;
        int iot_max = 1000;
		int cb_max = 100;
		int cb_min = 50;
		int iob_max = 50;
		int iob_min = 0;
		FileOutputStream fos; 
		DataOutputStream dos;
		
		try {

		    File file= new File("processes.txt");
		    fos = new FileOutputStream(file);
		    dos=new DataOutputStream(fos);
	
			int i =0;
			while( i < numOfProcesses ) {
				pID = i;
				new PrintStream(dos).print(i);
				new PrintStream(dos).print(" ");
				CPU_Bursts = (int) (Math.random() * (cb_max - cb_min + 1) ) + cb_min;
				new PrintStream(dos).print(CPU_Bursts);
				new PrintStream(dos).print(" ");
				CPU_BurstTime = (int) (Math.random() * (ct_max - ct_min + 1) ) + ct_min;
				new PrintStream(dos).print(CPU_BurstTime);
				new PrintStream(dos).print(" ");
				IO_Bursts = (int) (Math.random() * (iob_max - iob_min + 1) ) + iob_min;
				new PrintStream(dos).print(IO_Bursts);
				new PrintStream(dos).print(" ");
				IO_BurstTime = (int) (Math.random() * (iot_max - iot_min + 1) ) + iot_min;
				new PrintStream(dos).print(IO_BurstTime);
			//	new PrintStream(dos).print(" ");
				new PrintStream(dos).println("");
				
				i++;
			}
			
			    // Close  output stream
			    dos.close();
	
		} catch (IOException e) {
		      e.printStackTrace();
		    }

		 
	}
}