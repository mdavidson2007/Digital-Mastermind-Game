import java.util.ArrayList;
import java.util.Scanner;
/**
 * Controls the entire game.
 *
 * Michael Davidson
 * Game.java
 */
public class Game{
    private Code secretCode;
    private ArrayList<GuessRecord> history;
    private int guessesRemaining;
    private Scanner scnr;
    private boolean cheatMode;
    private boolean gameWon;
    
        
    /*
    Default constructor for a Game of Mastermind. 
    */
    public Game(){
        this.secretCode = new Code();
        this.history = new ArrayList<GuessRecord>();
        this.guessesRemaining = 10;
        this.scnr = new Scanner(System.in);
        this.gameWon = false;
        printIntro();
    }
    /*
    Prints the necessary information for the user to play
    Mastermind, and calls askCheatMode.
    */
    public void printIntro(){
        System.out.println("Welcome to Mastermind");
        System.out.println("Your Opponent is the Computer");
        System.out.println("It created a secret 4 color code");
        System.out.println("10 guesses to break the code");
        System.out.println("To create a guess:");
        System.out.println("Choose any 4 color combo");
        System.out.println("Each color in your guess must be valid");
        System.out.println();
        System.out.println("Valid color guesses are:");
        System.out.println("r (red), y (yellow), g (green)");
        System.out.println("a (gray), p (purple), or b (blue)");
        System.out.println();
        System.out.println("Ex: To guess red,red,blue,blue:");
        System.out.println("Enter 'rrbb'");
        System.out.println();
        System.out.println("Can also type 'h' to see past guesses (history)");
        System.out.println();
        askCheatMode();
    }
    /*
    Asks the user if they want to turn on or off cheat mode,
    then turns it on if their response was on.
    */
    public void askCheatMode(){
        System.out.println("Cheat mode? (enter on/off)");
        String response = scnr.nextLine();
        if (response.equals("on")){
            cheatMode = true;
            System.out.println("Code is: ");
            System.out.println(secretCode.toString());
        } else{
            cheatMode = false;
        }
    }
    /*
    Calls takeTurn until
    either there are no guesses left or the user wins the
    game. Reports the user's result once either condition is
    met.
    */
    public void play(){
        while (guessesRemaining>0 && !gameWon){
            takeTurn();
        }
        if (gameWon){
            System.out.println("You won after " + (10-guessesRemaining) + " guesses");
            System.out.println("Answer was: " + secretCode.toString());
        }else {
            System.out.println("You lost after 10 guesses");
            System.out.println("Answer was: " + secretCode.toString());
        }
    }
    /*
    Tells the user how many guesses they have and prompts
    them to either guess or view history. 
    */
    public void takeTurn(){
        System.out.println();
        System.out.println("Guesses Remaining:");
        System.out.println(guessesRemaining);
        System.out.println();
        System.out.println("Enter a guess or h to view history");
        System.out.println();
        String input = scnr.nextLine();
            if (input.equals("h")){
                printHistory();
            } else{
                processGuess(input);
            }
        
        
    }
    /*
    Takes the users String input guess, uses it in parse
    Guess to transform the String into a Code object. Uses
    that object to generate Feedback, and uses the Code
    guess and feedback String to create a history entry.
    Subtracts a guess each time one is used and checks if
    the user's feedback was "hhhh" resulting in a win.
    */

    public void processGuess(String input){
        Code guess = parseGuess(input);
        if (guess == null) {
            System.out.println("Invalid guess. Enter exactly 4 valid letters");
            System.out.println("Use r, y, g, a, p, or b.");
            return;
        }
        String feedback = generateFeedback(guess);
        GuessRecord entry = new GuessRecord(guess, feedback);
        history.add(entry);
        guessesRemaining -= 1;
        if (feedback.equals("")) {
            System.out.println("No hits or partials.");
        } else {
            System.out.println(feedback);
        }
        if (feedback.equals("hhhh")){
            gameWon = true;
        }
    }
    /*
    Takes the users String input guess, converts each letter
    into the actual color it represents, puts those colors 
    into an array list then checks if the array list of 
    Strings is a valid list. If so, a Code object is made 
    from that array list of Strings (colors) and sent into 
    processGuess.
    */
    public Code parseGuess(String input){
        ArrayList<String> parsedColors = new ArrayList<String>();
        input.toLowerCase();
        if (input.length()!=4){
            return null;
        }
        String[] divided = input.split("");
        for  (String l : divided){
            if (l.equals("r")){
                parsedColors.add("red");
            }
            if (l.equals("y")){
                parsedColors.add("yellow");
            }
            if (l.equals("g")){
                parsedColors.add("green");
            }
            if (l.equals("a")){
                parsedColors.add("gray");
            }
            if (l.equals("p")){
                parsedColors.add("purple");
            }
            if (l.equals("b")){
                parsedColors.add("blue");
            }
        }
        boolean check = Code.isValidCode(parsedColors);
        if (check){
            Code parsed = new Code(parsedColors);
            return parsed;
        }
        return null;
    }
    /*
    Generates and returns the feedback string called in 
    takeTurn. Takes an input from the Code object guess.
    */
    public String generateFeedback(Code guess){
        boolean[] secretCodeMatched = {false, false, false, false};
        boolean[] guessMatched = {false, false, false, false};
        String feedback = "";
        feedback+=findHits(guess, secretCodeMatched, guessMatched);
        feedback+=findPartials(guess, secretCodeMatched, guessMatched);
        return feedback;
    }
    /*
    Checks if there are direct matches at the 4 indexes and
    for each match found returns an "h". These are added to 
    the ongoing feedback String. Takes inputs of the Code
    object guess, and two boolean arrays of 4 false.
    */
    public String findHits(Code guess, boolean[] false1, boolean[] false2){
        String feedback = "";
        for (int i = 0; i<4; i++){
            if (guess.getColorAt(i).equals(secretCode.getColorAt(i))){
                feedback += "h";
                false1[i]=true;
                false2[i]=true;
            }
        }
        return feedback;
    }
    /*
    Checks for partial correctness with respect to repeats 
    using 4 separate conditionals. The parameters are the
    the Code object guess, and an updated boolean array for checking both the
    code and the guess. For each color in guess, there is a 
    check at all 16 index combinations. If a match is found,
    the index is set to true and that index is no longer
    checked for a match. It returns a string of 'p' or
    multiple which is added to the larger feedback string.
    */
    public String findPartials(Code guess, boolean[] secretCodeMatched, boolean[] guessMatched){
        String partials = "";
        for (int guessIndex=0; guessIndex<4; guessIndex++){
            if (!guessMatched[guessIndex]){
                int secretIndex = 0;
                    while (secretIndex<4 && !guessMatched[guessIndex]){
                        if (!secretCodeMatched[secretIndex]){
                            if (guess.getColorAt(guessIndex).equals(secretCode.getColorAt(secretIndex))){
                                partials+="p";
                                guessMatched[guessIndex]=true;
                                secretCodeMatched[secretIndex]=true;
                            }
                        }
                        secretIndex+=1;
                    }
                }
            }
        return partials;
    }
    /*
    When the user calls their history with 'h', their guess
    history is printed.
    */
    public void printHistory(){
        if (history.isEmpty()){
            System.out.println("No guesses yet.");
        } else{
            for (GuessRecord entry : history){
                System.out.println(entry.toString());
            }
        }
    }
    
    }