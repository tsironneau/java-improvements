import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Some tests to experience Java 13 Text blocks")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TextBlockExample {

    @Test
    void simple_print() {
        String simpleTextBlock ="""
            Hello, World
            This is a new line
                This one with indentation
            \tThis one too with indentation\rThen another new line""";
        System.out.println(simpleTextBlock);
    }

}
