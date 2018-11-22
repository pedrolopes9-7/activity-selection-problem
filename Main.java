import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;
import java.lang.Integer;

public class Main {
	public static final String ABSOLUTE_PATH = "instances/";
	public static final String OUTPUT_PATH = "output/";
	public static final double BILLION = 1000000000.0;
	public static final int FILE_SIZE = 10000;

 /** */
	public static void main (String[] args){
		double execTime = 0, execStartTime = 0, execFinishTime = 0;
		String fileName = "";
		List<Activity> listActivities = new ArrayList<Activity>();
		List<Activity> output = null;
		int fileQnt = 1;

		System.out.println("---------Activity Selection Problem---------\n");
		System.out.println("Setup: java Main -i <file_name> -g|-d|-b\n");
		System.out.println("-i <file_name> to run on instance <file_name>.txt \n" +
							"-g to run Greedy Heuristic.\n" +
							"-d to run Dynamic Programming Heuristic\n" +
							"-b to run Backtracking Heuristic\n" +
							"or run java Main -k <file_size> <number_of_files>.\n");
 
		if (args[0].equals("-i")){
			fileName = ABSOLUTE_PATH + args[1] + ".txt";
			readInstances(fileName, listActivities, FILE_SIZE);

			if (args[2].equals("-g")){
				execStartTime = System.nanoTime();
				output = greedyAlgorithm(listActivities);
				execFinishTime = System.nanoTime(); execTime = (execFinishTime - execStartTime) / BILLION;
				writeOutput(output, execTime);
			}else if (args[2].equals("-d")){
			//RUN dynamic programming algorithm
			}else if (args[2].equals("-b")){
				output = new ArrayList<Activity>();
				execStartTime = System.nanoTime();
				backtrackingAlgorithm(listActivities, output);
				execFinishTime = System.nanoTime(); execTime = (execFinishTime - execStartTime) / BILLION;
				writeOutput(output, execTime);
			}

		/* opcao de gerar as intancias do problema*/
		}else if (args[0].equals("-k")){
		 	fileQnt = Integer.parseInt(args[1]);
			generateInstances(fileQnt, FILE_SIZE);
		}
	}

	/** Método guloso para a resolução do problema proposto. As atividades são ordenadas crescentemente por tempo
		de finalização, e então a escolha gulosa é feita: caso a próxima atividade i possuir tempo de inicio maior que
		o tempo de finalização da atividade corrente j , então adiciona-se a atividade i à solução. Caso contrario, pula
		para a próxima atividade. 
		@param activities Lista de atividades
		@return Lista de atividades compatíveis */
	public static List<Activity> greedyAlgorithm(List<Activity> activities){
		List<Activity> selectedActivities = new ArrayList<Activity>();
		List<Integer> startTimes = new ArrayList<Integer>();
		List<Integer> endTimes = new ArrayList<Integer>();

		int i = 0, j;

		Collections.sort(activities);

		for (int k = 0; k < activities.size(); k++){
			startTimes.add(activities.get(k).getStartTime());
			endTimes.add(activities.get(k).getEndTime());
		}

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

	/** Função auxiliar para backtracking. Encontra a última atividade que não conflita com a atividade i. 
		Se não há atividades conmpatíveis, retorna -1;
		@param activities Lista de atividades
		@param i indice da atividade a ser comparada com as outras
		@return última atividade compatível. caso contrario, -1*/
	public static int latestNonConflict(List<Activity> activities, int i){
		for (int j = i - 1; j >= 0; j--){
			if (activities.get(j).getEndTime() <= activities.get(i-1).getStartTime())
				return j;
		}
		return -1;
	}

	/**Funcao recursiva que encontra a solução ótima para o problema de Weighted Job Scheduling por backtracking.
		No problema proposto, os pesos das atividades estão fixados em 1. A funcão recebe como parâmetros, a lista
		de atividades a serem agendadas, uma lista de atividades correspondete a solução ótima e o tamanho da lista
		inicial. O retorno é o valor ótima da solução.
		@param activities Lista de atividades a serem agendadas 
		@param addedList Lista que serão adicionados as atividades que estão na solução ótima
		@param n tamanho da lista de entrada (activities)
		@return valor inteiro da solução ótima*/
	public static int backtrackingAlgorithmRec(List<Activity> activities, List<Activity> addedList, int n){
		if (activities.size() == 1) return activities.get(n - 1).getProfit();

		int includedProfit = activities.get(n - 1).getProfit();
		int i = latestNonConflict(activities, n);
		System.out.println(i);
		if (i != -1){
			includedProfit += backtrackingAlgorithmRec(activities, addedList, i + 1);
			addedList.add(activities.get(n - 1));
		}
		int excludedProfit = backtrackingAlgorithmRec(activities, addedList, n - 1);
		
		return Math.max(includedProfit, excludedProfit);
	}

	/** Função para ordenar as atividades em ordem de tempo de finalização, e iniciar a chamada recursiva para
		o método auxiliar.
		@param list Lista das atividades
		@return void*/
	public static int backtrackingAlgorithm(List<Activity> list, List<Activity> output){
		Collections.sort(list);
		return backtrackingAlgorithmRec(list, output, list.size());
	} 

	/** Função que gera as instâncias para o problema em um arquivo externo. A função gera n instâncias, de tamanho m,
		que podem ser especificadas pelo programador. Qualquer valor para n ou m é válido. Por padrão, elas serão 
		salvas em instances/ , este diretório que deve estar no mesmo diretório do programa principal. Nomes das ins-
		tancias variam de acordo com o tamanho da mesma: 
			se FILE_SIZE [0,1000] -> pequena
			se FILE_SIZE [1000,10000] -> média
			se FILE_SIZE > 10000 -> grande
		@param fileQnt quantidade de arquivos a serem gerados
		@param fileSize tamanho de cada arquivo
		@return void*/
	public static void generateInstances(int fileQnt, int fileSize){
		File out = null;
		List<FileWriter> listWriter = new ArrayList<FileWriter>(fileQnt);

		final int min = 1, max = 180;
		int random1 = 0, random2 = 0;;

		for (int i = 0; i < fileQnt; i++){
			String sufix = String.valueOf(ThreadLocalRandom.current().nextInt(1, 10000));
			String name = "";
			
			if (fileSize < 1000) name = ABSOLUTE_PATH + "SMALL_instance" + sufix + ".txt";
			else if (fileSize < 10000) name = ABSOLUTE_PATH + "MEDIUM_instance" + sufix + ".txt";
			else name = ABSOLUTE_PATH + "BIG_instance" + sufix + ".txt";

			out = new File(name);
			try{
				out.createNewFile();
				listWriter.add(new FileWriter(out));

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

	/** Função de ler instâncias de arquivos de texto. O padrão de instancias para ser lido, é o mesmo que é 
		gerado no método generateInstances(), ou seja, para cada linha há dois valores inteiros: um corresponde ao
		tempo de inicio da atividade, e outro corresponde ao tempo de finalização da atividade. Por padrão as intancias
		são encontradas em instances/
		@param fileName nome do arquivo
		@param activities Lista de atividades
		@param fileSize tamanho do arquivo a ser lido
		@return void */
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

	/** Funcao que escreve alguma saída em um arquivo de texto. Os arquivos por padrão terão como destino o diretório 
		outputs/ , que deve ser criado na mesma pasta do projeto. As saídas possuem nomes diferentes para cada 
		resolução do problema.
		@param list Lista de atividades 
		@param execTime Tempo de execução do método proposto
		@return void*/
	public static void writeOutput(List<Activity> list, double execTime){
		final int subsetSize = list.size();
		String name = "output" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 1000));
		File output_file = new File(OUTPUT_PATH + name);

		try{
			FileWriter writer = new FileWriter(output_file);

			if (!output_file.exists()){
				output_file.createNewFile();
			}

			writer.write("Atividades: " + "\r\n------------------------------------------------------");

			for (Activity ac : list){
				writer.write("\r\nInicio: " + ac.getStartTime() + " " + "Termino: " + ac.getEndTime());				
			}

			writer.write("\r\n------------------------------------------------------" + 
						"\r\nTempo de execução: " + execTime + "s" +
						"\r\nTamanho da subestrutura ótima: " + subsetSize);
			writer.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}

class Activity implements Comparable<Activity>{
	private int start_time;
	private int end_time;
	private int profit;

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
		this.profit = 1;
	}

	public int getProfit(){
		return this.profit;
	}

	public void setProfit(int profit){
		this.profit = profit;
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