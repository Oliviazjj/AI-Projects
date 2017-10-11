import java.util.HashSet;
import java.util.Set;
import java.lang.*;

public class ProblemCannibals extends Problem {
	
    static final int cannL = 0;
    static final int missL = 1;
    static final int boatL = 2;
    static final int cannR = 3;
    static final int missR = 4;
    static final int boatR = 5;
    
	boolean goal_test(Object state) {
        StateCannibals can_state = (StateCannibals) state;
        
        if (can_state.canArray[cannR]==3 && can_state.canArray[missR]==3 && can_state.canArray[boatR]==1)
            return true;
        else return false;
	}
  
    Set<Object> getSuccessors(Object state) {
    	
        Set<Object> set = new HashSet<Object>();
        StateCannibals can_state = (StateCannibals) state;
        
        //Let's create without any constraint, then remove the illegal ones
        StateCannibals successor_state;
        //one cannibal only from left to right
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannL] -= 1;
        successor_state.canArray[cannR] += 1;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);

        //one cannibal only from right to left
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannL] += 1;
        successor_state.canArray[cannR] -= 1;
        successor_state.canArray[boatL] += 1;
        successor_state.canArray[boatR] -= 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //two cannibals from left to right
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannL] -= 2;
        successor_state.canArray[cannR] += 2;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //two cannibals from right to left 
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[cannL] += 2;
        successor_state.canArray[cannR] -= 2;
        successor_state.canArray[boatL] += 1;
        successor_state.canArray[boatR] -= 1;
        if (isValid(successor_state)) set.add(successor_state);      
        
        //one missionary only from left to right 
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] -= 1;
        successor_state.canArray[missR] += 1;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //one missionary only from right to left 
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] += 1;
        successor_state.canArray[missR] -= 1;
        successor_state.canArray[boatL] += 1;
        successor_state.canArray[boatR] -= 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //two missionaries from left to right 
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] -= 2;
        successor_state.canArray[missR] += 2;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);
        
        //two missionaries from right to left 
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] += 2;
        successor_state.canArray[missR] -= 2;
        successor_state.canArray[boatL] += 1;
        successor_state.canArray[boatR] -= 1;
        if (isValid(successor_state)) set.add(successor_state);  
        
        //one cannibal and one missionary from left to right 
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] -= 1;
        successor_state.canArray[missR] += 1;
        successor_state.canArray[cannL] -= 1;
        successor_state.canArray[cannR] += 1;
        successor_state.canArray[boatL] -= 1;
        successor_state.canArray[boatR] += 1;
        if (isValid(successor_state)) set.add(successor_state);  
        
        //one cannibal and one missionary from right to left 
        successor_state = new StateCannibals(can_state);
        successor_state.canArray[missL] += 1;
        successor_state.canArray[missR] -= 1;
        successor_state.canArray[cannL] += 1;
        successor_state.canArray[cannR] -= 1;
        successor_state.canArray[boatL] += 1;
        successor_state.canArray[boatR] -= 1;
        if (isValid(successor_state)) set.add(successor_state);  
        
        return set;
    }
    
    private boolean isValid(StateCannibals state)
    {   
        //Checking to see if any element of the array is negative 
        for (int i=0; i<6; i++)
            if (state.canArray[i] < 0) return false;
        
        //Checking to see if the numbers of cannibals, missionaries, and boat 
        //are more then 3,3,1 respectively
        
        if(state.canArray[missL]>3 || state.canArray[cannL]>3 || state.canArray[boatL]>1 
            || state.canArray[missR]>3 || state.canArray[cannR]>3 || state.canArray[boatR]>1) return false;
        
        //Now, checking if cannibals out number missionaries
        
        if ((state.canArray[cannL] > state.canArray[missL] && state.canArray[missL] > 0) || 
            (state.canArray[cannR] > state.canArray[missR] && state.canArray[missR] > 0)) return false;
        return true;
    }

	
	double step_cost(Object fromState, Object toState) { return 1; }

	public double h(Object state) { 
        StateCannibals can_state = (StateCannibals) state;
        return can_state.canArray[0] + can_state.canArray[1] + Math.abs(can_state.canArray[2] - 1);
        //return 0;
    }


	public static void main(String[] args) throws Exception {
		ProblemCannibals problem = new ProblemCannibals();
		int[] canArray = {3,3,1,0,0,0};
		problem.initialState = new StateCannibals(canArray); 
		System.out.println("initial state: ");
		Search search  = new Search(problem);
		System.out.println("search ");
		System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());

        System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());

        System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());

        System.out.println("IterativeDeepeningTreeSearch:\t\t" + search.IterativeDeepeningTreeSearch());

        System.out.println("GreedyBestFirstTreeSearch:\t\t" + search.GreedyBestFirstTreeSearch());

        System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());

        System.out.println("BreadthFirstGraphSearch:\t\t" + search.BreadthFirstGraphSearch());

        System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());

        System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch()); 
        
        System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());     
        
        System.out.println("GreedyBestFirstGraphSearch:\t" + search.GreedyBestFirstGraphSearch());

        System.out.println("AstarGraphSearch:\t" + search.AstarGraphSearch());
	}
}
