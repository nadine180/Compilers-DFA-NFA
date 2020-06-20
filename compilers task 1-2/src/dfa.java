import java.util.ArrayList;
import java.util.StringTokenizer;

public class dfa {
	
	String inputString;
	State currentState = null;
	ArrayList<State> States;

	public dfa(String input) {
		this.inputString = input;
		//tokenize and get the prefix and the suffix
		
//		StringTokenizer st = new StringTokenizer(input,"#");
//		String prefix = st.nextToken();
//		String suffix = st.nextToken();
		
		String [] inputs = input.split("#",2);
		String prefix = inputs[0];
		String suffix = inputs[1];
		
		StringTokenizer st = new StringTokenizer("");
		if(!prefix.equals(""))
		{
		
		st = new StringTokenizer(prefix,";");
		ArrayList<String> states = new ArrayList<String>();
		
		while(st.hasMoreElements())
			states.add(st.nextToken());
		
		States = new ArrayList<State>();
		
	//create an arraylist of states with the proper names and set transitions of next states
		for(int i = 0; i < states.size();i++)
		{
			st = new StringTokenizer(states.get(i), ",");
			String stateName = st.nextToken();
			State newState = new State(stateName);
			newState.setNextState0(st.nextToken());
			newState.setNextState1(st.nextToken());
			
			//---CHANGE
			
			
			States.add(newState);
			
		}
			
		}
		
	//get the suffix	
		
		if(!suffix.equals(""))
		{
		st = new StringTokenizer(suffix, ",");
		
		while(st.hasMoreTokens())
		{
			String a = st.nextToken();
			State temp = getTheState(States, a);
			temp.setAcceptState(true);
			
		}
		}
		setCurrentState(States.get(0));
		

			
		}
	
	public State getTheState(ArrayList<State> States,String lookfor)
	{
		for(int i = 0; i < States.size();i++)
		{
			State temp = States.get(i);
			if(lookfor.equals(temp.getStateName()))
				return temp;
		}
		
		return null;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public String setNextState(String input)
	{
		
		if(input.equals("0"))
		{
			String nname = currentState.getNextState0();
			currentState = getTheState(States,nname);
		}
		else
			if(input.equals("1"))
			{
				String nname = currentState.getNextState1();
				currentState = getTheState(States,nname);
			}
	
		return currentState.getStateName();
	}
	
	public boolean isAccepted()
	{
		return currentState.isAcceptState();
	}
}
