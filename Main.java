import java.util.Scanner;
class Main {
  
  public static void main(String[] args)
	{
		Magpie4 maggie = new Magpie4();
		
		System.out.println (maggie.getGreeting());
		Scanner in = new Scanner (System.in);
		String statement = in.nextLine();
    statement = statement.toLowerCase();
		
		while (!statement.equals("Bye"))
		{
			System.out.println (maggie.getResponse(statement));
			statement = in.nextLine();
		}
	}



}