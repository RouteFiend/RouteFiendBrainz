/**
 * 
 */
package routefiend;

public class RoutingTest 
{
    private static final String JAVABRIDGE_PORT="8087";
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