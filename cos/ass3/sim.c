/********************************************************************/
/********************** File sim.c **********************************/
/* can be separated from header.c by using include below  ***********/
#include "header.h"
int cc = 0;

int main() {
	long start = time(0);
//	printf("cache miss & #CPU & MEM & #disk & $n$ & MPL & resp. & \\$ \\\\\n\\hline\n");
//	experiment(0.05, 4, 8192.0, 1, 90, 20);
//	experiment(0.05, 4+1, 8192.0, 1, 90, 20);
//	experiment(0.05/2, 4, 8192.0, 1, 90, 20);
//	experiment(0.05, 4, 8192.0+1024, 1, 90, 20);
//	experiment(0.05, 4, 8192.0, 1+1, 90, 20);
//	experiment(0.05, 4, 8192.0, 1, 90, 10);//resp = 5.40
//	experiment(0.05, 4, 8192.0, 1, 90, 15);//resp = 9.00
//	experiment(0.05, 4, 8192.0, 1, 90, 20);//resp = 12.28
//	int i, j, k, l;
//	for (i = 0; i < 2; i++) for (j = 0; j < 2; j++) for (k = 0; k < 2; k++) for (l = 0; l < 2; l++) {
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 10);
//	}
//	experiment(MISS, NCPU, MEM, NDISK+4, 90, 10);//resp = 0.33
//	experiment(MISS, NCPU, MEM, NDISK+3, 90, 10);//resp = 0.36
//	experiment(MISS, NCPU, MEM, NDISK+2, 90, 10);//resp = 0.44
//	experiment(MISS, NCPU, MEM, NDISK+1, 90, 10);//resp = 0.97 !!!
//	experiment1(MISS, NCPU, MEM, NDISK, 90, 10, 1);//resp = 5.40
	int i, j, k, l;
//	for (l = 0; l < 1; l++) for (k = 0; k < 2; k++) for (j = 0; j < 3; j++) for (i = 0; i < 3; i++) {
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 20);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 15);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 10);
//	}

//	for (l = 0; l < 1; l++) for (k = 2; k < 3; k++) for (j = 0; j < 3; j++) for (i = 0; i < 3; i++) {
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 20);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 15);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 10);
//	}

//	for (l = 1; l < 2; l++) for (k = 0; k < 3; k++) for (j = 0; j < 3; j++) for (i = 0; i < 3; i++) {
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 20);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 15);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 10);
//	}

//	for (l = 2; l < 3; l++) for (k = 0; k < 3; k++) for (j = 0; j < 3; j++) for (i = 0; i < 3; i++) {
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 20);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 15);
//		experiment(MISS/(1<<i), NCPU+j, MEM+k*1024, NDISK+l, 90, 10);
//	}

//	experiment(MISS, NCPU-1, MEM, NDISK+1, 90, 10); resp. = 1.21

	printf("%ld seconds\n", time(0)-start);
	return 0;
}

void experiment(double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	experiment1(miss, ncpu, mem, ndisk, ni, mpl, 0);
}

void experiment1(double miss, int ncpu, double mem, int ndisk, int ni, int mpl, int full) {
//	printf("%f\n", TCPU*(miss*80.0+1)*1E-9);
	double global_time = 0.0;
	int process, event;

	init(miss, ncpu, mem, ndisk, ni, mpl);
	/***** Main simulation loop *****/
//	int level = 1;
	while (global_time <= TSIM) {

//		double percent = global_time/TSIM*100;
//		if (level == 9 && percent > 90) {
//			printf("\t90%%\n");
//			level++;
//		} else if (level == 8 && percent > 80) {
//			printf("\t80%%");
//			level++;
//		}
//		else if (level == 7 && percent > 70) {
//			printf("\t70%%");
//			level++;
//		}
//		else if (level == 6 && percent > 60) {
//			printf("\t60%%");
//			level++;
//		}
//		else if (level == 5 && percent > 50) {
//			printf("\t50%%");
//			level++;
//		}
//		else if (level == 4 && percent > 40) {
//			printf("\t40%%");
//			level++;
//		}
//		else if (level == 3 && percent > 30) {
//			printf("\t30%%");
//			level++;
//		}
//		else if (level == 2 && percent > 20) {
//			printf("\t20%%");
//			level++;
//		}
//		else if (level == 1 && percent > 10) {
//			printf("10%%");
//			level++;
//		}

		/***** Select the event e from the head of event list *****/
		process = elist.task[elist.head];
		global_time = elist.time[elist.head];
		event = elist.event[elist.head];
		elist.head = (elist.head + 1) % (NP+ni);
		elist.q--;
		/***** Execute the event e ******/
		switch (event) {
		case REQM:
			ReqM(process, global_time, miss, ncpu, mem, ndisk, ni, mpl);
			break;
		case REQCPU:
			ReqCPU(process, global_time, miss, ncpu, mem, ndisk, ni, mpl);
			break;
		case RELCPU:
			RelCPU(process, global_time, miss, ncpu, mem, ndisk, ni, mpl);
			break;
		case REQDISK:
			ReqD(process, global_time, miss, ncpu, mem, ndisk, ni, mpl);
			break;
		case RELDISK:
			RelD(process, global_time, miss, ncpu, mem, ndisk, ni, mpl);
		}
	}
	if (full) stats(miss, ncpu, mem, ndisk, ni, mpl);
	report_response_time(miss, ncpu, mem, ndisk, ni, mpl);
	cleanup(ncpu, ndisk);
}

/********************************************************************/
/********************* Event Functions ******************************/
/********************************************************************/

void ReqM(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	/**** Create a ReqCPU event or place a task in memory queue      ****/
	if (inmem < mpl) {
		inmem++;
		/**** Create a new task                                          ****/
		task[process].tcpu = exprnd(TCPU*(miss*80.0+1)*1E-9);
//		printf("%f\n", task[process].tcpu);
		task[process].tq = TQ;
		double m = (mem - OS) / mpl;
		double p = f(m, miss);
//		printf("interactive inter page time = %f\n", p); // 0.002999
		task[process].tp = exprnd(p);
		task[process].tio = exprnd(TIIO*(miss*80.0+1)*1E-9);
		create_event(process, REQCPU, time, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	} else
		qplace(process, time, MEMQ, miss, ncpu, mem, ndisk, ni, mpl);
}

void ReqCPU(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {

	/**** Place in CPU queue if all the CPUs are busy                       ****/
	int cpu = getAvailableCPU(miss, ncpu, mem, ndisk, ni, mpl);
	if (!cpu)
		qplace(process, time, CPUQ, miss, ncpu, mem, ndisk, ni, mpl);
	else {
		double t = exprnd(TS); // context switching time
		time += t;
		cstime += t;
		if (process >= ni) {
			task[process].tcpu = exprnd(TBS*(miss*80.0+1)*1E-9);
			task[process].tq = TQ;
			double m = (mem - OS) / mpl;
			double p = f(m, miss);
//			printf("paralell inter page time = %f\n", p); // 0.002999
			task[process].tp = exprnd(p);
			task[process].tio = exprnd(TPIO*(miss*80.0+1)*1E-9);
		}
		server[cpu].busy = 1;
		server[cpu].tch = time;
		server[cpu].serving = process;
		/**** Find the time of leaving CPU                               ****/
		double release_time = task[process].tq;
		if (process < ni && release_time > task[process].tcpu)
			release_time = task[process].tcpu;
		if (release_time > task[process].tp)
			release_time = task[process].tp;
		if (release_time > task[process].tio)
			release_time = task[process].tio;
//		if (process >= NI && release_time > task[process].tbs)
//			release_time = task[process].tbs;
		/**** Update the process times and create RelCPU event           ****/
		task[process].oncpu = cpu;
		task[process].tq -= release_time;
		task[process].tcpu -= release_time;
		task[process].tp -= release_time;
		task[process].tio -= release_time;
//		if (process >= NI) task[process].tbs -= release_time;
		create_event(process, RELCPU, time + release_time, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	}
}

int getAvailableCPU(double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	int i;
	for (i = 1; i < ncpu+1; i++) if (!server[i].busy) return i;
	return 0;
}

void RelCPU(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	int queue_head;

	/**** Update CPU statistics                                            ****/
	int cpu = task[process].oncpu;
	task[process].oncpu = 0;
	server[cpu].busy = 0;
	server[cpu].tser += (time - server[cpu].tch);
	if (process < ni) server[cpu].tseri += (time - server[cpu].tch);
	queue_head = qremove(CPUQ, time, miss, ncpu, mem, ndisk, ni, mpl); /* remove head of CPU queue */
	if (queue_head != EMPTY)
		create_event(queue_head, REQCPU, time, HIGH, miss, ncpu, mem, ndisk, ni, mpl);
	/**** Depending on reason for leaving CPU, select the next event */
	if (process < ni && task[process].tcpu == 0) { /* task termination */
		sum_response_time += time - task[process].start;
		finished_tasks++;
		task[process].start = time + exprnd(TT);
		create_event(process, REQM, task[process].start, LOW, miss, ncpu, mem, ndisk, ni, mpl);
		inmem--;
		queue_head = qremove(MEMQ, time, miss, ncpu, mem, ndisk, ni, mpl);
		if (queue_head != EMPTY)
			create_event(queue_head, REQM, time, HIGH, miss, ncpu, mem, ndisk, ni, mpl);
	} else if (task[process].tq == 0) { /* time slice interrupt */
		task[process].tq = TQ;
		create_event(process, REQCPU, time, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	} else if (task[process].tp == 0){ /* page interrupt */
		double m = (mem - OS) / mpl;
		double p = f(m, miss);
		task[process].tp = exprnd(p);
		create_event(process, REQDISK, time, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	} else if (task[process].tio == 0){ // IO interrupt
		task[process].tio = exprnd(TIIO*(miss*80.0+1)*1E-9);
		create_event(process, REQDISK, time, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	} else if (process >= ni && task[process].tcpu == 0) { // sync

		if (DEBUG) {
			FILE* fp = fopen(LOGFILE, "a");
			fprintf(fp, "%f\tProcess#%d enters sync\n", time, process);
			fclose(fp);
		}

		task[process].tcpu = exprnd(TBS*(miss*80.0+1)*1E-9);
		insync++;
		if (insync == NP) {
			int i;
			for (i = ni; i < ni+NP; i++) create_event(i, REQCPU, time, LOW, miss, ncpu, mem, ndisk, ni, mpl);
			insync = 0;
		}
	}
}

void ReqD(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	/**** If Disk busy go to Disk queue, if not create RelD event    ****/
	int disk = task[process].ondisk;
	if (server[disk].busy) qplace(process, time, disk, miss, ncpu, mem, ndisk, ni, mpl);
	else {
		server[disk].busy = 1;
		server[disk].tch = time;
		create_event(process, RELDISK, time + TDS, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	}
}

void RelD(int process, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	int disk_queue_head;
	/**** Update statistics for Disk and create ReqCPU event         ****/
	int disk = task[process].ondisk;
	server[disk].busy = 0;
	server[disk].tser += (time - server[disk].tch);
	disk_queue_head = qremove(disk, time, miss, ncpu, mem, ndisk, ni, mpl);
	if (disk_queue_head != EMPTY)
		create_event(disk_queue_head, REQDISK, time, HIGH, miss, ncpu, mem, ndisk, ni, mpl);
	create_event(process, REQCPU, time, LOW, miss, ncpu, mem, ndisk, ni, mpl);
}

/********************************************************************/
/******************* Auxiliary Functions ****************************/
/********************************************************************/

int qremove(int current_queue, double time, double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	char q[15];
	switch (current_queue) {
	case MEMQ:
		strcpy(q, "eligible queue");
		break;
	case CPUQ:
		strcpy(q, "CPU queue");
		break;
	default:
		sprintf(q, "disk queue#%d", current_queue-NCPU);
		break;
	}

	int process;

	/**** If queue not empty, remove the head of the queue              ****/
	if (queue[current_queue].q > 0) {
		process = queue[current_queue].task[queue[current_queue].head];
		/**** Update statistics for the queue                               ****/
		queue[current_queue].ws += time
				- queue[current_queue].tentry[queue[current_queue].head];
		queue[current_queue].ts += (time - queue[current_queue].tch)
				* queue[current_queue].q;
		queue[current_queue].q--;
		queue[current_queue].tch = time;
		/**** Create a new event for the task at the head and move the head ****/
		queue[current_queue].head = (queue[current_queue].head + 1) % (NP+ni);

		if (DEBUG) {
			FILE* fp = fopen(LOGFILE, "a");
			fprintf(fp, "%f\tProcess#%d is removed from %s\n", time, process, q);
			fclose(fp);
		}

		return (process);
	} else {

		if (DEBUG) {
			FILE* fp = fopen(LOGFILE, "a");
			fprintf(fp, "%f\t%s is empty\n", time, q);
			fclose(fp);
		}

		return (EMPTY);
	}
}

void qplace(int process, double time, int current_queue, double miss, int ncpu,
		double mem, int ndisk, int ni, int mpl) {

	if (DEBUG) {
		char q[15];
		switch (current_queue) {
		case MEMQ:
			strcpy(q, "eligible queue");
			break;
		case CPUQ:
			strcpy(q, "CPU queue");
			break;
		default:
			sprintf(q, "disk queue#%d", current_queue-NCPU);
			break;
		}
		FILE* fp = fopen(LOGFILE, "a");
		fprintf(fp, "%f\tProcess#%d is placed to %s\n", time, process, q);
		fclose(fp);
	}

	/**** Update statistics for the queue                               ****/
	queue[current_queue].ts += (time - queue[current_queue].tch)
			* queue[current_queue].q;
	queue[current_queue].q++;
	queue[current_queue].n++;
	queue[current_queue].tch = time;
	/**** Place the process at the tail of queue and move the tail       ****/
	queue[current_queue].task[queue[current_queue].tail] = process;
	queue[current_queue].tentry[queue[current_queue].tail] = time;
	queue[current_queue].tail = (queue[current_queue].tail + 1) % (NP+ni);
}

void create_event(int process, int event, double time, int priority, double miss,
		int ncpu, double mem, int ndisk, int ni, int mpl) {

	if (DEBUG) {
		char * e;
		switch (event) {
		case REQM:
			e = "require memory";
			break;
		case REQCPU:
			e = "require CPU";
			break;
		case RELCPU:
			e = "release CPU";
			break;
		case REQDISK:
			e = "require disk";
			break;
		case RELDISK:
			e = "release disk";
			break;
		}
		char * p;
		switch (priority) {
		case HIGH:
			p = "high";
			break;
		case LOW:
			p = "low";
			break;
		}
		FILE* fp = fopen(LOGFILE, "a");
		fprintf(fp, "%f\tProcess#%d %s with priority: %s\n", time, process, e, p);
		fclose(fp);
	}

	int i, notdone = 1, place = elist.tail;

	/**** Move all more futuristic tasks by one position                ****/
	for (i = (elist.tail + NP+ni - 1) % (NP+ni); notdone & (elist.q > 0); i = (i + NP+ni - 1)
			% (NP+ni)) {
		if ((elist.time[i] < time) | ((priority == LOW) & (elist.time[i]
				== time)))
			notdone = 0;
		else {
			elist.time[place] = elist.time[i];
			elist.task[place] = elist.task[i];
			elist.event[place] = elist.event[i];
			place = i;
		}
		if (i == elist.head)
			notdone = 0;
	}
	/**** Place the argument event in the newly created space           ****/
	elist.time[place] = time;
	elist.task[place] = process;
	elist.event[place] = event;
	elist.tail = (elist.tail + 1) % (NP+ni);
	elist.q++;
}

double exprnd(double y) {
	static unsigned long w = 0L, g, m, x;

	/**** Better random function ?? (found on the internet :)           ****/
	if (w == 0L) {
		w = sizeof(long) * 8;
		g = p2(w-1);
		m = p2(w/2) + 3;
		x = p2(w/4) + 1;
	}
	x = (x * m) % g;
	//   printf("%f, %f\n", y, -y*log((double)x/g)); // it is really random!!
	return -y * log((float) x / g); // log is ln actually
}

//double exprnd (double y) {
//	cc++;
//	if (cc < 100) printf("%f\n", -y*log((double)rand()/RAND_MAX));
//	return -y*log((double)rand()/RAND_MAX);
//}

/*
 * PAGE Inter paging interval: exponential with the following fault probability function:
 * f(m) = 2^−p where p = m/175 + 17
 * and m denotes memory allocated to a process measured in MBytes and f(m) denotes
 * probability that the given instruction will cause a page fault with memory allocation m.
 *
 * (paralell processes) PAGE Inter paging interval defined as for interactive processes
 *
 * There is also a cache from which 95% of all
 * memory references are loaded within one instruction cycle. Cache miss cost is 81 machine
 * cycles (that is the time in which the needed data will be loaded from main memory to
 * cache and CPU).
 *
 * An instruction cycle is 10^−9sec.
 */
double f(double m, double miss) {
	double p = m / 175 + 17;
	return pow(2, p) * (80.0*miss+1) * 1E-9; // 81*0.05+1*0.95=5
}

void init(double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {

	if (DEBUG) {
		remove(LOGFILE);
	}

	int i;

	/**** Initialize structures                                         ****/
	elist.head = elist.tail = elist.q = 0;
	elist.time = (double *)malloc(sizeof(double)*(ni+NP));
	elist.task = (int *)malloc(sizeof(int)*(ni+NP));
	elist.event = (int *)malloc(sizeof(int)*(ni+NP));

	queue = (struct Queue*)malloc(sizeof(struct Queue)*(1+ncpu+ndisk));
	for (i = 0; i < 1 + ncpu + ndisk; i++) {
		queue[i].head = queue[i].tail = queue[i].q = queue[i].n = 0;
		queue[i].ws = queue[i].ts = 0.0;
		queue[i].tch = 0;
		queue[i].task = (int *)malloc(sizeof(int)*(ni+NP));
		queue[i].tentry = (double *)malloc(sizeof(double)*(ni+NP));
	}

	server = (struct Device*)malloc(sizeof(struct Device)*(1+ncpu+ndisk));
	for (i = 0; i < 1 + ncpu + ndisk; i++) {
		server[i].busy = 0;
		server[i].tch = server[i].tser = server[i].tseri = 0.0;
	}

	task = (struct Task*)malloc(sizeof(struct Task)*(ni+NP));
	for (i = 0; i < ni; i++) {
		task[i].start = exprnd(TT);
		task[i].ondisk = i%ndisk+1+ncpu;
		create_event(i, REQM, task[i].start, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	}
	for (i = 0; i < NP; i++) {
		task[ni+i].ondisk = (ni+i)%ndisk+1+ncpu;
		create_event(ni+i, REQCPU, 0, LOW, miss, ncpu, mem, ndisk, ni, mpl);
	}

}

/*
 * These results should include at the minimum a table with:
 * (i) the response time,
 * (ii) the CPU utilization by users' processes,
 * (iii) the disk utilization, and
 * (iv) the average waiting time in eligible queue
 */
void stats(double miss, int ncpu, double mem, int ndisk, int ni, int mpl) {
	/**** Update utilizations                                          ****/
	int i;
	for (i = 1; i < ncpu+1; i++) if (server[i].busy == 1) {
		server[i].tser += (TSIM - server[i].tch);
		if (server[i].serving < ni) server[i].tseri += (TSIM - server[i].tch);
	}

	for (i = ncpu+1; i < ncpu+ndisk+1; i++) if (server[i].busy == 1)
		server[i].tser += (TSIM - server[i].tch);

	/**** Print statistics                                             ****/

	printf("average response time %5.2f processes finished %5d\n",
			sum_response_time / finished_tasks, finished_tasks);
	printf("CPU utilization by users' processes:\n");
	double totu = 0.0;
	for (i = 0; i < ncpu; i++) {
		totu += 100.0 * server[1+i].tseri / TSIM;
		printf("CPU#%d %5.2f%%\n", i+1, 100.0 * server[1+i].tseri
				/ TSIM);
	}
	printf("average CPU utilization by users' processes: %5.2f%%\n", totu/4);

	printf("CPU utilization by all processes:\n");
	totu = 0.0;
	for (i = 0; i < ncpu; i++) {
		totu += 100.0 * server[1+i].tser / TSIM;
		printf("CPU#%d %5.2f%%\n", i+1, 100.0 * server[1+i].tser
				/ TSIM);
	}
	printf("average CPU utilization by all processes: %5.2f%%\n", totu/4);

	printf("average CPU utilization by context switching: %5.2f%%\n", cstime/TSIM/4);

	printf("disk utilization:\n");
	for (i = 0; i < ndisk; i++) {
		printf("Disk#%d %5.2f%%\n", i+1, 100.0 * server[1+ncpu+i].tser
				/ TSIM);
	}

	printf("\n#########\n\n");
	printf("average waiting time in qe %5.2f qCPU %5.2f\n",
			queue[MEMQ].ws ? queue[MEMQ].ws / (queue[MEMQ].n - queue[MEMQ].q) : 0.0,
			queue[CPUQ].ws ? queue[CPUQ].ws / (queue[CPUQ].n - queue[CPUQ].q) : 0.0);
	printf("average waiting time in qDisk:\n");
	for (i = ncpu+1; i < ncpu+ndisk+1; i++) {
		printf("Disk#%d %5.2f\n", i-ncpu, queue[i].ws?queue[i].ws/(queue[i].n-queue[i].q):0.0);
	}
	printf("mean queue length in qe %5.2f qCPU %5.2f\n",
			queue[MEMQ].tch ? queue[MEMQ].ts / queue[MEMQ].tch : 0.0,
			queue[CPUQ].tch ? queue[CPUQ].ts / queue[CPUQ].tch : 0.0);
	printf("mean queue length in qDisk:\n");
	for (i = ncpu+1; i < ncpu+ndisk+1; i++) {
		printf("Disk#%d %5.2f\n", i-ncpu, queue[i].tch?queue[i].ts/queue[i].tch:0.0);
	}
	printf("number of visits in qe %5d qCPU %5d\n", queue[MEMQ].n
			- queue[MEMQ].q, queue[CPUQ].n - queue[CPUQ].q);
	printf("number of visits in qDisk:\n");
	for (i = ncpu+1; i < ncpu+ndisk+1; i++) {
		printf("Disk#%d %5.2f\n", i-ncpu, queue[i].n-queue[i].q);
	}
}

/*
 * The additions should be selected from the following list:
 * a – add a number of CPUs at $200 each?
 * b – add cache, each addition that cuts the current cache miss rate by half
 * costs $100, decide how many such additions you want (to clarify, two
 * additions cut the miss rate to 1.25%). Note that adding cache changes
 * tCPU, tbs, tIIO and tPIO, not just paging.
 * c – increase memory using 1GByte chips, and decide how many chips to
 * use? (cost: $100 per 1GByte chip), or
 * d – increase the number of disks at $100 per unit (assume that if there
 * are k disks, each takes 1=k load of disk requests and maintains its own
 * queue).
 */
void report_response_time(double miss, int ncpu, double mem, int ndisk, int ni, int mpl){
	printf("%f & %d & %.0f & %d & %d & %d & %.2f & %.0f \\\\\n", miss, ncpu, mem, ndisk, ni, mpl,
			sum_response_time / finished_tasks,
			log2(MISS/miss)*100+(ncpu-NCPU)*200+(mem-MEM)/1024*100+(ndisk-NDISK)*100);
}

void cleanup(int ncpu, int ndisk) {
	/**** cleanup structures ****/
	free(elist.time);
	free(elist.task);
	free(elist.event);

	int i;
	for (i = 0; i < 1 + ncpu + ndisk; i++) {
		free(queue[i].task);
		free(queue[i].tentry);
	}
	free(queue);

	free(server);

	free(task);

	inmem = 4;
	finished_tasks = 0;
	cstime = 0.0;
	insync = 0;
	sum_response_time = 0.0;
}
