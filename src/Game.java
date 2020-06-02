import java.io.IOException;
import java.util.Arrays;

public class Game {
	
	private boolean open,time;
	
	
	public Game() {
		// TODO Auto-generated constructor stub
	}

	public static void StartTheGame(String algo,boolean time,boolean open, int[] size,int[] black,int[] red,int[][] gameBoard ) throws IOException {
		 
		if(algo.equals("BFS")) 
		{
//			bfs_CloseLiset bfs = new bfs_CloseLiset(black, red,size,gameBoard);
			System.out.println(algo);
			System.out.println("time "+time );
			System.out.println("open "+open);
			System.out.println(Arrays.toString(size));
			System.out.println("black:" +Arrays.toString(black));
			System.out.println("red: " + Arrays.toString(red));
			System.out.println("");
			bfs_CloseLiset bfs = new bfs_CloseLiset(black, red,size,gameBoard);
			bfs.start(time,open);

		}
		if(algo.equals("A_Star"))
		{
//			System.out.println(algo);
//			System.out.println("time "+time );
//			System.out.println("open "+open);
//			System.out.println(Arrays.toString(size));
//			System.out.println("black:" +black);
//			System.out.println("red: " + red);
//			System.out.println("BFS");
			A_Star a = new A_Star(black, red, size, gameBoard);
		}
	}
}
