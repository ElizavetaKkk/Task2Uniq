package task.uniq;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

public class UniqTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Test
    public void mainTest() throws Exception {
        File fileActual = new File("output.txt");
        UniqLauncher.main(new String[]{"-i", "-c", "-o", "output.txt", "input.txt"});
        Assert.assertTrue(FileUtils.contentEquals(fileActual, new File("outputTest1.txt")));
        UniqLauncher.main(new String[]{"-u", "-s", "1", "-o", "output.txt", "input.txt"});
        Assert.assertTrue(FileUtils.contentEquals(fileActual, new File("outputTest2.txt")));
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> UniqLauncher.main(new String[]{"-s", "6", "input.txt"})
        );
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> UniqLauncher.main(new String[]{"-i", "file.txt"})
        );
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> UniqLauncher.main(new String[]{"-c", "-o", "file.txt", "input.txt"})
        );
        System.setOut(new PrintStream(output));
        UniqLauncher.main(new String[]{"-i", "-s", "1", "-c", "input.txt"});
        String expect = "3 aAa\r\n1 bBB\r\n2 fFF\r\n3 DdD\r\n";
        Assert.assertEquals(expect, output.toString());
        System.setOut(null);
    }

    @Test
    public void result() {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "omOm", "ktj", "KTJ", "FidH", "fiD", "gtyio", "GTyIo", "lkl");
        ArrayList<String> expectedRes = new ArrayList<>();
        Collections.addAll(expectedRes, "omOm", "FidH", "fiD", "lkl");
        Uniq uniq1 = new Uniq(true, true, false, 0);
        Assert.assertEquals(expectedRes, uniq1.result(list));
        list = new ArrayList<>();
        Collections.addAll(list, "omOm", "ktj", "KTJ", "FidH", "fiD", "gtyio", "GTyIo", "lkl");
        expectedRes = new ArrayList<>();
        Collections.addAll(expectedRes, "1 omOm", "2 ktj", "1 FidH", "1 fiD", "2 gtyio", "1 lkl");
        Uniq uniq2 = new Uniq(true, false, true, 0);
        Assert.assertEquals(expectedRes, uniq2.result(list));
    }
}
