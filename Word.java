package assignment3;

public class Word {
	String word;
	int frequency;
	
	Word() {
		this.word = null;
		this.frequency = 1;
	}
	
	Word(String word) {
		this.word = word;
		this.frequency = 0;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public void display() {
		System.out.println(this.word + " has frequency " + this.frequency); 
	}
}
