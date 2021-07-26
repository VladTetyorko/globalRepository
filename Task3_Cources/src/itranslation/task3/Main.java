package itranslation.task3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException {
		if (args.length % 2 == 0 || args.length < 3) {
			System.out.println("Moves quontity should not devide by 2 and be more then 3");
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
			Integer insert;
			while (true) {
				insert = menu.showMenu(hmac, availableMoves, availableMoves.get(compMove));
				if (insert == null) {
					System.out.println("Incorrect input! Try again!");
				} else if (insert == 0) {
					return;
				} else if (availableMoves.containsKey(insert)) {
					System.out.println(insert);
					userMove = insert;
					break;
				} else
					System.out.println("Incorrect input! Try again!");

			}
			menu.showResult(availableMoves, checkWin(args.length, compMove, userMove), compMove, userMove, key);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
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

	private static WinnerEnum checkWin(int moveLength, int compMove, int userMove) {
		if (compMove == userMove)
			return WinnerEnum.Tie;
		int center = moveLength / 2 + 1;
		if (userMove == center || compMove == center)
			if (userMove > compMove)
				return WinnerEnum.User;
			else
				return WinnerEnum.Computer;
		else if (userMove > center)
			if (userMove - compMove < center && userMove - compMove > 0)
				return WinnerEnum.User;
			else
				return WinnerEnum.Computer;
		else if (compMove > center)
			if (compMove - userMove < center && compMove - userMove > 0)
				return WinnerEnum.Computer;
			else
				return WinnerEnum.User;
		return null;
	}

}
