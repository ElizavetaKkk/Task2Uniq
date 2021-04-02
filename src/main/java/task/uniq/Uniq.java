package task.uniq;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Uniq {

    private final boolean caseInsensitive;
    private final boolean uniqStrings;
    private final boolean nBefore;
    private final int number;

    public Uniq(boolean caseInsensitive, boolean uniqStrings, boolean nBefore, int number) {
        this.caseInsensitive = caseInsensitive;
        this.uniqStrings = uniqStrings;
        this.nBefore = nBefore;
        this.number = number;
    }

    public void inAndOut(String inputName, String outputName) throws IOException {
        ArrayList<String> inputData = new ArrayList<>();
        Scanner in;
        if (inputName == null) {
            in = new Scanner(System.in);
        } else {
            in = new Scanner(Paths.get(inputName));
        }
        String line = in.nextLine();
        while (!line.isEmpty()) {
            if (line.length() < number) throw new IllegalArgumentException("Number of characters " +
                    "in line is less than in the flag -s");
            inputData.add(line.substring(number));
            if (in.hasNextLine()) line = in.nextLine();
            else break;
        }
        in.close();
        ArrayList<String> outputData = result(inputData);
        if (outputName == null) {
            for (String outputDatum : outputData) {
                System.out.println(outputDatum);
            }
        } else {
            FileWriter fw = new FileWriter(outputName, false);
            for (int i = 0; i < outputData.size() - 1; i++) {
                fw.write(outputData.get(i) + System.lineSeparator());
            }
            fw.write(outputData.get(outputData.size() - 1));
            fw.close();
        }
    }

    public ArrayList<String> result(ArrayList<String> inputData) {
        ArrayList<String> res = new ArrayList<>();
        int ind = 0;
        String line = inputData.get(0);
        for (int i = 1; i < inputData.size(); i++) {
            String s = inputData.get(i);
            if ((!caseInsensitive && !s.equals(line)) || (caseInsensitive && !s.equalsIgnoreCase(line))) {
                String firstPart = "";
                if (nBefore) firstPart = i - ind + " ";
                if (!uniqStrings || isUnique(inputData, line, ind)) {
                    res.add(firstPart + line);
                    ind = i;
                }
                else i = ind;
                line = s;
            }
        }
        String firstPart = "";
        if (nBefore) firstPart = inputData.size() - ind + " ";
        if (!uniqStrings || isUnique(inputData, line, ind)) res.add(firstPart + line);
        return res;
    }

    private boolean isUnique(ArrayList<String> inputData, String line, int ind) {
        boolean result = true;
        for (int i = ind + 1; i < inputData.size(); i++) {
            String s = inputData.get(i);
            if ((!caseInsensitive && s.equals(line)) || (caseInsensitive && s.equalsIgnoreCase(line))) {
                result = false;
                inputData.remove(i);
                i--;
            }
        }
        if (!result) inputData.remove(ind);
        return result;
    }
}