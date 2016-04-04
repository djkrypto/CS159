import java.util.Scanner;

/*********************************************************************
 * @author David Johnson
 * @version Mar 21, 2014
 *
 * This program checks strings to see if they are expressions.
 ********************************************************************/
public class Expressions
{
    /*********************************************************************
     * The main method for this program.
     * @param args Not used
     ********************************************************************/

	public static void main(String [] args)
    {
    	Scanner          stdin;
        String           status;
        String           line;

        stdin = new Scanner (System.in);
        line = getLine(stdin);
        
    	while (line.length() != 0 && line.charAt(0) != '?')
        {
            if (ExpressionParser.isExpr(line))
                status = "correct";
            else
                status = "incorrect";
            System.out.printf("Expression %9s: '%s'\n", status, line);
            line = getLine(stdin);
            if (line.length() == 0)
            {
            	status = "incorrect";
                System.out.printf("Expression %9s: '%s'\n", status, line);
                line = getLine(stdin);
            }
        }
    }

    /*********************************************************************
     * @param input
     * @return
     ********************************************************************/
    public static String getLine(Scanner input)
    {
        String line;

        if (input.hasNextLine())
            line = input.nextLine();
        else
            line = "?";
        return line;
    }
}