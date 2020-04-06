public class TextBlock {
    private static final String TEXT_BLOCK = """
            Use the new backslash escape sequence \
            to suppress the new line character at the end of the previous line.
            
            Spaces at the end of a line are removed in text blocks.
            Use\s
            to add a space at the end of the line.
            """;

    public static void main(String[] args) {
        System.out.println(TEXT_BLOCK);
    }
}
