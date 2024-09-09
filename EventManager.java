import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class EventManager extends JFrame implements KeyListener {

    public TextEditor textEditor;
    public FileHandler fileHandler;
    private JTextArea textArea;
    private int cursorPosition;
    private boolean shiftPressed;
    private boolean altPressed;
    public String currentFileName;

    public EventManager(TextEditor textEditor, FileHandler fileHandler) {
        this.textEditor = new TextEditor();
        this.fileHandler = new FileHandler(textEditor);
        this.cursorPosition = 0;
        this.shiftPressed = false;
        this.altPressed = false;
        textArea = new JTextArea();

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(textArea);

        textArea.setEditable(true);
        textArea.addKeyListener(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                displayText();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
    }

    public void start() {
        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int modifiers = e.getModifiers();

        switch (keyCode) {
            case KeyEvent.VK_SHIFT:
                shiftPressed = true;
                break;
            case KeyEvent.VK_ALT:
                altPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                if (cursorPosition > 0) {
                    cursorPosition--;
                    updateCaretPosition();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (cursorPosition < textEditor.textContent.size()) {
                    cursorPosition++;
                    updateCaretPosition();
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                if (cursorPosition > 0) {
                    cursorPosition--;
                    textEditor.textContent.remove(cursorPosition);
                    updateCaretPosition();

                }
                break;
            case KeyEvent.VK_SPACE:
                textEditor.insertTextAtCursor(" ", cursorPosition);
                cursorPosition++;
                updateCaretPosition();
                break;
            default:
                char keyChar = e.getKeyChar();
                if (modifiers == KeyEvent.SHIFT_MASK) {
                    if (keyChar != KeyEvent.CHAR_UNDEFINED) {
                        if (Character.isLetter(keyChar)) {
                            keyChar = Character.toUpperCase(keyChar);
                        }
                        textEditor.insertTextAtCursor(String.valueOf(keyChar), cursorPosition);
                        cursorPosition++;
                        updateCaretPosition();
                    }
                } else if (modifiers == (KeyEvent.ALT_MASK | KeyEvent.SHIFT_MASK)) {
                    System.out.println("Language change detected.");
                } else if (!altPressed && !shiftPressed) {
                    if (Character.isLetter(keyChar)) {
                        textEditor.insertTextAtCursor(String.valueOf(keyChar), cursorPosition);
                        cursorPosition++;
                        updateCaretPosition();
                    }
                }
                break;
        }

        textEditor.showLinkedListContent();
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void updateCaretPosition() {
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void updateTextArea(LinkedList<Character> textContent) {
        StringBuilder content = new StringBuilder();
        for (Character ch : textEditor.textContent) {
            content.append(ch);
        }
        textArea.setText(content.toString());
    }

    public void displayText() {
        StringBuilder content = new StringBuilder();
        for (Character ch : textEditor.textContent) {
            content.append(ch);
        }
        textArea.setText(content.toString());
    }

    public void getFileName(String fileName) {
        this.currentFileName = fileName;
    }

    public void onWindowClosing() {
        if (currentFileName != null &&
                !currentFileName.isEmpty()) {
            fileHandler.saveTextToFile(currentFileName, textEditor.textContent);
            System.out.println("Window is closing...");
            System.exit(0);
        } else {
            System.out.println("No file name specified.");
        }
    }

}
