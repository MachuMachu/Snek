public class Transition {
    public int transition (int Q, char input) {             //Q represents current state, input represents input for transition
        int result = 67;                            // state 67 is the dead state, if switch doesn't fall under any cases in switch, it means it is connected to the dead state, hence not accepted/valid lexeme
        switch (Q){
            case 0 -> result = switch (input){     // switch will return the next state of each transition depending on the input char
                case '\'' -> 1;                // for input apostrophe as starting input of identifier
                case '0','1','2','3','4','5','6','7','8','9' -> 3;      //for constant input
                case '|' -> 4;
                case '>' -> 5;
                case '<' -> 6;
                case '-', '+', '%', '*', '/', '^', '=', '&', ';', '(', ')', '{', '}', ',', '\"' , '.'-> 66;
                case '!' -> 8;
                case ':' -> 7;
                case 'S' -> 9;
                case 'E' -> 13;
                case 'M' -> 15;
                case 'o' -> 18;
                case 'i' -> 23;
                case 'd' -> 36;
                case 't' -> 42;
                case 'a' -> 47;
                case 'f' -> 51;
                case 'b' -> 60;
                default -> 67;                  //state 67 represents dead state
            };
            case 1, 2 -> result = switch (input){
                case'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                        '0','1','2','3','4','5','6','7','8','9' -> 2;    // for identifier, after the apostrophe symbol
                default -> 67;
            };
            case 3 -> result = switch (input){
                case '0','1','2','3','4','5','6','7','8','9' -> 3;   //constant
                default -> 67;
            };
            case 4 -> {
                if (input == '|') result = 66;
            }
            case 5 -> result = switch (input){
                case '>' -> 66;
                case '|' -> 7;
                default -> 67;
            };
            case 6 -> result = switch (input){
                case '<' -> 66;
                case '|' -> 7;
                default -> 67;
            };
            case 7 -> { if (input == '=') result = 66; }
            case 8 -> { if (input == '!') result = 66; }
            case 9 -> { if (input == 't') result = 10; }
            case 10 -> { if (input == 'a') result = 11; }
            case 11 -> { if (input == 'r') result = 12; }
            case 12-> { if (input == 't') result = 66; }
            case 13-> { if (input == 'n') result = 14; }
            case 14-> { if (input == 'd') result = 66; }
            case 15-> { if (input == 'a') result = 16; }
            case 16-> { if (input == 'i') result = 17; }
            case 17-> { if (input == 'n') result = 66; }
            case 18-> { if (input == 'u') result = 19; }
            case 19-> { if (input == 't') result = 20; }
            case 20-> { if (input == 'd') result = 21; }
            case 21-> { if (input == 'i') result = 22; }
            case 22-> { if (input == 's') result = 66; }
            case 23-> { if (input == 'n') result = 24; }
            case 24-> result = switch (input){
                case 'c' -> 25;
                case 't' -> 28;
                case 's' -> 33;
                default -> 67;
            };
            case 25-> { if (input == 'a') result = 26; }
            case 26-> { if (input == 's') result = 27; }
            case 27-> { if (input == 'e') result = 66; }
            case 28-> { if (input == 'e') result = 29; }
            case 29-> { if (input == 'g') result = 30; }
            case 30-> { if (input == 'r') result = 31; }
            case 31-> { if (input == 'a') result = 32; }
            case 32-> { if (input == 'l') result = 66; }
            case 33-> { if (input == 'c') result = 34; }
            case 34-> { if (input == 'a') result = 35; }
            case 35-> { if (input == 'n') result = 66; }
            case 36-> { if (input == 'e') result = 37; }
            case 37-> { if (input == 'c') result = 38; }
            case 38-> { if (input == 'i') result = 39; }
            case 39-> { if (input == 'm') result = 40; }
            case 40-> { if (input == 'a') result = 41; }
            case 41-> { if (input == 'l') result = 66; }
            case 42-> result = switch (input){
                case 'h' -> 43;
                case 'r' -> 45;
                default -> 67;
            };
            case 43-> { if (input == 'e') result = 44; }
            case 44-> { if (input == 'n') result = 66; }
            case 45-> { if (input == 'u') result = 46; }
            case 46-> { if (input == 'e') result = 66; }
            case 47-> result = switch (input){
                case 'c' -> 48;
                case 'm' -> 49;
                default -> 67;
            };
            case 48-> { if (input == 't') result = 66; }
            case 49-> { if (input == 'i') result = 50; }
            case 50-> { if (input == 'd') result = 66; }
            case 51-> result = switch (input){
                case 'o' -> 52;
                case 'a' -> 57;
                default -> 67;
            };
            case 52-> { if (input == 'r') result = 53; }
            case 53-> { if (input == 'l') result = 54; }
            case 54-> { if (input == 'o') result = 55; }
            case 55-> { if (input == 'o') result = 56; }
            case 56-> { if (input == 'p') result = 66; }
            case 57-> { if (input == 'l') result = 58; }
            case 58-> { if (input == 's') result = 59; }
            case 59-> { if (input == 'e') result = 66; }
            case 60-> { if (input == 'o') result = 61; }
            case 61-> { if (input == 'o') result = 62; }
            case 62-> { if (input == 'l') result = 63; }
            case 63-> { if (input == 'e') result = 64; }
            case 64-> { if (input == 'a') result = 65; }
            case 65-> { if (input == 'n') result = 66; }
            case 66,67-> result = 67;

        }
        return result;
    }




}//transition class
