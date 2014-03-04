package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  
  private static List<IStopwatch> stopwatchList = new ArrayList<IStopwatch>();
  
  private StopwatchFactory(){}

	/**
	 * Creates and returns a new IStopwatch object
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	 */
	public static IStopwatch getStopwatch(String id) 
	    throws IllegalArgumentException{
	  if (id == null){
	    String errMsg = "id is null";
	    throw new IllegalArgumentException(errMsg);
	  }else if(id.equals("")){
	    String errMsg = "id is empty";
	    throw new IllegalArgumentException(errMsg);
	  }else{
	    for (IStopwatch item : stopwatchList){
	      if (item.getId().equals(id)){
    	    String errMsg = "id is already taken";
    	    throw new IllegalArgumentException(errMsg);
	      }
	    }
	  }

	  IStopwatch stopwatch = new Stopwatch(id);
		return stopwatch;
	}

	/**
	 * Returns a list of all created stopwatches
	 * @return a List of al creates IStopwatch objects.  Returns an empty
	 * list if no IStopwatches have been created.
	 */
	public static List<IStopwatch> getStopwatches() {
	  return Collections.unmodifiableList(stopwatchList);
	}
}
