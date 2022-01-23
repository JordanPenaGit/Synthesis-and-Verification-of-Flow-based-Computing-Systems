import java.util.Scanner;
import java.io.*;

public class evaluate
{
    public static void main(String[] args ) throws IOException
    {
        Scanner sc1 = new Scanner(new File("TruthTableXbar.txt"));
        Scanner sc2 = new Scanner(new File("TruthTableBDD.txt"));

        int row1, row2;
        int col1, col2;
        row1 = sc1.nextInt();
        col1 = sc1.nextInt();
        row2 = sc2.nextInt();
        col2 = sc2.nextInt();
        int []xbarArray = new int[row1];
        int []bddArray = new int[row2];
        //debug
        //System.out.println(row1 + " "+ row2);
        //System.out.println(col1+ " "+ col2);

        if (row1 != row2)
        {
            System.out.println("The representations are not equivalent!");
        }
        else
        {
            for (int i = 0; i < row1; i++)
            {
                xbarArray[i] = sc1.nextInt();
                bddArray[i] = sc2.nextInt();
                if (xbarArray[i] != bddArray[i])
                {
                    System.out.println("The representations are not equivalent!");
                    return;
                }

            }
            
            System.out.println("The representations are equivalent!");

        }

    }

}