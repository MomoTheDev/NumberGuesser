package me.mohammad.numberguesser;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGuesser {
	
	protected static final String prefix;
	
	private int origin, bound, secretGuess;
	private Scanner scanner;
	private boolean playing;
	
	static {
		prefix = "[NumberGuesser] ";
	}
	
	private NumberGuesser(final int origin, final int bound) {
		scanner = new Scanner(System.in);
		this.origin = origin;
		this.bound = bound;
		playing = true;
		chooseSecret();
		run();
	}
	
	private void chooseSecret() {
		secretGuess = ThreadLocalRandom.current().nextInt(origin, bound);
	}
	
	private int requestInput() {
		final String nextLine = scanner.nextLine();
		if (nextLine.equalsIgnoreCase("exit"))
			System.exit(0);
		return Integer.parseInt(nextLine);
	}
	
	private boolean requestMatch() {
		System.out.printf("%sYou guessed it (%d), wanna play again? (yes or no)\n", prefix, secretGuess);
		final String nextLine = scanner.nextLine();
		if (nextLine.equalsIgnoreCase("yes"))
			return true;
		if (nextLine.equalsIgnoreCase("no"))
			return false;
		return false;
	}
	
	private void run() {
		System.out.printf("%sHey, the secret number has been picked\n", prefix);
		System.out.println();
		System.out.printf("%sWhat's your guess?\n", prefix);
		while (playing) {
			final int guess = requestInput();
			if (guess < secretGuess) {
				System.out.printf("%sMore...\n", prefix);
				continue;
			}
			if (guess > secretGuess) {
				System.out.printf("%sLess...\n", prefix);
				continue;
			}
			if (guess == secretGuess) {
				if (!(requestMatch())) {
					System.exit(0);
				}
				playing = true;
				chooseSecret();
				run();
			}
		}
	}
	
	public static void main(final String[] args) {
		final int origin = args.length > 0 ? Integer.parseInt(args[0]) : 0;
		final int bound = args.length > 1 ? Integer.parseInt(args[1]) : 1000;
		new NumberGuesser(origin, bound);
	}
	
}
