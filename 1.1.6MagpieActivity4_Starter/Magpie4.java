/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hello. My name is Ms. Eppie! This is my office hours. I teach a bunch of classes. What do you need help with?";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "hello") >= 0 || findKeyword(statement, "hi") >=0)
		{
			response = "Don't waste time with greetings. What do you want? Hurry up I don't have time for this";
		}
		else if (findKeyword(statement, "how") >= 0
				|| findKeyword(statement, "why") >= 0
				|| findKeyword(statement, "what") >= 0
				|| findKeyword(statement, "When") >= 0)
		{
			response = "I don’t answer stupid questions.";
		}
    else if (findKeyword(statement, "ms. eppie") >= 0)
		{
			response = "Yes I am the best in the world, now what do you want??";
		}
    else if (findKeyword(statement, "make up") >= 0)
		{
			response = "You missed your chance you stinky little thing";
		}
    else if (findKeyword(statement, "grade") >= 0)
		{
			response = "You deserve nothing but an F! Next time don't waste my time with this type of conversation";
		}
    else if (findKeyword(statement, "score") >= 0)
		{
			response = "You got what you deserved you smelly little cheese ball";
		}
    else if (findKeyword(statement, "appointment") >= 0)
		{
			response = "No, you are a waste of my time.";
		}
    else if (findKeyword(statement, "essay") >= 0)
		{
			response = "you did bad on that. Complete F!";
		}

		// Responses which require transformations
		else if (findKeyword(statement, "i want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				response = getRandomResponse();
			}
		}
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "i want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "HAHAH no! you do not get to " + restOfStatement + " EVER! that is an awful thing to ask me!";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 6;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
			
		if (whichResponse == 0)
		{
			response = "Hmmm.";
		}
		else if (whichResponse == 1)
		{
			response = "Do you really think so?";
		}
		else if (whichResponse ==2)
		{
			response = "You don't say.";
		}

    else if (whichResponse == 3)
		{
			response = "Get back on topic. I am here for SCHOOL only";
		}
    else if (whichResponse == 4)
		{
			response = "Comments like that are why you are failing.";
		}
    else if (whichResponse ==5)
		{
			response = "If you are just going to waste my time, you may as well leave.";
		}

    else if (whichResponse ==5)
		{
			response = "Elaborate.";
		}

		return response;
	}

}
