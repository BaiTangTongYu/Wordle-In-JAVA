/**
 * @Auther: Shenghan Bai
 * @Date: 2022/06/01
 * @Description: WordleGame
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class WordleGame {


    public static void play() {

        

        ArrayList<String> guesses = new ArrayList<>();
        char[] guess = new char[5];
        String randomWord = Words.chooseWord();

        // Create a JFrame object as a window for the game
        // And set the window style
        JFrame win = new JFrame("Wordle");
        win.setSize(500, 700);
        win.getContentPane().setBackground(Color.white);
        win.setResizable(false);

        // Set up the listener function to exit the program when closing the window
        win.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Create two JButton objects for play again and check
        // And set the button style
        JButton again = new JButton("again");
        JButton check = new JButton("check");


        again.setBackground(new Color(251, 252, 255));
        again.setBorder(new TextBorderUtlis(new Color(222, 225, 233), 3, true));
        again.setPreferredSize(new Dimension(100, 50));
        again.setFont(new Font("Arial", Font.BOLD, 25));

        check.setBackground(new Color(251, 252, 255));
        check.setBorder(new TextBorderUtlis(new Color(222, 225, 233), 3, true));
        check.setPreferredSize(new Dimension(100, 50));
        check.setFont(new Font("Arial", Font.BOLD, 25));

        // Create two ArrayLists to hold JTextField and JPanel
        ArrayList<JTextField> txt_arr = new ArrayList<>();
        ArrayList<JPanel> panel_arr = new ArrayList<>();

        // Create a 5*6 single-character text box using a loop and store it into an ArrayList
        for (int i = 0; i < 6; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            panel_arr.add(panel);

            for (int j = 0; j < 5; j++) {
                JTextField txt = new JTextField(1);
                txt.setDisabledTextColor(Color.BLACK);
                txt.setBackground(new Color(251, 252, 255));
                txt.setBorder(new TextBorderUtlis(new Color(222, 225, 233), 3, true));
                txt_arr.add(txt);
                txt.setEnabled(false);
            }
        }


        final int[] index = new int[1];
        txt_arr.get(0).setEnabled(true);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                JTextField txt = txt_arr.get(i * 5 + j);
                txt.setFont(new Font("Arial", Font.BOLD, 50));
                txt.setHorizontalAlignment(JTextField.CENTER);
                int finalI = i;
                int finalJ = j;

                // The implementation listens on each JTextField
                txt.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                        String s = txt.getText();

                        if (s.length() >= 1) {
                            e.consume();

                        } else {
                            char c = e.getKeyChar();
                            char[] cc = new char[1];
                            cc[0] = c;
                            String C = new String(cc);
                            if (!(c >= 'a' && c <= 'z' || c == '\b')) {
                                e.consume();
                            } else if (c == '\b' && finalJ != 0 && finalJ != 4) {
                                txt_arr.get(finalI * 5 + finalJ - 1).setEnabled(true);
                                txt_arr.get(finalI * 5 + finalJ).setEnabled(false);
                                txt_arr.get(finalI * 5 + finalJ - 1).setText("");
                                Backspace_jump();
                                guess[finalJ - 1] = ' ';
                            } else if (c == '\b' && finalJ == 4) {
                                if (guess[finalJ] == ' ') {
                                    txt_arr.get(finalI * 5 + finalJ - 1).setEnabled(true);
                                    txt_arr.get(finalI * 5 + finalJ).setEnabled(false);
                                    txt_arr.get(finalI * 5 + finalJ - 1).setText("");
                                    Backspace_jump();
                                    guess[finalJ - 1] = ' ';
                                } else {
                                    txt_arr.get(finalI * 5 + finalJ).setText("");
                                    guess[finalJ] = ' ';
                                }


                            } else if (c == '\b' && finalJ == 0) {
                                e.consume();
                            } else {
                                guess[finalJ] = c;
                                e.consume();
                                txt_arr.get(finalI * 5 + finalJ).setText(C);
                                if (finalJ != 4) {
                                    txt_arr.get(finalI * 5 + finalJ).setEnabled(false);
                                    txt_arr.get(finalI * 5 + finalJ + 1).setEnabled(true);
                                    Automatic_jump();
                                } else {
                                    index[0] = finalI;
                                }

                            }
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

                panel_arr.get(i).add(txt);
            }
            win.getContentPane().add(panel_arr.get(i));
        }

        // Implement the Check function
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guess_word = new String();
                for (int i = 0; i < guess.length; i++) {
                    if (guess[i] >= 'a' && guess[i] <= 'z') {
                        guess_word = guess_word + guess[i];
                    }
                }

                if (guess_word.length() != 5) {
                    JOptionPane.showMessageDialog(win, "too short");
                } else {
                    if (Words.isWord(guess_word)) {

                        change_color(check_word(guess_word, randomWord), index[0], txt_arr);


                        if (index[0] != 5 && !guess_word.equals(randomWord)) {
                            txt_arr.get(index[0] * 5 + 4).setEnabled(false);
                            txt_arr.get((index[0] + 1) * 5).setEnabled(true);
                            Automatic_jump();
                        }

                        if (guess_word.equals(randomWord)) {
                            txt_arr.get(index[0] * 5 + 4).setEnabled(false);
                        }

                    } else {
                        JOptionPane.showMessageDialog(win, "Word not found");
                    }
                }
            }
        });

        // Implement the again function
        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });


        JPanel panel = new JPanel();
        panel.setBackground(Color.white);

        panel.add(again);
        panel.add(check);


        win.getContentPane().add(panel);

        win.setLayout(new FlowLayout(1));
        win.setVisible(true);
    }

    /**
     * Install the rules of the game to check if the five-letter word is correct and return the color corresponding to the different letters
     * @param guess_word
     * @param randomWord
     * @return
     */
    public static Color[] check_word(String guess_word, String randomWord) {
        Color[] colors = new Color[5];

        char[] guess_c = guess_word.toCharArray();
        char[] random_c = randomWord.toCharArray();
        int[] color = {0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (guess_c[i] == random_c[j]) {
                    color[i] = 1;
                    if (i == j) {
                        color[i] = 2;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < color.length; i++) {
            if (color[i] == 0) {
                colors[i] = Color.gray;
            } else if (color[i] == 1) {
                colors[i] = Color.yellow;
            } else if (color[i] == 2) {
                colors[i] = Color.green;
            }
        }

        return colors;
    }

    /**
     * Receives an array of colors for different letters and changes JTextField to the corresponding color
     * @param colors
     * @param index
     * @param txt_arr
     */
    public static void change_color(Color[] colors, int index, ArrayList<JTextField> txt_arr) {
        for (int i = 0; i < 5; i++) {
            JTextField txt = txt_arr.get(index * 5 + i);
            txt.setBackground(colors[i]);
        }
    }

    /**
     * Focus from the current JTextField to the next
     */
    public static void Automatic_jump() {
        DefaultKeyboardFocusManager defaultKeyboardFocusManager = new DefaultKeyboardFocusManager();
        defaultKeyboardFocusManager.focusNextComponent();
    }

    /**
     * Focus from the current JTextField to the previous JTextField
     */
    public static void Backspace_jump() {
        DefaultKeyboardFocusManager defaultKeyboardFocusManager = new DefaultKeyboardFocusManager();
        defaultKeyboardFocusManager.focusPreviousComponent();
    }


    public static void main(String[] args) {
        play();
    }
}
