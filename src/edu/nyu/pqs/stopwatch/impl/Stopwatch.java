package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

public class Stopwatch implements IStopwatch {
  
  private final String id;
  private List<Long> laps = new ArrayList<Long>();
  private boolean isRunning = false;

  Stopwatch(){
    this.id = UUID.randomUUID().toString();
  }

  Stopwatch(String id){
    this.id = id;
  }

  private synchronized boolean isRunning() {
    return isRunning;
  }

  private synchronized void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }

  private void addLap(Long currTime){
      synchronized (this.laps) {
        laps.add(currTime);
      }
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void start() throws IllegalStateException{
    if (! this.isRunning()){
      this.setRunning(true);
      Long laptime = Long.valueOf(System.currentTimeMillis());
      this.addLap(laptime);
    }else{
      String errMsg = "Stopwatch is already running";
      throw new IllegalStateException(errMsg);
    }
  }

  @Override
  public void lap() throws IllegalStateException{
    if (this.isRunning()){
      Long laptime = Long.valueOf(System.currentTimeMillis());
      this.addLap(laptime);
    }else{
      String errMsg = "Stopwatch isn't running";
      throw new IllegalStateException(errMsg);
    }
  }

  @Override
  public void stop() throws IllegalThreadStateException{
    if(this.isRunning()){
      this.setRunning(false);
      Long laptime = Long.valueOf(System.currentTimeMillis());
      this.addLap(laptime);
    }else{
      String errMsg = "Stopwatch isn't running";
      throw new IllegalStateException(errMsg);
    }
  }

  @Override
  public void reset() {
    if (this.isRunning()){
      this.setRunning(false);
    }
    synchronized (this.laps) {
      laps.clear();
    }

  }

  @Override
  public List<Long> getLapTimes() {
    synchronized(this.laps){
      List<Long> ret = new ArrayList<Long>();
      int num = this.laps.size();
      for (int i = 0; i < num - 1; i++){
        Long interval = this.laps.get(i + 1) - this.laps.get(i);
        ret.add(interval);
      }
      return ret;
    }
  }

}
