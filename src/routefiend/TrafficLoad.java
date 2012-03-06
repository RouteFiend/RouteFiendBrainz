package routefiend;

import java.util.HashSet;

public class TrafficLoad 
{
	private static HashSet<TrafficLoad> _traffics = new HashSet<TrafficLoad>();
	
	public static void addTraffic(TrafficLoad traffic)
	{
		_traffics.add(traffic);
	}
	
	public static HashSet<TrafficLoad> getNeighbours(Intersection intersect)
	{
		HashSet<TrafficLoad> neighbours = new HashSet<TrafficLoad>();
		
		for(TrafficLoad load : _traffics)
		{
			if((load.intersection1() == intersect) 
					|| (load.isOneWay() == false && load.intersection2() == intersect))
				neighbours.add(load);
		}
		
		return neighbours;
		
	}
	
	private int _id;
	private Intersection _intersection1;
	private Intersection _intersection2;
	private double _distance;
	private int[] _load;
	private boolean _isOneWay;
	
	public int id()
	{
		return _id;
	}
	
	public Intersection intersection1()
	{
		return _intersection1;
	}
	
	public void setIntersection1(Intersection value)
	{
		_intersection1 = value;
	}
	
	public Intersection intersection2()
	{
		return _intersection2;
	}
	
	public void setIntersection2(Intersection value)
	{
		_intersection2 = value;
	}
	
	public double distance()
	{
		return _distance;
	}
	
	public void setDistance(double value)
	{
		_distance = value;
	}
	
	public int[] load()
	{
		return _load;
	}
	
	public void setLoad(int[] value)
	{
		_load = value;
	}
	
	public boolean isOneWay()
	{
		return _isOneWay;
	}
	
	public void setIsOneWay(boolean value)
	{
		_isOneWay = value;
	}
	
	public TrafficLoad(int id, Intersection intersect1, Intersection intersect2, double distance, boolean isOneWay)
	{
		_id = id;
		_load = new int[24];
		_intersection1 = intersect1;
		_intersection2 = intersect2;
		for(int i = 0; i < 24; i++)
			_load[i] = 0;
		_isOneWay = isOneWay;
	}
}
