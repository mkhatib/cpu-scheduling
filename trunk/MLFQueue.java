//package .Users.mkhatib.Documents.os.cpu-scheduling;
import java.util.concurrent;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class MLFQueue {
    
	private final int MAX_NUM_OF_PROCESSES = 20;
	private Object[] queues = {
		new RRQueue(8,2),
		new RRQueue(16,2),
		new ConcurrentLinkedQueue()
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
	public void add(int queueNum, Object o){
		
	}
	public int getNumOfElements(){
		return (((RRQueue)queues[0]).getSize() + ((RRQueue)queues[1]).getSize() + ((ConcurrentLinkedQueue)queues[2]).size());
	}
	public Object poll(){
		if(((RRQueue)queues[0]).getSize() != 0){
			return ((RRQueue)queues[0]).poll();
		}
	}
}
