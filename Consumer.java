

public final class Consumer {

		private Object mlf_Queue = new MLFQueue();
		
		
		
		
		public simulation() {
			
			Consumer consumer = new Consumer();
			Scheduler schObject = new Scheduler("processes.txt", consumer);
			
		
		
		}
		public insertProcess(Process process) {
				
			mlf_Queue.add(process); // add new process to first RRqueue
			
		}
	
	
}