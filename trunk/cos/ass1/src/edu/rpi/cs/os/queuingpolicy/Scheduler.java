package edu.rpi.cs.os.queuingpolicy;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * the abstract class defining the key aspects of a scheduler
 * @author Linyun Fu
 *
 */
public abstract class Scheduler {

	/**
	 * the display name of the scheduler
	 */
	public static String name;
	
	/**
	 * the number of processes to simulate
	 */
	public static int NUM_PROCESS = 10;
	
	/**
	 * the context switching time, measured by the number of CPU time units, each time unit = 0.5 ms
	 */
	public int contextSwitchingTime = 0;
	
	/**
	 * Processes to simulate
	 */
	public Process[] processes = new Process[NUM_PROCESS];
	
	/**
	 * the ID of the current process running in the CPU
	 */
	protected int currentProcess = 0;
	
	/**
	 * the ready queue, only the IDs of processes are stored in the queue, 
	 * the end of the queue is denoted by -1
	 */
	public int[] readyQueue = new int[NUM_PROCESS+1];
	
	/**
	 * the execution queue, used to find cycles of process execution 
	 */
	public StringBuilder exeQueue = new StringBuilder();
	
	/**
	 * system clock of the computer, records the number of time units elapsed 
	 * since the beginning of the simulation, each time unit = 0.5 ms
	 */
	public static int clock = 0;
	
	/**
	 * for how much time units to simulate
	 */
	public static int simulateTime = 0;
	
	/**
	 * if true, show log, else silent
	 */
	public static boolean verbose = false;
	
	/**
	 * add a process just get ready to a certain position of the ready queue, 
	 * a Scheduler must implement this method to exhibit its queuing policy
	 * @param id the ID of the process just get ready
	 */
	abstract public void addToQueue(int id);
	
	/**
	 * let time elapses for 0.5 ms
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
		
//		// for debugging
//		if (currentProcess == -1) {
//			System.out.println(clock);
//			System.exit(1);
//		}
		
		if (currentProcess == -1 || processes[currentProcess].getStatus() == Process.IO) {
			currentProcess = getNextProcess();
			if (currentProcess != -1) processes[currentProcess].enterCPU();
		}
	}
	
	/**
	 * if the ready queue is not empty, get the first process in the ready queue and 
	 * pass it to CPU for execution, contextSwitchingTime elapsed for this operation;
	 * otherwise do nothing and return -1
	 * @return the ID of the process to run in CPU, -1 if the readyQueue is empty
	 */
	public int getNextProcess() {
		int ret = readyQueue[0];
		if (ret != -1) {
			exeQueue.append(ret);
			for (int i = 0; readyQueue[i] != -1; i++) readyQueue[i] = readyQueue[i+1];
			clock += contextSwitchingTime;
			for (int i = 0; i < NUM_PROCESS; i++) 
				if (processes[i].getStatus() == Process.IO) 
					for (int j = 0; j < contextSwitchingTime; j++) 
						processes[i].runOneUnit();
				
		}
		return ret;
	}

	/**
	 * Utilization U is the percentage of the time the CPU executes the
	 * processes, so it excludes the time of context switches and the time CPU
	 * is idle.
	 * 
	 * @return the CPU utilization during the simulation
	 */
	public double getCpuUtilization() {
		int cputime = 0;
		for (Process p : processes) cputime += p.getCpuTime();
		return (cputime+0.0)/simulateTime;
	}
	
	/**
	 * 
	 * @return the disk utilization during the simulation
	 */
	public double getDiskUtilization() {
		boolean[] io = new boolean[simulateTime];
		Arrays.fill(io, false);
		for (Process p : processes) p.markIoUsage(io);
		int count = 0;
		for (int i = 0; i < io.length; i++) if (io[i]) count++;
		return (count+0.0)/simulateTime;
	}
	
	/**
	 * print the current ready queue
	 */
	public void printReadyQueue() {
		for (Integer i : readyQueue) System.out.print(" " + i);
		System.out.println();
	}
	
	/**
	 * simulation
	 * @param s the scheduler used
	 * @param tb9 cpu burst time for Process 9, measured in time units, 1 time unit = 0.5 ms
	 * @param tb0 cpu burst time for Process 0,2,4,6,8
	 * @param tb1 cpu burst time for Process 1,3,5,7
	 * @param td disk burst time for each process
	 * @param ts context switching time
	 * @param tq time quantum if RR scheduler is used
	 * @param simTime number of time units to simulate
	 * @param verbose if true, show log, else silent
	 */
	public static void simulate(Scheduler s, int tb9, int tb0, int tb1, int td, 
			int ts, int tq, int simTime, boolean verbose) {
		init(s, tb9, tb0, tb1, td, ts, tq, simTime, verbose);
		s.currentProcess = s.getNextProcess();
		s.processes[s.currentProcess].enterCPU();
		while (clock < simTime) s.runOneUnit();
		s.report();
	}
	
	/**
	 * report a) a response time of interactive tasks (tasks with the id number other
	 * than 9), b) a slow down of the processor for the computational task (the
	 * task numbered 9), which of course becomes infinity if this task is
	 * starving, c) the CPU utilization, d) the disk utilization
	 */
	private void report() {
		if (verbose) for (int i = 0; i < NUM_PROCESS; i++) processes[i].report();
		System.out.println("\nSimulation summary:");
		double totalResponseTime = 0;
		double totalFinishingTimes = 0;
		for (int i = 0; i < 9; i++) {
			double responsetime = processes[i].getResponseTime();
			System.out.println("Response time of Process #" + i + ": " + 
					(responsetime < 0 ? "infinity" : responsetime + " ms"));
			totalResponseTime += processes[i].getTotalResponseTime();
			totalFinishingTimes += processes[i].getFinishTimes();
		}
		System.out.println("Average response time: " + 
				(totalResponseTime/totalFinishingTimes-processes[0].tio/2));
		double slowdown = processes[9].getSlowdown();
		System.out.println("Slowdown of Process #9: " + 
				(slowdown < 0 ? "infinity" : slowdown));
		
		System.out.println("CPU utilization: " + getCpuUtilization());
		
		System.out.println("Disk utilization: " + getDiskUtilization());
		
		System.out.println("Cycle length: " + getCycle(exeQueue.toString()));
		
//		System.out.println("Cycle: " + exeQueue.toString());
		
		System.out.println("\n");
	}

	/**
	 * find cycle from a list of integers. A cycle must start from the beginning of the list
	 * @param queue
	 * @return the length of the cycle, -1 if no cycles are found
	 */
	private int getCycle(String queue) {
		for (int i = 2; i < queue.length(); i += 2) {
			String p1 = queue.substring(0, i/2);
			String p2 = queue.substring(i/2, i);
			if (p1.equalsIgnoreCase(p2)) return i/2;
		}
		return -1;
	}

	/**
	 * set parameters for the simulation
	 * @param s
	 * @param tb9
	 * @param tb0
	 * @param tb1
	 * @param td
	 * @param ts
	 * @param tq
	 * @param simTime
	 * @param verbose
	 */
	private static void init(Scheduler s, int tb9, int tb0, int tb1, int td,
			int ts, int tq, int simTime, boolean verbose) {
		for (int i = 0; i < Scheduler.NUM_PROCESS; i++) {
			s.processes[i] = new Process();
			s.processes[i].id = i;
		}
		s.processes[9].setTcpu(tb9);
		s.processes[0].setTcpu(tb0);
		s.processes[2].setTcpu(tb0);
		s.processes[4].setTcpu(tb0);
		s.processes[6].setTcpu(tb0);
		s.processes[8].setTcpu(tb0);
		s.processes[1].setTcpu(tb1);
		s.processes[3].setTcpu(tb1);
		s.processes[5].setTcpu(tb1);
		s.processes[7].setTcpu(tb1);
		Arrays.fill(s.readyQueue, -1);
		clock = 0;
		for (int i = 0; i < Scheduler.NUM_PROCESS; i++) {
			s.processes[i].setTd(td);
			s.addToQueue(i);
			s.processes[i].setStatus(Process.READY);
			if (verbose) System.out.println("Process #" + i + ": added to ready queue at " + 
					Util.getMs(clock));
		}
		s.setTs(ts);
		if (s instanceof RRScheduler) {
			((RRScheduler) s).tq = tq;
			((RRScheduler) s).tqRemain = tq;
		}
		s.setSimTime(simTime);
		Scheduler.verbose = verbose;
	}

	private void setSimTime(int simTime) {
		simulateTime = simTime;
	}

	private void setTs(int ts) {
		contextSwitchingTime = ts;
	}

	/**
	 * driver of the simulator
	 * @param args
	 */
	public static void interactiveMain(String[] args) {
		if (args.length < 3) {
			printHelpInfo();
			System.exit(0);
		}
		
		for (String s : args) if (s.toLowerCase().startsWith("--help")) {
			printHelpInfo();
			System.exit(0);
		}

		int tq = 0;
		Scheduler sch = new SJFScheduler();
		for (String s : args) {
			if (s.toLowerCase().startsWith("-hrrn")) {
				sch = new HRRNScheduler();
				break;
			} else if (s.toLowerCase().startsWith("-rr")) {
				sch = new RRScheduler();
				try {
					tq = Integer.parseInt(s.substring(3));
				} catch (NumberFormatException e) {
					System.out.println("Please enter the time quantum for Round Robin Scheduler " +
					"immediately after \"-rr\", e.g.: -rr6  " +
					"the time quantum is measured in time units, 1 time unit = 0.5 ms");
					System.exit(1);
				}
				break;
			}
		}

		int td = 0;
		try {
			td = Integer.parseInt(args[args.length-3]);
		} catch (NumberFormatException e) {
			System.out.println("Please enter the disk time for each process " +
					"as the 3rd last parameter, the disk time is measured in time units " +
					"and 1 time unit = 0.5 ms");
			System.exit(1);
		}
		
		int ts = 0;
		try {
			ts = Integer.parseInt(args[args.length-2]);
		} catch (NumberFormatException e) {
			System.out.println("Please enter the context switching time for each process " +
					"as the 2rd last parameter, the context switching time is measured in time units " +
					"and 1 time unit = 0.5 ms");
			System.exit(1);
		}
		
		int simTime = 0;
		try {
			simTime = Integer.parseInt(args[args.length-1]);
		} catch (NumberFormatException e) {
			System.out.println("Please enter the number of time units for simulation " +
					"as the last parameter, 1 time unit = 0.5 ms");
			System.exit(1);
		}
		
		for (String s : args) if (s.toLowerCase().startsWith("-v")) {
			verbose = true;
			break;
		}

		System.out.println("Scheduler: " + Scheduler.name);
		System.out.println("Tdisk: " + Util.getMs(td));
		System.out.println("Tswitch: " + Util.getMs(ts));
		if (sch instanceof RRScheduler) System.out.println("Tq: " + Util.getMs(tq));
		System.out.println("Simulate for " + Util.getMs(simTime));
		System.out.println("Start simulating...");
//		simulate(Scheduler s, int tb9, int tb0, int tb1, int td, 
//				int ts, int tq, int simTime, boolean verbose)
		simulate(sch, 55*2, 3, 6, td, ts, tq, simTime, verbose);
	}
	
	private static void printHelpInfo() {
		System.out.println(
			  "*****************************************\n"
			+ "*                                       *\n"
			+ "*       Scheduler Simulator v0.01       *\n"
			+ "*                                       *\n"
			+ "*           Author: Linyun Fu           *\n"
			+ "*                                       *\n"
			+ "*****************************************\n"
			+ "\n"
			+ "Usage: java edu.rpi.cs.os.queuingpolicy.Scheduler [options] "
			+ "[Td] [Ts] [Tsim]\n"
			+ "\n"
			+ "options:\n"
			+ "-v: verbose mode, print every detail of the simulation process\n"
			+ "-sjf: use preemptive SJF scheduler, which is the default scheduler used\n"
			+ "-hrrn: use HRRN scheduler\n"
			+ "-rr[Tq]: use RR scheduler with Tq as the time quantum, "
			+ "Tq is measured in time units, 1 time unit = 0.5 ms\n"
			+ "\n"
			+ "--help: show this help document\n"
			+ "\n"
			+ "Td: disk time for each process, measured in time units, 1 time unit = 0.5 ms\n"
			+ "Ts: context switching time, measured in time units, 1 time unit = 0.5 ms\n"
			+ "Tsim: simulation time, measured in time units, 1 time unit = 0.5 ms\n"
			+ "\n"
			+ "Samples:\n"
			+ "run preemptive SJF scheduler, Td=15ms Ts=1ms simulate 48500ms:\n"
			+ "java edu.rpi.cs.os.queuingpolicy.Scheduler -sjf 30 2 97000\n\n"
			+ "run HRRN scheduler, Td=25ms Ts=0ms simulate 50000ms:\n"
			+ "java edu.rpi.cs.os.queuingpolicy.Scheduler -hrrn 50 0 100000\n\n"
			+ "run Round Robin scheduler, Tq=1.5ms Td=6ms Ts=1ms simulate 50000ms:\n"
			+ "java edu.rpi.cs.os.queuingpolicy.Scheduler -rr3 12 2 100000\n\n"
		);

	}

	/**
	 * get the round robin results for plotting
	 */
	public static void rrMain() {
		System.out.println("td=6ms tq=1ms");
		simulate(new RRScheduler(), 110, 3, 6, 12, 2, 2, 10000000, false);
		System.out.println("td=15ms tq=1ms");
		simulate(new RRScheduler(), 110, 3, 6, 30, 2, 2, 10000000, false);
		System.out.println("td=25ms tq=1ms");
		simulate(new RRScheduler(), 110, 3, 6, 50, 2, 2, 10000000, false);
		System.out.println("td=6ms tq=1.5ms");
		simulate(new RRScheduler(), 110, 3, 6, 12, 2, 3, 10000000, false);
		System.out.println("td=15ms tq=1.5ms");
		simulate(new RRScheduler(), 110, 3, 6, 30, 2, 3, 10000000, false);
		System.out.println("td=25ms tq=1.5ms");
		simulate(new RRScheduler(), 110, 3, 6, 50, 2, 3, 10000000, false);
		System.out.println("td=6ms tq=3ms");
		simulate(new RRScheduler(), 110, 3, 6, 12, 2, 6, 10000000, false);
		System.out.println("td=15ms tq=3ms");
		simulate(new RRScheduler(), 110, 3, 6, 30, 2, 6, 10000000, false);
		System.out.println("td=25ms tq=3ms");
		simulate(new RRScheduler(), 110, 3, 6, 50, 2, 6, 10000000, false);
		System.out.println("td=6ms tq=11ms");
		simulate(new RRScheduler(), 110, 3, 6, 12, 2, 22, 10000000, false);
		System.out.println("td=15ms tq=11ms");
		simulate(new RRScheduler(), 110, 3, 6, 30, 2, 22, 10000000, false);
		System.out.println("td=25ms tq=11ms");
		simulate(new RRScheduler(), 110, 3, 6, 50, 2, 22, 10000000, false);

	}
	
	/**
	 * check the results of the HRRN scheduler
	 */
	public static void hrrnMain() {
		System.out.println("td=6ms ts=0ms");
		simulate(new HRRNScheduler(), 110, 3, 6, 12, 0, 2, 10000000, false);
		System.out.println("td=6ms ts=1ms");
		simulate(new HRRNScheduler(), 110, 3, 6, 12, 2, 2, 10000000, false);
		System.out.println("td=15ms ts=0ms");
		simulate(new HRRNScheduler(), 110, 3, 6, 30, 0, 2, 10000000, false);
		System.out.println("td=15ms ts=1ms");
		simulate(new HRRNScheduler(), 110, 3, 6, 30, 2, 3, 10000000, false);
		System.out.println("td=25ms ts=0ms");
		simulate(new HRRNScheduler(), 110, 3, 6, 50, 0, 3, 10000000, false);
		System.out.println("td=25ms ts=1ms");
		simulate(new HRRNScheduler(), 110, 3, 6, 50, 2, 3, 10000000, false);
		
	}
	
	/**
	 * check the results of the preemptive SJF scheduler
	 */
	public static void sjfMain() {
		System.out.println("td=6ms ts=0ms");
		simulate(new SJFScheduler(), 110, 3, 6, 12, 0, 2, 1500, false);
		System.out.println("td=6ms ts=1ms");
		simulate(new SJFScheduler(), 110, 3, 6, 12, 2, 2, 2500, false);
		System.out.println("td=15ms ts=0ms");
		simulate(new SJFScheduler(), 110, 3, 6, 30, 0, 2, 66*20, false);
		System.out.println("td=15ms ts=1ms");
		simulate(new SJFScheduler(), 110, 3, 6, 30, 2, 3, 70*20, false);
		System.out.println("td=25ms ts=0ms");
		simulate(new SJFScheduler(), 110, 3, 6, 50, 0, 3, 212*20, false);
		System.out.println("td=25ms ts=1ms");
		simulate(new SJFScheduler(), 110, 3, 6, 50, 2, 3, 59*20, false);
		
	}
	
	public static void main(String[] args) {
//		rrMain();
//		hrrnMain();
//		sjfMain();
		interactiveMain(args);
	}

}
