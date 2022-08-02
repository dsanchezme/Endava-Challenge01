import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
// import java.util.LinkedList;
import java.util.List;
// import java.util.Queue;

// import javafx.util.Pair;

public class App {

    // Number of rows of the matrix
    private static int m;
    // Number or columns of the matrix
    private static int n;
    // Matrix
    // private static List<List<Integer>> matrix;
    private static int matrix[][];
    // Longest Path lengths
    private static HashMap<List<Integer>, Integer> pathLengths = new HashMap<>();
    // Best paths
    // private static HashMap<List<Integer>, List<List<Integer>>> bestPaths = new HashMap<>();
    //Each Possible
    private static HashMap<List<Integer>, List<List<Integer>>> possibles = new HashMap<>();
    //Longest paths
    // private static List<List<List<Integer>>> longestPaths;
    // Initial cells
    private static List<List<Integer>> initPoints = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        readFile();
        System.out.println("Rows: " + m);
        System.out.println("Columns: " + n);

        fillPossibles();

        // Print all-possible-movements dictionary
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String toPrint = Arrays.asList(i,j) + " : ";
                for (List<Integer> movement : possibles.get(Arrays.asList(i,j))) {
                    toPrint += movement + " ";
                }
                System.out.println(toPrint);
            }
        }
        
        System.out.println("---");

        // Fill and print lengths for longest path from each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> key = Arrays.asList(i,j);
                pathLength(key);
                System.out.println(key + " : " + pathLengths.get(key));
            }
        }

    }



    // https://devqa.io/java-read-files/
    static void readFile() throws IOException{
        String filePath = "map-test.txt";
        Path path = Paths.get(filePath);
        BufferedReader bufferedReader = Files.newBufferedReader(path);
        
        
        String[] sizesLine = bufferedReader.readLine().split(" ");
        m = Integer.parseInt(sizesLine[0]); 
        n = Integer.parseInt(sizesLine[1]); 

        String[] line;
        matrix = new int[m][n];

        for (int i = 0; i < m; i++) {
            line = bufferedReader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(line[j]);
            }
        }

        bufferedReader.close();
    }

    // Return a list of all possible movements for a given cell
    static List<List<Integer>> possibleMovements(int i, int j){

        List<List<Integer>> allowedMovements = Arrays.asList(Arrays.asList(i-1,j), Arrays.asList(i+1,j), Arrays.asList(i,j-1), Arrays.asList(i,j+1));
        
        List<List<Integer>> result = new ArrayList<>();

        if(matrix[i][j] == -1){
            return Collections.<List<Integer>>emptyList();
        }

        for (List<Integer> movement : allowedMovements) {
            int x = movement.get(0);
            int y = movement.get(1);
            
            if( x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] != -1 && matrix[x][y] > matrix[i][j]){
                result.add(movement);
            }
        }

        return result;
    }

    // Fills possibles dictionary
    static void fillPossibles(){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> pair = Arrays.asList(i,j);
                List<List<Integer>> movements = possibleMovements(i, j);
                possibles.put(pair, movements);
                if(movements.size() > 0){
                    initPoints.add(pair);
                }
            }
        }
    }

    // Fills pathLengths dictionary with the lengths of the longest path from each cell
    static int pathLength(List<Integer> current){

        if( pathLengths.keySet().contains(current)){
            return pathLengths.get(current);
        }

        int longest;
        if (matrix[current.get(0)][current.get(1)] != -1) {
            longest = 1;
        }else{
            longest = 0;
        }

        for (List<Integer> movement : possibleMovements(current.get(0), current.get(1))) {
            int curLength = pathLength(movement) + 1;
            if(curLength > longest){
                longest = curLength;
            }
        }
        
        pathLengths.put(current, longest);
        return longest;
    }

    // static Pair<Integer, List<List<Integer>>> dfsTest(List<Integer> current){

    //     if( pathLengths.keySet().contains(current)){
    //         return new Pair<>(pathLengths.get(current), bestPaths.get(current));
    //     }

    //     List<List<Integer>> bestPath = new ArrayList<>();
    //     int longest;
    //     if (matrix[current.get(0)][current.get(1)] != -1) {
    //         longest = 1;
    //     }else{
    //         longest = 0;
    //     }

    //     for (List<Integer> movement : possibleMovements(current.get(0), current.get(1))) {
    //         Pair<Integer, List<List<Integer>>> temp = dfsTest(movement);
    //         int curLength = temp.getKey() + 1;
    //         List<List<Integer>> path = temp.getValue();
            
    //         if(curLength > longest){
    //             longest = curLength;
    //             bestPath = path;
    //         }
    //     }
        
    //     pathLengths.put(current, longest);
    //     List<List<Integer>> aux = new ArrayList<>(Arrays.asList(current));
    //     aux.addAll(bestPath);
    //     System.out.print(current + " : ");
    //     for (List<Integer> item : bestPath) {
    //         System.out.print(item + " ");
    //     }
    //     System.out.println("");
    //     bestPaths.put(current, bestPath);
    //     return new Pair<Integer, List<List<Integer>>>(longest, aux);
    // }

}
