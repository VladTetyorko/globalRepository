package itranslation.task3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

public class Main {

	public static void main(String[] args) throws IOException {
		if (args.length % 2 == 0 || args.length < 3) {
			System.out.print("Moves quontity should not devide by 2 and be more then 3");
			return;
		}
		HMACGenerator hmacGenerator = new HMACGenerator();
		Menu menu = new Menu();
		String key = RandomGenegator.getSecureKey();
		try {
			Map<Integer, String> availableMoves = getMapOfMoveset(args);
			int compMove = RandomGenegator.getMove(args.length);
			byte[] hmac = hmacGenerator.calcHmacSha256(key, availableMoves.get(compMove));
			int userMove;
			while(true) {
				int insert = menu.showMenu(hmac, availableMoves, availableMoves.get(compMove));
				if(insert==0) {
					System.out.println("Programm exited");
					return;
				}
				System.out.println(insert);
				if(availableMoves.containsKey(insert)) {
					userMove=insert;
					break;
				}
			}
			menu.showResult(availableMoves,checkWin(args.length, compMove, userMove), compMove, userMove, key);
		} catch (RuntimeException e) {
			System.out.print(e.getMessage());
		}
		

	}

	private static Map<Integer, String> getMapOfMoveset(String[] input) {
		Map<Integer, String> result = new HashMap<Integer, String>();
		for (int i = 0; i <= input.length - 1; i++) {
			if (result.containsValue(input[i]))
				throw new RuntimeException("Strings repeats! Try another arguments...");
			result.put(i + 1, input[i]);			
		}
		return result;
	}

	private static int checkWin(int moveLength,int compMove,int userMove) {
		return (compMove + moveLength - userMove ) % moveLength; 
	}

}
