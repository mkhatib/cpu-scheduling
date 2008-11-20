

class Main {
	
public static void main(String argv[]) throws Exception {
		int numOfProcesses = 200;
		
		ProcessGenerator processG = new ProcessGenerator("processes.txt");
		processG.generateProcesses(numOfProcesses);
		String processesFile = processG.getFileName();
		
		// Two Threads
		Consumer consumer = new Consumer(numOfProcesses);
		Thread consumerThread = new Thread(consumer);
		Supplier supplier =  new Supplier(processesFile, consumer);
		
		supplier.start();
		consumerThread.start();
    }


}