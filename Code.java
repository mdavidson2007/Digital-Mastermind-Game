import java.util.ArrayList;
/**
 * Checks and initializes default and custom Code objects.
 *
 * Michael Davidson
 * Code.java
 */
public class Code{
    private static final String[] VALID_COLORS = 
    {"red","green","yellow","blue","purple","gray"};
    private ArrayList<String> pegs;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GRAY = "\u001B[37m";
    
    /*
    Declares a custom constructor of a code object based on
    an array list of String color objects.
    */
    public Code(ArrayList<String> inputColors){
        this.pegs = new ArrayList<String>(); 
        for (String color : inputColors){
            this.pegs.add(color);
        }
    }
    /*
    Default constructor for a random code
     */
    public Code(){
        this.pegs = new ArrayList<String>();
        for (int i=0; i<4; i++){
            int randomIndex = (int)(Math.random()*6);
            String color = VALID_COLORS[randomIndex];
            this.pegs.add(color);
        
        }
    }
    /*
    Checks if a String color is a valid color contained in
    the constant static String array of valid colors
     */
    public static boolean isValidColor (String color){
        for (String valid : VALID_COLORS){
            if (valid.equals(color)){
                return true;
            }
        }
        return false;
    }
    /*
     Checks if an array list for a code is size 4
     If it is, checks each color to see if it is valid
     */
    public static boolean isValidCode(ArrayList<String> colors){
        if (colors.size()!=4){
            return false;
        }
        for (String color : colors){
            if (!isValidColor(color)){
                return false;
            }
        }
        return true;
    }
    /*
     Returns the color at a certain index
     */
    public String getColorAt(int index){
        return pegs.get(index);
    }
    @Override
    public String toString(){
        String output = "";
        for (String color: pegs){
            if (color.equals("red")) {
            output += ANSI_RED + color + ANSI_RESET + " ";
        }

        else if (color.equals("green")) {
            output += ANSI_GREEN + color + ANSI_RESET + " ";
        }
        else if (color.equals("yellow")) {
            output += ANSI_YELLOW + color + ANSI_RESET + " ";
        }
        else if (color.equals("blue")) {
            output += ANSI_BLUE + color + ANSI_RESET + " ";
        }
        else if (color.equals("purple")) {
            output += ANSI_PURPLE + color + ANSI_RESET + " ";
        }
        else if (color.equals("gray")) {
            output += ANSI_GRAY + color + ANSI_RESET + " ";
        }
        }
        return output;
    }
}