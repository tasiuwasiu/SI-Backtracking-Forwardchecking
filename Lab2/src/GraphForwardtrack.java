import java.util.LinkedList;
import java.util.List;

public class GraphForwardtrack implements Algorithm
{
	private int size;
	private int range;
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
		previousSets = new Backup[size][size];
		currentSet = new LinkedList<Integer>();
		
		for (int i=0; i<range; i++)
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
		
		if(currentY==0)
		{
			if(currentX==0)
			{
				if(removeRight(curr) || removeDown(curr) || removeRightDown(curr) )
					return false;
			}
			else
			{
				if(currentX==size-1)
				{
					if(removeDown(curr) || removeLeftDown(curr) )
						return false;
				}
				else
				{
					if(removeRight(curr) || removeDown(curr) || removeLeftDown(curr) || removeRightDown(curr))
						return false;
				}
			}
		}
		else
		{
			if(currentY==size-1)
			{
				if(currentX==0)
				{
					if(removeRight(curr) )
						return false;
				}
				else
				{
					if(currentX!=size-1)
					{
						if( removeRight(curr))
							return false;
					}
				}
			}
			else
			{
				if(currentX==0)
				{
					if(removeRight(curr) || removeDown(curr) ||  removeRightDown(curr) )
						return false;
				}
				else
				{
					if(currentX==size-1)
					{
						if(removeDown(curr) || removeLeftDown(curr) )
							return false;
					}
					else
					{
						if( removeRight(curr) || removeDown(curr) || removeRightDown(curr) || removeLeftDown(curr))
							return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean removeLeftDown(int curr)
	{
		sets[currentY+1][currentX-1].remove(new Integer(curr));
		return sets[currentY+1][currentX-1].isEmpty();
	}

	private boolean removeRightDown(int curr)
	{
		sets[currentY+1][currentX+1].remove(new Integer(curr));
		return sets[currentY+1][currentX+1].isEmpty();
	}

	private boolean removeRight(int curr)
	{
		for (int i=curr-1; i<curr+2;i++)
			sets[currentY][currentX+1].remove(new Integer(i));
		return sets[currentY][currentX+1].isEmpty();
	}

	private boolean removeDown(int curr)
	{
		for (int i=curr-1; i<curr+2;i++)
			sets[currentY+1][currentX].remove(new Integer(i));
		return sets[currentY+1][currentX].isEmpty();
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
