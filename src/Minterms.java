import java.util.ArrayList;
public class Minterms {

	public static ArrayList<Integer> parseString(String mintermString)
	{
		mintermString += ",";
		String parseSub = ""; //substring that is used to temporarily store the numbers being parsed;
		ArrayList<Integer> mintermsList = new ArrayList<Integer>();
		for (int i = 0; i < mintermString.length(); i++)
		{
			if (!(mintermString.substring(i,i+1).equals(",") || mintermString.substring(i,i+1).equals(" ")))
			{
				while (!(mintermString.substring(i,i+1).equals(",") || mintermString.substring(i,i+1).equals(" ")) && i < mintermString.length())
				{
					parseSub += mintermString.substring(i,i+1);
					i++;
				}
				if (parseSub.length() > 0)
				{
					mintermsList.add(Integer.parseInt(parseSub));
				}
					parseSub = "";
			}
		}
		return mintermsList;
	}
	
	public static String dec2bin(int decimal, int bits)
	{
		String binary = "";
		for (int i = 0; i < bits; i++)
		{
			binary = Integer.toString(decimal % 2) + binary;
			decimal /= 2;
		}
		return binary;
	}
	
	public static int bin2dec(String binary)
	{
		int decimal = 0;
		for (int i = 0; i < binary.length(); i++)
		{
			if (binary.charAt(i) == '1')
			{
				decimal += Math.pow(2, binary.length() - 1 - i);
			}
		}
		return decimal;
	}
	
}
