package routefiend;

public class Street 
{
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
