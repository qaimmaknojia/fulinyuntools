package edu.rpi.cs.os.queuingpolicy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * HRRN scheduler implementation
 * @author Linyun Fu
 *
 */
public class HRRNScheduler extends Scheduler {

	static {
		name = "Highest Response Ratio Next";
	}

	/**
	 * add to the ready queue according to response ratio, highest response ratio first
	 */
	@Override
	public void addToQueue(int id) {
		int i;
		for (i = 0; readyQueue[i] != -1; i++) ;
		readyQueue[i] = id;
		processes[id].setWait(0);
	}

	/**
	 * if the ready queue is not empty, sort the ready queue, get the HRR process id and 
	 * pass it to CPU for execution, contextSwitchingTime elapsed for this operation;
	 * otherwise do nothing and return -1
	 * @return the ID of the process to run in CPU, -1 if the readyQueue is empty
	 */
	public int getNextProcess() {
		int ret = readyQueue[0];
		if (ret != -1) {
			double highest = processes[ret].getResponseRatio();
			for (int i = 0; readyQueue[i] != -1; i++) {
				double temp = processes[readyQueue[i]].getResponseRatio();
				if (temp > highest) {
					ret = readyQueue[i];
					highest = temp;
				}
			}
			exeQueue.append(ret);
			int i;
			for (i = 0; readyQueue[i] != ret; i++) ;
			for (; readyQueue[i] != -1; i++) readyQueue[i] = readyQueue[i+1];
			clock += contextSwitchingTime;
			for (i = 0; i < NUM_PROCESS; i++) 
				if (processes[i].getStatus() == Process.IO) 
					for (int j = 0; j < contextSwitchingTime; j++) 
						processes[i].runOneUnit();
		}
		return ret;
	}

	/**
	 * test function
	 * @param args
	 */
	public static void main(String[] args) {
		Scheduler hrrns = new HRRNScheduler();
		for (int i = 0; i < Scheduler.NUM_PROCESS; i++) {
			hrrns.processes[i] = new Process();
			hrrns.processes[i].setTcpu(i+2);
			hrrns.processes[i].setWait(i+1);
		}
		Arrays.fill(hrrns.readyQueue, -1);
		for (int i = 0; i < Scheduler.NUM_PROCESS; i += 2) {
			hrrns.addToQueue(i);
			hrrns.printReadyQueue();
		}
		for (int i = 1; i < Scheduler.NUM_PROCESS; i += 2) {
			hrrns.addToQueue(i);
			hrrns.printReadyQueue();
		}
	}

}
