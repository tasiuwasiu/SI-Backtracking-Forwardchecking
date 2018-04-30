import java.util.LinkedList;
import java.util.List;

public class SquareBacktrack implements Algorithm
{
	private int size;
	private boolean hasSolution = false;
	private List<Integer>[][] sets;
	private int[][] solution;
	private List<Integer> fullSet;
	private int currentX = 0;
	private int currentY = 0;
	
	@Override
	public boolean execute(int number, int range)
	{
		init (number);
		findSolution();
		return hasSolution;
	}
	
	private void init(int number)
	{
		size = number;
		sets = new LinkedList[size][size];
		fullSet = new LinkedList<Integer>();
		solution = new int[size][size];
		
		for (int i=0; i<size; i++)
			fullSet.add(i);
		
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
			{
				solution[i][j] = -1;
				sets[i][j] = new LinkedList<Integer>();
				sets[i][j].addAll(fullSet);
			}
	}
	
	private void findSolution()
	{
		for(; currentY<size; currentY++)
		{
			for(; currentX<size; currentX++)
			{
				if(!findPossibleValue())
					return;
			}
			currentX=0;
		}
		printSolution();
	}
	
	private boolean findPossibleValue()
	{
		
		while (!sets[currentY][currentX].isEmpty())
		{
			solution[currentY][currentX] = sets[currentY][currentX].remove(0);
			
			if(!isWrong())
				return true;
		}
		
		if(currentX==0 && currentY==0)
			return false;
			
		if(currentX == 0) 
		{
			restore();
			currentX = size-2;
			currentY--;
		}
		else
		{
			restore();
			currentX-=2;
		}
		return true;
	}
	
	private boolean isWrong()
	{
		if (size==1)
			return false;
		int curr = solution[currentY][currentX];
		return checkColumn(curr) || checkRow(curr);
	}
	
	private boolean checkColumn(int curr)
	{
		boolean result = false;
		for (int i=0; i< size; i++)
		{
			if(i!=currentY)
			{
				if (solution[i][currentX]==curr)
				{
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	private boolean checkRow(int curr)
	{
		boolean result = false;
		for (int i=0; i< size; i++)
		{
			if(i!=currentX)
			{
				if (solution[currentY][i]==curr)
				{
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	private void restore()
	{
		for (int j=currentX; j<size; j++)
		{
			solution[currentY][j]=-2;
			sets[currentY][j].clear();
			sets[currentY][j].addAll(fullSet);
		}
		
		for (int i=currentY+1; i<size; i++)
			for (int j=0; j<size; j++)
			{
				solution[i][j]=-2;
				sets[i][j].clear();
				sets[i][j].addAll(fullSet);
			}
	}
	
	private void printSolution()
	{
		hasSolution = true;
		for (int i=0; i<size; i++)
		{
			for (int j=0; j<size; j++)
				System.out.print(solution[i][j] + "  ");
			System.out.println();
		}
	}

}
