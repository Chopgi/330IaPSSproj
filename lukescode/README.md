# Process-State Simulation — README

## Project Overview
A lightweight simulation of OS process scheduling with interrupts.
Processes move through: **NEW → READY → RUNNING → WAITING → TERMINATED**

## Files
| File | Role | Replaces |
|---|---|---|
| `ProcessState.java` | Enum of all process states | *(new)* |
| `ProcessControlBlock.java` | Models a process (PCB) | `ParallelSum.java` |
| `InterruptHandler.java` | Timer + I/O interrupt logic | `TwoTasks.java` |
| `Scheduler.java` | Round-robin CPU scheduler loop | `ThreadDemo.java` |
| `SimLogger.java` | Timestamped event logger | *(new)* |
| `App.java` | Main entry point | `App.java` |

## Compile
```bash
javac ProcessState.java SimLogger.java ProcessControlBlock.java \
      InterruptHandler.java Scheduler.java App.java
```

## Run
```bash
java App
```

## What It Demonstrates
- **4 processes** cycling through all states over time
- **Timer interrupt** — preempts a running process after each time-slice → back to READY
- **I/O request interrupt** — moves a process from RUNNING → WAITING mid-execution
- **I/O completion interrupt** — fires asynchronously on a background thread, waking the process from WAITING → READY
- **Timestamped log** — every state transition is printed with a millisecond timestamp

## Simulation Parameters (in Scheduler.java)
| Constant | Default | Meaning |
|---|---|---|
| `TIME_SLICE_MS` | 300 ms | Duration of one CPU time-slice |
| `IO_DELAY_MS` | 800 ms | How long an I/O operation takes |
