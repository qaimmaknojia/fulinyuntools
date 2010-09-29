package edu.rpi.cs.os.queuingpolicy;

import java.util.Arrays;
/**
 * round robin scheduler implementation
 * @author Linyun Fu
 *
 */
public class RRScheduler extends Scheduler {

	static {
		name = "Round Robin";
	}
	
	/**
	 * time quantum
	 */
	public int tq = 0;
	
	/**
	 * remaining time quantum to run before switching process
	 */
	public int tqRemain;
	
	/**
	 * simply add to the tail of the queue
	 */
	@Override
	public void addToQueue(int id) {
		int i;
		for (i = 0; readyQueue[i] != -1; i++) ;
		readyQueue[i] = id;
	}

	/**
	 * overrides the function to add Round Robin features
	 */
	public void runOneUnit() {
		clock++;
	
		for (Process p : processes) {
			p.runOneUnit();
			if (p.getIORemain() == 0) {
				addToQueue(p.getID());
				p.setIoRemain(p.tio);
			}
		}
		
		if (currentProcess != -1) {
			tqRemain--;
			
			// if current process has not finished its CPU execution, it has to yield CPU
			if (tqRemain == 0) {
				if (processes[currentProcess].getStatus() == Process.CPU) {
					processes[currentProcess].yieldCPU();
					addToQueue(currentProcess);
				}
				currentProcess = getNextProcess();
				if (currentProcess != -1) {
					processes[currentProcess].enterCPU();
					tqRemain = tq;
				}
			}
		}
		
		if (currentProcess == -1 || processes[currentProcess].getStatus() == Process.IO) {
			currentProcess = getNextProcess();
			if (currentProcess != -1) {
				processes[currentProcess].enterCPU();
				tqRemain = tq;
			}
		}

	}
	
	/**
	 * test function
	 * @param args
	 */
	public static void main(String[] args) {
		Scheduler rrs = new RRScheduler();
		for (int i = 0; i < Scheduler.NUM_PROCESS; i++) {
			rrs.processes[i] = new Process();
		}
		Arrays.fill(rrs.readyQueue, -1);
		for (int i = 0; i < Scheduler.NUM_PROCESS; i += 2) {
			rrs.addToQueue(i);
			rrs.printReadyQueue();
		}
		for (int i = 1; i < Scheduler.NUM_PROCESS; i += 2) {
			rrs.addToQueue(i);
			rrs.printReadyQueue();
		}
	}

}
