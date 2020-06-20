
public class State {

	String  stateName;
	String NextState0;
	String NextState1;
	boolean acceptState;
	boolean startState;
	
	public boolean isStartState() {
		return startState;
	}

	public void setStartState(boolean startState) {
		this.startState = startState;
	}

	public State(String stateName) 
	{
		this.stateName = stateName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getNextState0() {
		return NextState0;
	}

	public void setNextState0(String nextState0) {
		NextState0 = nextState0;
	}

	public String getNextState1() {
		return NextState1;
	}

	public void setNextState1(String nextState1) {
		NextState1 = nextState1;
	}

	public boolean isAcceptState() {
		return acceptState;
	}

	public void setAcceptState(boolean acceptState) {
		this.acceptState = acceptState;
	}
	
	public String ToString()
	{
		return stateName+"," + NextState0 + "," + NextState1;
	}
	
}
