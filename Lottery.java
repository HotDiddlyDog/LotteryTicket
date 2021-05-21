import java.util.Scanner;
import java.util.Random;


public class Lottery{
	public static void main(String[] args){
		Scanner scnr = new Scanner(System.in);
		Random rng = new Random();
		System.out.println(" ");
		System.out.println("First, let's set up the game!");
		System.out.println(" ");
		System.out.print("How many distinct numbers should the player pick? ");
		int k = scnr.nextInt();
		while (k <= 0){
			System.out.print("Error - must be at least 1. Please try again: ");                                      // <--------- user input for numbers
			k = scnr.nextInt();
		}
		System.out.println(" ");
		
		System.out.print("OK. Each of those " + k + " numbers should range from 1 to what? ");
		int n = scnr.nextInt();
		while (n < k){
			System.out.print("Error - range must be at least 1 to " + k + " to have a valid game. Please try again: ");
			n = scnr.nextInt();
		}
		System.out.println(" ");

		System.out.print("OK. And finally, the bonus number should range from 1 to what? ");
		int m = scnr.nextInt();
		while (m <= 0){
			System.out.print("Error - range must be at least 1 to 1 to have a valid game. Please try again: ");
			m = scnr.nextInt();
		}
		System.out.println(" ");
		double posTickets = numPossibleTickets(k,n,m);
		System.out.print("There are " + numPossibleTickets(k, n, m) + " possible tickets in this game. Each ticket has a " + (1/posTickets)*100 + "% chance of winning the jackpot. Let's play, good luck!");
		System.out.println(" ");
		System.out.print("How many tickets would you like to buy? ");
		int t = scnr.nextInt();
		while (t <= 0){
			System.out.print("Error - must buy at least 1 ticket! Please try again: ");
			t = scnr.nextInt();
		}
		System.out.println(" ");
		int[][] c = new int[t][k];
		int[] bonusTickets = new int[t];
		int[] a = new int[k]; 

		int rowNumber = 0;
		for (int i = 1; i < t + 1; i++){
			//getting user input for numbers											<-------- find the ticket numbers 
			System.out.print(" * Ticket #" + i + " of " + t + " * ");
			System.out.println(" ");
			System.out.println("Pick your " + k + " distinct numbers!");
			a = getPlayerNumbers(k, n);

			//getting user input for bonus number
			System.out.println(" ");
			System.out.print("Now pick your bonus number (must be 1-" + m + "): ");
			int y = scnr.nextInt();

			//checking input 
			while (y <= 0 || y > m){
				System.out.print("Error - number must be between 1 and " + m + ". Please try again: ");
				y = scnr.nextInt(); 
			}

			for (int o = 0; o < a.length; o++){
				c[rowNumber][o] = a[o];
			}
			System.out.println(" ");
			System.out.println("Your tickets so far:");
			System.out.println("--------------------");
			System.out.println(" ");
			bonusTickets[rowNumber] = y;

			for(int first = 0; first < c.length; first++){
				for (int second = 0; second < c[first].length; second++){                              //<-------------calculate ticket numbers so far 
					System.out.print(c[first][second] + "   ");
				}
				
				System.out.print("||   Bonus Number: ");
				System.out.print(bonusTickets[first]);
				System.out.println();
			}
			rowNumber++;


			System.out.println(" ");
			

		}
		System.out.println("*****");
		System.out.println("The moment of truth has arrived! Here are the drawn numbers: ");
		System.out.println(" ");

		int[] b = new int[k];
		b = getDrawnNumbers(k,n);
		for (int i = 0; i < b.length; i++){
			System.out.print(b[i] + "   ");
		}
		System.out.print("||   Bonus Number: ");
		int random = rng.nextInt((m - 1) + 1) + 1;                                                          //<-- draw random ticket
		int bonusNum = 0;
		bonusNum = random;
		System.out.print(bonusNum);	
		System.out.println();


		System.out.println("Your best ticket(s): ");
		double max = 0;
		for (int i = 0; i < c.length; i++){
			for (int j = 0; j < c[i].length; j++){
				if (countMatches(c[i], b) > max){                                                    //<-------finds best ticket 
					max = countMatches(c[i], b);
				}

			}			
		}
		int index = 0;
		for (int i = 0; i < c.length; i++){
			for (int j = 0; j < c[i].length; j++){
				if (countMatches(c[i], b) == max){
					index = i;
					//break;
				}
				if (countMatches(c[i], b) == max && (index != i)){
					if (bonusTickets[i] == bonusNum){
						index = i;
					} 
				
				}
			}			
		}

		for (int i = 0; i < c[index].length; i++){
			System.out.print(c[index][i] + "   ");
		}
		System.out.print("||   Bonus Number: ");
		System.out.print(bonusTickets[index]);
		// int max = 0;
		// int row = 0;
		// boolean row2 = false;
		// for (int i = 0; i < t; i++){
		// 	countMatches(c[i], b);
		// 	if (countMatches(c[i], b) > max){
		// 		max = countMatches(c[i], b);
		// 		row = i;
		// 	}
		// 	if (max == countMatches(c[i], b)){
		// 		row2 = true;
		// 	}
		// }
		// // for (int i = 0; i < a.length; i++){
		// // 	System.out.print(c[row]);
		// // } 
		

		

	}
	
	public static int factorial(int n, int k){
		int c = n;//numerator
		int d = k;//denominator
		int result = 1;
		if (n == 0) { // if n = 0
			result = 1;
		}
		if (n != 0 && k !=0){
			int i = 1;
			while (i < k){ // factorial for the numerator
				c = c * (n - i);
				i++;

			}
			int j = 1;
			while (j < k){ // factorial for the denominator
				d = d * (k - j);
				j++;
			}
		} // if is true, will compute result  
		if (n != 0 && k != 0){
			result = c/d;
			return result; 
		}
		else {
			return result; // if n and k are 1, print 1 
		}
		
	}
	public static long numPossibleTickets(int k, int n, int m){
		long result =  m * factorial(n, k);
		return result;
	}
	public static int[] getPlayerNumbers(int k, int n){
		Scanner scnr = new Scanner(System.in);
		int[] playerNumbers = new int[k];
		int temp = 0;
		for (int i = 1; i < playerNumbers.length + 1; i++){
			System.out.print("Enter number " + i + " (must be 1-" + n + ", cannot repeat): ");
			temp = scnr.nextInt();
			while (temp <= 0 || temp > n){
				System.out.println("Error - number must be between 1 and " + n + ". Please try again.");
				System.out.print("Enter number " + i + " (must be 1-" + n + ", cannot repeat): ");
				temp = scnr.nextInt();

			}
			
			for (int j = 0; j < playerNumbers.length; j++){
				while (temp == playerNumbers[j]){
			 		System.out.println("Error - the number " + temp + " has already been entered. Please try again:");
			 		System.out.print("Enter number " + i + " (must be 1-" + n + ", cannot repeat): ");
			 		temp = scnr.nextInt();

				}
			}
			playerNumbers[i - 1] = temp;
		//	System.out.print("Now pick your bonus number (must be 1-" + m + "): ");


			
		}
		// for (int i = 0; i < playerNumbers.length; i++){
		// 	//System.out.print(playerNumbers[i] + " ");
		// }
		return playerNumbers;

	}
	public static int[] getDrawnNumbers(int k, int n){
		Random rng = new Random();
		int[] drawnNumbers = new int[k];
		for(int i = 0; i < drawnNumbers.length; i++){
			int random = rng.nextInt((n - 1) + 1) + 1;
			//drawnNumbers[i] = random;
			for (int j = 0; j < drawnNumbers.length; j++){
				while (random == drawnNumbers[j]){
				random = rng.nextInt((n - 1) + 1) + 1;
				}
			}
			drawnNumbers[i] = random;
		}
		return drawnNumbers;
		// for (int i = 0; i < drawnNumbers.length; i++){
		// 	System.out.print(drawnNumbers[i] + " ");
		// }
	}
	public static int countMatches(int[] a, int[] b){
		int count = 0;
		for (int i = 0; i < a.length; i++){
			for (int j = 0; j < b.length; j++){
				if (b[j] == a[i]){
					count++;
				}
			}

			
		}
		return count; 

	}
}
