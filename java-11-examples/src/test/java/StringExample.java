import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Some tests to experience Java 11 String Methods")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringExample {

    @BeforeAll
    static void global_set_up() {
    }

    @BeforeEach
    void set_up() {
    }

    @AfterEach
    void tear_down() {
    }

    @Test
    void should_be_empty_when_repeat_zero_times() {
        var toRepeat = "toRepeat";
        final String result = toRepeat.repeat(0);
        Assertions.assertEquals("", result);
    }

    @Test
    void empty_string_still_empty_after_repeat() {
        var emptyString = "";
        final String result = emptyString.repeat(Integer.MAX_VALUE);
        Assertions.assertEquals("", result);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n", "   "})
    void should_be_blank(String blankString) {
        Assertions.assertTrue(blankString.isBlank());
    }

    @ParameterizedTest
    @ValueSource(strings = {"@", "a", "$"})
    void should_not_be_blank(String blankString) {
        Assertions.assertFalse(blankString.isBlank());
    }

    @Test
    void strip_remove_surrounding_spaces() {
        final var toStrip = "   to strip   ";
        final String stripped = toStrip.strip();
        Assertions.assertEquals("to strip", stripped);
    }
}
