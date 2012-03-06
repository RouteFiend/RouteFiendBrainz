package routefiend;

public class TrafficLoad 
{
	private int _id;
	private Intersection _intersection1;
	private Intersection _intersection2;
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
	
	public TrafficLoad(Intersection intersect1, Intersection intersect2, int distance, boolean isOneWay)
	{
		_load = new int[24];
		_intersection1 = intersect1;
		_intersection2 = intersect2;
		for(int i = 0; i < 24; i++)
			_load[i] = distance;
		_isOneWay = isOneWay;
	}
}
