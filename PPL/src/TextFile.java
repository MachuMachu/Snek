import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFile {
    public String checkWord(String word, String filePath){
        try {
            Scanner inputFile = new Scanner(new File(filePath));

            //Check if the lexeme is in the text file===================================================================
            while (inputFile.hasNext()) {
                String unknown = inputFile.next();
                if (word.equals(unknown)) {
                    return String.valueOf(inputFile.nextLine());                        // pwedeng nextLine or next lang ()
                }
            }
        }//try
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }//catch
        return null;
    }
}//class
