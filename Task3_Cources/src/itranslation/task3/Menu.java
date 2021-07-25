package itranslation.task3;

import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class Menu {

	public int showMenu(byte hmac[], Map<Integer, String> moves, String compMove) {
		System.out.printf("HMAC:%s \n", DatatypeConverter.printHexBinary(hmac));
		System.out.printf("Available moves:\n");
		moves.forEach((id, value) -> {
			System.out.printf("%d) %s\n", id, value);
		});
		System.out.printf("0) Exit\n");
		System.out.print("Insert your move id: ");
		Scanner scaner = new Scanner(System.in);
		int result = scaner.nextInt();
		return result;
	}

	public void showResult(Map<Integer, String> availableMoves, int isWinner, int compMove, int userMove, String key) {
		System.out.printf("Your move:%s \n"
				+ "Comp move:%s\n",availableMoves.get(userMove),availableMoves.get(compMove));
		if (isWinner == 0 ) {
		     System.out.println ("A Tie!") ;
		 } else if (isWinner == 1) {
		     System.out.println ("Computer won!");
		 } else {
		     System.out.println ("You won!");
		 }
		System.out.println ("Key= "+key);
	}

}
