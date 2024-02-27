package br.ufal.ic.p2.wepayu.utils;

import java.io.*;
import java.util.Scanner;



public class Persistence {

    protected String path;


    public Persistence(String path)
    {
        this.path = path;
    }


    public void createXMLFile()
    {
        try {
            File myObj = new File((this.path.endsWith(".xml") ? this.path :this.path.concat(".xml")));
            while (!myObj.createNewFile()) {
                //System.out.println("File already exists.");
                deleteXMLFile();
            }
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createFile()
    {
        try {
            File myObj = new File(this.path);
            while (!myObj.createNewFile()) {
                //System.out.println("File already exists.");
                deleteFile();
            }
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToXMLFile(String content)
    {
        try {
            FileWriter myWriter = new FileWriter((this.path.endsWith(".xml") ? this.path :this.path.concat(".xml")));
            myWriter.write(content);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String content)
    {
        try {
            FileWriter myWriter = new FileWriter(this.path);
            myWriter.write(content);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void appendToFile(String content)
    {
        try {
            FileWriter myWriter = new FileWriter(this.path, true);
            myWriter.append(content);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String readXMLFile()
    {
        String data = "";
        try {
            File myObj = new File((this.path.endsWith(".xml") ? this.path :this.path.concat(".xml")));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = data.concat(myReader.nextLine());

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    public String readFile()
    {
        String data = "";
        try {
            File myObj = new File(this.path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = data.concat(myReader.nextLine());

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            //System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    public void readWriteToFile(String originPath) throws IOException {
        FileWriter myWriter = new FileWriter(this.path, true);
        BufferedWriter out = new BufferedWriter(myWriter);
        FileReader reader = new FileReader(originPath);
        BufferedReader myReader = new BufferedReader(reader);
        String line;
        for (line = myReader.readLine() ;line != null; line = myReader.readLine()){
            myWriter.write(line);
            myWriter.write("\n");
        }

        myReader.close();
        myWriter.close();
    }

    private void deleteXMLFile()
    {
        File myObj = new File((this.path.endsWith(".xml") ? this.path :this.path.concat(".xml")));
        if (myObj.delete()) {
            //System.out.println("Deleted the file: " + myObj.getName());
        } else {
            //System.out.println("Failed to delete the file.");
        }
    }

    private void deleteFile()
    {
        File myObj = new File(this.path);
        if (myObj.delete()) {
            //System.out.println("Deleted the file: " + myObj.getName());
        } else {
            //System.out.println("Failed to delete the file.");
        }
    }

}
