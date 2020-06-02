import java.util.Arrays;

public class Algoritm {
	
	private double cost;
	private int[] black;
	private int[] red;
	protected int[] size;
	private int[][] gameBoard;
	protected int moves=0;
	
	
	public Algoritm( int[]  black,  int[]  red,int[] size,int[][] gameBoard) {
		this.black = black;
		this.red = red;
		this.size = size;
		this.gameBoard = gameBoard;
	}


	//compute the cost of the algo
	public void computeCost(String str) 
	{ 
		//cost = 100;
	}
	


	public double getCost() {
		return cost;
	}


	public int[] getBlack() {
		return black;
	}


	public int[] getRed() {
		return red;
	}
	
	
	//
}
	
