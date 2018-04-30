import java.util.LinkedList;
import java.util.List;

public class SquareForwardtrack implements Algorithm
{
	private int size;
	private boolean hasSolution = false;
	private List<Integer>[][] sets;
	private int[][] solution;
	private List<Integer> fullSet;
	private int currentX = 0;
	private int currentY = 0;
	private Backup[][] previousSets;
	Backup allFullSets;
	private List<Integer> currentSet;
	
	
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
		previousSets = new Backup[size][size];
		currentSet = new LinkedList<Integer>();
		
		for (int i=0; i<size; i++)
			fullSet.add(i);
		
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
			{
				solution[i][j] = -2;
				sets[i][j] = new LinkedList<Integer>();
				sets[i][j].addAll(fullSet);
				previousSets[i][j] = new Backup(size);
			}
		
		allFullSets=new Backup(size);
		allFullSets.save(sets);
		previousSets[0][0].save(sets);
	}

	private void findSolution()
	{
		for(; currentY<size; currentY++)
		{
			for(; currentX<size; currentX++)
			{
				currentSet.clear();
				currentSet.addAll(sets[currentY][currentX]);
				if(!findPossibleValue())
				{
					return;
				}
			}
			currentX=0;
		}
		printSolution();
	}
	
	private boolean findPossibleValue()
	{
		while (!currentSet.isEmpty())
		{
			solution[currentY][currentX] = currentSet.remove(0);
			if(removeWrongValues())
			{
				sets[currentY][currentX].clear();
				sets[currentY][currentX].addAll(currentSet);
				previousSets[currentY][currentX].save(sets);
				return true;
			}
			else
				restoreSets();
		}

		if(currentX==0 && currentY==0)
			return false;
		
		if(currentX == 0) 
		{
			currentX = size-2;
			currentY--;
		}
		else
		{
			currentX-=2;
		}

		return true;
	}
	
	private boolean removeWrongValues()
	{
		if (size==1)
			return true;
		
		int curr = solution[currentY][currentX];
		
		return removeRight(curr) && removeDown(curr);
	}
	
	private boolean removeRight(int curr)
	{
		boolean check=false;
		for (int i=currentX+1; i<size; i++)
		{
			sets[currentY][i].remove(new Integer(curr));
			if(check = sets[currentY][i].isEmpty())
				break;
		}
		return !check;
	}

	private boolean removeDown(int curr)
	{
		boolean check=false;
		for (int i=currentY+1; i<size; i++)
		{
			sets[i][currentX].remove(new Integer(curr));
			if(check = sets[i][currentX].isEmpty())
				break;
		}
		return !check;
	}

	private void restoreSets()
	{
		for (int j=currentX; j<size; j++)
		{
			solution[currentY][j]=-2;
		}
		
		for (int i=currentY+1; i<size; i++)
			for (int j=0; j<size; j++)
			{
				solution[i][j]=-2;
			}	

		if(currentX==0 && currentY==0)
			return;
		if(currentX==1 && currentY==0)
		{
			List<Integer>[][] s=previousSets[currentY][currentX-1].load();
			for(int i=0; i<sets.length;i++)
				for(int j=0; j<sets[i].length;j++) 
				{
					sets[i][j].clear();
					sets[i][j].addAll(s[i][j]);
				}
			return;
		}
			
		if(currentX == 0) 
		{
			List<Integer>[][] s=previousSets[currentY-1][size-1].load();	
			for(int i=0; i<sets.length;i++)
				for(int j=0; j<sets[i].length;j++) 
				{
					sets[i][j].clear();
					sets[i][j].addAll(s[i][j]);
				}
		}
		else
		{
			if (currentX == 1)
			{
				List<Integer>[][] s=previousSets[currentY][0].load();
				for(int i=0; i<sets.length;i++)
					for(int j=0; j<sets[i].length;j++) 
					{
						sets[i][j].clear();
						sets[i][j].addAll(s[i][j]);
					}
			}
			else
			{
				List<Integer>[][] s=previousSets[currentY][currentX-1].load();
				for(int i=0; i<sets.length;i++)
					for(int j=0; j<sets[i].length;j++) 
					{
						sets[i][j].clear();
						sets[i][j].addAll(s[i][j]);
					}
			}
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
