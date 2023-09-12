/*
 * Avinash Vadivelu
 * axv200086
 * .004
 */

import java.util.Collections;
import java.util.Arrays;
import java.io.PrintWriter; 
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

/*
 * This class generates a maze by randomly inserting horizontal or vertical lines within a bordered box
 */

public class MazeGenerator 
{
	private final int rows;
	private final int columns;
	private final int[][] maze;

	public MazeGenerator(int x, int y) 
    {
		rows = x;
		columns = y;
		maze = new int[rows][columns];
		generateMaze(0, 0);
	}

    // creates random walls in each direction to create a maze
	private void generateMaze(int row, int column) 
    {
        // creates array of enum values with each directions
		DIRECTION[] dirs = DIRECTION.values();
        // randomizes the direction (horizontal or vertical) of walls to be placed in maze
		Collections.shuffle(Arrays.asList(dirs));
		for (DIRECTION dir : dirs) 
        {
			int x = row + dir.xDirect;
			int y = column + dir.yDirect;
            // checks if x and y are within range to be included in the maze
			if (between(x, rows) && between(y, columns) && (maze[x][y] == 0)) 
            {
				maze[row][column] += dir.bitNum;
				maze[x][y] += dir.opposite.bitNum;
				generateMaze(x,y);
			}
		}
	}

    // returns true if within boundary else returns false
	private static boolean between(int lower, int upper) 
    {
		return (lower >= 0) && (lower < upper);
	}

	private enum DIRECTION 
    {
        // each direction is encoded with a bit number for identification
		North(1, 0, -1), South(2, 0, 1), East(4, 1, 0), West(8, -1, 0);
		private final int bitNum;
		private final int xDirect;
		private final int yDirect;
		private DIRECTION opposite;

        // constructor for Direction enumerator
        private DIRECTION(int bitNum, int xDirect, int yDirect) 
        {
			this.bitNum = bitNum;
			this.xDirect = xDirect;
			this.yDirect = yDirect;
		}

		// establishes relations of directions with other types of direction
		static 
        {
			North.opposite = South;
			South.opposite = North;
			East.opposite = West;
			West.opposite = East;
		}
	};

    // creates the architecture of maze, which is then sent/written to a txt file for solving
	public void output() throws IOException
    {
        String output = "";
		for (int i = 0; i < columns; i++) 
        {
			// creates most northern section of maze
			for (int j = 0; j < rows; j++) 
            {
                if((maze[j][i] & 1) == 0)
                {
                    System.out.print("+---");
                    output += "+---";
                }
                else 
                {
                    System.out.print("+   ");
                    output += "+   ";
                }
			}
			System.out.println("+");
            output += "+\n";
			// creates most western section of maze
			for (int j = 0; j < rows; j++) 
            {
                if((maze[j][i] & 8) == 0)
                {
                    System.out.print("|   ");
                    output += "|   ";
                }
                else
                {
                    System.out.print("    ");
                    output += "    ";
                }
			}
			System.out.println("|");
            output += "|\n";
		}
		// creates most southern section of maze
		for (int j = 0; j < rows; j++) 
        {
			System.out.print("+---");
            output += "+---";
		}
		System.out.println("+");
        output += "+";

        // final result of maze generation is transfered to a txt file
        File mazeFile = new File("maze.txt");
        mazeFile.createNewFile();
        PrintWriter outFil = new PrintWriter(mazeFile);
        outFil.print(output);
        outFil.close();
	}

    // used for generating a maze with a click of a button
    public void Simulation()
    {
		for (int i = 0; i < columns; i++) 
        {
			// draw the north edge
			for (int j = 0; j < rows; j++) 
            {
                if((maze[j][i] & 1) == 0)
                {
                    System.out.print("+---");
                }
                else 
                {
                    System.out.print("+   ");
                }
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < rows; j++) 
            {
                if((maze[j][i] & 8) == 0)
                {
                    System.out.print("|   ");
                }
                else
                {
                    System.out.print("    ");
                }
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < rows; j++) 
        {
			System.out.print("+---");
		}
		System.out.println("+");
	}

	public static void main(String[] args) throws IOException
    {
        Scanner input = new Scanner(System.in);

        // asks for user input for sizes of row and column
        System.out.println("Enter horizontal size: ");
        int horizontalSize = input.nextInt();

        System.out.println("Enter vertical size: ");
        int verticalSize = input.nextInt();

        // object for maze generator to display maze
		MazeGenerator maze = new MazeGenerator(verticalSize, horizontalSize);
        maze.output();
	}
}
