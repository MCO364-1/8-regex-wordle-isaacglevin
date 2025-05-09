import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        WordleResponse r1 = new WordleResponse('g', 0, LetterResponse.WRONG_LETTER);
        WordleResponse r2 = new WordleResponse('r', 1, LetterResponse.CORRECT_LOCATION);
        WordleResponse r3 = new WordleResponse('i', 2, LetterResponse.CORRECT_LOCATION);
        WordleResponse r4 = new WordleResponse('p', 3, LetterResponse.CORRECT_LOCATION);
        WordleResponse r5 = new WordleResponse('e', 4, LetterResponse.CORRECT_LOCATION);

        List<WordleResponse> word = new ArrayList<>();
        word.add(r1);
        word.add(r2);
        word.add(r3);
        word.add(r4);
        word.add(r5);

        List<List<WordleResponse>> guesses = new ArrayList<>();
        guesses.add(word);

        List<String> answers = wordleMatches(guesses);

        System.out.println(answers);
    }

    public static List<String> wordleMatches(List<List<WordleResponse>> guesses) {
        Set<String> dictionary = DictionaryLoader.loadDictionaryWords("words.txt");

        for (List<WordleResponse> guess : guesses) {

            String regex = regexBuilder(guess);
            System.out.println(regex);

            Set<String> filtered = new HashSet<>();
            for (String word : dictionary) {
                if (word.matches(regex)) {
                    filtered.add(word);
                }
            }
            dictionary = filtered;
        }
        return new ArrayList<>(dictionary);
    }
    public static String regexBuilder(List<WordleResponse> guess) {
        String[] regex = {".", ".", ".", ".", "."};
        StringBuilder lookAheads = new StringBuilder();
        StringBuilder negativeLookAheads = new StringBuilder();
        Set<Character> yellowCharacters = new HashSet<>();
        Set<Character> greenCharacters = new HashSet<>();
        Set<Character> greyCharacters = new HashSet<>();

        //loadInAllResponses
        for (int i = 0; i < 5; i++) {
            WordleResponse currentResponse = guess.get(i);
            char c = Character.toLowerCase(currentResponse.getChar());
            LetterResponse response = currentResponse.getResponse();

            if (response == LetterResponse.CORRECT_LOCATION) {
                greenCharacters.add(c);
            }
            if (response == LetterResponse.WRONG_LOCATION) {
                yellowCharacters.add(c);
            }
            if (response == LetterResponse.WRONG_LETTER) {
                greyCharacters.add(c);
            }
        }

        //remove the greens and yellows from the greys to have only TRUE greys
        greyCharacters.removeAll(greenCharacters);
        greyCharacters.removeAll(yellowCharacters);

        //Create regex for greens and yellows
        for (int i = 0; i < 5; i++) {
            WordleResponse currentResponse = guess.get(i);
            char c = Character.toLowerCase(currentResponse.getChar());
            LetterResponse response = currentResponse.getResponse();
            //Green check
            if (response == LetterResponse.CORRECT_LOCATION) {
                regex[i] = String.valueOf(c);
            }
            //Yellow check
            if (response == LetterResponse.WRONG_LOCATION) {
                regex[i] = "[^" + c + "]";
            }
        }

        //Create lookAheads for yellows
        for (Character c : yellowCharacters) {
            lookAheads.append("(?=.*").append(c).append(")");
        }

        //Create negative lookaheads for greys
        if (!greyCharacters.isEmpty()) {
            negativeLookAheads.append("(?!.*[");
            for (Character c : greyCharacters) {
                negativeLookAheads.append(c);
            }
            negativeLookAheads.append("])");
        }

        //Come lookAheads and green/yellow regexes into final regex
        StringBuilder finalRegex = new StringBuilder();
        finalRegex.append(lookAheads);
        finalRegex.append(negativeLookAheads);
        for (String part : regex) {
            finalRegex.append(part);
        }
        return finalRegex.toString();
    }

    static boolean properName(String s) {
        return s.matches("^[A-Z][a-z]+$");
    }

    static boolean integer(String s) {
        return s.matches("^[+-]?(0|[1-9][0-9]*)(\\.[0-9]+)?$");
    }

    static boolean ancestor(String s) {
        return s.matches("^(great-)*(grand)?(father|mother)$");
    }

    static boolean palindrome(String s) {
        return s.matches("^(.)(.)(.)(.)(.)\\5\\4\\3\\2\\1$");
    }

}