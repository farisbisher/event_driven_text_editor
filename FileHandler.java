import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class FileHandler {

    public String currentFileName;
    public TextEditor textEditor;

    public FileHandler(TextEditor textEditor) {
        this.textEditor = new TextEditor();

    }

    public String setCurrentFileName(String fileName) {
        this.currentFileName = fileName;
        return currentFileName;
    }

    public void saveTextToFile(String fileName, LinkedList<Character> textContent) {
        try {
            FileWriter writer = new FileWriter(fileName);
            StringBuilder content = new StringBuilder();

            for (Character c : textContent) {
                content.append(c);
            }

            writer.write(content.toString());
            writer.close();
            System.out.println("Text content saved to " + fileName + " successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the text content to the file.");
            e.printStackTrace();
        }
    }

    public void readTextFromFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();

            String fileContent = stringBuilder.toString();
            textEditor.textContent.clear();

            for (char c : fileContent.toCharArray()) {
                textEditor.textContent.add(c);
            }

            // Update the textArea after reading the file content
            // eventManager.updateTextArea(); // Assuming eventManager is accessible here

            System.out.println("The content of the file " + fileName + " has been read successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        }
    }
}
