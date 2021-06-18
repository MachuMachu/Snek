import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;


public class FiniteStateAutomaton {
    int state = 0;
    boolean result;
    String lexeme = "";
    File symbolFile, wordFile;
    File tempFile;
    File outputFile;

    FiniteStateAutomaton(File inputFile) throws IOException {
        symbolFile = new File ("SymbolsTable.txt");
        wordFile = new File ("WordsTable.txt");
        tempFile = inputFile;
        outputFile = new File ( tempFile.getParent()+"\\outputFile.txt");

        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
        writer.printf("%-40s%-40s%-40s\n\n", "LEXEME", "TOKEN", "DEFINITION");
        writer.close();

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(Objects.requireNonNull(fileReader));
        int intX = -1, marker = 0, ctr = 0;
        char charX;
        while (true) {
            try {
                if ((intX = bufferedReader.read()) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            charX = (char) intX;
            state = transition(state, charX);
            if (state != 14) {                                                                 //state 14 is a dead state which serves as a terminating condition for each lexeme
                if (marker == 12) {                                                            //characters will continuously concatenate until it reaches a dead state
                    result = lookup(lexeme, symbolFile);                                        //states 10,16 and 12 are final states for quotation and comment symbol, this is
                    lexeme = "";                                                                //for the program to distinguish text literals from comments
                    System.out.print("Lexeme: " + lexeme + "      Symbol\n");
                }

                if (state == 10 || state == 16 || state == 12){
                    lexeme = lexeme.concat(Character.toString(charX));
                    result = lookup(lexeme, symbolFile);
                    System.out.print("Lexeme: " + lexeme + "      Symbol\n");
                    marker = state;
                    lexeme = "";
                }else if ((state >= 17 && state <= 20) || state == 15 || state == 22){          //states 17,20,15 and 22 are states in between the quotations and comment symbols
                    lexeme = lexeme.concat(Character.toString(charX));
                    marker = state;
                    ctr++;
                }else if (state == 21 || state == 23) {                                         //states 21 and 23 are final states for end quote and comment symbol
                    lexeme = lexeme.concat(Character.toString(charX));
                    int stringSize = lexeme.length();
                    if (state == 21) {
                        if (stringSize > 9) {
                            print(lexeme.substring(0, 9) + "...", "Multiple Line Comment", "Comment that occupies more than one line");
                            System.out.print("Lexeme: " + lexeme.substring(0, 9) + "...      Multiple Line Comment\n");
                        }
                        else if(stringSize != 2){
                            print(lexeme.substring(0, ctr - 1), "Multiple Line Comment", "Comment that occupies more than one line");
                            System.out.print("Lexeme: " + lexeme.substring(0, ctr - 1) + "     Multiple Line Comment\n");
                        }
                        result = lookup(lexeme.substring(ctr - 1, ctr + 1), symbolFile);
                        System.out.print("Lexeme: " + lexeme.substring(ctr - 1, ctr + 1) + "      Symbol\n");
                    } else {
                        if (stringSize != 1) {
                            if (stringSize > 9) {
                                print(lexeme.substring(0, 9) + "...", "Text Literal", "Value for Text data type");
                                System.out.print("Lexeme: " + lexeme.substring(0, 9) + "...      Text Literal\n");
                            }
                            else {
                                print(lexeme.substring(0, ctr), "Text Literal", "Value for Text data type");
                                System.out.print("Lexeme: " + lexeme.substring(0, ctr) + "     Text Literal\n");
                            }
                        }
                        lookup(lexeme.substring(ctr, ctr + 1), symbolFile);
                        System.out.print("Lexeme: " + lexeme.substring(ctr, ctr + 1) + "      Symbol\n");
                    }
                    lexeme = "";
                    ctr = 0;
                    state = 0;
                } else {
                    marker = state;
                    lexeme = lexeme.concat(Character.toString(charX));
                }
            } else{
                FinalStates(marker);
                lexeme = "";
                lexeme = lexeme.concat(Character.toString(charX));
                state = 0;
                state = transition(state, charX);
                marker = state;
            }//end else
        }// end while
        if(!(lexeme.isEmpty())){
            FinalStates(marker);
        }
    }

    int transition(int Q, char input) {                                        //this method represents Snek automaton
        int result = 14;                                                       //it will return value of the state after each transition
        switch (Q) {
            case 0 -> result = switch (input) {
                case '\'' -> 1;
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> 3;
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' -> 5;
                case '|' -> 6;
                case '!' -> 7;
                case '<' -> 8;
                case '>' -> 9;
                case ':' -> 11;
                case '-', '+', '%', '*', '/', '^', '=', '&', ';', '(', ')', '{', '}', ',' -> 13;
                case '\"' -> 12;
                default -> 14;
            };
            case 1, 2 -> result = switch (input) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> 2;    // for identifier, after the apostrophe symbol
                default -> 14;
            };
            case 3 -> result = switch (input) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> 3;
                case '.' -> 4;
                default -> 14;
            };
            case 4 -> result = switch (input) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> 4;
                default -> 14;
            };
            case 5 -> result = switch (input) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' -> 5;    // for identifier, after the apostrophe symbol
                default -> 14;
            };
            case 6 -> { if (input == '|') result = 13; }
            case 7 -> { if (input == '!') result = 13; }
            case 8 -> { if (input == '|') result = 11; }
            case 9 -> result = switch (input) {
                case '|' -> 11;
                case '>' -> 10;
                case '/' -> 16;
                default -> 14;
            };
            case 10,15 -> result = switch (input) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        ' ','-', '+', '%', '*', '/', '^', '=', '&', ';', '(', ')', '{', '}', ',', '\"' , '.', '|', '!', ':','<', '>' -> 15;
                default -> 14;
            };
            case 11 -> { if (input == '=') result = 13; }
            case 12,22 -> result = switch (input) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    ' ','-', '+', '%', '*', '/', '^', '=', '&', ';', '(', ')', '{', '}', ',', '.', '|', '!', ':', '<', '>' -> 22;
                case '\"' -> 23;
                default -> 14;
            };

            case 16,17 -> result = switch (input) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        ' ','-', '+', '%', '*', '^', '=', '&', ';', '(', ')', '{', '}', ',', '\"' , '.', '|', '!', ':', '<', '>' -> 17;
                case '\r' -> 18;
                case '/' -> 20;
                default -> 14;
            };
            case 18 -> { if (input == '\n') result = 19; }
            case 19 -> result = switch (input) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        ' ','-', '+', '%', '*', '^', '=', '&', ';', '(', ')', '{', '}', ',', '\"' , '.', '|', '!', ':', '>', '<' -> 17;
                case '\r' -> 18;
                case '/' -> 20;
                default -> 14;
            };
            case 20 -> result = switch (input) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        ' ','-', '+', '%', '*', '^','/', '=', '&', ';', '(', ')', '{', '}', ',', '\"' , '.', '|', '!', ':', '>' -> 17;
                case '\r' -> 18;
                case '<' -> 21;
                default -> 14;
            };
            case 23 -> { if (input == '\n') result = 24; }
        }
        return result;
    }

    void FinalStates (int marker) throws IOException {
        PrintWriter writer =new PrintWriter(new FileWriter(outputFile, true));
        if (marker == 2) {
            print (lexeme, "Identifier", "Variable Name");
            System.out.print("Lexeme: " + lexeme + "      Identifier     Variable Name\n");
        }else if (marker == 3) {
            print (lexeme, "Integral Literal", "Integral Value");
            System.out.print("Lexeme: " + lexeme + "     Integral Literal     Integral Value\n");
        }else if (marker == 4) {
            print (lexeme, "Decimal Literal", "Decimal Value");
            System.out.print("Lexeme: " + lexeme + "     Decimal Literal     Decimal Value\n");
        }else if (marker == 5) {
            result = lookup (lexeme, wordFile);
            if (!result) print (lexeme, "Invalid", "Not part of the syntax");
            System.out.print("Lexeme: " + lexeme + "          Invalid/Keyword\n");
        }else if (marker == 13 || marker == 7 || marker == 9 || marker == 10 || marker == 12 || marker == 22) {
            result = lookup(lexeme,symbolFile);
            if (!result) print (lexeme, "Invalid", "Not part of the syntax");
            System.out.print("Lexeme: " + lexeme + "          Symbol\n");
        }else if (marker == 15){
            print (lexeme, "Single Line Comment", "Comment that occupies one line");
            System.out.print("Lexeme: " + lexeme + "          Single Line Comment\n");
        }//end if
    }

    boolean lookup (String lexeme, File table) throws IOException {
        Scanner objScan = null;
        String line;
        String[] strings;
        boolean result = false;
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true));

        try {
            objScan = new Scanner(table);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(objScan).nextLine();
        while (objScan.hasNextLine()) {
            line = objScan.nextLine();
            strings = line.split("\t\t\t");
            if (strings[0].equals(lexeme)) {
                writer.printf("%-40s%-40s%-40s\n", strings[0], strings[1], strings[2]);
                writer.close();
                result = true;
            }
        }
        return result;
    }

    void print (String lexeme, String token, String definition) throws IOException {
        PrintWriter writer =new PrintWriter(new FileWriter(outputFile, true));

        writer.printf("%-40s%-40s%-40s\n", lexeme, token, definition);
        writer.close();

    }
}