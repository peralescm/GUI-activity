/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class DiaryEntry {
    private String id;
    private String date;
    private String content;

    public DiaryEntry(String id, String date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nDate: " + date + "\nContent: " + content;
    }
}

class DiaryManager {
    private static final String FILENAME = "diary_database.txt";
    
    
    public DiaryManager() {
        try {
            File file = new File(FILENAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating database file: " + e.getMessage());
        }
    }
    
    public void addEntry(String id, String date, String content) {
        try (FileWriter fw = new FileWriter(FILENAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(id);
            out.println(date);
            out.println(content);
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }
    
    public List<DiaryEntry> getAllEntries() {
        List<DiaryEntry> entries = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String id = line;
                String date = br.readLine();
                String content = br.readLine();
                
                if (id != null && date != null && content != null) {
                    entries.add(new DiaryEntry(id, date, content));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from database file: " + e.getMessage());
        }
        
        return entries;
    }
    
    public DiaryEntry getEntryById(String id) {
        List<DiaryEntry> entries = getAllEntries();
        
        for (DiaryEntry entry : entries) {
            if (entry.getId().equals(id)) {
                return entry;
            }
        }
        
        return null;
    }
    
    public boolean isIdUnique(String id) {
        List<DiaryEntry> entries = getAllEntries();
        
        for (DiaryEntry entry : entries) {
            if (entry.getId().equals(id)) {
                return false;
            }
        }
        
        return true;
    }
}

public class DiaryApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static DiaryManager diaryManager = new DiaryManager();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            displayMenu();
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addNewEntry();
                    break;
                case "2":
                    viewAllEntries();
                    break;
                case "3":
                    searchEntryById();
                    break;
                case "4":
                    running = false;
                    System.out.println("\nThank you for using the Diary Application. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter a number from 1 to 4.");
                    break;
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n===== DIARY APPLICATION =====");
        System.out.println("1. Add a new Diary entry");
        System.out.println("2. View all Diary entries");
        System.out.println("3. Search for entry by ID");
        System.out.println("4. Exit");
        System.out.println("============================");
        System.out.print("Enter your choice: ");
    }
    
    private static void addNewEntry() {
        System.out.println("\n--- Add New Diary Entry ---");
        
        //get and validate ID
        String id;
        while (true) {
            System.out.print("Enter diary ID: ");
            id = scanner.nextLine();
            
            if (id.trim().isEmpty()) {
                System.out.println("ID cannot be empty. Please try again.");
                continue;
            }
            
            if (!diaryManager.isIdUnique(id)) {
                System.out.println("ID '" + id + "' already exists. Please choose a different ID.");
                continue;
            }
            
            break;
        }
        
        //date
        String date;
        while (true) {
            System.out.print("Enter date (YYYY-MM-DD) or press Enter for today's date: ");
            String dateInput = scanner.nextLine();
            
            if (dateInput.trim().isEmpty()) {
                //use today's date if no input
                date = LocalDate.now().format(dateFormatter);
                break;
            }
            
            try {
                //date format
                LocalDate.parse(dateInput, dateFormatter);
                date = dateInput;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        
        //diary content
        System.out.println("Enter your diary content (press Enter twice to finish):");
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        boolean previousLineEmpty = false;
        
        while (true) {
            line = scanner.nextLine();
            
            if (line.isEmpty() && previousLineEmpty) {
                break;
            }
            
            if (contentBuilder.length() > 0) {
                contentBuilder.append("\n");
            }
            contentBuilder.append(line);
            previousLineEmpty = line.isEmpty();
        }
        
        String content = contentBuilder.toString().trim();
        
        //entry to database
        diaryManager.addEntry(id, date, content);
        System.out.println("\nDiary entry added successfully!");
    }
    
    private static void viewAllEntries() {
        System.out.println("\n--- All Diary Entries ---");
        List<DiaryEntry> entries = diaryManager.getAllEntries();
        
        if (entries.isEmpty()) {
            System.out.println("No diary entries found.");
        } else {
            for (DiaryEntry entry : entries) {
                System.out.println("\nID: " + entry.getId());
                System.out.println("Date: " + entry.getDate());
                System.out.println("Content: " + entry.getContent());
                System.out.println("-".repeat(30));
            }
        }
    }
    
    private static void searchEntryById() {
        System.out.print("\nEnter diary ID to search: ");
        String idToSearch = scanner.nextLine();
        DiaryEntry entry = diaryManager.getEntryById(idToSearch);
        
        if (entry != null) {
            System.out.println("\n--- Diary Entry Found ---");
            System.out.println("ID: " + entry.getId());
            System.out.println("Date: " + entry.getDate());
            System.out.println("Content: " + entry.getContent());
        } else {
            System.out.println("No diary entry found with ID '" + idToSearch + "'.");
        }
    }
}
