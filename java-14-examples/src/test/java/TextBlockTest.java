import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextBlockTest {

    @Test
    void new_backslash_escape_sequence_removes_end_line_character() {
        final String textBlock = """
                This is a text block \
                with an end line character.""";
        final String theSameTextAsString = "This is a text block with an end line character.";

        Assertions.assertEquals(textBlock, theSameTextAsString);
    }

    @Test
    void new_backslash_s_sequence_keep_space_at_end_of_line() {
        final String spaceRemovedAsTextBlock = """
                This is a text block with a space 
                removed at end of line""";
        final String spaceRemovedAsString = "This is a text block with a space\nremoved at end of line";
        Assertions.assertEquals(spaceRemovedAsString, spaceRemovedAsTextBlock);

        final String spaceKeptAsTextBlock = """
                This is a text block with a space\s
                kept at end of line""";
        final String spaceKeptAsString = "This is a text block with a space \nkept at end of line";
        Assertions.assertEquals(spaceKeptAsString, spaceKeptAsTextBlock);
    }
}
