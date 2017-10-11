import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProblemJugsPuzzle extends Problem {
	
    static final int twelve_g = 0;
    static final int eight_g = 1;
    static final int three_g = 2;
    static Map<Integer, Integer> gallons = new HashMap<Integer, Integer>(){{
        put(0, 12);
        put(1, 8);
        put(2, 3);
    }};
    
	boolean goal_test(Object state) {
        StateJugsPuzzle jug_state = (StateJugsPuzzle) state;   
        if (jug_state.jugsArray[twelve_g]==1 || jug_state.jugsArray[eight_g]==1 || jug_state.jugsArray[three_g]==1){
            return true;
        }
        return false;
	}
  
    Set<Object> getSuccessors(Object state) {
    	
        Set<Object> set = new HashSet<Object>();
        StateJugsPuzzle jug_state = (StateJugsPuzzle) state;
        
        //Let's create with constraint
        StateJugsPuzzle successor_state;
        for(int i=0; i<3; i++){
            //fill jug up 
            if(jug_state.jugsArray[i]<gallons.get(i)){
                successor_state = new StateJugsPuzzle(jug_state);
                successor_state.jugsArray[i] = gallons.get(i);
                    set.add(successor_state); 
            }
        
            
            if(jug_state.jugsArray[i]>0){
                //pour water onto ground
                successor_state = new StateJugsPuzzle(jug_state);
                successor_state.jugsArray[i] = 0;
                set.add(successor_state); 

                //move water to fill another jug
                for(int j=0; j<3; j++){
                    if(j != i && jug_state.jugsArray[j]<gallons.get(j)){
                        successor_state = new StateJugsPuzzle(jug_state);
                        int j_gap = gallons.get(j)- jug_state.jugsArray[j];
                        if(j_gap >= jug_state.jugsArray[i]) {
                                successor_state.jugsArray[i]=0;
                                successor_state.jugsArray[j]=jug_state.jugsArray[j]+jug_state.jugsArray[i];
                        }else{
                            successor_state.jugsArray[i] -= j_gap;
                            successor_state.jugsArray[j] = gallons.get(j);

                        }
                        set.add(successor_state);  

                    }
                }
            }
            
            

        }
        
        return set;
    }
    
    private boolean isValid(StateJugsPuzzle state)
    {   
        //Checking to see if any element of the array is negative 
        for (int i=0; i<3; i++){
            if (state.jugsArray[i] < 0) {
                return false;
            }
            if(state.jugsArray[twelve_g]>12 || state.jugsArray[eight_g]>8 || state.jugsArray[three_g]>3) return false;
        }
        
        //Checking to see if the numbers of 12g, 8g, and 3g jugs 
        //are more then 12,8,3 respectively    

        return true;
    }

	
	double step_cost(Object fromState, Object toState) { 
        StateJugsPuzzle state_before = (StateJugsPuzzle) fromState;
        StateJugsPuzzle state_after = (StateJugsPuzzle) toState;
        int dec = -1, inc = -1;
        for(int i=0; i<3; i++){
            if(state_after.jugsArray[i] > state_before.jugsArray[i]) inc = i;
            if(state_after.jugsArray[i] < state_before.jugsArray[i]) dec = i;
        }
        //fill jug
        if(inc > -1 && dec == -1){
            return gallons.get(inc)-state_before.jugsArray[inc];
        //pour water onto ground
        }else if(inc == -1 && dec > -1){
            return state_before.jugsArray[dec];
        //pour water from jug dec into jug inc
        }else if(inc > -1 && dec > -1){
            return state_after.jugsArray[inc] - state_before.jugsArray[inc];
        }
        return 0;

    }

	public double h(Object state) { return 0; }


	public static void main(String[] args) throws Exception {
		ProblemJugsPuzzle problem = new ProblemJugsPuzzle();
		int[] jugArray = {0,0,0};
		problem.initialState = new StateJugsPuzzle(jugArray); 
		System.out.println("initial state: ");
		Search search  = new Search(problem);
		System.out.println("search ");
		System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());

        System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());

        System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());

        System.out.println("BreadthFirstGraphSearch:\t\t" + search.BreadthFirstGraphSearch());

        System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());

        System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());

        System.out.println("IterativeDeepeningTreeSearch:\t\t" + search.IterativeDeepeningTreeSearch());
        
        System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());


	}
}
