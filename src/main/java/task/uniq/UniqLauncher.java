package task.uniq;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class UniqLauncher {

    @Option(name = "-i", usage = "Case-insensitive comparison")
    private boolean caseInsensitive;

    @Option(name = "-u", usage = "Output of unique strings")
    private boolean uniqStrings;

    @Option(name = "-c", usage = "Number of identical rows before the line")
    private boolean nBefore;

    @Option(name = "-s", metaVar = "num", usage = "Number of characters ignored at the beginning")
    private int number;

    @Option(name = "-o", metaVar = "ofile", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "file", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new UniqLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Task2Uniq.jar -i -u -c -s num -o ofile file");
            parser.printUsage(System.err);
            return;
        }
        Uniq uniq = new Uniq(caseInsensitive, uniqStrings, nBefore, number);
        try {
            uniq.inAndOut(inputFileName, outputFileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
