/**
 * 
 */
package routefiend;

/*
public class RoutingTest 
{
    private static final String JAVABRIDGE_PORT="8097";
    private static final php.java.bridge.JavaBridgeRunner runner = php.java.bridge.JavaBridgeRunner.getInstance(JAVABRIDGE_PORT);
    
	private static DjikstraSolver solver;

	public static void main(String[] args) 
	{
		try
		{
			runner.waitFor();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void  init(String address, String username, String password)
	{
		solver = new DjikstraSolver(address, username, password);
	}
	
	public static int[] findShortestDistance(int startId, int endId, int hour)
	{
		return solver.findShortestDistance(startId, endId, hour);
	}
}
//*/

//*

public class RoutingTest 
{
	DjikstraSolver solver;

	public RoutingTest()
	{
		solver = new DjikstraSolver("//igor.gold.ac.uk/ma001id_RouteFiend", "USERNAME", "PASSWORD" );
		solver.findShortestDistance(1, 7, 9);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		RoutingTest test = new RoutingTest();
	}
}
//*/