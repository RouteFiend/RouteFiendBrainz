package routefiend;

public class TrafficLoad 
{
	private int _id;
	private int _intersection1;
	private int _intersection2;
	private int[] _load;
	
	public int id()
	{
		return _id;
	}
	
	public int intersection1()
	{
		return _intersection1;
	}
	
	public void setIntersection1(int value)
	{
		_intersection1 = value;
	}
	
	public int intersection2()
	{
		return _intersection2;
	}
	
	public void setIntersection2(int value)
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
}
