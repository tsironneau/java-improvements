import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecordWithAnnotationsTest {

    @Test
    void throw_exception_when_null_parameter_given_to_constructor(){
        //noinspection ConstantConditions
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new RecordWithAnnotations<>(null)
        );
    }
}