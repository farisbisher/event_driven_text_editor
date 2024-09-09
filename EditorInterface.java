import java.util.Scanner;
import java.io.File;

public class EditorInterface {
    public TextEditor textEditor;
    public FileHandler fileHandler;
    public EventManager eventManager;
    public Scanner scanner;

    public EditorInterface() {
        this.textEditor = new TextEditor();
        this.fileHandler = new FileHandler(textEditor);
        this.eventManager = new EventManager(textEditor, fileHandler);
        scanner = new Scanner(System.in);
        fileCreation();
    }

    public void fileCreation() {
        String fileName;

        while (true) {
            System.out.println(
                    "Welcome to the TextEditor Interface: \n 1- Create a new file \n 2- Edit an existing file");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 2:
                    System.out.println("Insert the name of the file that you want to edit:");
                    fileName = scanner.nextLine();
                    File file = new File(fileName + ".txt");
                    if (!file.exists()) {
                        System.out.println("There is no file with that name");
                    } else {
                        eventManager.getFileName(fileHandler.setCurrentFileName(fileName + ".txt"));

                        fileHandler.readTextFromFile(fileName + ".txt");
                        eventManager.updateTextArea(textEditor.textContent);
                        eventManager.start();
                        return;
                    }
                    break;

                case 1:
                    System.out.println("Insert the name of the new file:");
                    fileName = scanner.nextLine();
                    eventManager.getFileName(fileHandler.setCurrentFileName(fileName + ".txt"));

                    fileHandler.saveTextToFile(fileName + ".txt", textEditor.textContent);
                    eventManager.start();
                    return;
            }
        }
    }

}
