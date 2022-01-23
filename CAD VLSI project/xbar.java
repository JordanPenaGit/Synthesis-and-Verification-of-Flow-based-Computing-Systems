import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class xbar{


    public static int[][] truthTable(int vars)
    {
        int combinations = (int)Math.pow(2,vars);
        int [][] TT = new int [combinations][vars+1];

        int rows = TT.length;
        int cols = TT[0].length-1;

        for (int i = cols - 1; i >=0; i--)
        {
            for (int j = 0; j < rows; j++)
            {
                TT[j][vars - i - 1] = (int)(j/Math.pow(2,i) % 2);
            }
        }

        
        return TT;
    }

    public static void writeMatrix(String name, int[][] matrix) throws IOException
    {
        BufferedWriter output = new BufferedWriter(new FileWriter(name));
        output.write(matrix.length + " " + matrix[0].length+ "\n");
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                output.write(matrix[i][j]+" ");
            }
            output.newLine();
        }
        output.flush();
        output.close();
    }

    public static int[][] evaluate(int[] input, int[][] matrix) throws IOException
    {
        int row = matrix.length;
        int cols = matrix[0].length;
        
        int[][] eXbar = new int[row][cols];
        
        
        for(int i = 0; i < row ; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if (matrix[i][j] < 0)
                {
                    if (input[Math.abs(matrix[i][j]) - 1] == 0)
                    {
                        eXbar[i][j] = 1;
                    }
                    else
                    {
                        eXbar[i][j] = 0;
                    }

                }
                else if (matrix[i][j] == 99)
                {
                    eXbar[i][j] = 1;
                }
                else if(matrix[i][j] == 0)
                {
                    eXbar[i][j] = 0;
                }
                else
                {
                    eXbar[i][j] = input[matrix[i][j]-1];
                }




            }

        }

        return eXbar;
    }

    public static int DFS(int start, int[][] matrix) throws IOException
    {
        int counter = 0;
        boolean [] visited = new boolean[matrix.length];
        counter = DFS(start, visited, matrix, counter);
        return counter;
    }

    private static int DFS(int node, boolean [] visited, int[][] matrix, int counter)
    {
        visited[node] = true;

        if (node == 0)
        {
            counter++;

        }
        

        for (int i = 0; i < matrix.length; i++)
        {
            if (matrix[node][i] != 0 && !visited[i])
            {
               counter= DFS(i, visited, matrix, counter);
            }

        }
        return counter;
    }



    public static void main(String[] args) throws IOException {
        int rows = 0;
        int cols = 0;
        int vars = 0;

        //Takes user input from terminal for specific crossbar file
        Scanner sc = new Scanner(new File(args[0]));
        sc.skip("vars");
        vars = sc.nextInt();
        sc.next();
        rows = sc.nextInt();
        sc.next();
        cols = sc.nextInt();

        int [][] xbarArray = new int[rows][cols];
        for (int j = 0; j < rows; j++)
        {
            for (int k = 0; k < cols; k++)
            {   
                xbarArray[j][k] = sc.nextInt();
            }

        } 
        // crossbar saved 
        System.out.println(" ");
        // saving crossbar to file
        File Array = new File("xbarmatrix");
        String name = "xbarmatrix.txt";

        // makes the file of the output
        writeMatrix(name, xbarArray);
        
        // we need to make a matrix that is equivalent to whatever the input we give it will be
        // since the xbar values depend on the 
        int [][]evaluatedMatrix = new int[rows][cols];
        int [] input = {1,0,0,0,0,1,1,1,0,1,0,1};

        for (int j = 0; j < rows; j++)
        {
            for (int k = 0; k < cols; k++)
            {   
                
                //System.out.print(evaluatedMatrix[j][k] +" ");
            }
               //System.out.println(" ");

        } // debug

        // now dfs search to see if it has an output
        // Needs to be a path from Rin to Rout. Rin will be the most bottom input on the
        // graph.
        //System.out.print("Is there a path? ");
        int res;
        res = DFS((rows-1) ,evaluatedMatrix);
        
        

        // truth table generation
        int[][] truthTable;
        int[] input2 = new int[vars];
        truthTable = truthTable(vars);

        // Now make plug values of truth table into xbar
        // and create xbar equivalent 
        for (int i = 0; i < truthTable.length; i++)
        {
            for (int j = 0; j < truthTable[0].length-1; j++)
            {
                input2[j] = truthTable[i][j];
            }
            
            
            evaluatedMatrix = evaluate(input2, xbarArray);
            truthTable[i][truthTable[0].length-1]= DFS(rows-1, evaluatedMatrix);
            
            
        }

        // writes truthtable and result to text file
        writeMatrix("TruthTableXbar.txt", truthTable);
        

    }// end of main
}