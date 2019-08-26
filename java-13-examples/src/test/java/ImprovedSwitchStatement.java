import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImprovedSwitchStatement {

    @ParameterizedTest
    @ValueSource(strings = {"Wolf, Tiger, Demon, Dragon, God"})
    void improved_switch_style(String monsterRank) {
        int numberOfPunches =
                switch (monsterRank) {
                    case "Tiger" -> 1;
                    case "Demon" -> 1;
                    case "Dragon" -> 1;
                    default -> {
                        System.out.println("Always only one punch !");
                        yield 1;//yield is new in Java 13
                    }
                };
        Assertions.assertEquals(1, numberOfPunches);
    }
}
