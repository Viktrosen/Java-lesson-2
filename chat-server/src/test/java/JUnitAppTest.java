import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JUnitAppTest {
    private JUnitApp app;

    @BeforeEach
    public void init() {
        app = new JUnitApp();
    }

    @Test
    void cutArr1() {
        Assertions.assertEquals(true, Arrays.equals(new int[]{12, 54, 32},app.cutArr(new int[]{1, 2, 4, 1, 5, 5, 8, 4, 12, 54, 32})));
    }

    @Test
    void cutArr2() {
        Assertions.assertEquals(true, Arrays.equals(new int[]{1},app.cutArr(new int[]{7,4,1})));
    }

    @Test
    void cutArr3() {
        Assertions.assertEquals(false, Arrays.equals(new int[]{8, 5, 5},app.cutArr(new int[]{1, 2, 4, 1, 5, 5, 8, 4, 12, 54, 32})));
    }

    @Test
    void checkArr1() {
        Assertions.assertEquals(false, app.checkArr(new int[]{1,1,3,1,4,1,1,4,1,4,1,5,}));
    }

    @Test
    void checkArr2() {
        Assertions.assertEquals(true, app.checkArr(new int[]{1,1,4,1,4,1,1,4,1,4,1,1,}));
    }
    @Test
    void checkArr3() {
        Assertions.assertEquals(false, app.checkArr(new int[]{1,1,1,1,1,1}));
    }
    @Test
    void checkArr4() {
        Assertions.assertEquals(false, app.checkArr(new int[]{4,4,4,4,4,4}));
    }
}