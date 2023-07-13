import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {
    @Test
    public void testOffByN() {
        for (int d = 0; d < 256; ++d) {
            OffByN offbyn = new OffByN(d);
            for (int diff = 0; diff < 256; ++diff) {
                for (char x = '\0', y = (char) (x + diff); y < 256; ++x, ++y) {
                    if (diff == d) {
                        assertTrue(offbyn.equalChars(x, y));
                        assertTrue(offbyn.equalChars(y, x));
                    } else {
                        assertFalse(offbyn.equalChars(x, y));
                        assertFalse(offbyn.equalChars(y, x));
                    }

                }
            }
        }
    }
}
