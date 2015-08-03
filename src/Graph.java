import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by Alex on 8/1/2015.
 */
public class Graph {

    public byte[][] matrix;
    public int size;

    public Graph(String filename){

        readGraphFile(filename);
    }

    public void print(){

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){

                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }
    }

    private void readGraphFile(String filename){

        String nextLine;

        try{

            BufferedReader inFile = new BufferedReader(new FileReader(filename));
            StringTokenizer lineToParse;

            //Read the size of the graph
            nextLine  = inFile.readLine();
            size = Integer.parseInt(nextLine);
            matrix = new byte[size][size];

            for(int i = 0; i < size; i++){

                nextLine = inFile.readLine();
                lineToParse = new StringTokenizer(nextLine, " ");

                for(int j = 0; j < size; j++){

                    matrix[i][j] = Byte.parseByte(lineToParse.nextToken());
                }
            }
        }

        catch(FileNotFoundException fnfe)   { fnfe.printStackTrace(); }
        catch(IOException ioe)              { ioe.printStackTrace(); }
    }
}
