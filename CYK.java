import java.io.*;
import java.util.*;

/**
 * Implementation of CYK algorithm for checking if given word can be derived from context-free grammar.
 * File format:
 * S                             -> Starting Symbol
 * a b                           -> Terminals
 * S A B E C X Y Z               -> Nonterminals
 * S XY AZ *                     -> Everything else is set of rules for CFG
 * E AB EC                       -> This reads as E -> AB | EC
 */

public class CYK {
    public static String word;
    public static String startSymbol;
    public static boolean isTokenWord = false;
    public static ArrayList<String> terminals = new ArrayList<>();
    public static ArrayList<String> nonTerminals = new ArrayList<>();
    public static TreeMap<String, ArrayList<String>> grammar = new TreeMap<>();

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("java CYK <File> <Word>");
            System.exit(1);
        } else if (args.length > 2) {
            isTokenWord = true;
        }
        execute(args);
    }

    public static void execute(String[] args) {
        parseGrammar(args);
        String[][] table = createCYKTable();
        printResult(doCYK(table));
    }

    public static void parseGrammar(String[] args) {
        Scanner input = openFile(args[0]);
        ArrayList<String> tmp = new ArrayList<>();
        int line = 2;

        word = getWord(args);
        startSymbol = input.next();
        input.nextLine();

        while (input.hasNextLine() && line <= 3) {
            tmp.addAll(Arrays.<String>asList(toArray(input.nextLine())));
            if (line == 2) {
                terminals.addAll(tmp);
            }
            if (line == 3) {
                nonTerminals.addAll(tmp);
            }
            tmp.clear();
            line++;
        }

        while (input.hasNextLine()) {
            tmp.addAll(Arrays.<String>asList(toArray(input.nextLine())));
            String left = tmp.get(0);
            tmp.remove(0);
            grammar.put(left, new ArrayList<String>());
            grammar.get(left).addAll(tmp);
            tmp.clear();
        }

        input.close();
    }

    public static String[][] createCYKTable() {
        int length = isTokenWord ? toArray(word).length : word.length();
        String[][] table = new String[length + 1][];
        table[0] = new String[length];

        for (int i = 1; i < table.length; i++) {
            table[i] = new String[length - i + 1];
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = "";
            }
        }

        return table;
    }

    public static String[][] doCYK(String[][] table) {
        for (int i = 0; i < table[0].length; i++) {
            table[0][i] = manageWord(word, i);
        }

        for (int i = 0; i < table[1].length; i++) {
            String[] validCombinations = checkIfProduces(new String[] {
                    table[0][i]
            });
            table[1][i] = toString(validCombinations);
        }
        if (word.length() <= 1) {
            return table;
        }

        for (int i = 0; i < table[2].length; i++) {
            String[] downwards = toArray(table[1][i]);
            String[] diagonal = toArray(table[1][i + 1]);
            String[] validCombinations = checkIfProduces(getAllCombinations(downwards, diagonal));
            table[2][i] = toString(validCombinations);
        }
        if (word.length() <= 2) {
            return table;
        }

        TreeSet<String> currentValues = new TreeSet<String>();

        for (int i = 3; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                for (int compareFrom = 1; compareFrom < i; compareFrom++) {
                    String[] downwards = table[compareFrom][j].split("\\s");
                    String[] diagonal = table[i - compareFrom][j + compareFrom].split("\\s");
                    String[] combinations = getAllCombinations(downwards, diagonal);
                    String[] validCombinations = checkIfProduces(combinations);
                    if (table[i][j].isEmpty()) {
                        table[i][j] = toString(validCombinations);
                    } else {
                        String[] oldValues = toArray(table[i][j]);
                        ArrayList<String> newValues = new ArrayList<String>(Arrays.asList(oldValues));
                        newValues.addAll(Arrays.asList(validCombinations));
                        currentValues.addAll(newValues);
                        table[i][j] = toString(currentValues.toArray(new String[currentValues.size()]));
                    }
                }
                currentValues.clear();
            }
        }
        return table;
    }

    public static String manageWord(String word, int position) {
        if (!isTokenWord) {
            return Character.toString(word.charAt(position));
        }
        return toArray(word)[position];
    }

    public static String[] checkIfProduces(String[] toCheck) {
        ArrayList<String> storage = new ArrayList<>();
        for (String s : grammar.keySet()) {
            for (String current : toCheck) {
                if (grammar.get(s).contains(current)) {
                    storage.add(s);
                }
            }
        }
        if (storage.size() == 0) {
            return new String[] {};
        }
        return storage.toArray(new String[storage.size()]);
    }

    public static String[] getAllCombinations(String[] from, String[] to) {
        int length = from.length * to.length;
        int counter = 0;
        String[] combinations = new String[length];
        if (length == 0) {
            return combinations;
        }
        ;
        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < to.length; j++) {
                combinations[counter] = from[i] + to[j];
                counter++;
            }
        }
        return combinations;
    }

    public static String toString(String[] input) {
        return Arrays.toString(input).replaceAll("[\\[\\]\\,]", "");
    }

    public static String[] toArray(String input) {
        return input.split("\\s");
    }

    public static Scanner openFile(String file) {
        try {
            return new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            System.out.println("Error: Can't find or open the file: " + file + ".");
            System.exit(1);
            return null;
        }
    }

    public static String getWord(String[] args) {
        if (!isTokenWord) {
            return args[1];
        }
        String[] argsWithoutFile = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            argsWithoutFile[i - 1] = args[i];
        }
        return toString(argsWithoutFile);
    }

    public static void printResult(String[][] table) {
        System.out.println("Word: " + word);
        System.out.println("\nG = (" + terminals.toString().replace("[", "{").replace("]", "}") +
                ", " + nonTerminals.toString().replace("[", "{").replace("]", "}") +
                ", P, " + startSymbol + ")\n\nWith Productions P as:");
        for (String s : grammar.keySet()) {
            System.out.println(
                    s + " -> " + grammar.get(s).toString().replaceAll("[\\[\\]\\,]", "").replaceAll("\\s", " | "));
        }
        System.out.println("\nApplying CYK-Algorithm:\n");
        drawTable(table);
    }

    public static void drawTable(String[][] table) {
        int l = findLongestString(table) + 2;
        String formatString = "| %-" + l + "s ";
        String s = "";
        StringBuilder sb = new StringBuilder();

        sb.append("+");
        for (int x = 0; x <= l + 2; x++) {
            if (x == l + 2) {
                sb.append("+");
            } else {
                sb.append("-");
            }
        }
        String low = sb.toString();
        sb.delete(0, 1);
        String lowRight = sb.toString();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j <= table[i].length; j++) {
                System.out.print((j == 0) ? low : (i <= 1 && j == table[i].length - 1) ? "" : lowRight);
            }
            System.out.println();
            for (int j = 0; j < table[i].length; j++) {
                s = (table[i][j].isEmpty()) ? "-" : table[i][j];
                System.out.format(formatString, s.replaceAll("\\s", ","));
                if (j == table[i].length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
        System.out.println(low + "\n");

        if (table[table.length - 1][table[table.length - 1].length - 1].contains(startSymbol)) {
            System.out.println("The word " + word + " is an element of the CFG and can be derived from it.");
        } else {
            System.out.println("The word " + word + " is not an element of the CFG and can not be derived from it.");
        }
    }

    public static int findLongestString(String[][] table) {
        int x = 0;
        for (String[] s : table) {
            for (String d : s) {
                if (d.length() > x) {
                    x = d.length();
                }
            }
        }
        return x;
    }
}
