import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
/**
 * This bfs algorithm  will look for the optimal children states.
 *  
 * @author NviCoder
 *
 */

public class bfs_CloseLiset extends Algoritm {
	
	private int[][] goal;
	private Node start;
	private int openListIterations=0;
	
	
	
    	
//	public static void main(String[] args) {
//		 int goal[][] = { 
//				   {0, 1, 2, 3}, 
//				   {4, 5, 6, 7}, 
//				   {8, 9, 10, 11}};
//	int stateForNode[][] = { 
//			   	{1,0, 2, 3}, 
//			   	{4, 5, 6, 7}, 
//			   	{8, 9, 10, 11}};
//		Node walla = new Node(stateForNode,0,"");
//		start(walla,goal);
////		 int a[] = findEmptySlot(A);
////		 Node walla = new Node(A);
////		 System.out.println(nodeIdGenertor(A));
////		int gameBoard = 7;
////		String walla = Integer.toString(gameBoard)+"U";
////		walla = walla +"-"+ "Elad";
////		System.out.println(walla);
//		//System.out.println(matrixIdGenertor());
//	}
	
//******************Constructor***************************************************//
	
	public bfs_CloseLiset(int[] black, int[] red,int[] size, int[][] gameBoard) {
		super(black, red, size, gameBoard);
		int numberFillMatrixGoal = 1;
		this.goal = new int [size[0]][size[1]];
		for(int i=0;i<size[0];i++) {
			for(int j=0;j<size[1];j++) {
				if(i == size[0]-1 && j == size[1]-1 ) {
					this.goal[i][j]=0;
				}
				else {
					this.goal[i][j]=numberFillMatrixGoal;
				}
				
				numberFillMatrixGoal++;
			}
		}
		start = new Node(gameBoard,0,"NULL",this.size); // init the first Node.
		
				
	}
	
/////******************************* ALGORITHM ******************************************************////	


	/**
	 * THis is the algorithm!
	 * @param time yes or not
	 * @param open yes or not
	 * @throws IOException if there is any problem with the .txt files.
	 */
	public void start(boolean time,boolean open) throws IOException
	{
		long START = System.nanoTime();//Time
		output out = new output();
	    Queue<Node> queue = new ArrayDeque<>();
	    Hashtable<String, Node> openList = new Hashtable<String, Node>();
	    Hashtable<String, Node> closedList = new Hashtable<String, Node>();
	    //HashSet<Node> openList = new HashSet<>();
	    //HashSet<Node> closedList = new HashSet<>();
	  
	    //Add the start Node to the queue.
	    queue.add(this.start);
	    openList.put(this.start.getID(), this.start);
	    
	    while(0 != queue.size())
	    {	
	    	if(open) {
	    		openListIterations++;
	    		withOpen(openList,openListIterations);
	    	}	
	        Node vertex = queue.poll();
	        closedList.put(vertex.getID(), vertex);
	        //finding the "_" on the vertex.state -> findEmptySlot()
	        int [] EmptySlot = findEmptySlot(vertex.getState());
	        //finding the allowed sons of this vertex
	        Set<Node> sons = findSons(vertex.getState(), EmptySlot,vertex);
	        for(Node son: sons) 
	        {
	        	this.moves++;//compute the moves of the algorithm.
	        	if(!openList.containsKey(son.getID()) && !closedList.containsKey(son.getID())) 
	        	{
	        		if(isGoal(son))
		        	{	
	        			//String path = findHispathToRoot(son);
	        			//son.setPath(path);
	        			long END = System.nanoTime();
	        			long seconds = (long) ((END-START)/ 10000000.00);
	    			    out.make(findHispathToRoot(son),this.moves,findHisCostToRoot(son),seconds,time);
	    			    queue.clear();//End the while loop.
	    			    break;
		        	}
	        		
	        		queue.add(son);
    			    openList.put(son.getID(), son);
	        		
	        	}
	        	
	        }
	        
	    }
	}
	
/////*******************************END OF ALGORITHM******************************************************////	
	
	
	
/*********************************Methods*****************************************************************///
	/**
	 * Print the iteration on each 
	 * @param openList the hash table to print
	 * @param iteration which one is it. 
	 */
	private void withOpen(Hashtable<String, Node> openList, int iteration) {
		System.out.println("This is the "+iteration+" itertion");
		System.out.println("");
		int counter =1;
		for (Entry<String, Node> entry : openList.entrySet()) {
		    String key = entry.getKey();
		    Node value = entry.getValue();
		    System.out.println ("("+counter+") "+"Key: " + key + " Value: " + value.getdir());
		    counter++;
		}
		System.out.println("");
		System.out.println("*********************************************");
	}

/*******************************************************************************************************///

	/**
	 * Find the Empty slot in the matrix 
	 * @param gameboard The state
	 * @return An Array with the indexes 
	 */
	public int[] findEmptySlot(int[][]gameboard) {
		int[] indexesOfEmptySlot = new int[2];
		for(int i=0;i<size[0];i++) {
			for(int j=0;j<size[1];j++) {
				if (gameboard[i][j] == 0) {
					indexesOfEmptySlot[0] = i;
					indexesOfEmptySlot[1] = j;
				}
			}
		}
		return indexesOfEmptySlot;
	}
	
	
/*******************************************************************************************************///
	
	/**
	 * This function finding the optional operator for the current node.
	 * @param gameboard The state of the current node
	 * @param position Shows where is the blank ("_") 
	 * @param father The current node.
	 * @return A set of the sons of the current node.
	 */
	private Set<Node> findSons(int[][] gameboard, int[] position,Node father) {
	
		 Set<Node> sons = new HashSet<Node>();
		 int[][] stateForSon =  new int [size[0]][size[1]];
		 copyMatrix(stateForSon,gameboard);
		//***********EDGE CASES**************//
		 /***Corners***/
		// i=0,j=0
	        if(position[0] == 0 && position[1] == 0) { 
	        	if(!checkIfBlack(gameboard[0][1])) { // R
	        		Node son = moveIt(stateForSon,"R",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[1][0])) {//D
	        		Node son = moveIt(stateForSon,"D",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	      // i=n,j=n
	        else  if(position[0] == this.size[0]-1 && position[1] == this.size[1]-1) { 
	        	if(!checkIfBlack(gameboard[this.size[0]-2][this.size[1]-1])) { // U
	        		Node son = moveIt(stateForSon,"U",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[this.size[0]-1][this.size[1]-2])) {//L
	        		Node son = moveIt(stateForSon,"L",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	     // i=0,j=n
	        else  if(position[0] == 0 && position[1] == this.size[1]-1) { 
	        	if(!checkIfBlack(gameboard[0][this.size[1]-2])) { // L
	        		Node son = moveIt(stateForSon,"L",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[1][this.size[1]-1])) {//D
	        		Node son = moveIt(stateForSon,"D",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	     // i=n,j=0
	        else if(position[0] == this.size[0]-1 && position[1] == 0) { 
	        	if(!checkIfBlack(gameboard[this.size[0]-2][0])) { // U
	        		Node son = moveIt(stateForSon,"U",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[this.size[0]][1])) {//R
	        		Node son = moveIt(stateForSon,"R",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	        /******END Corners******/ 
	        /***Edges***/
	     // i=0,j=1...n-1
	        else if(position[0] == 0 && (position[1] != 0 && position[1] != this.size[1]-1) ) { 
	        	if(!checkIfBlack(gameboard[position[0]+1][position[1]])) { // D
	        		Node son = moveIt(stateForSon,"D",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[position[0]][position[1]-1])) {//L
	        		Node son = moveIt(stateForSon,"L",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        	if(!checkIfBlack(gameboard[position[0]][position[1]+1])) {//R
	        		Node son = moveIt(stateForSon,"R",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	        // i=1...n-1,j=0
	        else  if(position[1] == 0 && (position[0] != 0 && position[0] != this.size[0]-1) ) { 
	        	if(!checkIfBlack(gameboard[position[0]-1][position[1]])) { // U
	        		Node son = moveIt(stateForSon,"D",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[position[0]+1][position[1]])) {//D
	        		Node son = moveIt(stateForSon,"L",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        	if(!checkIfBlack(gameboard[position[0]][position[1]+1])) {//R
	        		Node son = moveIt(stateForSon,"R",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	     // i=n,j=1...n-1
	        else  if(position[0] == this.size[0]-1 && (position[1] != 0 && position[1] != this.size[1]-1) ) { 
	        	if(!checkIfBlack(gameboard[position[0]][position[1]-1])) { // L
	        		Node son = moveIt(stateForSon,"L",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[position[0]-1][position[1]])) {//U
	        		Node son = moveIt(stateForSon,"U",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        	if(!checkIfBlack(gameboard[position[0]][position[1]+1])) {//R
	        		Node son = moveIt(stateForSon,"R",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	        // i=1...n-1,j=n
	        else if(position[1] == this.size[1]-1 && (position[0] != 0 && position[0] != this.size[0]-1) ) { 
	        	if(!checkIfBlack(gameboard[position[0]-1][position[1]])) { // U
	        		Node son = moveIt(stateForSon,"U",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[position[0]][position[1]-1])) {//L
	        		Node son = moveIt(stateForSon,"L",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        	if(!checkIfBlack(gameboard[position[0]+1][position[1]])) {//D
	        		Node son = moveIt(stateForSon,"D",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	        
	        /********END Edges***************************/ 
	        
	        /***********************EDGE CASES*******************************/
	        //Simple cases!! 
	        else { 
		        if(!checkIfBlack(gameboard[position[0]-1][position[1]])) { // U
	        		Node son = moveIt(stateForSon,"U",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        		}
	        	if(!checkIfBlack(gameboard[position[0]][position[1]-1])) {//L
	        		Node son = moveIt(stateForSon,"L",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        	if(!checkIfBlack(gameboard[position[0]+1][position[1]])) {//D
	        		Node son = moveIt(stateForSon,"D",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        	if(!checkIfBlack(gameboard[position[0]][position[1]+1])) {//R
	        		Node son = moveIt(stateForSon,"R",position);
	        		sons.add(son);
	        		son.setFather(father);
	        		copyMatrix(stateForSon,gameboard);
	        	}
	        }
	        
		return sons;
	}
	
//*****************************************************************************************************************************//	
	/**
	 * This function copy one matrix to Other.
	 * 
	 * @param a Matrix A
	 * @param b Matrix B
	 */
	private void copyMatrix(int[][] a, int[][] b) {
		for(int i=0; i<a.length; i++)
			  for(int j=0; j<a[i].length; j++)
			    a[i][j]=b[i][j];
		
	}
	
//*****************************************************************************************************************************//
	/**
	 * This function moves the gameBoard for the right direction
	 * @param stateForSon The game board.
	 * @param direction Where to move the puzzles
	 * @param position The position of the blank
	 * @return
	 */
	private Node moveIt(int[][] stateForSon, String direction,int[]position) {
		// TODO Auto-generated method stub
		int[][] newState = stateForSon;
		int moveCost = 0;
		Node son;
		
		if(direction.equals("U")){ //move the puzzle down to the "_" (=blank).
			newState[position[0]][position[1]] = stateForSon[position[0]-1][position[1]]; //Move
			stateForSon[position[0]-1][position[1]]=0; // blank
			if(checkIfRed(newState[position[0]][position[1]])) {
				moveCost =  100;
			}
			else {
				moveCost =  1;
				
			}
		 son = new Node(newState,moveCost,Integer.toString(newState[position[0]][position[1]])+"D",this.size); //The direction the Node went to.	
		}
		
		else if(direction.equals("D")){//move the puzzle up to the "_" (=blank).
			newState[position[0]][position[1]] = stateForSon[position[0]+1][position[1]]; //Move
			stateForSon[position[0]+1][position[1]]=0; // blank
			if(checkIfRed(newState[position[0]][position[1]])) {
				moveCost =  100;
			}
			else {
				moveCost =  1;
				
			}
			 son = new Node(newState,moveCost,Integer.toString(newState[position[0]][position[1]])+"U",this.size); //The direction the Node went to.	
		}
		else if(direction.equals("R")){//move the puzzle left to the "_" (=blank).
			newState[position[0]][position[1]] = stateForSon[position[0]][position[1]+1]; //Move
			stateForSon[position[0]][position[1]+1]=0; // blank
			
			if(checkIfRed(newState[position[0]][position[1]])) {
				moveCost =  100;
			}
			else {
				moveCost =  1;	
			}
			 son = new Node(newState,moveCost,Integer.toString(newState[position[0]][position[1]])+"L",this.size); //The direction the Node went to.	
		}
		
		else{//if(direction.equals("L")){
			newState[position[0]][position[1]] = stateForSon[position[0]][position[1]-1]; //Move
			stateForSon[position[0]][position[1]-1]=0; // blank
			if(checkIfRed(newState[position[0]][position[1]])) {
				moveCost =  100;
			}
			else {
				moveCost =  1;
				
			}
			 son = new Node(newState,moveCost,Integer.toString(newState[position[0]][position[1]])+"R",this.size); //The direction the Node went to.	
		}
		return son;
	}
	
//*****************************************************************************************************************************//
	/**
	 * Find the path to the root from some Node v.
	 * @param v The Node
	 * @return String of the path.
	 */
	private String findHispathToRoot(Node v) {
		if (v.getFather()==null)
			return "";
		else
		return v.getdir()+"-"+ findHispathToRoot(v.getFather());
	}
//*****************************************************************************************************************************//
	/**
	 * Find How much is cost from some Node v.
	 * 
	 * @param v Node.
	 * @return Integer with the sum.
	 */
	private int findHisCostToRoot(Node v) {
			if (v.getFather()==null)
				return 0;
			else
			return v.getCost()+findHisCostToRoot(v.getFather());
	}

//*****************************************************************************************************************************//
	/**
	 * This function checks if the given puzzle piece is black!
	 * @param number The piece
	 * @return True - black. False - Not black.
	 */
	private boolean checkIfBlack(int number) {
		boolean flag = false;
		if(this.getBlack()!=null) {
			for(int i=0; i<this.getBlack().length;i++) {
				if (this.getBlack()[i]==number)
					flag = true;
			}	
		}
		return flag;
	}
//*****************************************************************************************************************************// 	
	/**
	 * This function checks if the given puzzle piece is Red!
	 * @param number  The piece
	 * @return True - black. False - Not black.
	 */
	private boolean checkIfRed(int number) {
		boolean flag = false;
		if(this.getRed()!=null) {
			for(int i=0; i<this.getRed().length;i++) {
				if (this.getRed()[i]==number)
					flag = true;
			}
		}
		return flag;
	}
	
	
//*****************************************************************************************************************************//	
	/**
	 * This function checks if the given node is The goal we looking for.
	 * @param son The Node. 
	 * @return True - This is the goal!!!. False - if not.
	 */
	private boolean isGoal(Node son) {
		boolean flag = true;
		for(int i=0;i<son.getState().length;i++) {
			for(int j=0;j<son.getState().length;j++) {
				if(son.getState()[i][j]!= this.goal[i][j])
					flag=false;
			}
		}
		return flag;
	}
	


}
