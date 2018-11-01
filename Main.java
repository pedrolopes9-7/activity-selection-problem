import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileWriter;

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

		fileName = "instances/" + args[1] + ".txt";

		/* TODO implementar os 3 métodos de resolucao
		if (args[2] == "-g"){
			//Roda algoritmo guloso
		}else if (args[2] == "-d"){
			//Roda algoritmo de programação dinâmica
		}else{
			//Roda algoritmo de backtracking
		}
		*/

		//fileQnt = número de arquivos de instancias
		//fileSize = tamanho da instancia
		//generateInstances(fileQnt, fileSize);
		//readInstances(List<File> listFiles, list, n);
		//readInstances(fileName, list, 8);
		generateInstances(10, 10);

	}

	public static void generateInstances(int fileQnt, int fileSize){
		final String ABSOLUTE_PATH = "/home/sigma/Documentos/activity-selection-problem/instances/";
		File out = null;

		for (int i = 0; i < fileQnt; i++){
			out = new File(ABSOLUTE_PATH + "instance" + i + ".txt");

			try{
				out.createNewFile();
				FileWriter writer = new FileWriter(out);
				for (int j = 0; i < fileSize; i++){
					writer.write("1"); writer.write("2");
				}
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
	}

	public static void readInstances(String fileName, List<Activity> activities, int fileSize){
		Scanner s = null;
		try {
			File file = new File(fileName.trim());
			s = new Scanner(file);

			for (int i = 0; i < fileSize; i++){
				activities.add(new Activity(s.nextDouble(), s.nextDouble()));
			}

		} catch(IOException ex){
			ex.printStackTrace();
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