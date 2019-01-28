package common;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class JobQueue {

  private static JobQueue jobQueue;

  private Queue<Target> jobs;

  public synchronized Target dequeue() throws InterruptedException {
    while (jobs.size() == 0) {
      wait();
    }
    System.out.println("nofity");
    notify();
    return jobs.poll();
  }

  public synchronized void enqueue(Target target) throws InterruptedException {
    while (jobs.size() == 1) {
      System.out.println("wait");
      wait();
    }
    jobs.add(target);
    notify();
  }

  private JobQueue() {
    this.jobs = new ConcurrentLinkedDeque<>();
  }

  public static JobQueue getInstance() {
    if (jobQueue == null) {
      jobQueue = new JobQueue();
    }
    return jobQueue;
  }
}
