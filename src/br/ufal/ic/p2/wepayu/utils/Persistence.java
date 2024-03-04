package br.ufal.ic.p2.wepayu.utils;

import java.io.*;



public class Persistence {

    protected String path;


    public Persistence(String path)
    {
        this.path = path;
        File file = new File(path);
        if (file.exists()) if (file.length()>0) file.delete();
    }

    public void writeToFile(String content)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path,true));
            writer.write(content+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFrom(String fromPath)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fromPath));
            String content;
            while(true)
            {
                content = reader.readLine();
                if (content == null) break;
                this.writeToFile(content);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
