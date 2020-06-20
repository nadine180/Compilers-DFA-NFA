import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Task2 {

	
	public static dfa dfa (String nfa) {

		
		String[] inputs = nfa.split("#",4);
		String Z = inputs[0];
		String O = inputs[1];
		String E = inputs[2];
		String A = inputs[3];
				
		
		         //state|input  ----| Hashtable in the format of parent state --> input
		Hashtable<Hashtable,String> states = new Hashtable<Hashtable,String>();
		
		
		//constructing the hashtable of all transitions for all states
		states = constructDependencies(states,Z,"0");
		states = constructDependencies(states,O,"1");
		states = constructDependencies(states,E,"ep");
		Hashtable test = new Hashtable<String,String>();
//		test.put("1", "1");
		ArrayList<State> result = new ArrayList<State>();
		result = constructDFATable(states, A);
		String input = constructInputString(result);
		
		dfa DFA = new dfa(input);
		
		
		
		
		
		return DFA;
	}
	public static void main(String[] args) {

		
		String case1 = "0,0;0,1;1,3#0,1;1,2;2,3#1,2;3,2#3";
		
		dfa DFA1 = dfa(case1);
		run(DFA1,"000111");
		run(DFA1,"10110");
		run(DFA1,"001100");
		run(DFA1,"101010");
		run(DFA1,"1111");
//		
		
		String case2 = "0,1;1,2;2,3#1,3;3,4;4,2#0,2;3,1;2,4#2";
		dfa DFA2 = dfa(case2);
		run(DFA2,"0000");
		run(DFA2,"011011");
		run(DFA2,"01010");
		run(DFA2,"101010");
		run(DFA2,"11100");
		
		
	}
	
	public static Hashtable constructDependencies(Hashtable states, String Z, String var) {
		if(!Z.equals("")) {
		StringTokenizer s = new StringTokenizer(Z,";");
		while(s.hasMoreTokens())
		{
			
			StringTokenizer st = new StringTokenizer(s.nextToken(),",");
			String from = st.nextToken();
			String to = st.nextToken();
			Hashtable h = new Hashtable<String,String>();
			h.put(from,var);
			if(states.containsKey(h)) {
			String value = (String)states.get(h);
			states.put(h, value+" " +to);
			}
			else
				states.put(h, to);

		}
		
		}
		return states;
	}

	
	public static String constructInputString(ArrayList<State> result)
	{
		
		String input = "";
		String accept = "";
		for(int i = 0; i < result.size();i++) {
			input += result.get(i).ToString()+";";
			if((result.get(i)).isAcceptState())
				accept+=(result.get(i)).getStateName()+",";
		}
		
		
		input = input.substring(0,input.length()-1)+ "#";
		if(accept.length()>0)
		accept = accept.substring(0,accept.length()-1);
		input += accept;
		System.out.println(input);
		return input;
	}
	
	public static ArrayList<State> constructDFATable(Hashtable states, String A) {
		
		
		Hashtable<String,State> result = new Hashtable<String,State>();  //the result that will be returned
		Hashtable<String,String> temp = new Hashtable<String,String>();
		//Creating an array of accept states
		StringTokenizer st = new StringTokenizer(A,",");
		
		ArrayList<String> acc = new ArrayList<String>();
		while(st.hasMoreTokens())
			acc.add(st.nextToken());
		
		//--------------------------------------------------------------------
		
		boolean complete = false;  //stop when all states are created
		boolean start = true;  //to differentiate between starting the dfa conversion and the rest of the states
		
		Queue <State> remainingStates = new LinkedList<State>();  //Queue to keep track of the states that need to be created
		String stateName = "0";
		String startState = "";
		while(!complete) {
			
			if(start)
			{
				start = false;
				stateName = checkEpsilon(states,stateName);
				State s = new State(stateName);
				remainingStates.add(s);
				startState = s.getStateName();
			}
			
			
			State s = remainingStates.poll();
			String sn = s.getStateName();
			if(noRelation(result,sn)) {
	
			String trans0 = "";
			String trans1 = "";
			
			if(sn.equals("DEAD"))
			{
				State dead = new State(sn);
				dead.setNextState0("DEAD");
				dead.setNextState1("DEAD");
				if(noRelation(result,"DEAD"))
					result.put(sn,dead);
				
			}
			else {
			//getting the zero transitions
			st = new StringTokenizer(sn);
			while(st.hasMoreElements())
			{
				String nextState = st.nextToken();
				temp = new Hashtable<String,String>();
				temp.put(nextState,"0");
				if(states.get(temp) != null) {
				StringTokenizer sm = new StringTokenizer((String)states.get(temp));
				while(sm.hasMoreElements())
				{
					String t = sm.nextToken();
					if(trans0.equals(""))
						trans0+= t;
					else
					trans0 += (trans0.contains(t)) ? "" : " " +t;
					
					
					trans0 = checkEpsilon(states,trans0);
				}
			}
			}
			
			//getting the one transitions
			st = new StringTokenizer(sn);
			while(st.hasMoreElements())
			{
				
				String nextState = st.nextToken();
				temp = new Hashtable<String,String>();
				temp.put(nextState,"1");
				if(states.get(temp) != null) {
				StringTokenizer sm = new StringTokenizer((String)states.get(temp));
				while(sm.hasMoreElements())
				{
					String t = sm.nextToken();
					if(trans1.equals(""))
					trans1+= t;
					else
					trans1 += (trans1.contains(t)) ? "" :  " " +t;

					trans1 = checkEpsilon(states,trans1);
				//	}
				}
			}
			}
			
			
			if(trans0.equals(""))
				trans0 = "DEAD";
			if(trans1.equals(""))
				trans1 = "DEAD";
			
			if(noRelation(result,trans0) && !checkIfSameString(sn,trans0)) {
				
				remainingStates.add(new State(trans0));
				
			}
			else
			{
				if(checkIfSameString(sn,trans0))
					trans0 = sn;
				else
			{
				trans0 = findSimilarState(result,trans0);
			}
			}
			s.setNextState0(trans0);			
			
			
			
			if(noRelation(result,trans1) && !checkIfSameString(sn,trans1))
			{
		
				remainingStates.add(new State(trans1));
			}
			else
			{
				if(checkIfSameString(sn,trans1))
				{
			 		trans1 = sn;

				}
				else
			{
				trans1 = findSimilarState(result,trans1);
					
			}
			}
			 s.setNextState1(trans1);
			 
			
			for(int i = 0; i < acc.size();i++)
			{
				
				if((s.getStateName()).contains(acc.get(i) + ""))
				{
					s.setAcceptState(true);
				}
			}
			result.put(s.getStateName(), s);
			}
			
		}
			if(remainingStates.size() == 0)
				complete = true;
			
	}
		

		
	ArrayList<State> finalStates = new ArrayList<State>();
	
	Set<String> keys = result.keySet();
	
	finalStates.add(result.get(startState));
	result.remove(startState);
	
	for(String key: keys)
		finalStates.add(result.get(key));
	
	
	
	
	return finalCheckDuplicate(finalStates);
	}

	public static void run(dfa DFA, String input) {
		
		String inputString = DFA.inputString;
		for(int i = 0; i < input.length();i++)
		{
			
			DFA.setNextState(input.charAt(i)+"");
		}
		System.out.println();
		System.out.println(DFA.getCurrentState().isAcceptState());
		System.out.println("-----------------------------------");
		
		DFA.setCurrentState(DFA.States.get(0));
	}
	
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
					re &= statename.contains( k);
			}		
			norelation &= !re;
			}
		}
		
		
		return norelation;
	}
	
	public static String findSimilarState(Hashtable result, String statename)
	{
		Set<String> keys = result.keySet();
		boolean norelation = true;
		for(String key : keys)
		{
			if(key.length() == statename.length()) {
			StringTokenizer st = new StringTokenizer(key);
			boolean nore = true;
			while(st.hasMoreTokens()) {
				nore &= statename.contains(st.nextToken());
			
			}
			if(nore)
			{
				return key;
			}
			}
		}
		
		return null;
	}
	
	public static boolean checkIfSameString(String state, String trans)
	{
		
		if(state.length() != trans.length())
			return false;
		
		else 
		{
			StringTokenizer st = new StringTokenizer(trans);
			while(st.hasMoreElements())
			{
				if(!state.contains(st.nextToken()))
					
					return false;
					
			}
		}
		
		return true;
		
	}
	
	public static String checkEpsilon(Hashtable states, String state)
	{
		String copy = state;

		boolean newTrans = true;
		
		while(newTrans)
		{
			StringTokenizer st = new StringTokenizer(copy);
			
		    while(st.hasMoreTokens())
		    {
		    	
		    	String token = st.nextToken();
		    	Hashtable<String,String> temp = new Hashtable<String,String>();
		    	temp.put(token, "ep");
		    	if(states.get(temp) != null)
		    	{
		    		String ff = (String) states.get(temp);
		    		if(!copy.contains(ff))
		    		{
		    				copy += " " +ff;
		    		}
		    	}
		    	
		    }
		    
		    if(copy.length() == state.length())
		    	newTrans = false;
		    else
		    {
		    	state = copy;
		    }
		}
		return state;
				
	}

	public static ArrayList<State> finalCheckDuplicate(ArrayList<State> result)
	{
		for(int i = 0; i < result.size()-1;i++)
		{
			State s = result.get(i);
			
			if(checkIfSameString(s.getStateName(), s.getNextState0()))
				s.setNextState0(s.getStateName());
			
			if(checkIfSameString(s.getStateName(), s.getNextState1()))
				s.setNextState1(s.getStateName());
				
			for(int j = 0;j<result.size();j++)
			{
				if(checkIfSameString(s.getStateName(),result.get(j).getStateName()))
					result.get(j).setStateName(s.getStateName());
				
				if(checkIfSameString(s.getStateName(),result.get(j).getNextState0()))
					result.get(j).setNextState0(s.getStateName());
				
				if(checkIfSameString(s.getStateName(),result.get(j).getNextState1()))
					result.get(j).setNextState1(s.getStateName());
			}
		}
		
		return result;
	}
}

	
