package br.ufal.ic.p2.wepayu;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Persistence {

    protected String path;


    public Persistence(String path)
    {
        this.path = path;
    }


    public String getPath() {
        return path;
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

    private void deleteXMLFile()
    {
        File myObj = new File((this.path.endsWith(".xml") ? this.path :this.path.concat(".xml")));
        if (myObj.delete()) {
            //System.out.println("Deleted the file: " + myObj.getName());
        } else {
            //System.out.println("Failed to delete the file.");
        }
    }

}
