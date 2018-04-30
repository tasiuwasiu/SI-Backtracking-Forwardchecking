import java.util.LinkedList;
import java.util.List;

public class Backup
{
	private List<Integer>[][] sets;
	
	public Backup(int size)
	{
		sets= new LinkedList[size][size];
		for(int i=0; i<sets.length;i++)
			for(int j=0; j<sets[i].length;j++)
				sets[i][j] = new LinkedList();
	}
	
	public void save(List<Integer>[][] s)
	{
		for(int i=0; i<sets.length;i++)
			for(int j=0; j<sets[i].length;j++) {
				sets[i][j].clear();
				sets[i][j].addAll(s[i][j]);
			}
		//sets = s.clone();
	}
	
	public List<Integer>[][] load()
	{
		return sets;
	}
}
