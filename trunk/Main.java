

class Main {

public static void main(String argv[]) throws Exception {
		
		ProcessGenerator processG = new ProcessGenerator("processes.txt");
		processG.generateProcesses(200);
		String processesFile = processG.getFileName();
		
		// Two Threads
		Consumer consumer = new Consumer();
		Supplier supplier =  new Supplier(processesFile, consumer);
		
		supplier.start();
		consumer.start();
    }


}