import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;

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
    void strip_removes_surrounding_spaces() {
        final var toStrip = "   toStrip   ";
        final String stripped = toStrip.strip();
        Assertions.assertEquals("toStrip", stripped);
    }

    @Test
    void stripLeading_removes_only_leading_spaces() {
        final var toStrip = "    toStrip    ";
        final String stripped = toStrip.stripLeading();
        Assertions.assertEquals("toStrip    ", stripped);
    }

    @Test
    void stripTrailing_removes_only_trailing_spaces() {
        final var toStrip = "    toStrip    ";
        final String stripped = toStrip.stripTrailing();
        Assertions.assertEquals("    toStrip", stripped);
    }

    @Test
    void strip_does_not_remove_middle_spaces() {
        final var toStrip = "to Str ip";
        final String stripped = toStrip.strip();
        Assertions.assertEquals("to Str ip", stripped);
    }

    @Test
    void lines_stream_collected_match_list_of(){
        final String lines = "line_one\nline_two\nline_three";
        final List<String> linesList = lines.lines().collect(Collectors.toList());
        Assertions.assertLinesMatch(List.of("line_one", "line_two", "line_three"), linesList);
    }

}
