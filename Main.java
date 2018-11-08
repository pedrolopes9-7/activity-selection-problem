import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.System;

public class Main {
	public static final String ABSOLUTE_PATH = "instances/";
	//public static final String ABSOLUTE_PATH = "/home/sigma/Documentos/activity-selection-problem/instances/";
	public static final String OUTPUT_PATH = "output/";
	public static final int FILE_SIZE = 1000;
	public static final int FILE_QNT = 1000;

	public static void main (String[] args){
		long execTime = 0, execStartTime = 0, execFinishTime = 0;
		String fileName = "";
		List<Activity> listActivities = new ArrayList<Activity>();
		List<Activity> output = null;

		System.out.println("---------Activity Selection Problem---------\n");
		System.out.println("Setup: java Main -i <file_name> -g|-d|-b\n");
		System.out.println("-i <file_name> to run on instance <file_name>.txt \n" +
							"-g to run Greedy Heuristic.\n" +
							"-d to run Dynamic Programming Heuristic\n" +
							"-b to run Backtracking Heuristic\n" 
							+ "or run java Main -k to generate a set of instances.\n");

		/* programa principal: le uma instancia do problem especificada no args[1] e executa algum
			algoritmo, especificado em args[2], para o problema de selecao de atividades*/ 
		if (args[0].equals("-i")){
			fileName = ABSOLUTE_PATH + args[1] + ".txt";
			readInstances(fileName, listActivities, FILE_SIZE);

			if (args[2].equals("-g")){
				/* TODO melhorar exicibição com métricas: tempo de execucao, valor da soluçao otima... pensar em mais...
					Funcao de escrever resultados em arquivo externo nao está escrevendo nada..*/ 
				execStartTime = System.currentTimeMillis();
				output = greedyAlgorithm(listActivities);
				execFinishTime = System.currentTimeMillis();

				execTime = execFinishTime - execStartTime;
				writeOutput(output, execTime);
				for (Activity ac : output){
					System.out.println(ac);
				}
				System.out.println(execTime);
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
		String space = "";
		File out = null;
		List<FileWriter> listWriter = new ArrayList<FileWriter>(fileQnt);

		/* intervalo do numero aleatorio*/
		final int min = 1, max = 60;
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
					listWriter.get(i).write(String.valueOf(random1) + " " + String.valueOf(random2) + "\r\n"); 
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

	public static void writeOutput(List<Activity> list, long execTime){
		final int subset_size = list.size();

		try{
			File output_file = new File(OUTPUT_PATH + "output.txt");
			FileWriter writer = new FileWriter(output_file);

			if (!output_file.exists()){
				output_file.createNewFile();
			}

			for (Activity ac : list){
				/*TODO funcao escrevendo caracter vazio.. problema esta nesse for.*/
				writer.write("Inicio: " + ac.getStartTime() + " " + "Termino: " + ac.getEndTime());
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}

class Activity implements Comparable<Activity>{
	private int start_time;
	private int end_time;

	public Activity(int st, int et){
		if (st == et){
			this.start_time = st;
			this.end_time = st + 1;
		}else if (st < et){
			this.start_time = st;
			this.end_time = et;
		}else{
			this.start_time = et;
			this.end_time = st;
		}
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
		return "Inicio: " + this.start_time + " " + "Termino: " + this.end_time + "\n";
	}
}