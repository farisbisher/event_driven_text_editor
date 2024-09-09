
import java.util.LinkedList;
import java.util.Scanner;

public class TextEditor {
    public Scanner scanner;
    public LinkedList<Character> textContent;

    public TextEditor() {
        textContent = new LinkedList<Character>();
    }

    public void insertTextAtCursor(String text, int position) {
        for (int i = 0; i < text.length(); i++) {
            textContent.add(position + i, text.charAt(i));
        }
    }

    public void showLinkedListContent() {
        for (int i = 0; i < textContent.size(); i++) {
            System.out.print(textContent.get(i));
        }
        System.out.println();

    }

}
