package routefiend;

import java.util.HashSet;

public class CityMap 
{
	private HashSet<Intersection> _intersections;
	private HashSet<TrafficLoad> _routes;
	
	public HashSet<Intersection> intersections()
	{
		return _intersections;
	}
	
	public void setIntersections(HashSet<Intersection> value)
	{
		_intersections = value;
	}
	
	public HashSet<TrafficLoad> routes()
	{
		return _routes;
	}
	
	public void setRoutes(HashSet<TrafficLoad> value)
	{
		_routes = value;
	}
	
	public CityMap()
	{
		_intersections = new HashSet<Intersection>();
		_routes = new HashSet<TrafficLoad>();
	}
	
	public TrafficLoad addRoute(Intersection intersect1, Intersection intersect2, int dist, boolean isOneWay)
	{
		TrafficLoad route = new TrafficLoad(intersect1, intersect2, dist, isOneWay);
		_routes.add(route);
		return route;
	}
	
	public HashSet<TrafficLoad> getNeighbours(Intersection intersect)
	{
		HashSet<TrafficLoad> neighbours = new HashSet<TrafficLoad>();
		
		for(TrafficLoad load : _routes)
		{
			if((load.intersection1() == intersect) 
					|| (load.isOneWay() == false && load.intersection2() == intersect))
				neighbours.add(load);
		}
		
		return neighbours;
		
	}
	
	public int getDistance(Intersection intersect1, Intersection intersect2)
	{
		int distance = 0;
		
		
		
		return distance;
	}
}
