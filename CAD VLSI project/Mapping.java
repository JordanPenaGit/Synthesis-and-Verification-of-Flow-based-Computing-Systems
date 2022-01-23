import java.util.Scanner;
import java.io.*;
import java.io.File;
import java.util.*;

public class Mapping{

    public static void writeMatrix(String name, int[][] matrix,int vars) throws IOException
    {
        BufferedWriter output = new BufferedWriter(new FileWriter(name));
        output.write("vars "+vars+"\n");
        output.write("rows "+matrix.length + "\n" +"cols " +matrix[0].length+ "\n");
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


    public static void main(String[] args) throws IOException
    {
        Scanner sc = new Scanner(new File(args[0]));
        int vars, nodes;
        sc.skip("vars");
        vars = sc.nextInt();
        sc.next();
        nodes = sc.nextInt();
        int memresistors = 0;
        
        int [][] bddArray = new int[nodes][4];
        //System.out.println("vars "+vars);
        System.out.println("nodes "+nodes);
        int edges = 0;
        int forbidden=0;
        for (int j = 0; j < nodes; j++)
        {
            for (int k = 0; k < 4; k++)
            {
                bddArray[j][k] = sc.nextInt();
                System.out.print(bddArray[j][k]); //debug
            }        
            System.out.println("");
        }


        int on=0;
        int rows = bddArray.length;
        int cols = bddArray[0].length;
        if (bddArray[rows-1][cols-1] == 0)
        {
            forbidden = bddArray[rows-1][0];
        }
        if(bddArray[rows-2][cols-1] == 0)
        {
            forbidden = bddArray[rows-2][0];
        }
        if (bddArray[rows-1][cols-1] == 1)
        {
            on = bddArray[rows-1][0];
        }
        if(bddArray[rows-2][cols-1] == 1)
        {
            on = bddArray[rows-2][0];
        }
        //System.out.println(on+" on");

        //System.out.println("forbidden: "+forbidden);
        
        for (int i = 0; i < nodes-2; i ++)
        {
            for (int j = 1; j < 3 ; j++)
            {
                //System.out.print(bddArray[i][j]+" ");
                if (bddArray[i][j] != forbidden)
                {
                    edges++;
                }
            }
            //System.out.println("");
        }
        System.out.println("edges "+edges);
        int[][] xbar = new int[nodes-1][edges]; 
        System.out.println("xbar size: "+xbar.length+" "+ xbar[0].length);
        //System.out.println("size row : "+rows+ " "+ bddArray[0].length);
        //mapping starts here
         System.out.println("");
        System.out.println("===================");
        int g = 0;
        
        int last = xbar.length - 1;
        System.out.println(xbar.length);

        for (int i = 0 ; i < xbar.length ; i++)
        {
            if(bddArray[i][0] == forbidden) // this is fine
            {
                continue;
            }
                
            int left = bddArray[i][1];
            int right = bddArray[i][2];
            int first = bddArray[i][0];
            int id = bddArray[i][3];
            //System.out.println("*first: "+first+" left: "+left +" right: "+ right+" at i: "+ i+" bdd[0] is:"+ bddArray[i][3]);

            if (left != forbidden && right != forbidden)
            {
                if (g == 1)
                {
                    g = 0;
                }
                xbar[first-1][i] = 99;
                xbar[left-1][i] = id;
                xbar[first-1][i+1] = 99;
                xbar[right-1][i+1] = -id;
                g = 1;
               memresistors += 2;
                
                continue;
            }

            if (left == forbidden)
            {
                if (on > forbidden && right == on) 
                {
                    right = xbar.length;
                }
                xbar[first-1][i+g] = 99;
                xbar[right-1][i+g] = -id;
                memresistors += 1;
              
                continue;
            }

            if(right == forbidden)
            {
                
                if(on > forbidden && left == on)
                {
                    left = xbar.length;
                }

                xbar[first-1][i+g] = 99;
                xbar[left-1][i+g] = id;
                memresistors += 1;
                
      
            
            continue;
            }
            
        }

        for (int h = 0; h < xbar.length; h++)
        {
            for (int k = 0; k < xbar[0].length; k++)
            {
                System.out.print(xbar[h][k]+" ");
            }           
            System.out.println("");
        }

        
        System.out.println("memresistors: "+memresistors +" rows: "+rows+ " cols: "+cols);
        writeMatrix(args[1], xbar,vars);

        sc.close();

        
        //xbar = map(bddArray);


        return;
    }



}