import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PromptGenerator {
	
	static PromptGenerator generator;
	Prompt prompt;
	static ArrayList<String> actors;
	static ArrayList<String> actions;
	static ArrayList<String> adjectives;
	static ArrayList<String> locations;
	
	public static void main (String [] args){
		generator = new PromptGenerator();
		try {
			actors = readFile(new File("src/PromptActors.txt"));
			actions = readFile(new File("src/PromptActions.txt"));
			adjectives = readFile(new File("src/Adjectives.txt"));
			locations = readFile(new File("src/PromptLocations.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File missing.");
		}
		generator.run();
	}
	void run(){
		double type = Math.random() * 4;
		prompt =  type < 1 ? new PromptType1() : type < 2 ? new PromptType2() : type < 3 ? new PromptType3() : new PromptType4();
		prompt.randomize();
		prompt.print();
	}
	private static ArrayList<String> readFile(File file) throws IOException{
		ArrayList<String> list = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line;
		while((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();
		return list;
	}
	
	public class Prompt {
		private ArrayList<String> sentence;
		public void initialize(){
			sentence = new ArrayList<String>();
		}
		public ArrayList<String> getSentence(){
			return sentence;
		}
		public void print(){
			for (String str : sentence){
				System.out.print(str);
			}
			System.out.println();
		}
		public void randomize(){
			String str;
			Random roll = new Random();
			ArrayList<String> sentence = new ArrayList<String>();
			for (String string : this.sentence){
				switch (string) {
				case "actorBlock" :
					str = actors.get(roll.nextInt(actors.size()));
					break;
				case "actionBlock" : 
					str = actions.get(roll.nextInt(actions.size()));
					break;
				case "locationBlock" :
					str = locations.get(roll.nextInt(locations.size()));
					break;
				case "adjective" :
					str = adjectives.get(roll.nextInt(adjectives.size()));
					break;
				default : 
					str = string;
				}
				sentence.add(str);
			}
			this.sentence = sentence;
		}
		public void append(String str){
			sentence.add(str);
		}
	}
	public class PromptType1 extends Prompt {
		public PromptType1(){
			initialize();
			append("actorBlock");
			append("actionBlock");
			append("actorBlock");
			append("locationBlock");
		}
	}
	public class PromptType2 extends Prompt {
		public PromptType2(){
			initialize();
			append("actorBlock");
			append("and ");
			append("actorBlock");
			append("actionBlock");
			append("adjective");
			append("actorBlock");
			append("locationBlock");
		}
	}
	public class PromptType3 extends Prompt {
		public PromptType3(){
			initialize();
			append("what if ");
			append("actorBlock");
			append("were actually ");
			append("adjective");
			append("actorBlock");
		}
	}
	public class PromptType4 extends Prompt {
		public PromptType4(){
			initialize();
			append("In a world where ");
			append("adjective");
			append("actorBlock");
			append("actionBlock");
			append("adjective");
			append("actorBlock");
			append("our only hope is ");
			append("actorBlock");
		}
	}
}
