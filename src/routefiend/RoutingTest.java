/**
 * 
 */
package routefiend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoutingTest 
{
	DjikstraSolver solver;
	Connection conn = null;
	Statement statement;
	ResultSet result;
	
	public RoutingTest()
	{
		try
		{
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/RouteFiend?" + "user=mort&password=routefiend");
		    statement = conn.createStatement();
		    result = statement.executeQuery("SELECT * FROM Streets");
		    readStreets(result);
		    
		    result = statement.executeQuery("SELECT * FROM Intersections");
		    readIntersections(result);
		    
		    result = statement.executeQuery("SELECT * FROM TrafficLoad");
		    readTrafficLoad(result);
		} catch (SQLException ex) 
		{
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception ex)
		{
			
		}
		
		solver = new DjikstraSolver();
		solver.findShortestDistance(Intersection.getIntersectionForId(1), Intersection.getIntersectionForId(7), 9);
	}
	
	public void readStreets(ResultSet result)
	{
		try
		{
			while(result.next())
			{
				Street street = new Street(result.getInt("id"), result.getString("Name"));
				Street.addStreet(street);
			}
		} catch (SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
		}
	}
	
	public void readIntersections(ResultSet result)
	{
		try
		{
			while(result.next())
			{
				Intersection intersect = new Intersection(result.getInt("id"));
				intersect.setLat(result.getDouble("Lat"));
				intersect.setLon(result.getDouble("Lon"));
				intersect.setStreet1(Street.getStreetForId(result.getInt("Street1")));
				intersect.setStreet2(Street.getStreetForId(result.getInt("Street2")));
				intersect.setStreet3(Street.getStreetForId(result.getInt("Street3")));
				intersect.setStreet4(Street.getStreetForId(result.getInt("Street4")));
				intersect.setName(result.getString("Name"));
				
				Intersection.addIntersection(intersect);
			}
		} catch (SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
		}
	}
	
	public void readTrafficLoad(ResultSet result)
	{
		try
		{
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
		} catch (SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) 
	{
		RoutingTest test = new RoutingTest();
	}
}
