/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class FileUtility {
    private String fileName;
    
    public FileUtility(String fileName){
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }
    
    public ArrayList<String> getFromFile(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            ArrayList<String> al = new ArrayList<>();
            String line;
            while( (line = reader.readLine()) != null ){
                al.add(line);
            }
            return al;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean appendToFile(String data){
        try{
            FileWriter writer = new FileWriter(this.fileName, true);
            writer.write("\n" + data);
            writer.close();
            return true;
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean writeToFile(String data){
        try{
            FileWriter writer = new FileWriter(this.fileName);
            writer.write(data);
            writer.close();
            return true;
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}

