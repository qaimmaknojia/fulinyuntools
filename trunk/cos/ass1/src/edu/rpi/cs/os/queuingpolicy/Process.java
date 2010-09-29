package edu.rpi.cs.os.queuingpolicy;

import java.util.TreeSet;

/**
 * the data structure for process information
 * @author Linyun Fu
 *
 */
public class Process {

	/**
	 * status constant indicating the process comes back from IO
	 */
	public static final int READY = 0;
	
	/**
	 * status constant indicating the process is doing IO
	 */
	public static final int IO = 1;
	
	/**
	 * status constant indicating the process is running in the CPU
	 */
	public static final int CPU = 2;
	
	/**
	 * the current status of the process
	 */
	private int status = READY;
	
	/**
	 * process ID
	 */
	public int id = 0;
	
	/**
	 * the CPU burst time, measured by the number of time units, 1 time unit = 0.5 ms
	 */
	public int tcpu = 0;
	
	/**
	 * the IO burst time, measured by the number of time units, 1 time unit = 0.5 ms
	 */
	public int tio = 0;
	
	/**
	 * the accumulated waiting time of the process, measured by the number of time units, 
	 * 1 time unit = 0.5 ms
	 */
	private int wait = 0;
	
	/**
	 * the remaining CPU time units to execute, 1 time unit = 0.5 ms
	 */
	private int cpuRemain = 0;
	
	/**
	 * the remaining IO time units to go, 1 time unit = 0.5 ms
	 */
	private int ioRemain = 0;
	
//	/**
//	 * the time points at which the process finishes, measured by time units, 1 time unit = 0.5 ms
//	 */
//	private TreeSet<Integer> finishTimes = new TreeSet<Integer>();
	
	/**
	 * the time points at which the process begins CPU execution, measured by time units, 
	 * 1 time unit = 0.5 ms
	 */
	private TreeSet<Integer> cpuStartTimes = new TreeSet<Integer>();
	
	/**
	 * the time points at which the process stops CPU execution, measured by time units, 
	 * 1 time unit = 0.5 ms
	 */
	private TreeSet<Integer> cpuStopTimes = new TreeSet<Integer>();
	
	/**
	 * the time points at which the process begins IO operation, measured by time units, 
	 * 1 time unit = 0.5 ms
	 */
	private TreeSet<Integer> ioStartTimes = new TreeSet<Integer>();
	
	/**
	 * the time points at which the process stops IO operation, measured by time units, 
	 * 1 time unit = 0.5 ms
	 */
	private TreeSet<Integer> ioStopTimes = new TreeSet<Integer>();
	
	/**
	 * 1 time unit (0.5 ms) elapsed
	 */
	public void runOneUnit() {
		switch (status) {
		case READY:
			wait++;
			break;
		case IO:
			ioRemain--;
			if (ioRemain == 0) {
				status = READY;
				ioStopTimes.add(Scheduler.clock);
				cpuRemain = tcpu;
				if (Scheduler.verbose) 
					System.out.println("Process #" + id + ": finished IO at " + 
							Util.getMs(Scheduler.clock));
				
//				finishTimes.add(Scheduler.clock);
			}
			break;
		case CPU:
			cpuRemain--;
			if (cpuRemain == 0) {
				status = IO;
				cpuStopTimes.add(Scheduler.clock);
				ioStartTimes.add(Scheduler.clock);
				if (Scheduler.verbose)
					System.out.println("Process #" + id + ": finished CPU at " + 
							Util.getMs(Scheduler.clock));
			
			}
			break;
		}
	}

	/**
	 * 
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * things to do when the process is picked by the scheduler to run in the CPU
	 */
	public void enterCPU() {
		status = CPU;
		cpuStartTimes.add(Scheduler.clock);
		if (Scheduler.verbose)
			System.out.println("Process #" + id + ": started CPU at " + 
					Util.getMs(Scheduler.clock));
		
	}

	/**
	 * for HRRN scheduling, calculate the response ratio of the process
	 */
	public double getResponseRatio() {
		return (wait+0.0)/(tcpu+0.0);
	}

	/**
	 * Round time R is defined only for interactive processes and it is a time
	 * from the end of the one service on the disk to the end of the next
	 * service on the disk. Hence, the round time includes waiting time in the
	 * ready queue, the execution on the CPU and the service time on the disk.
	 * 
	 * @return the avg. response time of the process during the simulation if
	 *         it's not infinity, otherwise return -1
	 */
	public double getResponseTime() {
		if (ioStopTimes.size() < 2) return -1;
		int first = ioStopTimes.first();
		int last = ioStopTimes.last();
		return (last-first+0.0)/(ioStopTimes.size()-1)/2;
	}

	/**
	 * 
	 * @return response time multiplied by finishing times
	 */
	public double getTotalResponseTime() {
		if (ioStopTimes.size() < 2) {
			return Integer.MAX_VALUE; // the process is starving
		}
		int first = ioStopTimes.first();
		int last = ioStopTimes.last();
		return (last-first+0.0)/2;
	}
	
	/**
	 * 
	 * @return finishing times
	 */
	public double getFinishTimes() {
		return ioStopTimes.size()-1.0;
	}
	
	/**
	 * Slowdown S is the inverse of the percentage of the time CPU executes the
	 * computational processes. It shows how much slower computational processes
	 * execute because of interference of interactive processes and the OS.
	 * 
	 * @return the avg. slowdown of the process during the simulation if it's
	 *         not infinity, otherwise return -1
	 */
	public double getSlowdown() {
		if (cpuStopTimes.size() == 0) return -1;
		return (Scheduler.simulateTime+0.0)/getCpuTime();
	}
	
	/**
	 * report the logs recorded along the simulation
	 */
	public void report() {
		System.out.println("\nProcess #" + id + " summary:");
		Integer[] cpustart = new Integer[cpuStartTimes.size()];
		Integer[] cpustop = new Integer[cpuStopTimes.size()];
		Integer[] iostart = new Integer[ioStartTimes.size()];
		Integer[] iostop = new Integer[ioStopTimes.size()];
		cpuStartTimes.toArray(cpustart);
		cpuStopTimes.toArray(cpustop);
		ioStartTimes.toArray(iostart);
		ioStopTimes.toArray(iostop);
		int i;
		for (i = 0; i < cpustart.length && i < cpustop.length && i < iostart.length 
		&& i < iostop.length; i++) {
			System.out.println("start cpu at " + Util.getMs(cpustart[i]) + ", stop cpu at " 
					+ Util.getMs(cpustop[i]) + ", start io at " + Util.getMs(iostart[i]) 
					+ ", stop io at " + Util.getMs(iostop[i]));
		}
		if (i < cpustart.length) System.out.print("start cpu at " + Util.getMs(cpustart[i]));
		if (i < cpustop.length) System.out.print(", stop cpu at " + Util.getMs(cpustop[i]));
		if (i < iostart.length) System.out.print(", start io at " + Util.getMs(cpustop[i]));
		System.out.println();
		System.out.println("Totally finished " + cpustop.length + " times\n");
	}

	public int getID() {
		return id;
	}

	public int getIORemain() {
		return ioRemain;
	}

	public int getCpuRemain() {
		return cpuRemain;
	}

	public void setCpuRemain(int cpu) {
		cpuRemain = cpu;
	}

	public void setTcpu(int i) {
		tcpu = i;
		cpuRemain = i;
	}

	public void setWait(int i) {
		wait = i;
	}

	public void setTd(int td) {
		tio = td;
		ioRemain = td;
	}

	/**
	 * 
	 * @return the total CPU time of this process during the simulation
	 */
	public int getCpuTime() {
		int total = 0;
		Integer[] cpustart = new Integer[cpuStartTimes.size()];
		Integer[] cpustop = new Integer[cpuStopTimes.size()];
		cpuStartTimes.toArray(cpustart);
		cpuStopTimes.toArray(cpustop);
		int i;
		for (i = 0; i < cpuStopTimes.size(); i++) total += cpustop[i]-cpustart[i];
		if (i < cpuStartTimes.size() && cpustart[i] < Scheduler.simulateTime) 
			total += Scheduler.simulateTime - cpustart[i];
		return total;
	}

	/**
	 * mark the time units the process is doing IO with "true"
	 * @param io
	 */
	public void markIoUsage(boolean[] io) {
		Integer[] iostart = new Integer[ioStartTimes.size()];
		Integer[] iostop = new Integer[ioStopTimes.size()];
		ioStartTimes.toArray(iostart);
		ioStopTimes.toArray(iostop);
		int i;
		for (i = 0; i < ioStopTimes.size(); i++) 
			for (int j = iostart[i]; j < io.length && j < iostop[i]; j++) io[j] = true;
		if (i < ioStartTimes.size()) 
			for (int j = iostart[i]; j < Scheduler.simulateTime; j++) io[j] = true;

	}

	public void setStatus(int s) {
		status = s;
	}

	public void setIoRemain(int r) {
		ioRemain = r;
	}

	public void yieldCPU() {
		setStatus(Process.READY);
		cpuStopTimes.add(Scheduler.clock);
		if (Scheduler.verbose) {
			System.out.println("Process #" + id
					+ ": yielded CPU at " + Util.getMs(Scheduler.clock));
		}
	}
}
