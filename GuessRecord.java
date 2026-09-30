
/**
 * GuessRecord represents one turn of gameplay by pairing a 
 * player’s guess with the feedback generated for that guess. 
 * This simplifies storing and printing game history.
 *
 * Michael Davidson
 * GuessRecord.java
 */
public class GuessRecord{
    private Code guess;
    private String feedback;
    
    /*
    Creates a record object of a guess and its feedback 
    */
    public GuessRecord(Code guess, String feedback){
        this.guess = guess;
        this.feedback = feedback;
    }
    /*
    Void method to return a Code object guess 
    */
    public Code getGuess(){
        return guess;
    }
    /*
    Void method to return a Code object guess 
    */
    public String getFeedback(){
        return feedback;
    }
    @Override
    public String toString(){
        if (feedback.equals("")){
            return guess.toString() + "-> no matches";
        } else{
            return guess.toString() + " -> " + feedback;
        }
    }
}