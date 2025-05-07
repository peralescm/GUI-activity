


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
import java.util.Scanner;
import javax.swing.JOptionPane;
public class WriteFile {
    public static void main(String[] args) {
       FileUtility fu = new FileUtility("C:/data/data.txt");
       
        OUTER:
        for (;;) {
            System.out.println("\n\n\n\nMENU");
            System.out.println("1. New Student");
            System.out.println("2. List Student");
            System.out.println("3. Exit");
            System.out.print("Type here: ");
            Scanner scan = new Scanner(System.in);
            int i = scan.nextInt();
            scan.nextLine();
            switch (i) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = scan.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scan.nextLine();
                    fu.appendToFile(id+"/"+name);
                    break;
                case 2:
                    ArrayList<String> list = (ArrayList<String>) fu.getFromFile();
                    for(String s:list){
                        String[] sa = s.split("/");
                        System.out.println("id: " + sa[0] + "   /   Name: " + sa[1]);
                    }    break;
                default:
                    break OUTER;
            }
        }
       
    }
    
    
    
    
    
}
