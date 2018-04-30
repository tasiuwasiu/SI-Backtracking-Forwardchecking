import java.util.LinkedList;
import java.util.List;

public class GraphBacktrack implements Algorithm
{	
	private int size;
	private int range;
	private boolean hasSolution = false;
	private List<Integer>[][] sets;
	private int[][] solution;
	private List<Integer> fullSet;
	private int currentX = 0;
	private int currentY = 0;
	
	@Override
	public boolean execute(int number, int range)
	{
		init (number, range);
		findSolution();
		return hasSolution;
	}

	private void init(int number, int rang)
	{
		size = number;
		range = rang;
		sets = new LinkedList[size][size];
		fullSet = new LinkedList<Integer>();
		solution = new int[size][size];
		
		for (int i=0; i<range; i++)
			fullSet.add(i);
		
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
			{
				solution[i][j] = -2;
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
			restoreSets();
			currentX = size-2;
			currentY--;
		}
		else
		{
			restoreSets();
			currentX-=2;
		}
		return true;
	}
	
	private boolean isWrong()
	{
		if (size==1)
			return false;
		int curr = solution[currentY][currentX];
		if(currentY==0)
		{
			if(currentX==0)
			{
				if(checkRight(curr) || checkDown(curr) || checkRightDown(curr) )
					return true;
			}
			else
			{
				if(currentX==size-1)
				{
					if(checkLeft(curr) || checkDown(curr) || checkLeftDown(curr) )
						return true;
				}
				else
				{
					if(checkLeft(curr) || checkRight(curr) || checkDown(curr) || checkLeftDown(curr) || checkRightDown(curr))
						return true;
				}
			}
		}
		else
		{
			if(currentY==size-1)
			{
				if(currentX==0)
				{
					if(checkRight(curr) || checkUp(curr) || checkRightUp(curr) )
						return true;
				}
				else
				{
					if(currentX==size-1)
					{
						if(checkLeft(curr) || checkUp(curr) || checkLeftUp(curr) )
							return true;
					}
					else
					{
						if(checkLeft(curr) || checkRight(curr) || checkUp(curr) || checkLeftUp(curr) || checkRightUp(curr))
							return true;
					}
				}
			}
			else
			{
				if(currentX==0)
				{
					if(checkRight(curr) || checkDown(curr) || checkUp(curr) || checkRightUp(curr) ||  checkRightDown(curr) )
						return true;
				}
				else
				{
					if(currentX==size-1)
					{
						if(checkLeft(curr) || checkDown(curr) || checkUp(curr) || checkLeftUp(curr) || checkLeftDown(curr) )
							return true;
					}
					else
					{
						if(checkLeft(curr) || checkRight(curr) || checkDown(curr) || checkUp(curr) || checkLeftUp(curr) || checkRightDown(curr) || checkLeftDown(curr) || checkRightUp(curr))
							return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean checkLeft(int curr)
	{
		return Math.abs(curr - solution[currentY][currentX-1]) < 2;
	}
	
	private boolean checkRight(int curr)
	{
		return Math.abs(curr - solution[currentY][currentX+1]) < 2 ;
	}
	
	private boolean checkDown(int curr)
	{
		return Math.abs(curr - solution[currentY+1][currentX]) < 2;
	}
	
	private boolean checkUp(int curr)
	{
		return Math.abs(curr - solution[currentY-1][currentX]) < 2;
	}
	
	private boolean checkLeftUp(int curr)
	{
		return Math.abs(curr - solution[currentY-1][currentX-1]) < 1;
	}
	
	private boolean checkLeftDown(int curr)
	{
		return Math.abs(curr - solution[currentY+1][currentX-1]) < 1;
	}
	
	private boolean checkRightUp(int curr)
	{
		return Math.abs(curr - solution[currentY-1][currentX+1]) < 1;
	}
	
	private boolean checkRightDown(int curr)
	{
		return Math.abs(curr - solution[currentY+1][currentX+1]) < 1;
	}
	
	private void restoreSets()
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
