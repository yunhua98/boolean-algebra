import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Comparator;
public class Simplifier {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int variableCount;

		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter number of variables: ");
		
		variableCount = in.nextInt();
		
		if (variableCount > 26)
			System.out.println("Warning: too many variables. Program will display minterm groupings without converting to equation form.");
		
		double mintermCount = Math.pow(2, variableCount);
		
		System.out.println("Enter minterms (in the form 'a, b, c,...'): ");
		
		String mintermString = in.next();
		
		ArrayList<Integer> decimalList = Minterms.parseString(mintermString);
		
		if (Collections.max(decimalList) > mintermCount)
		{
			System.out.println("You're stupid.");
		}
			
		Collections.sort(decimalList);
		
		ArrayList<Minterm> mintermsList = new ArrayList<Minterm>();
		
		for (int decimal : decimalList)
		{
			mintermsList.add(new Minterm(decimal, variableCount));
		}
		
		ArrayList<Minterm> mergedMintermsList = new ArrayList<Minterm>();
		
		for (int mergeCount = 0; mergeCount < variableCount; mergeCount++)
		{
			mergedMintermsList = merge(mintermsList, variableCount);
			//mergedMintermsList.addAll(merge(mintermsList, variableCount));
			for (Minterm newMinterm : mergedMintermsList)
			{
				ArrayList<Minterm> removeList = new ArrayList<Minterm>();
				for (Minterm oldMinterm: mintermsList)
				{
					if (newMinterm.getDecimals().containsAll(oldMinterm.getDecimals()))
						removeList.add(oldMinterm);
				}
				mintermsList.removeAll(removeList);
			}
			mergedMintermsList.addAll(mintermsList);
			mintermsList.clear();
			mintermsList.addAll(mergedMintermsList);
		}
		
		ArrayList<ArrayList<Minterm>> coveredMinterms = new ArrayList<ArrayList<Minterm>>();
		
		for (int mintermNumber = 0; mintermNumber < mintermCount; mintermNumber++)
		{
			coveredMinterms.add(new ArrayList<Minterm>());
			for (Minterm correspondingMinterm : mergedMintermsList)
			{
				if (correspondingMinterm.getDecimals().contains(mintermNumber))
					coveredMinterms.get(mintermNumber).add(correspondingMinterm);
			}
		}
		
		ArrayList<Integer> finishedList = new ArrayList<Integer>();
		ArrayList<Minterm> simplifiedMinterms = new ArrayList<Minterm>();
		
		for (int representations = 1; representations <= mergedMintermsList.size(); representations++)
		{
			for (int mintermNumber = 0; mintermNumber < coveredMinterms.size(); mintermNumber++)
			{
				ArrayList<Minterm> coveredMinterm = new ArrayList<Minterm>();
				coveredMinterm = coveredMinterms.get(mintermNumber);
				if (!finishedList.contains(mintermNumber))
				{
					if (coveredMinterm.size() == representations)
					{
						int span = (int) mintermCount;
						int biggestIndex = 0;
						int listSize = 0;
						for (int i = 0; i < coveredMinterm.size(); i++)
						{
							if (coveredMinterm.get(i).getDecimals().size() > listSize)
							{
								listSize = coveredMinterm.get(i).getDecimals().size();
								biggestIndex = i;
							}
						}
						simplifiedMinterms.add(coveredMinterm.get(biggestIndex));
						finishedList.addAll(coveredMinterm.get(biggestIndex).getDecimals());
					}
				}
			}
		}
		
		String booleanExpression = "";
		ArrayList<Character> letters = new ArrayList<Character>();
		letters.addAll(Arrays.asList('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'));
		
		for (Minterm minterm : simplifiedMinterms)
		{
			if (variableCount > 26)
			{
				System.out.println(minterm.getBinary());
				System.out.println(minterm.getDecimals());
			}
			else
			{
				booleanExpression += " + ";
				for (int charNumber = 0; charNumber < minterm.getBinary().length(); charNumber++)
				{
					if (minterm.getBinary().charAt(charNumber) == '0')
					{
						booleanExpression += letters.get(charNumber) + "'";
					}
					if (minterm.getBinary().charAt(charNumber) == '1')
					{
						booleanExpression += letters.get(charNumber);
					}
				}
			}
		}
		System.out.println(booleanExpression.substring(3));
		
	}

	public static ArrayList<Minterm> merge(ArrayList<Minterm> mintermsList, int variableCount)
	{
		ArrayList<Minterm> mergedMinterms = new ArrayList<Minterm>();
		ArrayList<ArrayList<Minterm>> sortedMinterms = new ArrayList<ArrayList<Minterm>>();
		for (int i = 0; i <= variableCount; i++)
		{
			sortedMinterms.add(new ArrayList<Minterm>());
			for (Minterm minterm : mintermsList)
			{
				if (minterm.getNumOfOnes() == i)
					sortedMinterms.get(sortedMinterms.size() - 1).add(minterm);
			}
		}
		
		for (int listSelector = 0; listSelector < variableCount; listSelector++)
		{
			for (Minterm fewerOnes : sortedMinterms.get(listSelector))
			{
				//System.out.print("\n" + fewerOnes.getDecimals());
				for (Minterm moreOnes : sortedMinterms.get(listSelector + 1))
				{
					//System.out.print(moreOnes.getDecimals());
					int differences = 0;
					int indexOfChange = 0;
					for (int j = 0; j < variableCount; j++)
					{
						if (fewerOnes.getBinary().charAt(j) != moreOnes.getBinary().charAt(j))
						{
							differences++;
							indexOfChange = j;
						}
					}
					if (differences == 1)
					{
						String mergedBin = fewerOnes.getBinary().substring(0, indexOfChange) + "X" + fewerOnes.getBinary().substring(indexOfChange + 1);
						ArrayList<Integer> mergedDec = new ArrayList<Integer>();
						mergedDec.addAll(fewerOnes.getDecimals());
						mergedDec.addAll(moreOnes.getDecimals());
						Minterm mergedMinterm = new Minterm(mergedDec, mergedBin, variableCount);
						boolean isUnique = true;
						for (Minterm mMinterm : mergedMinterms)
						{
							if (mMinterm.equals(mergedMinterm))
								isUnique = false;
						}
						if (isUnique)
							mergedMinterms.add(mergedMinterm);
					}
				}
			}
		}
		
		return mergedMinterms;
	}
	
}
