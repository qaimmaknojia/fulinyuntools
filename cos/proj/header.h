/********************************************************************/
/*********************************** File header.h ******************/
/********************************************************************/
/***** Define simulation *****/

/*
 * Terminal (thinking time): tt = 5sec, exponential distribution
 */
#define TT 5			// mean value for thinking time

/*
 * CPU : tCPU = 100ms, exponential distribution
 */
#define TCPU 20000000		// mean value for CPU burst time, in terms of number of instructions

/*
 * There is also a cache from which 95% of all
 * memory references are loaded within one instruction cycle.
 */
#define MISS 0.05		// cache miss rate - original value

/*
 * The processing system is a quad (four CPU’s) with a shared memory of 8 Gbytes
 */
#define NCPU 4			// number of CPUs - original value
#define MEM 8192.0		// total memory size, measured in Mbytes - original value

/*
 * The operating system occupies 512 Mbytes
 */
#define OS 512.0		// memory occupied by OS, measured in Mbytes

/*
 * There is a disk used for paging and IO operations with the service time tdiskser = 5ms, constant.
 */
#define NDISK 1			// number of disks - original value
//#define TDS 0.004		// value for disk service time different maple value
#define TDS 0.005		// question value

//#define MPL 20			// MPL 10 15 20

/*
 * Interactive processes created at n terminals,
 * repeatedly making rounds between the terminal and the memory system.
 */
//#define NI 90			// number of interactive processes 30 60 90

/*
 * A parallel computation consisting of four parallel processes executing constantly in
 * the memory system (i.e., its processing time is so large that it will not leave the
 * memory system during the simulation).
 */
#define NP 4			// number of parallel processes

/*
 * The time quantum for all processes, tq, is 150 ms.
 */
#define TQ 0.15			// time quantum

/*
 * IO Inter IO interval time tIIO = 15ms, exponential distribution. different
 */
//#define TIIO 0.01		// mean value of inter IO time for interactive processes maple value
#define TIIO 3000000		// question value, in terms of number of instructions

/*
 * IO Inter IO interval time tPIO = 15ms, exponential distribution. different
 */
//#define TPIO 0.03		// mean value of inter IO time for paralell processes maple value
#define TPIO 3000000		// question value, in terms of number of instructions

/*
 * Context switching time ts = 0.5ms.
 */
#define TS 0.0005		// mean value for context switching time

/*
 * Inter-synchronization (time between subsequent barrier synchronization points):
 * tbs = 0.2sec, exponential distribution. After reaching a synchronization point, a parallel
 * process is suspended until all other parallel processes reach this point. Unsuspended
 * parallel processes enter the CPU queue.
 */
#define TBS 40000000			/*
								 * mean value of the time between subsequent barrier synchronization points
								 * in terms of number of instructions
								 */

/*
 * For the final submission, simulating 10,000,000 ms of execution time
 */
#define TSIM 10000	// simulation time

#define MEMQ 0
#define CPUQ 1
//#define DISKQ 2	// NCPU+1 to NCPU+NDISK are all disk queues
#define REQM 0
#define REQCPU 1
#define RELCPU 2
#define REQDISK 3
#define RELDISK 4

#define EMPTY -1
#define LOW 0
#define HIGH 1

#define LOGFILE "simlog.txt"
#define DEBUG 0

#define p2(x) (1L<<((x)-1))
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct Task {
	double tcpu, tq, tp, tio, start;
	int oncpu, ondisk;
} * task; /**** Job list, 0 to NI-1 - interactive processes, NI to NI+NP-1 - paralell processes ****/

struct Events { /**** Event list ****/
	int head, tail, q;
	double * time;
	int * task, * event;
} elist;

struct Queue { /* Queues: 0 - memory queue, 1 - CPU queue, NCPU+1 to NCPU+NDISK - Disk queue */
	int head, tail, q, n, * task;
	double ws, tch, ts, * tentry;
} * queue;

struct Device { /* Devices: 0 - memory (never used) 1 to NCPU - CPUs, NCPU+1 to NCPU+NDISK - Disks */
	int busy, serving;
	double tch, tser, tseri;
} * server;

int inmem = 4, finished_tasks = 0; /* inmem, actual number of tasks in memory */
double cstime = 0.0; // context switching time
int insync = 0; // number of parallel processes waiting for sync
double sum_response_time = 0.0;

void ReqM(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl),
ReqCPU(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl), /**** Event procedures ****/
RelCPU(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl),
ReqD(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl),
RelD(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl);

double exprnd(double mean), /* generate random value from exponential distribution, given the mean value */
f(double m, double miss); /* return inter page fault time given the allocated memory size */
void qplace(int process, double time, int queue, double miss, int ncpu, double mem, int ndisk, int ni, int mpl);
void create_event(int process, int event, double time, int priority, double miss, int ncpu,
		double mem, int ndisk, int ni, int mpl);
void init(double miss, int ncpu, double mem, int ndisk, int ni, int mpl);
void stats(double miss, int ncpu, double mem, int ndisk, int ni, int mpl);
int qremove(int queue, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl);
int getAvailableCPU(double miss, int ncpu, double mem, int ndisk, int ni, int mpl);
void cleanup(int ncpu, int ndisk);
void experiment(double miss, int ncpu, double mem, int ndisk, int ni, int mpl);
void experiment1(double miss, int ncpu, double mem, int ndisk, int ni, int mpl, int full);
void report_response_time(double miss, int ncpu, double mem, int ndisk, int ni, int mpl);
