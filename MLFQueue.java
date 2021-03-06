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
    
	private  int MAX_NUM_OF_PROCESSES = 20;
	private int queue1Quantum = 8, queue2Quantum = 16, contextSwitchTime=2;
	
	private int lastProcessQueue;
	private Object[] queues = new Object[3]; 
	// {{{ MLFQueue constructor
    /**
     * 
     */
	public MLFQueue(int q0,int q1, int ctxt, int dgr){
		queue1Quantum = q0;
		queue2Quantum = q1;
		contextSwitchTime = ctxt;
		MAX_NUM_OF_PROCESSES = dgr;
		queues[0] = new RRQueue(queue1Quantum,contextSwitchTime);
		queues[1] = new RRQueue(queue2Quantum,contextSwitchTime);
		queues[2] = new ArrayList();
	}
    public MLFQueue() {
		this(8,16,2,20);
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
			Process p = (Process)((ArrayList)queues[2]).get(0);
			((ArrayList)queues[2]).remove(0);
			return p;
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
