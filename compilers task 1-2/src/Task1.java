
public class Task1 {

	public static void main(String [] args) {
		
		String dfaConstruction = "0,2,1;1,1,2;2,3,4;3,4,3;4,5,5;5,5,5#3,5";
		String dfaa = "0,0,1;1,2,1;2,0,3;3,3,3#1,3";
		dfa DFA = new dfa(dfaa);
		
		String input = "10101110";
		String in = "101";
		
		run(DFA,in);
	}
	
	public static void run(dfa DFA, String input) {
		
		for(int i = 0; i < input.length();i++)
		{
			DFA.setNextState(input.charAt(i)+"");
		}
		System.out.println(DFA.getCurrentState().isAcceptState());
		
	}
}
