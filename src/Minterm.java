import java.util.ArrayList;
import java.util.Collections;
public class Minterm {

	private int decimal;
	private int bits;
	private String binary;
	private ArrayList<Integer> decimalList;
	
	public Minterm(int decimal, int bits)
	{
		this.decimal = decimal;
		this.bits = bits;
		binary = Minterms.dec2bin(decimal, bits);
		decimalList = new ArrayList<Integer>();
		decimalList.add(decimal);
	}
	
	public Minterm(ArrayList<Integer> decimalList, String binary, int bits)
	{
		this.bits = bits;
		this.binary = binary;
		this.decimalList = new ArrayList<Integer>();
		this.decimalList.addAll(decimalList);
		Collections.sort(decimalList);
	}
	
	public String getBinary()
	{
		return binary;
	}
	
	public ArrayList<Integer> getDecimals()
	{

		Collections.sort(decimalList);
		return decimalList;
	}

	public int getNumOfOnes()
	{
		int numOfOnes = 0;
		for (int i = 0; i < binary.length(); i++)
		{
			if (binary.charAt(i) == '1')
				numOfOnes++;
		}
		return numOfOnes;
	}
	
	public boolean equals(Minterm comparedMinterm)
	{
		return binary.equals(comparedMinterm.getBinary());
	}
}
