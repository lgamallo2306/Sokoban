import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextParser {
    public void Parse(String fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader("lvl1.txt"))) {
            String character;
            while ((character = br.readLine()) != null) {
                System.out.println(character);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


}
