import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import java.util.stream.*;
import java.util.Random;
import java.util.List;

/* There are F files on a machine, each containing some N values in random order. 
Sort all the data in all the files and write it back to the disk in sorted order. 
In memory you can store a smaller subset of all the data at once, say O(N), e.g., the content of 1-2-3 files. 
This means you read the data from some files, do a partial in memory sort, then write the data to the disk. 
The free disk space available is also limited, i.e., you can store F more files containing N values each (same size as the input data for the problem).
Devise an algorithm to sort all the values in all the files. Discuss space/time complexity.
Extend the problem for the case when each one of M machines stores F files containing N values */
public class SortingMachine {
    public static final String storagePathName = "/Users/irislitiu/work/misc-coding-challenges/ProgrammingClubChallenges/allFileData";
    public static final String randomFileDirName = "/Users/irislitiu/work/misc-coding-challenges/ProgrammingClubChallenges/FileDataGen/";
    private File[] files;
    private int numMachines;

    public SortingMachine(File[] files) {
        this.files = files;
    }

    public SortingMachine(File[] files, int numMachines) {
        this.files = files;
        this.numMachines = numMachines;
    }

    public void writeToDisk(PriorityQueue<Double> sortedValues) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(SortingMachine.storagePathName));
            for (double val : sortedValues) {
                writer.write(String.valueOf(val));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortFiles(File[] allFiles, int maxSubsetSize) {
        PriorityQueue<Double> sortedValues = new PriorityQueue<>();
        int curSubsetSize = 0;

        for (File file : allFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                for (String line : reader.lines().collect(Collectors.toList())) {
                    if (curSubsetSize > maxSubsetSize) {
                        writeToDisk(sortedValues);
                    }
                    sortedValues.add(Double.parseDouble(line));
                    curSubsetSize++;
                } 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!sortedValues.isEmpty()) {
            writeToDisk(sortedValues);
        }
    }

    //using threads to simulate separate machines
    public void sortFilesAcrossMachines(int maxSubsetSize) {
        int numFilesPerMachine = (int) Math.floor(files.length/numMachines);
        int numExtra = files.length % numMachines;
        int curFileIndex = 0;

        Thread[] machines = new Thread[numMachines];
        for (int i = 0; i < machines.length; i++) {
            int startIndex = curFileIndex+1;
            int endIndex = startIndex + numFilesPerMachine;
            if (i < numExtra) {
                endIndex++;
            }
            curFileIndex = endIndex;
            machines[i] = new Thread(() -> {
                    sortFiles(new File[]{}, maxSubsetSize);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (Thread machine : machines) {
            machine.start();
        }

        try {
            for (Thread machine : machines) {
                machine.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All machines done!");
    }

    public static void generateRandomFiles(int numFiles) {
        Random r = new Random();
        for (int i = 0; i < numFiles; i++) {
            try {
                BufferedWriter w = new BufferedWriter(new PrintWriter(SortingMachine.randomFileDirName + r.nextInt((int) 1e10)));
                int numberOfRandomNumbers = 1000;
                for (int j = 0; j < numberOfRandomNumbers; j++) {
                    int randomNumber = r.nextInt((int) 1e10);
                    w.write(randomNumber + "\n");
                }
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        String directoryPath = SortingMachine.randomFileDirName;

        File[] allFiles;
        try {
            allFiles = new File[Files.walk(Paths.get(directoryPath)).toList().size()];
        } catch (IOException e) {
            allFiles = new File[]{};
            e.printStackTrace();
        }
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            Stream<Path> files = paths.filter(Files::isRegularFile);
            List<Path> fileList = files.collect(Collectors.toList());
            for (int i = 0; i < fileList.size(); i++) {
                allFiles[i] = fileList.get(i).toFile();
                System.out.println(fileList.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SortingMachine m = new SortingMachine(allFiles);
        m.sortFiles(allFiles, 5);
    }
    
}
