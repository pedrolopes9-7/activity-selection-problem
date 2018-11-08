import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static final String ABSOLUTE_PATH = "instances/";
	//public static final String ABSOLUTE_PATH = "/home/sigma/Documentos/activity-selection-problem/instances/";
	public static final int FILE_SIZE = 10;
	public static final int FILE_QNT = 10;

	public static void main (String[] args){
		String fileName = "";
		List<Activity> listActivities = new ArrayList<Activity>();
		List<Activity> output = null;

		System.out.println("---------Activity Selection Problem---------\n");
		System.out.println("Setup: java Main -i <file_name> -g|-d|-b");
		System.out.println("-i <file_name> to run on instance <file_name>.txt \n" +
							"-g to run Greedy Heuristic.\n" +
							"-d to run Dynamic Programming Heuristic\n" +
							"-b to run Backtracking Heuristic\n");

		/* programa principal: le uma instancia do problem especificada no args[1] e executa algum
			algoritmo, especificado em args[2], para o problema de selecao de atividades*/ 
		if (args[0].equals("-i")){
			fileName = ABSOLUTE_PATH + args[1] + ".txt";
			readInstances(fileName, listActivities, FILE_SIZE);

			if (args[2].equals("-g")){
				output = greedyAlgorithm(listActivities);
				System.out.println("Seleção de atividades compatíveis pelo algoritmo guloso:");
				for (Activity ac : output){
					System.out.println(ac);
				}
			}else if (args[2].equals("-d")){
			//RUN dynamic programming algorithm
			}else if (args[2].equals("-b")){
			//RUN backtracking algorithm
			}

		/* opcao de gerar as intancias do problema*/
		}else if (args[0].equals("-k")){
			generateInstances(FILE_QNT, FILE_SIZE);
		}
	}

	public static List<Activity> greedyAlgorithm(List<Activity> activities){
		List<Activity> selectedActivities = new ArrayList<Activity>();
		List<Integer> startTimes = new ArrayList<Integer>();
		List<Integer> endTimes = new ArrayList<Integer>();

		int i = 0, j;

		//Ordenação crescente por tempo de finalizacao
		Collections.sort(activities);

		for (int k = 0; k < activities.size(); k++){
			startTimes.add(activities.get(k).getStartTime());
			endTimes.add(activities.get(k).getEndTime());
		}

		/* Escolha gulosa - checa se o tempo de inicio da próxima atividade é maior ou igual ao tempo de 
		finalizacao da utlima atividade inserida na sub-estrutura otima */
		for (j = 1; j < activities.size(); j++){
			if (startTimes.get(j) >= endTimes.get(i)){
				selectedActivities.add(activities.get(j));
				i = j;
			}
		}
		return selectedActivities;
	}

	public static List<Activity> DP_Algorithm(List<Activity> activities){
		List<Activity> list = new ArrayList<Activity>();
		return list;
	}

	public static void generateInstances(int fileQnt, int fileSize){
		File out = null;
		List<FileWriter> listWriter = new ArrayList<FileWriter>(fileQnt);

		/* intervalo do numero aleatorio*/
		final int min = 1, max = 20;
		int random1 = 0, random2 = 0;;

		/* gera n instancias para o problema. n = FILE_QNT*/
		for (int i = 0; i < fileQnt; i++){
			out = new File(ABSOLUTE_PATH + "instance" + i + ".txt");

			try{
				out.createNewFile();
				listWriter.add(new FileWriter(out));

				/* gera m linhas de atividades para a instancia. m = FILE_SIZE */
				for (int j = 0; j < fileSize; j++){
					random1 = ThreadLocalRandom.current().nextInt(min, max + 1);
					random2 = ThreadLocalRandom.current().nextInt(min, max + 1);
					System.out.println(random1 + " " + random2);
					listWriter.get(j).write(String.valueOf(random1) + " " + String.valueOf(random2) + "\n"); 
				}

			} catch (IOException ex){
				ex.printStackTrace();
			}
		}

		try{
			for (FileWriter lw : listWriter){
				lw.close();
			} 
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public static void readInstances(String fileName, List<Activity> activities, int fileSize){
		Scanner s = null;
		try {
			File file = new File(fileName.trim());
			s = new Scanner(file);

			for (int i = 0; i < fileSize; i++){
				activities.add(new Activity(s.nextInt(), s.nextInt()));
			}

		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
}

class Activity implements Comparable<Activity>{
	private int start_time;
	private int end_time;

	public Activity(int st, int et){
		this.start_time = st;
		this.end_time = et;
	}

	public int getStartTime(){
		return this.start_time;
	}

	public int getEndTime(){
		return this.end_time;
	}

	public void setStartTime(int st){
		this.start_time = st;
	}

	public void setEndTime(int et){
		this.end_time = et;
	}

	@Override
	public int compareTo(Activity activity){
		int compareEnd_Time = ((Activity) activity).getEndTime();
		return this.end_time - compareEnd_Time;
	}

	@Override
	public String toString(){
		return "Inicio: " + this.start_time + " " + "Termino: " + this.end_time;
	}
}