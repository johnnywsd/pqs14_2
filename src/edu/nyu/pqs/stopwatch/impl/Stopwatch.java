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

      synchronized (this.laps) {
        //It remove the last lap when resume.
        if (!this.laps.isEmpty()){
          int lapsSize = this.laps.size();
          this.laps.remove(lapsSize - 1);
        }
      }

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


  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((laps == null) ? 0 : laps.hashCode());
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Stopwatch other = (Stopwatch) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (laps == null) {
      if (other.laps != null)
        return false;
    } else if (!laps.equals(other.laps))
      return false;
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder lapStrBuilder = new StringBuilder();
    lapStrBuilder.append("[");
    List<Long> laptimes = this.getLapTimes();
    for (Long item : laptimes){
      lapStrBuilder.append(item.toString() + ", ");
    }
    int lapStrSize = laptimes.size();
    if (lapStrSize > 0){
      lapStrBuilder.delete(lapStrSize - 2, lapStrSize);
    }
    lapStrBuilder.append("]");
    return "Stopwatch [id=" + id 
        + ", laps=" + lapStrBuilder.toString() 
        + ", isRunning=" + isRunning + "]";
  }
  

}
