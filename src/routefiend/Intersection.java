package routefiend;

import java.util.HashMap;

public class Intersection implements Comparable<Intersection>
{
	private static HashMap<Integer, Intersection> _intersections = new HashMap<Integer, Intersection>();
	
	public static void addIntersection(Intersection intersect)
	{
		_intersections.put(intersect.id(), intersect);
	}
	
	public static Intersection getIntersectionForId(Integer id)
	{
		if(id == null)
			return null;
		else
			return _intersections.get(id);
	}
	
	private int _id;
	private double _lat;
	private double _lon;
	private Street _street1;
	private Street _street2;
	private Street _street3;
	private Street _street4;
	private String _name;
	
	public int id()
	{
		return _id;
	}
	
	public double lat()
	{
		return _lat;
	}
	
	public void setLat(double value)
	{
		_lat = value;
	}
	
	public double lon()
	{
		return _lon;
	}
	
	public void setLon(double value)
	{
		_lon = value;
	}
	
	public Street street1()
	{
		return _street1;
	}
	
	public void setStreet1(Street value)
	{
		_street1 = value;
	}
	
	public Street street2()
	{
		return _street2;
	}
	
	public void setStreet2(Street value)
	{
		_street2 = value;
	}
	
	public Street street3()
	{
		return _street3;
	}
	
	public void setStreet3(Street value)
	{
		_street3 = value;
	}
	
	public Street street4()
	{
		return _street4;
	}
	
	public void setStreet4(Street value)
	{
		_street4 = value;
	}
	
	public String name()
	{
		return _name;
	}
	
	public void setName(String value)
	{
		_name = value;
	}
	
	public Intersection(int id)
	{
		_id = id;
	}
	
	public Intersection(int id, Street street1, Street street2, Street street3, Street street4, String name)
	{
		_id = id;
		_street1 = street1;
		_street2 = street2;
		_street3 = street3;
		_street4 = street4;
		_name = name;
	}
	
    public int compareTo(Intersection i)
    {
        return this.id() - i.id();
    }
}
