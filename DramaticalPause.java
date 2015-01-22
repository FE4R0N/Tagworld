
public class DramaticalPause {
	public static void miniPause(){
		try
        {
            Thread.sleep(180);
        } 
        catch (InterruptedException e)
        {
            // ignoring exception at the moment
        }
	}
	public static void shortPause(){
		try
        {
            Thread.sleep(400);
        } 
        catch (InterruptedException e)
        {
            // ignoring exception at the moment
        }
	}
	public static void mediumPause(){
		try
        {
            Thread.sleep(5000);
        } 
        catch (InterruptedException e)
        {
            // ignoring exception at the moment
        }
	}
	public static void longPause(){
		try
        {
            Thread.sleep(9000);
        } 
        catch (InterruptedException e)
        {
            // ignoring exception at the moment
        }
	}
}
