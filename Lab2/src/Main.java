import java.text.SimpleDateFormat;
import java.util.Date;

public class Main
{
	
	public static void main(String[] args)
	{
		int number = 13;
		int range = 1;
		long start;
		long stop;
		boolean check;
		Algorithm[] set = new Algorithm[4];
		String[] names = new String[4];
		
		set[0] = new GraphBacktrack();
		set[1] = new GraphForwardtrack();
		set[2] = new SquareBacktrack();
		set[3] = new SquareForwardtrack();
		
		names[0] = "Graph Backtracking";
		names[1] = "Graph Forwardchecking";
		names[2] = "Square Backtracking";
		names[3] = "Square Forwardchecking";
		
		for (int i=0; i<4; i++)
		{
			start = System.currentTimeMillis();
			while (check = !set[i].execute(number, range))
			{
				stop = System.currentTimeMillis();
				result(!check, names[i], stop-start, range);
				range++;
			}
			
			stop = System.currentTimeMillis();
			result (!check, names[i], stop-start, range);
			range=1;
		}
	}
	
	private static void result (boolean check, String name, long time, int range)
	{
		String t = new SimpleDateFormat("mm.ss.SSS").format(new Date(time));
		if(check)
			System.out.println("Odnaleziono rozwiazanie! Zakres: 0-" + (range-1) + ", Metoda: " + name + " , czas: " + t);
		else
			System.out.println("Brak rozwiazania! Zakres: 0-" + (range-1) + ", Metoda: " + name + ", czas: " + t);
	}

}
