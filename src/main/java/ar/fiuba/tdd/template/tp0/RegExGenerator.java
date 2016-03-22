package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegExGenerator {
    private int maxLength;
    public String regularExpression = "";
    ArrayList<String> regularExpressionList = new ArrayList<>();

    public RegExGenerator(int maxLength) {
        if (maxLength <= 0) {
            try {
                throw new Exception("the constructor cannot be empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.maxLength = maxLength;
    }


    public String generateRandomStringPlus(int numberOfResults) {
        Random random = new Random();
        String randomStringPlus = "";
        for (int i = 0; i < numberOfResults; i++) {
            int randomCharNumber = random.nextInt(256);
            randomStringPlus += (char) randomCharNumber;
        }
        return randomStringPlus;
    }

    public String generateStringLiteralPlus(int numberOfResults, char literal) {
        char[] chars = new char[numberOfResults];
        Arrays.fill(chars, literal);
        return new String(chars);
    }

    public String generateStringLiteralAsterics(int numberOfResults, char literal) {
        char[] chars = new char[numberOfResults + 1];
        Arrays.fill(chars, literal);
        return new String(chars);
    }

    public String generateStringLiteralQuestionMark(int numberOfResults, char literal) {
        String stringLiteralPlus;
        if (numberOfResults == 0) {
            char[] chars = new char[0];
            Arrays.fill(chars, literal);
            stringLiteralPlus = new String(chars);
        } else {
            char[] chars = new char[1];
            Arrays.fill(chars, literal);
            stringLiteralPlus = new String(chars);
        }
        return stringLiteralPlus;
    }

    public String generateRandomStringAsterics(int numberOfResults) {
        Random random = new Random();
        String randomStringPlus = "";
        for (int i = 1; i <= numberOfResults; i++) {
            int randomCharNumber = random.nextInt(256);
            randomStringPlus += (char) randomCharNumber;
        }
        return randomStringPlus;
    }

    public String generateRandomStringQuestionMark(int numberOfResults) {
        Random random = new Random();
        String randomStringQuestionMark = "";
        if (numberOfResults > 0) {
            int randomCharNumber = random.nextInt(256);
            randomStringQuestionMark += (char) randomCharNumber;
        }
        return randomStringQuestionMark;
    }

    public String generateRandomStringBracketsPlus(int numberOfResults, String item) {
        Random random = new Random();
        String alphabet = item.replaceAll("(.)\\1+", "$1");
        alphabet = alphabet.substring(1);
        String randomStringBrackets = "";
        for (int i = 0; i <= numberOfResults; i++) {
            int randomAlphabetNumber = random.nextInt(alphabet.length() - 2);
            randomStringBrackets += alphabet.substring(randomAlphabetNumber, randomAlphabetNumber + 1);
        }
        return randomStringBrackets;
    }

    public String generateRandomStringBracketsAsterics(int numberOfResults, String item) {
        Random random = new Random();
        String alphabet = item.replaceAll("(.)\\1+", "$1");
        String randomStringBrackets = "";
        for (int i = 0; i < numberOfResults; i++) {
            int randomAlphabetNumber = random.nextInt(alphabet.length());
            randomStringBrackets += alphabet.substring(randomAlphabetNumber, randomAlphabetNumber + 1);
        }
        return randomStringBrackets;
    }

    public char generateStringBrackets(int numberOfResults, String item) {
        if (numberOfResults < item.length() - 1)
            return item.charAt(numberOfResults + 1);
        else
            return item.charAt(item.length() - 1);
    }


    public char generateRandomChar() {
        Random random = new Random();
        int randomCharNumber = random.nextInt(256);
        return (char) randomCharNumber;
    }

    private boolean validateSyntax() {
        String userInputPattern = regularExpression;
        try {
            Pattern compile = Pattern.compile(userInputPattern);
        } catch (PatternSyntaxException exception) {
            return false;
        }
        return true;
    }

    private void parseTokens() {
        //..+[ab]*d?c
        String auxExpression = "";
        if (regularExpression.length() == 1)
            regularExpressionList.add(regularExpression);
        else {
            while (!regularExpression.isEmpty()) {
                if (regularExpression.charAt(0) == '[') {
                    String aux = "";
                    while (regularExpression.charAt(0) != ']') {
                        aux += regularExpression.substring(0, 1);
                        regularExpression = regularExpression.substring(1);
                    }
                    aux += ']';
                    String substring = regularExpression.substring(0, 1);
                    regularExpression = regularExpression.substring(1);
                    if (!regularExpression.isEmpty()) {
                        if (regularExpression.charAt(0) == '+') {
                            aux += '+';
                            regularExpression = regularExpression.substring(1);
                        }
                        if (!regularExpression.isEmpty())
                            if (regularExpression.charAt(0) == '*') {
                                aux += '*';
                                String sstring = regularExpression.substring(0, 1);
                                regularExpression = regularExpression.substring(1);
                            }
                        if (!regularExpression.isEmpty())
                            if (regularExpression.charAt(0) == '?') {
                                aux += '?';
                                String sbstring = regularExpression.substring(0, 1);
                                regularExpression = regularExpression.substring(1);
                            }
                    }
                    regularExpressionList.add(aux);
                }

                if (regularExpression.length() > 1) {
                    char firstmember = regularExpression.charAt(0);
                    char secondmember = regularExpression.charAt(1);

                    if (firstmember == '\\') {
                        regularExpressionList.add(regularExpression.substring(0, 2));
                        regularExpression = regularExpression.substring(2);
                    } else {
                        switch (secondmember) {
                            case '+':
                                regularExpressionList.add(regularExpression.substring(0, 2));
                                regularExpression = regularExpression.substring(2);
                                break;
                            case '*':
                                regularExpressionList.add(regularExpression.substring(0, 2));
                                regularExpression = regularExpression.substring(2);
                                break;
                            case '?':
                                regularExpressionList.add(regularExpression.substring(0, 2));
                                regularExpression = regularExpression.substring(2);
                                break;
                            default:
                                regularExpressionList.add(regularExpression.substring(0, 1));
                                regularExpression = regularExpression.substring(1);
                                break;
                        }
                    }
                } else {
                    if (!regularExpression.isEmpty()) {
                        regularExpressionList.add(regularExpression.substring(0, 1));
                        regularExpression = regularExpression.substring(1);
                    }
                }
            }
        }
    }

    private String stringFromTokens(int numberOfResults) {
        String matchingString = "";
        for (String item : regularExpressionList) {
            switch (item.charAt(0)) {
                case '\\':
                    matchingString += item.charAt(1);
                    break;
                case '.':
                    matchingString += generateRandomChar();
                    break;
            }
            switch (item.charAt(item.length() - 1)) {
                case ']':
                    matchingString += generateStringBrackets(numberOfResults, item);
                    break;
                case '+':
                    switch (item.charAt(0)) {
                        case '.':
                            matchingString += generateRandomStringPlus(numberOfResults);
                            break;
                        case '[':
                            matchingString += generateRandomStringBracketsPlus(numberOfResults, item);
                            break;
                        default:
                            matchingString += generateStringLiteralPlus(numberOfResults, item.charAt(0));
                            break;
                    }
                    break;
                case '*':
                    switch (item.charAt(0)) {
                        case '.':
                            matchingString += generateRandomStringAsterics(numberOfResults);
                            break;
                        case '[':
                            matchingString += generateRandomStringBracketsAsterics(numberOfResults, item);
                            break;
                        default:
                            matchingString += generateStringLiteralAsterics(numberOfResults, item.charAt(0));
                            break;
                    }
                    break;
                case '?':
                    switch (item.charAt(0)) {
                        case '.':
                            matchingString += generateRandomStringQuestionMark(numberOfResults);
                            break;
                        case '[':
                            matchingString += generateStringBrackets(numberOfResults, item);
                            break;
                        default:
                            matchingString += generateStringLiteralQuestionMark(numberOfResults, item.charAt(0));
                            break;
                    }
                    break;
            }
        }
        return matchingString;
    }

    public List<String> generate(String regEx, int numberOfResults) {
        ArrayList<String> regularResultsList = new ArrayList<>();
        regularExpression = regEx;
        validateSyntax();
        parseTokens();
        for (int i = 0; i < numberOfResults; i++) {
            regularResultsList.add(stringFromTokens(i));
        }
        return regularResultsList;
    }
}