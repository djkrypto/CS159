import java.util.ArrayList;
/*********************************************************************
 * @author David Johnson
 * @version Mar 21, 2014
 *
 * This class parses java-like expressions.
 ********************************************************************/
public class ExpressionParser
{
    private final static String[] OPERATORS    = { "||", "&&", "==", "!=", "<", ">", "<=",
        ">=", "+", "-", "*", "/", "%"         };
    private final static int OPERATORS_LENGTH = OPERATORS.length;

    /*********************************************************************
     * This method returns whether or not the parameter is an expression.
     * @param line The string to parse
     * @return Whether the string is an expression or not
     ********************************************************************/
    public static boolean isExpr(String line)
    {
    	boolean isExp = false;
    	String expression;
    	expression = line.trim();

      if (isParenExpr(expression))
        isExp = true;
    	else if (isBinaryExpr(expression))
        isExp = true;
      else if (isInt(expression))
        isExp = true;
      else if (isVar(expression))
        isExp = true;

        return isExp;
    }

    /*********************************************************************
     * This method returns whether or not the parameter is a variable.
     * @param line The string to parse
     * @return Whether the string is a variable or not
     ********************************************************************/
    public static boolean isVar(String line)
    {
        boolean isVariable = false;
        int length = line.length();

        if (isLet(line.substring(0,1)))
        {
        	if (length == 1)
        		isVariable = true;
        	else if (isVarRem(line.substring(1,length)))
        		isVariable = true;
        }

        return isVariable;
    }

    /*********************************************************************
     * This method returns whether or not the parameter is a letter.
     * @param line The String to parse
     * @return Whether the String is a letter or not
     ********************************************************************/
    public static boolean isLet(String letter)
    {
    	boolean isLetter = false;

    	if (letter != null)
        {
        	if (letter.matches("[a-zA-Z_$]+"))
        		isLetter = true;
        }

    	return isLetter;
    }

    /***************************************************************************
     * This method returns whether or not the parameter is a variable remainder.
     * @param line The String to parse
     * @return Whether the String is a variable remainder or not
     **************************************************************************/
    public static boolean isVarRem(String input)
    {
    	boolean isRem = false;
    	int length = input.length();

    	if (isLet(input.substring(0,1)) || isInt(input.substring(0,1)))
    	{
    		if (length > 1)
    			isRem = isVarRem(input.substring(1,length));
    		else
    			isRem = true;
    	}

    	return isRem;
    }

    /*********************************************************************
     * This method returns whether or not the parameter is an integer.
     * @param line The string to parse
     * @return Whether the string is an integer or not
     ********************************************************************/
    public static boolean isInt(String line)
    {
        boolean isInteger = false;

      if (line != null)
    	{
	    	if (line.matches("\\d+"))
	    	  isInteger = true;
    	}

        return isInteger;
    }

    /*********************************************************************************
     * This method returns whether or not the parameter is a parenthetical expression.
     * @param line The string to parse
     * @return Whether the string is a parenthetical expression or not
     ********************************************************************************/
    public static boolean isParenExpr(String line)
    {
    	int length = line.length();
    	boolean isExp = false;

    	if (length > 2)
    	{
    		if (line.charAt(0) == '(' && line.charAt(length-1) == ')')
        	{
    			if (isExpr(line.substring(1,length-1)))
            		isExp = true;
        	}
    	}

        return isExp;
    }

    /*********************************************************************
     * This method returns whether or not the parameter is a binary expression.
     * @param line The string to parse
     * @return Whether the string is a binary expression or not
     ********************************************************************/
    public static boolean isBinaryExpr(String line)
    {
        String firstHalf;
        String secondHalf;
        int length = line.length();
        int locLength;
        boolean isExp = false;
        ArrayList<Integer> location = new ArrayList<Integer>();
        ArrayList<Integer> opLength = new ArrayList<Integer>();
        //initial search of line must begin at 0, aka tempLocation+1
        int tempLocation = -1;
        int tempOpLength = 0;

        /*********************************************
         * check length to protect against expressions
         * such as when the operand is the expression
         ********************************************/
        if (length > 2)
        {
        	for (int ii = 0; ii < OPERATORS_LENGTH; ii++)
            {
            	tempLocation = line.indexOf(OPERATORS[ii],tempLocation+1);
            	tempOpLength = OPERATORS[ii].length();
            	// Not equal to -1, the first element, or the last element of string
        		if (tempLocation > 0 && tempLocation < length-tempOpLength)
        		{
        			location.add(line.indexOf(OPERATORS[ii],tempLocation));
        			opLength.add(OPERATORS[ii].length());
            		ii--; //we must keep checking the string for the same operand
        		}
            }
            locLength = location.size();

            for (int jj = 0; jj < locLength; jj++)
            {
            	tempLocation = location.get(jj);
            	firstHalf = line.substring(0,tempLocation);
               	secondHalf = line.substring(location.get(jj)+opLength.get(jj),length);
                if (isExpr(firstHalf) && isExpr(secondHalf))
                {
                	isExp = true;
    				jj = locLength; // instant end in order to save runtime
                }
            }
        }

        return isExp;
    }
}
