/*
 * Avinash Vadivelu
 * axv200086
 * .004
 */

import java.io.*;
import java.util.ArrayList;

/*
 * This class uses the shortest path algorithm to print out the symbols in the path most efficient to take to complete the generated maze
 * from the previous class (MazeGenerator). 
 */

public class MazeSolver
{
    // return true if maze is solvable and construct path using shortest path algorithm
    private static boolean solveMaze(char[][] maze, int x, int y, int z)
    {
        boolean isSolveable = false;
        for (int i = 0; i < 4 && !isSolveable; i++)
            if (i != z)
            {
                switch (i)
                {
                // 0 = North, 1 = East, 2 = South, 3 = West
                // checks if neighboring cells are empty or not and solves maze accordingly
                case 0:
                    if (maze[y-1][x] == ' ')
                        isSolveable = solveMaze(maze, x, y - 2, 2);
                    break;
                case 1:
                    if (maze[y][x+1] == ' ')
                        isSolveable = solveMaze(maze, x + 2, y, 3);
                    break;
                case 2:
                    if (maze[y+1][x] == ' ')
                        isSolveable = solveMaze(maze, x, y + 2, 0);
                    break;
                case 3:
                    if (maze[y][x-1] == ' ')
                        isSolveable = solveMaze(maze, x - 2, y, 1);
                    break;
                }
            }
        // check for end condition
        if (x == 1 && y == 1)
            isSolveable = true;
        // if maze is solveable print asterisk symbols in path that solves the maze
        if (isSolveable)
        {
            // puts asterisk at the start of the maze and acts as reference point for other asterisks
            maze[y][x] = '$';
            // prints asterisks in each direction if criteria is met
            switch (z)
            {
                case 0:
                    maze[y-1][x] = '$';
                    // prints out direction N
                    System.out.print("N");
                    break;
                case 1:
                    maze[y][x+1] = '$';
                    // prints out direction E
                    System.out.print("E");
                    break;
                case 2:
                    maze[y+1][x] = '$';
                    // prints out direction S
                    System.out.print("S");
                    break;
                case 3:
                    maze[y][x-1] = '$';
                    // prints out direction W
                    System.out.print("W");
                    break;
            }
        }
        // result of if maze is solveable or not
        return isSolveable;
    }

    // helper function to acutally solve the maze and draw the path.
    private static void solveMaze(char[][] maze)
    {
        System.out.print("Directions that maze takes is: ");
        solveMaze(maze, maze[0].length - 2, maze.length - 2, -1);
    }

    // changes the width size of each box to equal vertical/height size
    private static char[][] organizeMazeHorizontally(String[] rows)
    {
        // horizontal length of each box of maze cut in half
        final int width = (rows[0].length() + 1) / 2;
        char[][] c = new char[rows.length][width];
        // traverses through each individual box and resizes width of all of them to equal height of each box
        for (int i = 0; i < rows.length; i++)
            for (int j = 0; j < width; j++)
                c[i][j] = rows[i].charAt(j * 2);
        return c;
    }

    // adds aproprate spacing between each asterisk in the solved maze path
    private static String[] spacingBetweenAsterisks(char[][] maze)
    {
        char[] box = new char[3];
        String[] solvedPath = new String[maze.length];
        for (int i = 0; i < maze.length; i++)
        {
            StringBuilder sB = new StringBuilder(maze[i].length * 2);
            // traverses through each paved path in maze
            for (int j = 0; j < maze[i].length; j++)
            {
                if (j % 2 == 0)
                    sB.append(maze[i][j]);
                else
                {
                    // adds extra space in each box that is occupied by an asterisk
                    box[0] = box[1] = box[2] = maze[i][j];
                    if (box[1] == '$')
                        box[0] = box[2] = ' ';
                    sB.append(box);
                }
            }
            solvedPath[i] = sB.toString();
        }
        return solvedPath;
    }

    // reads maze generated previous output file to solve 
    private static String[] readGenerateMazeFile(InputStream f) throws IOException
    {
        BufferedReader bR = new BufferedReader (new FileReader("maze.txt")); 
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        while ((line = bR.readLine()) != null)
            lines.add (line);
        return lines.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException
    {
        InputStream iS = (args.length > 0 ? new FileInputStream (args[0]) : System.in);
        // organizes maze output display
        String[] lines = readGenerateMazeFile(iS);
        char[][] maze = organizeMazeHorizontally(lines);

        // generates maze path using input of generated maze and writes output into a new file
        solveMaze (maze);
        String[] solvedPath = spacingBetweenAsterisks(maze);
        File mazeOutFile = new File("mazeOutput.txt");
        mazeOutFile.createNewFile();
        PrintWriter outFil = new PrintWriter(mazeOutFile);

        System.out.println();

        for (int i = 0; i < solvedPath.length; i++)
        {
            System.out.println(solvedPath[i]);
            outFil.println(solvedPath[i]);
        }
        // closes file
        outFil.close();
    }
}
