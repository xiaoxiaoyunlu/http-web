package com.zj.server.statistic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionStatisticer {
	private static class Metric
	  {
	    public AtomicInteger handledTransaction = new AtomicInteger(0);
	    public AtomicInteger finishedTransaction = new AtomicInteger(0);
	    public AtomicInteger handledThroughput = new AtomicInteger(0);
	    public AtomicInteger finishedThroughput = new AtomicInteger(0);
	    private int lastHandledTransaction = 0;
	    private int lastFinishedTransaction = 0;
	    
	    public void calculatePerformance(long interval)
	    {
	      int handledTransactionNow = this.handledTransaction.get();
	      int finishedTransactionNow = this.finishedTransaction.get();
	      
	      this.handledThroughput.set((int)((handledTransactionNow - this.lastHandledTransaction) * 1000 / interval));
	      
	      this.lastHandledTransaction = handledTransactionNow;
	      
	      this.finishedThroughput.set((int)((finishedTransactionNow - this.lastFinishedTransaction) * 1000 / interval));
	      
	      this.lastFinishedTransaction = finishedTransactionNow;
	    }
	  }
	  
	  private Metric metric = new Metric();
	  private Timer timer = new Timer();
	  private long caculateInterval = 1000L;
	  private long lastTimestamp = 0L;
	  
	  private void calculatePerformance()
	  {
	    long now = System.currentTimeMillis();
	    long interval = now - this.lastTimestamp;
	    if (interval > 0L) {
	      this.metric.calculatePerformance(interval);
	    }
	    this.lastTimestamp = now;
	  }
	  
	  public void start()
	  {
	    this.timer.scheduleAtFixedRate(new TimerTask()
	    {
	      public void run()
	      {
	        TransactionStatisticer.this.calculatePerformance();
	      }
	    }, this.caculateInterval, this.caculateInterval);
	  }
	  
	  public void stop()
	  {
	    this.timer.cancel();
	  }
	  
	  public long getCaculateInterval()
	  {
	    return this.caculateInterval;
	  }
	  
	  public void setCaculateInterval(long caculateInterval)
	  {
	    this.caculateInterval = (caculateInterval * 1000L);
	  }
	  
	  public long getLastTimestamp()
	  {
	    return this.lastTimestamp;
	  }
	  
	  public AtomicInteger getHandledTransaction()
	  {
	    return this.metric.handledTransaction;
	  }
	  
	  public AtomicInteger getFinishedTransaction()
	  {
	    return this.metric.finishedTransaction;
	  }
	  
	  public AtomicInteger getHandledThroughput()
	  {
	    return this.metric.handledThroughput;
	  }
	  
	  public AtomicInteger getFinishedThroughput()
	  {
	    return this.metric.finishedThroughput;
	  }
	  
	  public void incHandledTransactionEnd()
	  {
	    getFinishedTransaction().incrementAndGet();
	  }
	  
	  public void incHandledTransactionStart()
	  {
	    getHandledTransaction().incrementAndGet();
	  }
}
