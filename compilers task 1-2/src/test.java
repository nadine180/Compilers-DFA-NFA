import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

public class test {
	public static boolean noRelation(Hashtable result, String statename)
	{

		Set<String> keys = result.keySet();
		boolean norelation = true;
		for(String key : keys)
		{
	
			if(key.length() == statename.length()) {
			StringTokenizer st = new StringTokenizer(key);
			boolean re = true;
			
			while(st.hasMoreTokens())
			{
				String k = st.nextToken();
					re &= statename.contains(k);

			}		
			norelation &= !re;
			}
		}
		
		
		return norelation;
	}
	
	public static void main(String[] args) {
		Hashtable<String,String> result = new Hashtable<String,String>();
		
		//result.put("2 1 0","state1");
		result.put("0 0 0", "state2");
		String test = "##5";
		StringTokenizer st = new StringTokenizer(test,"#");
		System.out.println(st.nextElement());
		
		System.out.println(noRelation(result,"1 2 0"));
	}
}
