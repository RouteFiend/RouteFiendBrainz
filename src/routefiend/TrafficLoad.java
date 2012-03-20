package routefiend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;

//This class represents entries in the TrafficLoad table in the DB.
//It contains a static container holding all entries (unefficient for huge DBs) and provides some static methods to work on them
public class TrafficLoad 
{
	private static HashSet<TrafficLoad> _traffics = new HashSet<TrafficLoad>();
	
	public static void addTraffic(TrafficLoad traffic)
	{
		_traffics.add(traffic);
	}

	//Iterates over all TrafficLoad entries, (They also represent direct connections between intersections)
	//and returns all connections between the searched intersection and it's neighbours
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
	
	//Returns the TrafficLoad object for any two intersections or null if there is no direct connection between them
	public static TrafficLoad getLoadForIntersections(Intersection intersect1, Intersection intersect2)
	{
		for(TrafficLoad load : _traffics)
		{
			if((load.intersection1() == intersect1 && load.intersection2() == intersect2) ||
					(load.intersection1() == intersect2 && load.intersection2() == intersect1))
				return load;
		}
		
		return null;
	}
	
	//Iterates over all connections that form a route and returns it's cost for a specified time in a day
	@SuppressWarnings("deprecation")
	public static int getCostForRoute(int[] route, Date time)
	{
		int cost = 0;
		for(int i = 0; i < (route.length - 1); i++)
		{
			if(route[i] == 0)
    			continue;
			TrafficLoad load = getLoadForIntersections(Intersection.getIntersectionForId(route[i]), 
														Intersection.getIntersectionForId(route[i + 1]));
			if(load == null)
    			continue;
			cost += load.load()[time.getHours()];
		}
		
		return cost;
	}
	
	//Reads all TrafficLoad entries from the DB
	public static void readTrafficsFrom(String address, String username, String password)
	{
		Connection conn = null;
		Statement statement;
		ResultSet result;
		
		System.out.println("Reading TrafficLoad from " + address + " with user " + username + " and password " + password );
		
		try
		{
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection("jdbc:mysql:" + address + "?" + "user=" + username + "&password=" + password);
		    statement = conn.createStatement();
		    result = statement.executeQuery("SELECT * FROM TrafficLoad");
		    
			while(result.next())
			{
				TrafficLoad traffic = new TrafficLoad(result.getInt("id"),
														Intersection.getIntersectionForId(result.getInt("Intersect1")),
														Intersection.getIntersectionForId(result.getInt("Intersect2")),
														result.getDouble("Distance"),
														result.getBoolean("IsOneWay"));
				int[] load = new int[24];
				for(int i = 0; i < 24; i++)
				{
					load[i] = result.getInt(4+i);
				}
				traffic.setLoad(load);
				TrafficLoad.addTraffic(traffic);
			}
		} catch (SQLException ex) 
		{
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception ex)
		{
			System.out.println("Other Exception: " + ex.getMessage());
		}
	}
	
	//WHat follows are accessors and a constructor
	
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
