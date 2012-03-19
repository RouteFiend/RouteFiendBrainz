package routefiend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DjikstraSolver
{
	public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;
	private static final int INITIAL_CAPACITY = 8;
	private static final long MAX_LATE = 900000; //15 minutes 
	
	private final Comparator<Intersection> shortestDistanceComparator = new Comparator<Intersection>()
	{
		public int compare(Intersection left, Intersection right)
		{
			// note that this trick doesn't work for huge distances, close to Integer.MAX_VALUE
			int result = getShortestDistance(left) - getShortestDistance(right);

			return (result == 0) ? left.compareTo(right) : result;
		}
	};
	
	private final PriorityQueue<Intersection> unvisitedNodes = new PriorityQueue<Intersection>(INITIAL_CAPACITY, shortestDistanceComparator);
	private final Set<Intersection> visitedNodes = new HashSet<Intersection>();
	
	private final Map<Intersection, Integer> shortestDistances = new HashMap<Intersection, Integer>();
	private final Map<Intersection, Intersection> predecessors = new HashMap<Intersection, Intersection>();
	
	class DateCompare implements Comparator<Date>
	{
		    public int compare(Date one, Date two)
		    {
		    	return one.compareTo(two);
		    }
	}
	
	//Reinitializes the algorithm for new solve
    private void init(Intersection start)
    {
    	visitedNodes.clear();
        unvisitedNodes.clear();
        
        shortestDistances.clear();
        predecessors.clear();
        
        // add source
        setShortestDistance(start, 0);
        unvisitedNodes.add(start);
    }
    
    //Constructor, that preloads the DB data from a given address and credentials
    public DjikstraSolver(String address, String username, String password)
    {
    	Street.readStreetsFrom(address, username, password);
    	Intersection.readIntersectionFrom(address, username, password);
    	TrafficLoad.readTrafficsFrom(address, username, password);
    }
    
    //Gives the optimal route for a given set of destinations and corresponding expected times
    //The return value is an array of Intersections, that compose the optimal path.
    //If there is a set of destinations, that are impossible to be reached in a reasonable time, the special value of '-1' is inserted in the array
    //and the unreachable destinations are added after it
    @SuppressWarnings("deprecation")
	public int[] solveForDestinationsAndTimes(int[] destinationIds, String[] times)
    {
    	ArrayList<Date> objTimes = new ArrayList<Date>();
    	ArrayList<Integer> unreachables = new ArrayList<Integer>();
    	HashMap<Date, Integer> timedDestinations = new HashMap<Date, Integer>();
    	DateCompare compare = new DateCompare();
    	Date last; //The destination time, that has be considered reachable last
    	
    	
    	int[] result = new int[destinationIds.length + 1];
    	int resultIndex = 1;
    	
    	//Iterate over the times, create objects from them and store them into an array.
    	//Associate each destination with it's expected time
    	for(int i = 0; i < times.length; i++)
    	{
    		try
    		{
				Date time = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).parse(times[i]);
				
				while(timedDestinations.containsKey(time))
				{
					System.out.println("Same keys");
				}
				
				timedDestinations.put(time, destinationIds[i]);
				objTimes.add(time);
			} catch (ParseException e) 
			{
				// Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//Now sort the destination times
    	Collections.sort(objTimes, compare);
    	
    	//Start from the first destination and find the time needed to get to the second
    	//If it's after it's destination time + some reasonable delay interval (e.g. 15 minutes) check the next destination
    	last = objTimes.get(0);
    	result[0] = destinationIds[resultIndex];
    	for(int i = 0; i < (objTimes.size() - 1); i++)
    	{
    		Date time1 = objTimes.get(i);
    		if(last != time1) //Check if the currently selected destination time corresponds to the destination considered reachable last.
    						  //Otherwise skip
    			continue;
    		
    		//Iterate over the destination that follow the one selected last
    		for(int j = 1; (i + j) < objTimes.size(); j++)
    		{
	    		Date time2 = objTimes.get(j);
	    		Calendar cal1 = Calendar.getInstance();
	    		Calendar cal2 = Calendar.getInstance();
	    		int id1 = timedDestinations.get(time1);
	    		int id2 = timedDestinations.get(time2);
	    		int[] route = findShortestDistance(id1, id2, time1.getHours());
	    		int minutes = TrafficLoad.getCostForRoute(route, time1);
	    		
	    		cal1.setTime(objTimes.get(i));
	    		cal2.setTime(objTimes.get(i));
	    		cal1.add(Calendar.MINUTE, minutes);
	    		//Check if it's possible to get to the next destination in reasonable time
	    		if(cal1.after(cal2))
	    		{
	    			if((cal1.getTimeInMillis() - cal2.getTimeInMillis()) > MAX_LATE)
	    			{
	    				unreachables.add(id2);
	    				continue;
	    			}
		    		else
		    		{
		    			last = time2;
		    			resultIndex++;
		    			result[resultIndex] = id2;
		    			break;
		    		}
	    		}
    		}
    	}
    	
    	//If some destinations are considered unreachable add them to the result set
    	if(unreachables.size() > 0)
    	{
	    	result[resultIndex + 1] = -1;
	    	resultIndex += 2;
	    	
	    	for(Integer id : unreachables)
	    	{
	    		result[resultIndex] = id;
	    		resultIndex++;
	    	}
    	}

		return result;
    	
    }
    
    //Djikstra. Solve the graph for a given start end end node and time
    public int[] findShortestDistance(int startId, int endId, int hour)
    {
    	Intersection start = Intersection.getIntersectionForId(startId);
    	Intersection end = Intersection.getIntersectionForId(endId);
    	init(start);
    	Intersection current;
    	while((current = unvisitedNodes.poll()) != null)
    	{
    		if(current == end)
    		{
    			Intersection ancient = predecessors.get(current);
    			System.out.print(current.name() + " ");
    			while(ancient != null)
    			{
    				System.out.print(ancient.name() + " ");
    				ancient = predecessors.get(ancient);
    			}
    			System.out.println();
    			break;
    		}
    		
    		visitedNodes.add(current);
    		computeShortestToNeighbours(current, 9);
    	}
    	int[] route = new int[predecessors.size() + 1];
    	int id = 1;
    	route[0] = end.id();
    	while(current != null)
    	{
    		current = predecessors.get(current);
    		if(current == null)
    			continue;
    		route[id] = current.id();
    		id++;
    	}
    	
    	return route;
    }
    
    //Consider all neighbours of a given node and find the closest neighbour
    public void computeShortestToNeighbours(Intersection intersect, int hour)
    {
    	for(TrafficLoad route : TrafficLoad.getNeighbours(intersect))
    	{
    		Intersection neighbour = (route.intersection1() == intersect) ? route.intersection2() : route.intersection1();
    		if(visitedNodes.contains(neighbour))
    			continue;
    		
    		int shortest = getShortestDistance(intersect) + route.load()[hour];
    		if(shortest < getShortestDistance(neighbour))
    		{
    			setShortestDistance(neighbour, shortest);
    			predecessors.put(neighbour, intersect);
    		}
    			
    	}
    }
    
    public int getShortestDistance(Intersection intersect)
    {
        Integer d = shortestDistances.get(intersect);
        return (d == null) ? INFINITE_DISTANCE : d;
    }
    
    //Rebalance the queue, according to the comparator, when setting new distance
    private void setShortestDistance(Intersection intersect, int distance)
    {
    	unvisitedNodes.remove(intersect);
    	shortestDistances.put(intersect, distance);
    	unvisitedNodes.add(intersect);
    }
}
