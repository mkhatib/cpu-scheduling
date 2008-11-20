//package .Users.mkhatib.Documents.os.cpu-scheduling;
import java.io.*;
import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class Supplier extends Thread{

    private ArrayList<Process> processes = new ArrayList<Process>();
	private Consumer consumer;
	
	// {{{ Supplier constructor
    /**
     * 
     */
	public Supplier(String fileName, Consumer consumer) throws Exception{
		this(fileName);
		this.consumer = consumer;
		// Get the condition that will signal the insert method when there's enough space in the queue
		//this.processFinished = consumer.getProcessFinishCondition();
	}
    public Supplier(String fileName) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String pline;
		while((pline = br.readLine()) != null)
		{
			String[] params = pline.split(" ");
			int pid = Integer.parseInt(params[0]);
			int cpuBurstsNum = Integer.parseInt(params[1]);
			int cpuBurstTime = Integer.parseInt(params[2]);
			int ioBurstsNum = Integer.parseInt(params[3]);
			int ioBurstTime = Integer.parseInt(params[4]);
			// Should we sort them on the Number of CPU Bursts Or The CPU Burst Time 
			// Or the Total CPU Time it will need the CPU Which is equal to CPUBurstTime * NumOfCPUBursts
			/*
			int i;
			for(i=0;i<processes.size(); i++)
				if(cpuBurstsNum*cpuBurstTime < processes.get(i).getTotalCPUTime())
					break;
			*/
			processes.add(/*i,*/new Process(pid,cpuBurstsNum,cpuBurstTime, ioBurstsNum, ioBurstTime));
		}
    }
	// }}}
	
	public int getNumberOfProcesses(){
		return processes.size();
	}
	
	public void printAllProcesses()
	{
		for(int i=0;i<processes.size(); i++){
			System.out.println(processes.get(i));
		}
	}
	
	public void run(){
		// inserts processes into Ready Queue
		while(processes.size() != 0)
	 	{	
			// insertProcess Must Block if the Ready Queue is full (20 processes)
			//while(consumer.getNumberOfProcesses() >= MAX_PROCESSES_NUM)
			//	processFinished.await();
			consumer.insertProcess(processes.get(0));
		 	processes.remove(0);
		}
	}
	
	static public void main(String[] args) throws Exception
	{
		Supplier s = new Supplier("processes.txt");
		
		//System.out.println(s.getNumberOfProcesses());
		s.printAllProcesses();
	}
    
}
