/**
 * 
 */
package routefiend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RoutingTest 
{
	public static void main(String[] args) 
	{
		Intersection a = new Intersection(1, 1, 2, -1, -1, "a");
		Intersection b = new Intersection(2, 1, 2, -1, -1, "b");
		Intersection c = new Intersection(3, 1, 2, -1, -1, "c");
		Intersection d = new Intersection(4, 1, 2, -1, -1, "d");
		Intersection e = new Intersection(5, 1, 2, -1, -1, "e");
		Intersection f = new Intersection(6, 1, 2, -1, -1, "f");
		Intersection g = new Intersection(7, 1, 2, -1, -1, "g");
		Intersection h = new Intersection(8, 1, 2, -1, -1, "h");
		Intersection i = new Intersection(9, 1, 2, -1, -1, "i");
		CityMap map = new CityMap();
		
		map.addRoute(a, d, 5, false);
		map.addRoute(a, b, 7, false);
		map.addRoute(a, c, 4, false);
		map.addRoute(c, b, 2, false);
		map.addRoute(c, h, 100, false);
		map.addRoute(d, f, 9, false);
		map.addRoute(b, e, 25, false);
		map.addRoute(e, g, 10, false);
		map.addRoute(f, h, 20, false);
		map.addRoute(g, i, 2, false);
		map.addRoute(h, i, 3, false);
		
		DjikstraSolver solver = new DjikstraSolver(map);
		solver.findShortestDistance(a, i, 1);
	}
}
