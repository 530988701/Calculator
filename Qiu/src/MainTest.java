import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MainTest {
    @Test
    public void solve() {
        String sum = Main.Solve("11+22");
        System.out.println(sum);
        Assert.assertEquals("11+22=33", sum);
    }

    @Test
    public void solve1() throws IOException {
        Main.save();
    }
}
