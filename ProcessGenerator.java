import java.lang.Math;
import java.io.*;

/*
	this component generates a sequence of processes with different properties and writes them to a file for future use in the simulation
	the file format: process ID CPU_Bursts CPU_BurstTime IO_Bursts IO_BurstTime
	
	************** NOTE: bursts number must be modified ************
*/

class ProcessGenerator

{
	String fileName;
	private int inputCPUBursts=75,inputIOBursts=25;
	private int ct_min = 5, ct_max = 50, iot_min = 500, iot_max = 1000;
	// Should provide a more general constructor to set the other parameteres...
	
	public ProcessGenerator(String fileName){
		this.fileName = fileName;
	}
	public void generateProcesses(int numOfProcesses){
		int	pID;			// process ID
		int CPU_BurstTime;	// CPU burst time 
		int IO_BurstTime;	// IO burst time 
		int	CPU_Bursts;		// number of CPU bursts
		int	IO_Bursts;		// number of I/O bursts
		FileOutputStream fos; 
		DataOutputStream dos;
		double segma_cpu =  Math.sqrt( 1/( 2*Math.PI*inputCPUBursts*inputCPUBursts ) );// 0.005;//0.005321; 1/ (  2*Math.PI*75*75 )
		double segma_io = Math.sqrt( 1/( 2*Math.PI*inputIOBursts*inputIOBursts ) );
		int mean = 0;//numOfProcesses/2;
		
		try {

		    File file= new File(fileName);
		    fos = new FileOutputStream(file);
		    dos=new DataOutputStream(fos);
	
			int i =0 , j = - (numOfProcesses/2);
			while( i < numOfProcesses ) {
				pID = i;
				new PrintStream(dos).print(i);
				new PrintStream(dos).print(" ");

				CPU_Bursts =  (int)  ( ( 1/( Math.sqrt( 2*Math.PI*(Math.pow(segma_cpu,2) ) ) ) ) * Math.exp( -(Math.pow( (j - mean ),2) ) / 2*Math.pow(segma_cpu,2) ) ); // (int) (Math.random() * (cb_max - cb_min + 1) ) + cb_min;
				new PrintStream(dos).print(CPU_Bursts);
				new PrintStream(dos).print(" ");
				CPU_BurstTime = (int) (Math.random() * (ct_max - ct_min + 1) ) + ct_min;
				new PrintStream(dos).print(CPU_BurstTime);
				new PrintStream(dos).print(" ");
				IO_Bursts = (int)  ( ( 1/( Math.sqrt( 2*Math.PI*(Math.pow(segma_io,2) ) ) ) ) * Math.exp( -(Math.pow( (j - mean ),2) ) / 2*Math.pow(segma_io,2) ) ); // (int) (Math.random() * (cb_max - cb_min + 1) ) + cb_min;//(int) (Math.random() * (iob_max - iob_min + 1) ) + iob_min;
				new PrintStream(dos).print(IO_Bursts);
				new PrintStream(dos).print(" ");
				IO_BurstTime = (int) (Math.random() * (iot_max - iot_min + 1) ) + iot_min;
				new PrintStream(dos).print(IO_BurstTime);
			//	new PrintStream(dos).print(" ");
				new PrintStream(dos).println("");
				
				i++;
				j++;
			}
		//	( 1/( Math.sqrt( 2*PI*(pow(segma,2) ) ) ) ) * exp( -(pow( (i - mean),2) ) / 2*pow(segma,2) ) ;
			    // Close  output stream
			    dos.close();
	
		} catch (IOException e) {
		      e.printStackTrace();
		    }

		 
	}
	public String getFileName(){
		return fileName;
	}
}