/*
 * INTELLIGENT SYSTEMS PROGRAMMING PROJECT 2 - SOLVING N-QUEEN USING HILL CLIMBING ALGORITHM
 * AUTHORS: DEEPAK VEERAPANDIAN - 801100869
 * 			RISHIKUMAR GNANASUNDARAM - 801101490
 */

package queen;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;


public class hillClimbing {
	/* output list is used to store the output boards.
	 * tempChild is used to store the nodes with same heuristics and pick random one.
	 * solutionPath is used to print the solution path.
	 * REPEATS is used in sideways moves to continue in the same heuristics.
	 *  RESTARTS is used to restart the problem.
	 *  N is used to get the size of the array.
	 *  isSideWaysMove is used find whether it is true or false.
	 *  isRandomRestart is used find whether it is true or false.
	 *  isGoal is used find whether it is true or false.
	 *  isFailure is used find whether it is true or false.
	 *  successSteps is used to have the count of steps in which we achieved success.
	 *  failureSteps is used to have the count of steps in which we achieved failure.
	 *  averageSteps is used to have the count of number of times it has to execute random restart
	 */
	static ArrayList<Node> output = new ArrayList<Node>();
	static ArrayList<Node> tempChild = new ArrayList<Node>();
	static ArrayList<char[][]> solutionPath = new ArrayList<>();
	static int REPEATS = 100;
	static int RESTARTS = 500;
	static int N = 0;
	static boolean isSideWaysMove;
	static boolean isRandomRestart;
	static boolean isGoal = false;
	static boolean isFailure = false;
	static int successSteps = 0;
	static int failureSteps = 0;
	static int x = 1;
	static int averageSteps = 100;
	
	public static void main(String[] args ) 
	{
		System.out.println("Enter the board size(4/8/16):	");
		Scanner queen = new Scanner(System.in);
		N = queen.nextInt();
		char [][] board = new char[N][N];

		if(N==4 || N==8 || N==16)
		{
			int totalAttempts = 0;
			int failureAttempts = 0;
			int successAttempts = 0;										
			
			System.out.println("Enter 1 for Steepest- ascent hill climbing \nEnter 2 for Hill-climbing search with sideways move \nEnter 3 for Random-restart hill-climbing search without sideways move \nEnter 4 for Random-restart hill-climbing search with sideways move");
			int option = queen.nextInt();
			switch(option)
			{
				case 1:
					isSideWaysMove = false;
					break;
				case 2:
					isSideWaysMove = true;
					break;
				case 3:
					isSideWaysMove = false;
					isRandomRestart = true;
					break;	
				case 4:
					isSideWaysMove = true;
					isRandomRestart = true;
					break;	
			}
			
			int average  = 0;
			while(isRandomRestart? (average < averageSteps):(average==0))		//looping to find the average for random restarts
			{
				isGoal = false;
				while(isRandomRestart? (!isGoal): (totalAttempts < RESTARTS))		//restarting the random generation for RESTART times to check success%
				{	
					Random r = new Random();
					for(int i=0; i<N; i++)		//random generation
					{
						for(int j=0; j<N; j++)
						{
							board[i][j]='-';
						}
					}
					for(int j=0; j<N; j++)
					{
						int randomPos = r.nextInt(N);
						board[randomPos][j] = 'Q';
					}
					
					solutionPath.add(board);
					
					goalCheck(board);		//checking if the goal node is achieved or not
					
					if(isGoal)
						successAttempts++;
					else
						failureAttempts++;
					
					totalAttempts++;
				}	
				average++; 
			}
			
			if(!isRandomRestart)
			{
				System.out.println("total = " + totalAttempts + " failure = " + failureAttempts + " success = "+successAttempts);
				float success = (float)(successAttempts) / (float)totalAttempts;
				System.out.println("Success =  " + (success*100) + " %");
				if(successSteps!=0)
					System.out.println("Success Steps = " + (successSteps/successAttempts));
				if(failureSteps!=0)
					System.out.println("Failure Steps = " + (failureSteps/failureAttempts));
			}	
			else
			{
				System.out.println("Total restarts done = " + (totalAttempts/averageSteps) + "\tNumber of steps = " + ((successSteps+failureSteps)/averageSteps) );
			}
		}
		else
			System.out.println("Wrong Input!!!");
	}
	/*
     *@function goalCheck
     *@return void
     * @param source {char board[][]} - input array.
     * This function checks whether the goal is been succeeded or not.
     */
	public static void goalCheck(char [][] board)
	{
		int sideWaysMoveCount = 1;	
		int stepCount = 0;
		isFailure = false;
		isGoal = false;
		output.removeAll(output);
		
		int parentHeuristic = heuristic(board);
		if(parentHeuristic == 0)
			isGoal = true;
		if(!isGoal)
			output.addAll(createChildren(board));
		
		while(!isGoal && !isFailure)
		{
			if(output.get(0).heuristics == 0)	//checking if goal node is achieved
			{
				isGoal = true;
				board = output.get(0).child;
				solutionPath.add(board);
			}
			if(!isGoal)							//looping until goal node is achieved
			{
				char [][] newChild = output.get(0).child;
				// if sidewaysMove allowed-check for lesser/equal heuristic to parent,if sidewaysMove not allowed - check for lesser heuristic than parent
				if(isSideWaysMove? (output.get(0).heuristics <= parentHeuristic && sideWaysMoveCount<REPEATS) : (output.get(0).heuristics < parentHeuristic))
				{
					parentHeuristic = output.get(0).heuristics;					
					
					if(isSideWaysMove)
					{
						for(Node childList : output )
						{
							if(childList.heuristics == parentHeuristic)
								tempChild.add(childList);
						}
						
						Random arr = new Random();
						newChild = tempChild.get(arr.nextInt(tempChild.size())).child;
						tempChild.removeAll(tempChild);
					}
					
					output.removeAll(output);
					output.addAll(createChildren(newChild));	//creating all possible child
					solutionPath.add(newChild);
						
					if(isSideWaysMove && output.get(0).heuristics == parentHeuristic)
						sideWaysMoveCount++;
				}
				else
				{
					isFailure = true;
				}		
				stepCount++;
			}	
		}	
		
		if(isGoal)
		{	
			if(!isRandomRestart && x<5)
			{
				System.out.println("\nOutput " + x + " :\n");
				for(char[][] arr : solutionPath)
					arrayPrint(arr);
				int a = heuristic(solutionPath.get(solutionPath.size()-1));
				if(a==0)
					System.out.println("***Goal Achieved***");
				else
					System.out.println("***Failure***");
				System.out.println("****************************");
				solutionPath.removeAll(solutionPath);
				x++;
			}			
			successSteps += stepCount;
		}
		else
		{
			failureSteps += stepCount;
		}
	}
	/*
     *@function heuristic
     *@return { int heuristicVal} 
     * @param source {char current[][]} - input array.
     * This function gives us the heuristic of a particular configuration.
     */
	public static int heuristic(char current[][])
	{
		int heuristicVal = 0;
		for(int i=0; i<N; i++)
		{
			for(int j=0; j<N; j++)
			{
				if(current[i][j] == 'Q')
				{					
					int x=1;
					while(x < N)											//finding number of attacks
					{
						if((j+x)<N && current[i][j+x] == 'Q')				//row check
							heuristicVal++;					
						
						if((i+x)<N && (j+x)<N && current[i+x][j+x] == 'Q')	//lower diagonal check
							heuristicVal++;
						
						if((i-x)>=0 && (j+x)<N && current[i-x][j+x] == 'Q')	//upper diagonal check
							heuristicVal++;
						
						x++;
					}
				}
			}
		}
		return heuristicVal;
	}
	/*
     *@function createChildren
     *@return {List<Node>} 
     * @param source {char current[][]} - input array.
     * This function is used to generate children for the given board.
     */
			
	public static List<Node> createChildren(char current[][])
	{		
		List<Node> childList = new ArrayList<Node>();
		int pos = 0, heuristicVal = 0;
		char [][]originalArray = arrayCopy(current);
		int k = 0;
		for(int column=0; column<N; column++)
		{
			pos = findQueen(originalArray,column);
			for(int row=0; row<N; row++)
			{
				if(row != pos)		//generating the possible combinations for new child
				{
					current[row][column] = 'Q';
					while(k < N)
					{
						if(k != row)
							current[k][column] = '-';
						k++;
					}
					heuristicVal = heuristic(current);
					childList.add(new Node(current, heuristicVal));
					current = arrayCopy(originalArray);
					k = 0;
				}
			}
		}
		Collections.sort(childList, new heuristicComparator());		//sorting the children in increasing order of heuristic
		return childList;
	}
	/*
     *@function findQueen
     *@return { int rowwithQ} 
     * @param source {char current[][],int col} - input array,input column
     * This function copies returns the row with queen in the passed column
     */
	public static int findQueen(char current[][],int col)
	{
		int rowWithQ = 0;
		for(int i=0; i<N; i++)
		{
			if(current[i][col] == 'Q')
			{
				rowWithQ = i;
				break;
			}			
		}
		return rowWithQ;
	}
	
	/*
     *@function arrayCopy
     *@return {char source[][]} 
     * @param source {char} - input array
     * This function copies the array and returns another copy of the current array
     */
    public static char[][] arrayCopy(char source[][])
    {
        char [][] dest = new char[N][N];
        for(int i=0; i<source.length; i++)
            for(int j=0; j<source[i].length; j++)
                dest[i][j]=source[i][j];
        return dest;
    }
    
    /*
     *@function arrayPrint
     *@return {char source[][]} 
     * @param source {char} - input array
     * This function prints the given array in matrix format
     */
    public static void arrayPrint(char source[][])
    {
    	for(int i=0; i<source.length; i++)
        {
            for(int j=0; j<source.length; j++)
                System.out.print(source[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

}

class Node
{
	char child[][];
	int heuristics;	
	
	public Node(char child[][],int heuristics)
	{
		this.heuristics = heuristics;
		this.child = child;
	}	
	
	public int getHeuristics() {
		return heuristics;
	}
}

class heuristicComparator implements Comparator<Node>
{
	@Override
	public int compare(Node h1, Node h2)
	{
		int heuristic1 = h1.getHeuristics();
		int heuristic2 = h2.getHeuristics();
		return heuristic1 - heuristic2;
	}
}