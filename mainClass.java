package assignment3;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

class vocabularyThread extends Thread {
	String file;
	public vocabularyThread(String f){
		this.file = f;
	}
	@Override
	public void run() {

			 try {
			      File myObj = new File(file);
			      Scanner myReader = new Scanner(myObj);
			      while (myReader.hasNextLine()) {
			        String data = myReader.nextLine();
			        //System.out.println(data);
			        String word[]= data.split(" ");
			        for(int j=0; j<word.length; j++)
			        mainClass.bst.insert(word[j]);
			      }
			      myReader.close();
			    } catch (FileNotFoundException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
			
			//mainClass.bst.inorder();
			
	}
}


class inputfileThread extends Thread {
	String file;
	public inputfileThread(String f){
		this.file = f;
	}
	@Override
	public void run() {
		 
		try {
		      File myObj = new File(file);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        //System.out.println(data);
		        String word[]= data.split(" ");
		        for(int j=0; j<word.length; j++)
		        	mainClass.vec.add(word[j]);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }  
	
	}
}

public class mainClass {
	
	static BST bst=new BST();
	static Vector<String> vec = new Vector<>();
	
	public static void MAINMENU(String args[]) {
		
		System.out.println("-----MAIN MENU-----\n");
		
		System.out.println("1. Display BST from Vocabulary File.");
		System.out.println("2. Display Vector from Input File.");
		System.out.println("3. View Match Words and their Frequency.");
		System.out.println("4. Search a Query");
		System.out.println("5. Exit");
		
    	Scanner m = new Scanner(System.in);
		int opt = m.nextInt();

		if(opt == 1) {
			System.out.println("~~~Displaying BST build from Vocabulary File~~~");
			bst.inorder();
		}
		
		if(opt == 2) {
			System.out.println("~~~Displaying Vector build from Input File~~~");	
			for (int i=0; i<vec.size(); i++) {           
				System.out.println("Word is: " +mainClass.vec.get(i));  
		    }  
		}
		if(opt == 3) {
			System.out.println("~~~Viewing Match Words and their Frequency~~~");	
			try {
				wordsAndFrequency(args);
			}catch (FileException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			}
		}
		if(opt == 4) {
			System.out.println("~~~Searching a Query~~~\n");	
			System.out.println("Enter query string...");

	    	Scanner s = new Scanner(System.in);
			String str = s.nextLine();
			
			for(int i=0; i<args.length-3;i++)
			searchQueryInFile(args[i+3], str);
	        
			
		}
		if(opt == 5) {
			System.out.println("~~~EXIT~~~");	
			System.exit(0);
		}
		
	}//End MAINMENU
	
	
	static void wordsAndFrequency(String args[]) throws FileException {
	//	System.out.println("CHECKING WORDS AND FREQUENCY");
		ArrayList<Word> words1 = new ArrayList<Word>();
		boolean flag=false;

		String files[] = new String[3];
		files[0] = args[2];
		files[1] = args[3];
		files[2] = args[4];
		
		for (String file : files) {
			
			flag=true;
				try {
				      File myObj = new File(file);
				      Scanner myReader = new Scanner(myObj);
				      int line = 0;
				      while (myReader.hasNextLine()) {
			    		  String data = myReader.nextLine();
			    		  String data_words[]= data.split(" ");        
			    		  for(String wd : data_words) {
			    			  if (!isThere(wd, words1)) {
			    				  Word newWord = new Word();
			    				  newWord.setWord(wd);
			    				  words1.add(newWord);
							}
			    		  }
			    		 line++; 
			      		}
				      myReader.close();
				} 
				catch (FileNotFoundException e) {
				      System.out.println("An error occurred.");
				      e.printStackTrace();
				}
		}
		for (int i = 0; i < words1.size(); i++) {
			words1.get(i).display();
		}
		
		if(!flag)
			throw new FileException("File doesnt exist");
		
	}
	
	

	private static boolean isThere(String wd, ArrayList<Word> words1) {
		// TODO Auto-generated method stub
		for (Word word : words1) {
			if(word.getWord().equals(wd)) {
				int freq = word.getFrequency();
				freq++;
				word.setFrequency(freq);
				return true;
			}
		}
		return false;
	}


	static void searchQueryInFile(String file,String query) {

        String word[]= query.split(" ");
        boolean flag = false;
        
		try {
		      File myObj = new File(file);
		      Scanner myReader = new Scanner(myObj);
		      
		      for(int i=0;i<word.length;i++) {
		    	  int count=0;
		    	  while (myReader.hasNextLine()) {
		    		  String data = myReader.nextLine();
		    		  String data_words[]= data.split(" ");        
		    		  for(String wd : data_words) {
		    			  if(wd.equals(word[i])) {
		    				  count++;
		    			  }
		    		  }
		      		}
		    	  if(count!=0) {
		    		  System.out.println("The word " + word[i] + " is present " + count + " times in file: " + file);
		    	  }
		    	  else {
		    		  System.out.println("The word" + word[i] + " is not present in file " + file);  
		    	  }
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	
	public static void main(String[] args) {
		//whatever written here will be done by main thread
		
		vocabularyThread t1 = new vocabularyThread(args[2]);
		t1.start();
		
		inputfileThread[] t = new inputfileThread[args.length-3];
		for (int i = 0; i < args.length-3; i++) {
		    t[i] = new inputfileThread(args[i+3]);
		    t[i].start();
		}
		for (int i = 0; i < args.length-3; i++) {
		    try {
		        t[i].join();
		    } catch (InterruptedException e) {
		    	
		        e.printStackTrace();
		    }
		}
		
		while(true) {
			MAINMENU(args);
		}	
	}

}
