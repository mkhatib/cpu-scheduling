//package .Users.mkhatib.Documents.os.cpu-scheduling;
import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class RRQueue {
	
	private ArrayList queue = new ArrayList();
	private int quantum;
	private int contextSwitchingTime;
	// {{{ RRQueue constructor
    /**
     * 
     */
    public RRQueue(int quantum, int context) {
		this.quantum = quantum;
		this.contextSwitchingTime = context;
    }
	// }}}
	public int getQuantum(){
		return quantum;
	}
	public int getContextSwitchingTime(){
		return contextSwitchingTime;
	}
	
	public void add(Object o){
		queue.add(o);
	}
	public Object poll(){
		Object o = queue.get(0);
		queue.remove(0);
		return o;
	}
	public void remove(int index){
		queue.remove(index);
	}
	public int getSize(){
		return queue.size();
	}
}
