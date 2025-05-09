import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DictionaryLoader {

    public static Set<String> loadDictionaryWords(String fileName) {
        Set<String> words = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading dictionary" + e.getMessage());
        }
        return words;
    }
}
