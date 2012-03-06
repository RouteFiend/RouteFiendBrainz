package routefiend;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DjikstraSolver
{
	public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;
	private static final int INITIAL_CAPACITY = 8;
	
	private final Comparator<Intersection> shortestDistanceComparator = new Comparator<Intersection>()
	{
		public int compare(Intersection left, Intersection right)
		{
			// note that this trick doesn't work for huge distances, close to Integer.MAX_VALUE
			int result = shortestDistances.get(left) - shortestDistances.get(right);

			return (result == 0) ? left.compareTo(right) : result;
		}
	};
	
	private final PriorityQueue<Intersection> unvisitedNodes = new PriorityQueue<Intersection>(INITIAL_CAPACITY, shortestDistanceComparator);
	private final Set<Intersection> visitedNodes = new HashSet<Intersection>();
	
	private final Map<Intersection, Integer> shortestDistances = new HashMap<Intersection, Integer>();
	private final Map<Intersection, Intersection> predecessors = new HashMap<Intersection, Intersection>();
	
	CityMap _map;
	
	public DjikstraSolver(CityMap map)
	{
		_map = map;
	}
	
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
    
    public void findShortestDistance(Intersection start, Intersection end, int hour)
    {
    	init(start);
    	Intersection current;
    	while((current = unvisitedNodes.poll()) != null)
    	{
    		if(current == end)
    			break;
    		
    		visitedNodes.add(current);
    		computeShortestToNeighbours(current, 9);
    	}
    }
    
    public void computeShortestToNeighbours(Intersection intersect, int hour)
    {
    	System.out.println("Examine: " + intersect.name);
    	for(TrafficLoad route : _map.getNeighbours(intersect))
    	{
    		Intersection neighbour = (route.intersection1() == intersect) ? route.intersection2() : route.intersection1();
    		if(visitedNodes.contains(neighbour))
    			continue;
    		
    		Integer objInt = shortestDistances.get(intersect);
    		objInt = (objInt == null) ? INFINITE_DISTANCE : objInt;
    		objInt += route.load()[hour];
    		if(objInt < shortestDistances.get(neighbour))
    		{
    			System.out.println("New shortest: " + neighbour.name);
    			setShortestDistance(neighbour, objInt);
    			predecessors.put(neighbour, intersect);
    		}
    			
    	}
    }
    
    //Rebalance the queue, according to the comparator, when setting new distance
    private void setShortestDistance(Intersection intersect, int distance)
    {
    	unvisitedNodes.remove(intersect);
    	shortestDistances.put(intersect, distance);
    	unvisitedNodes.add(intersect);
    }
}
