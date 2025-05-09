import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Test
    void properName() {
        assertTrue(Main.properName("Fred"));
        assertFalse(Main.properName("FreD"));
    }

    @Test
    void integer() {
        assertTrue(Main.integer("1213.435"));
        assertTrue(Main.integer("0.231"));
        assertTrue(Main.integer("+15.4"));
        assertTrue(Main.integer("-0.4324123445"));
    }

    @Test
    void ancestor() {
        assertTrue(Main.ancestor("father"));
        assertTrue(Main.ancestor("mother"));
        assertTrue(Main.ancestor("grandfather"));
        assertTrue(Main.ancestor("grandmother"));
    }

    @Test
    void testGreatAncestors() {
        assertTrue(Main.ancestor("great-grandfather"));
        assertTrue(Main.ancestor("great-grandmother"));
        assertTrue(Main.ancestor("great-great-grandfather"));
        assertTrue(Main.ancestor("great-great-great-grandmother"));
    }

    @Test
    void testNonAncestors() {
        assertFalse(Main.ancestor("great-uncle"));
        assertFalse(Main.ancestor("great-sister"));
        assertFalse(Main.ancestor("brother"));
        assertFalse(Main.ancestor("fathermother"));
    }

    @Test
    void testValidPalindromes() {
        assertTrue(Main.palindrome("asdfggfdsa"));
        assertTrue(Main.palindrome("aaabaabaaa"));
    }

    @Test
    void testInvalidPalindromes() {
        assertFalse(Main.palindrome("abcdefghij"));
        assertFalse(Main.palindrome("racecarxx"));
        assertFalse(Main.palindrome("asdfggfd"));
        assertFalse(Main.palindrome("asdfggfdsa1"));
    }

    @Test
    void testGreenOnly() {
        List<WordleResponse> guess = List.of(
                new WordleResponse('c', 0, LetterResponse.CORRECT_LOCATION),
                new WordleResponse('r', 1, LetterResponse.WRONG_LETTER),
                new WordleResponse('a', 2, LetterResponse.WRONG_LETTER),
                new WordleResponse('n', 3, LetterResponse.WRONG_LETTER),
                new WordleResponse('e', 4, LetterResponse.WRONG_LETTER)
        );
        String regex = Main.regexBuilder(guess);

        assertTrue("clops".matches(regex));
        assertFalse("crane".matches(regex)); // r, a, n, e are banned
    }

    @Test
    void testYellowOnly() {
        List<WordleResponse> guess = List.of(
                new WordleResponse('t', 0, LetterResponse.WRONG_LOCATION),
                new WordleResponse('a', 1, LetterResponse.WRONG_LOCATION),
                new WordleResponse('r', 2, LetterResponse.WRONG_LETTER),
                new WordleResponse('p', 3, LetterResponse.WRONG_LETTER),
                new WordleResponse('y', 4, LetterResponse.WRONG_LETTER)
        );
        String regex = Main.regexBuilder(guess);

        assertTrue("untad".matches(regex));
        assertFalse("cable".matches(regex));
        assertFalse("caddy".matches(regex));
    }

    @Test
    void testGrayOnly() {
        List<WordleResponse> guess = List.of(
                new WordleResponse('x', 0, LetterResponse.WRONG_LETTER),
                new WordleResponse('y', 1, LetterResponse.WRONG_LETTER),
                new WordleResponse('z', 2, LetterResponse.WRONG_LETTER),
                new WordleResponse('q', 3, LetterResponse.WRONG_LETTER),
                new WordleResponse('v', 4, LetterResponse.WRONG_LETTER)
        );
        String regex = Main.regexBuilder(guess);

        assertTrue("crane".matches(regex));
        assertFalse("vexed".matches(regex));
    }

    @Test
    void testGreenYellowGrayCombo() {
        List<WordleResponse> guess = List.of(
                new WordleResponse('s', 0, LetterResponse.CORRECT_LOCATION),
                new WordleResponse('l', 1, LetterResponse.WRONG_LOCATION),
                new WordleResponse('a', 2, LetterResponse.WRONG_LETTER),
                new WordleResponse('t', 3, LetterResponse.WRONG_LOCATION),
                new WordleResponse('e', 4, LetterResponse.WRONG_LETTER)
        );
        String regex = Main.regexBuilder(guess);

        assertTrue("stolh".matches(regex));
        assertFalse("slate".matches(regex));
        assertFalse("state".matches(regex));
    }

    @Test
    void testRepeatedLettersGreenAndGray() {
        List<WordleResponse> guess = List.of(
                new WordleResponse('p', 0, LetterResponse.CORRECT_LOCATION),
                new WordleResponse('p', 1, LetterResponse.WRONG_LETTER),
                new WordleResponse('l', 2, LetterResponse.WRONG_LETTER),
                new WordleResponse('e', 3, LetterResponse.WRONG_LETTER),
                new WordleResponse('a', 4, LetterResponse.WRONG_LETTER)
        );
        String regex = Main.regexBuilder(guess);

        assertTrue("prism".matches(regex));   // starts with p, avoids rest
        assertFalse("apple".matches(regex));  // too many p’s, includes banned
    }

    @Test
    void testFalsePositiveBlocking() {
        List<WordleResponse> guess = List.of(
                new WordleResponse('t', 0, LetterResponse.WRONG_LOCATION),
                new WordleResponse('e', 1, LetterResponse.WRONG_LOCATION),
                new WordleResponse('s', 2, LetterResponse.WRONG_LOCATION),
                new WordleResponse('t', 3, LetterResponse.WRONG_LETTER),
                new WordleResponse('s', 4, LetterResponse.WRONG_LETTER)
        );
        String regex = Main.regexBuilder(guess);
        System.out.println("REGEX: " + regex);


        assertTrue("spite".matches(regex));
        assertFalse("tests".matches(regex));
    }
}