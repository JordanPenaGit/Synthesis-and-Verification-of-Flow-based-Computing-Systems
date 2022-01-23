import java.util.Scanner;
import java.io.*;
import java.io.File;
import java.util.*;

public class BDD
{
    //int [][]bddArray;
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

   static int evaluateBDD(int ID, int [] inputVector, int[][] bddArray)
    {
        int row = ID - 1;
        //System.out.println(ID);
        if (bddArray[row][1] == -1 && bddArray[row][2] == -1)
        {
            return bddArray[row][3];
        }

        if (inputVector[bddArray[row][3] - 1] == 1)
        {
            return evaluateBDD(bddArray[row][1], inputVector,bddArray);
        }
        else
        {
            return evaluateBDD(bddArray[row][2], inputVector,bddArray);
        }
        

    }

/*  var2.bdd
    vars 2
    nodes 5
    1 2 3 1
    2 4 5 2
    3 5 4 2
    4 -1 -1 0
    5 -1 -1 1*/

    public static void main(String[] args) throws IOException
    {
       
        
        //read in specific file from user input in terminal
        Scanner sc = new Scanner(new File(args[0]));
        int vars, nodes;
        sc.skip("vars");
        vars = sc.nextInt();
        sc.next();
        nodes = sc.nextInt();

        int [][] bddArray = new int[nodes][4];


        System.out.println("vars "+vars);
        System.out.println("nodes "+nodes);
        for (int j = 0; j < nodes; j++)
        {
            for (int k = 0; k < 4; k++)
            {
                
                bddArray[j][k] = sc.nextInt();

            }        
        }
        sc.close();
        // Printing stored BDD 

        for (int i = 0; i < nodes; i ++)
        {
            for (int j = 0; j < 4 ; j++)
            {
                System.out.print(bddArray[i][j]+" ");
            }
            System.out.println("");
        }
        int i =0, j=0;
        
        //generate truth table
        int combinations = (int)Math.pow(2,vars);
        int [][] truthtable = new int [combinations][vars];
        int rows = truthtable.length;
        int cols = truthtable[0].length;
        for (int k = cols - 1; k >=0; k--)
        {
            for (i = 0; i < rows; i++)
            {
                truthtable[i][vars - k -1] = (int)(i/Math.pow(2, k)) % 2;
            }
        }

        int[][] matrix = new int [rows][cols+1];

        for ( i = 0; i < rows; i++)
        {
            for (j = 0; j <truthtable[0].length; j++)
            {
                matrix[i][j] = truthtable[i][j];
            }
        }

        for (i = 0; i < rows; i++)
        {
            matrix[i][cols] = evaluateBDD(1, truthtable[i], bddArray);
        }
        
        for(i = 0; i < rows; i++)
        {
            //System.out.println(Arrays.toString(matrix[i]));
        }

        // Save truth table to text file
        writeMatrix("TruthTableBDD.txt",matrix);
        
    } 




}