/**
 * @Auther: Shenghan Bai
 * @Date: 2022/06/01
 * @Description: The call file returns random words and checks if the words are valid
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

public class Words {

    static File answerWords = new File("Answer.txt");

    /**
     * Check if the word is legal
     * @param input
     * @return
     */
    public static boolean isWord(String input){
        try (BufferedReader br = new BufferedReader(new FileReader(answerWords))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(input.equals(line)){
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Select a word in the document
     * @return
     */
    public static String chooseWord(){
        Random rand = new Random();
        int num = rand.nextInt(5757);
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(answerWords))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(i == num){
                    return line;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
            return "false";
        }
        return "false";
    }



}
