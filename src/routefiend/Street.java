package routefiend;

import java.util.HashMap;

public class Street 
{
	private static HashMap<Integer, Street> _streets = new HashMap<Integer, Street>();
	
	public static void addStreet(Street street)
	{
		_streets.put(street.id(), street);
	}
	
	public static Street getStreetForId(Integer id)
	{
		if(id == null)
			return null;
		else
			return _streets.get(id);
	}
	
	
	private int _id;
	private String _name;
	
	public int id()
	{
		return _id;
	}
	
	public String name()
	{
		return _name;
	}
	
	public void setName(String value)
	{
		_name = value;
	}
	
	public Street(int id, String name)
	{
		_id = id;
		_name = name;
	}
}
