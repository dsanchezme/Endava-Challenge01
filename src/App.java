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
    // Best paths
    private static HashMap<List<Integer>, List<List<Integer>>> bestPathEachCell = new HashMap<>();
    //Longest paths
    private static List<List<List<Integer>>> longestPaths  = new ArrayList<>();
    //Best length
    private static int bestLength;
    //Best length
    private static List<List<Integer>> winnerPath;


    public static void main(String[] args) throws Exception {
        readFile();
        // System.out.println("Rows: " + m);
        // System.out.println("Columns: " + n);

        // Fill and print lengths for longest path from each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> key = Arrays.asList(i,j);
                findPath(key);
                // System.out.println(key + " : " + bestPathEachCell.get(key));
            }
        }

        // System.out.println("---");
        
        selectWinnerPath();
        printOutput();

        // System.out.println("\n-------------");
        // System.out.println("-- Details --");
        // System.out.println("-------------\n");
        // printOutputDetails();

    }

    // https://devqa.io/java-read-files/
    static void readFile() throws IOException{
        String filePath = "map.txt";
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

    static List<List<Integer>> findPath(List<Integer> current){

        if( bestPathEachCell.keySet().contains(current)){
            return bestPathEachCell.get(current);
        }

        List<List<Integer>> bestPath;
        if (getValue(current) != -1) {
            bestPath = new ArrayList<>(Arrays.asList(current));
        }else{
            bestPath = new ArrayList<>();
        }

        for (List<Integer> movement : possibleMovements(current.get(0), current.get(1))) {
            List<List<Integer>> curPath = new ArrayList<>(Arrays.asList(current));
            curPath.addAll(findPath(movement));
            if(curPath.size() >= bestPath.size()){
                bestPath = curPath;
                if(bestPath.size() >= bestLength){
                    if(bestPath.size() > bestLength){
                        longestPaths = new ArrayList<>();
                    }
                    bestLength = bestPath.size();
                    longestPaths.add(bestPath);
                }

            }
        }
        
        bestPathEachCell.put(current, bestPath);
        return bestPath;
    }

    static void selectWinnerPath(){
        int maxVerticalDistance = 0;
        List<List<Integer>> winner = new ArrayList<>();
        for (List<List<Integer>> path : longestPaths) {
            List<Integer> start = path.get(0);
            List<Integer> end = path.get(path.size() - 1);
            int verticalDistance = getValue(end) - getValue(start);
            if(verticalDistance > maxVerticalDistance){
                maxVerticalDistance = verticalDistance;
                winner = path;
            }
        }
        longestPaths.remove(winner);
        winnerPath = winner;
    }
    
    static void printOutput(){
        System.out.println("Steepest path length: " + bestLength);
        System.out.println("List of paths:");

        for (List<Integer> step : winnerPath) {
            System.out.print(getValue(step));
            if(step != winnerPath.get(winnerPath.size() - 1)){
                System.out.print("-");
            }
        }
        System.out.println("");
        for (List<List<Integer>> path : longestPaths) {
            for (List<Integer> step : path) {
                System.out.print(getValue(step));
                if(step != path.get(path.size() - 1)){
                    System.out.print("-");
                }
            }
            System.out.println("");
        }
    }

    static void printOutputDetails(){
        System.out.println(winnerPath);
        for (List<List<Integer>> path : longestPaths) {
            System.out.println(path);    
        }
    }

    static int getValue(List<Integer> cell){
        return matrix[cell.get(0)][cell.get(1)];
    }

}
