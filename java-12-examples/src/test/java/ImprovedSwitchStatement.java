import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ImprovedSwitchStatement {

    @ParameterizedTest
    @ValueSource(strings = {"Wolf, Tiger, Demon, Dragon, God"})
    void old_switch_style(String monsterRank) {
        int numberOfPunches;
        switch (monsterRank) {
            case "Tiger":
                numberOfPunches = 1;
                break;
            case "Demon":
                numberOfPunches = 1;
                break;
            case "Dragon":
                numberOfPunches = 1;
                break;
            default:
                numberOfPunches = 1;
                break;
        }
        Assertions.assertEquals(1, numberOfPunches);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Wolf, Tiger, Demon, Dragon, God"})
    void improved_switch_style(String monsterRank) {
        int numberOfPunches =
                switch (monsterRank) {
                    case "Tiger" -> 1;
                    case "Demon" -> 1;
                    case "Dragon" -> 1;
                    default -> 1;
                };
        Assertions.assertEquals(1, numberOfPunches);
    }

}
