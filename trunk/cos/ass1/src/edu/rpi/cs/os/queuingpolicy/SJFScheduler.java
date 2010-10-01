package edu.rpi.cs.os.queuingpolicy;

import java.util.Arrays;

/**
 * preemptive SJF scheduler implementation
 * one problem not solved yet: when a process finishes IO and comes back to the ready queue, 
 * it does not (but it should) interrupt the scheduler and trigger a context switch
 * @author Linyun Fu
 *
 */
public class SJFScheduler extends Scheduler {

	static {
		name = "Preemptive Shortest Job First";
	}

	/**
	 * add to ready queue according to remaining cpu time, shortest first
	 */
	@Override
	public void addToQueue(int id) {
		int i;
		int cpuRemain = processes[id].getCpuRemain();
		for (i = 0; readyQueue[i] != -1 
		&& processes[readyQueue[i]].getCpuRemain() <= cpuRemain; i++) ;
		for (int j = readyQueue.length-1; j > i; j--) readyQueue[j] = readyQueue[j-1];
		readyQueue[i] = id;
	}

	/**
	 * test function
	 * @param args
	 */
	public static void main(String[] args) {
		Scheduler sjfs = new SJFScheduler();
		for (int i = 0; i < Scheduler.NUM_PROCESS; i++) {
			sjfs.processes[i] = new Process();
			sjfs.processes[i].setCpuRemain(i);
		}
		Arrays.fill(sjfs.readyQueue, -1);
		for (int i = 0; i < Scheduler.NUM_PROCESS; i += 2) {
			sjfs.addToQueue(i);
			sjfs.printReadyQueue();
		}
		for (int i = 1; i < Scheduler.NUM_PROCESS; i += 2) {
			sjfs.addToQueue(i);
			sjfs.printReadyQueue();
		}
	}
}
