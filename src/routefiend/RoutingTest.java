/**
 * 
 */
package routefiend;

public class RoutingTest 
{
	DjikstraSolver solver;

	public RoutingTest()
	{
		solver = new DjikstraSolver("//localhost/RouteFiend", "root", "ext3!fs" );
		solver.findShortestDistance(1, 7, 9);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		RoutingTest test = new RoutingTest();
	}
}