import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main (String[] args){
		String fileName = "";
		List<Activity> list = new ArrayList<Activity>();

		System.out.println("---------Activity Selection Problem---------\n");
		System.out.println("Setup: java Main -i <file_name>.txt -g|-d|-b");
		System.out.println("-i <file_name> to run on instance <file_name>.txt \n" +
							"-g to run Greedy Heuristic.\n" +
							"-d to run Dynamic Programming Heuristic\n" +
							"-b to run Backtracking Heuristic\n");
		if (args[0] == "-i"){
			fileName = args[1];
		}

		readInstances(fileName, list, 8);
	}

	private static void readInstances(String fileName, List<Activity> activities, int n){
		File file = new File(fileName);
		//n -> number of instances
		double a, b;

		try {
			Scanner s = new Scanner(file);

			for (int i = 0; i < n; i++){
				a = s.nextDouble();
				b = s.nextDouble();

				activities.add(new Activity(a, b));
			}

		} catch(IOException ex){
			ex.printStackTrace();
		}

		for (Activity it : activities){
			System.out.println(it.getStartTime() + "  " + it.getEndTime());
		}
	}
}

class Activity {
	private double start_time;
	private double end_time;

	public Activity(double st, double et){
		this.start_time = st;
		this.end_time = et;
	}

	public double getStartTime(){
		return this.start_time;
	}

	public double getEndTime(){
		return this.end_time;
	}

	public void setStartTime(double st){
		this.start_time = st;
	}

	public void setEndTime(double et){
		this.end_time = et;
	}
}