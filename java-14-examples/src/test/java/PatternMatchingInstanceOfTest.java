import net.jqwik.api.Arbitraries;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.lifecycle.BeforeTry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PatternMatchingInstanceOfTest {

    private PatternMatchingInstanceOf testedObject;

    @BeforeTry
    void setup(){
        testedObject = new PatternMatchingInstanceOf();
    }

    @Property
    void before_and_after_impl_returns_the_same_string(@ForAll() String aString){
        final Optional<String> beforeJava_14 = testedObject.beforeJava_14(aString);
        final Optional<String> withJava_14 = testedObject.withJava_14(aString);

        Assertions.assertAll(
                () -> assertTrue(beforeJava_14.isPresent()),
                () -> assertTrue(withJava_14.isPresent())
        );

        assertEquals(withJava_14.get(), beforeJava_14.get());
    }

}