//package .Users.mkhatib.Documents.os.cpu-scheduling;
import java.util.concurrent.*;
import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class MLFQueue {
    
	private final int MAX_NUM_OF_PROCESSES = 20;
	private int queue1Quantum = 8, queue2Quantum = 16;
	
	private int lastProcessQueue;
	private Object[] queues = {
		new RRQueue(8,2),
		new RRQueue(16,2),
		//new ConcurrentLinkedQueue()
		new ArrayList()
	};
	// {{{ MLFQueue constructor
    /**
     * 
     */
    public MLFQueue() {
        
    }
	// }}}
	
	// Each new process should be added to the first RRQueue
	// If the queues are already full (20 processes), then this method should block unti there's some space for it.
	public void add(Object o){
		((RRQueue)queues[0]).add(o);
	}
	
	public void add(Object o, int queueNum){
		if(queueNum == 2)
			((/*ConcurrentLinkedQueue*/ArrayList)queues[queueNum]).add(o);
		else 
			((RRQueue)queues[queueNum]).add(o);
	}
	
	public int getNumOfElements(){
		return (((RRQueue)queues[0]).getSize() + ((RRQueue)queues[1]).getSize() + ((/*ConcurrentLinkedQueue*/ArrayList)queues[2]).size());
	}
	// Queue 0 processes will return first 
	// If Queue 0 doesn't contain processes, Queue 1 processes will return
	// If Queue 1 also doesn't contain processes, Queue 2 processes will return
	// Else it will return null
	public Object poll(){
		if(((RRQueue)queues[0]).getSize() != 0){
			lastProcessQueue = 0;
			return ((RRQueue)queues[0]).poll();
		}
		else if(((RRQueue)queues[1]).getSize() != 0){
			lastProcessQueue = 1;
			return ((RRQueue)queues[1]).poll();
		}
		else if(((/*ConcurrentLinkedQueue*/ArrayList)queues[2]).size() != 0){
			lastProcessQueue = 2;
			return ((/*ConcurrentLinkedQueue*/ArrayList)queues[2]).get(0);
		}
		return null;
	}
	
	public int getLastProcessQueue()
	{
		return lastProcessQueue;
	}
	
	public int getQueue1Quantum(){
		return queue1Quantum;
	}
	
	public int getQueue2Quantum(){
		return queue2Quantum;
	}
	
	public void removeFromFCFS(Object o){
		((ArrayList)queues[2]).remove(o);
	}
	
}
