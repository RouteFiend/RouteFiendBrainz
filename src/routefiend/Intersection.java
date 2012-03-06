package routefiend;

public class Intersection implements Comparable<Intersection>
{
	private int _id;
	private int _street1;
	private int _street2;
	private int _street3;
	private int _street4;
	public String name;
	
	public int id()
	{
		return _id;
	}
	
	public int street1()
	{
		return _street1;
	}
	
	public void setStreet1(int value)
	{
		_street1 = value;
	}
	
	public int street2()
	{
		return _street2;
	}
	
	public void setStreet2(int value)
	{
		_street2 = value;
	}
	
	public int street3()
	{
		return _street3;
	}
	
	public void setStreet3(int value)
	{
		_street3 = value;
	}
	
	public int street4()
	{
		return _street4;
	}
	
	public void setStreet4(int value)
	{
		_street4 = value;
	}
	
	public Intersection(int id, int street1, int street2, int street3, int street4, String nname)
	{
		_id = id;
		_street1 = street1;
		_street2 = street2;
		_street3 = street3;
		_street4 = street4;
		name = nname;
	}
	
    public int compareTo(Intersection i)
    {
        return this.id() - i.id();
    }
}
