import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class App {

    // Number of rows of the matrix
    private static int m;
    // Number or columns of the matrix
    private static int n;
    // Matrix
    // private static List<List<Integer>> matrix;
    private static int matrix[][];
    // Counter
    private static int counter[][];
    //Each Possible
    private static HashMap<List<Integer>, List<List<Integer>>> possibles = new HashMap<>();
    //Longest paths for each cell
    private static HashMap<List<Integer>, List<List<List<Integer>>>> allPaths = new HashMap<>();




    public static void main(String[] args) throws Exception {
        readFile();
        System.out.println("Rows: " + m);
        System.out.println("Columns: " + n);

        fillPossibles();

        // for (List<Integer> key : possibles.keySet()) {
        //     List<Integer> posMinValue = new ArrayList<>();
        //     int minValue = 0;
        //     for (List<Integer> movement : possibles.get(key)) {
                
        //     }
        
        // }


        // 

        // Print all-possible-movements dictionary
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String toPrint = Arrays.asList(i,j) + " : ";
                for (List<Integer> movement : possibles.get(Arrays.asList(i,j))) {
                    toPrint += "[" + movement.get(0) + "," + movement.get(1) + "] ";
                }
                System.out.println(toPrint);
            }
        }
        System.out.println("---");

        // findAllPathsFromACell(Arrays.asList(0,2));

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> key = Arrays.asList(i,j);
                if( allPaths.keySet().contains(key) ){
                    for (List<List<Integer>> path : allPaths.get(key)) {
                        System.out.print(key + " : ");
                        for (List<Integer> cell : path) {
                            System.out.print(cell + " ");
                        }
                        System.out.println("");
                    }
                }
            }
        }

        // for (List<Integer> key : possibles.keySet()) {
        //     String toPrint = "[" + key.get(0) + "," + key.get(1) + "] : ";
        //     for (List<Integer> movement : possibles.get(key)) {
        //         toPrint += "[" + movement.get(0) + "," + movement.get(1) + "] ";
        //     }
        //     System.out.println(toPrint);
        // }
    }



    // https://devqa.io/java-read-files/
    static void readFile() throws IOException{
        String filePath = "../map-test.txt";
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

    // static void setLengths(){
    //     for (int i = 0; i < m; i++) {
    //         for (int j = 0; j < n; j++) {
    //             for (List<Integer> movement : possibleMovements(i, j)) {
    //                 counter[movement.get(0)][movement.get(1)] =+ 1;
    //             }
    //         }
    //     }
    // }

    static void fillPossibles(){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> pair = Arrays.asList(i,j);
                possibles.put(pair, possibleMovements(i, j));
            }
        }
    }

    // static int getPathLenght(int i, int j){
    //     if(possibleMovements(i, j).size() == 0){
    //         return 1;
    //     }
    //     int temp = 0;
    //     for (List<Integer> movement : possibleMovements(i, j)) {
    //         temp += getPathLenght(movement.get(0), movement.get(1));
    //     }
    //     return temp;
    // }

    // static List<List<List<Integer>>> findAllPathsFromACell(List<Integer> current){
    //     // List<Integer> current = Arrays.asList(i,j);
        
    //     if( allPaths.keySet().contains(current) ){
    //         return allPaths.get(current);
    //     }

    //     List<List<List<Integer>>> result = new ArrayList<>();
    //     for (int i = 0; i < possibles.get(current).size(); i++) {
    //         List<Integer> cell = possibles.get(current).get(i);
    //     // for (List<Integer> cell : possibles.get(current)) {
    //         // temp.addAll(findAllPathsFromACell(cell));
    //         // System.out.println(cell);
            
    //         result.add(Arrays.asList(cell));
            
    //         for (List<List<Integer>> list : findAllPathsFromACell(cell)) {
    //             result.set(i, Arrays.asList(cell).addAll(list));
    //         }
    //             // List<List<List<Integer>>> list = findAllPathsFromACell(cell);
    //         // for (int i = 0; i < list.size(); i++) {
    //         //     result.set(i, Arrays.asList(cell).addAll(list.get(i)));
            
    //         // }
    //         // for (List<List<Integer>> list : findAllPathsFromACell(cell)) {
    //         //     list.add(0, cell);
    //         //     temp.add(list);
    //         //     result.get(0).add(e)
    //         // }
    //         // temp.addAll(allPaths.get(cell));
    //     }

    //     allPaths.put(current, result);

    //     for (List<List<Integer>> path : result) {
    //         System.out.println(path.size());
    //     }      

    //     return result;


    // }

    // static List<List<List<Integer>>> findAllPaths(int i, int j){
    //     if(possibleMovements(i, j).size() == 0){
    //         return Collections.<List<List<Integer>>>emptyList();
    //     }
    //     for (List<Integer> movement : possibleMovements(i, j)) {
            
    //     }
    // }
}
