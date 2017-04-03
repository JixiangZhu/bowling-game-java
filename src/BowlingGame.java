import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple 10 bowling game
 * 
 * @author Jixiang
 *
 */
public class BowlingGame {
	/* A Round object to receive roll from user */
	private Round theRound = new Round();
	
	/* A list to store the result of each round */
	private List<Round> rounds = new ArrayList<Round>();
	
	/* define the max number of rounds, aka 10 */
	private final int MAX_ROUND_NUM = 10;

	/**
	 * A function provides a randomly playing mode. It randomly generates tosses
	 * for each round (roll) and print the score board per round. Note that the
	 * score for a strike or spare (10 scores) is added to current total score
	 * if the game haven't finished. For example, If I make a strike at first
	 * round, the total score will increase by 10 at that time, the bonus for
	 * that strike is added once the ensuing tosses are made.
	 */
	public void randomPlay() {
		System.out.println("Welcome to the Bowling Game! Press any key to start the game.");
		Scanner scanner = new Scanner(System.in);
		String scoreSheet = "ScoreSheet: ";
		int[] scores = new int[10];
		for (int i = 0; i < 10; i++) {
			System.out.println("Round " + i + " : Press any key to roll");
			String key = scanner.nextLine();
			if (key != null) {
				scoreSheet += randomlyRoll();
				System.out.println(scoreSheet);
				scores[i] = getScore();
				System.out.println(scores[i]);
			}
		}
		Round lastRound = rounds.get(MAX_ROUND_NUM - 1);
		if (lastRound.isStrike() || lastRound.isSpare()) {
			randomlyRollBonus();
			scoreSheet += rounds.get(MAX_ROUND_NUM - 1).bonusResult;
			System.out.println(scoreSheet);
			scores[MAX_ROUND_NUM - 1] += getScore();
			System.out.println(scores[MAX_ROUND_NUM - 1]);
		}
		scanner.close();
	}

	/**
	 * A function provides a user interaction mode. The program asks for user's
	 * input for each tosses, and print the score board per round. Note that the
	 * score for a strike or spare (10 scores) is added to current total score
	 * if the game haven't finished. For example, If I make a strike at first
	 * round, the total score will increase by 10 at that time, the bonus for
	 * that strike is added once the ensuing tosses are made.
	 */
	public void playWithInput() {
		System.out.println("Welcome to the Bowling Game! "
						+ "Input a number to indicate the pins knocked down for each toss");
		Scanner scanner = new Scanner(System.in);
		String scoreSheet = "ScoreSheet: ";
		int[] scores = new int[10];
		for (int i = 0; i < 10; i++) {
			System.out.println("Round " + i + " , ball 1: ");
			int pins = scanner.nextInt();
			if (pins > 10 || pins < 0) {
				System.out.println("invalid input! Try again.");
				pins = scanner.nextInt();
			} else {
				roll(pins);
				if (pins != 10) {
					System.out.println("Round " + i + " , ball 2: ");
					pins = scanner.nextInt();
					roll(pins);
				}
				scoreSheet += rounds.get(i).getResult();
				System.out.println(scoreSheet);
				scores[i] = getScore();
				System.out.println(scores[i]);
			}
		}
		Round lastRound = rounds.get(MAX_ROUND_NUM - 1);
		if (lastRound.isStrike()) {
			System.out.println("Bonus, ball 1: ");
			int pins = scanner.nextInt();
			rounds.get(MAX_ROUND_NUM - 1).firstBonusToss = pins;

			System.out.println("Bonus, ball 2: ");
			pins = scanner.nextInt();
			rounds.get(MAX_ROUND_NUM - 1).secondBonusToss = pins;

		} else if (lastRound.isSpare()) {
			System.out.println("Bonus, ball 1: ");
			int pins = scanner.nextInt();
			rounds.get(MAX_ROUND_NUM - 1).firstBonusToss = pins;
		}
		scoreSheet += rounds.get(MAX_ROUND_NUM - 1).bonusResult;

		System.out.println(scoreSheet);
		scores[MAX_ROUND_NUM - 1] += getScore();
		System.out.println(scores[MAX_ROUND_NUM - 1]);
		scanner.close();
	}

	public int getScore() {
		int score = 0;
		for (int roundIndex = 0; roundIndex < rounds.size(); roundIndex++) {
			Round round = rounds.get(roundIndex);

			if (round.isStrike()) {
				score += 10;
				if (roundIndex < rounds.size() - 2) {
					score += rounds.get(roundIndex + 1).getScore();
					if (rounds.get(roundIndex + 1).isStrike())
						score += rounds.get(roundIndex + 2).firstToss;
				} else if (roundIndex < rounds.size() - 1) {
					score += rounds.get(roundIndex + 1).getScore();
					if (rounds.get(roundIndex + 1).isStrike())
						score += rounds.get(roundIndex + 1).firstBonusToss;
				}

			} else if (round.isSpare()) {
				score += 10;
				if (roundIndex < rounds.size() - 1)
					score += rounds.get(roundIndex + 1).firstToss;
			} else {
				score += round.firstToss + round.secondToss;
			}
		}
		if (rounds.size() >= MAX_ROUND_NUM) {
			score += rounds.get(MAX_ROUND_NUM - 1).firstBonusToss + rounds.get(MAX_ROUND_NUM - 1).secondBonusToss;
		}
		return score;
	}

	public void roll(int pins) {
		if (!theRound.firstTossFinished) {
			theRound.firstThrow(pins);
		} else if (!theRound.isFinished) {
			theRound.secondThrow(pins);
		} else {
			theRound.isFinished = false;
			theRound.firstThrow(pins);
		}

		if (theRound.isFinished)
			rounds.add(theRound);
	}

	public String randomlyRoll() {
		Round round = new Round();
		round.randomlyThrow();
		rounds.add(round);
		return round.getResult();
	}

	public void rollBonus(int firstBonus, int secondBonus) {
		rounds.get(MAX_ROUND_NUM - 1).firstBonusToss = firstBonus;
		rounds.get(MAX_ROUND_NUM - 1).secondBonusToss = secondBonus;
	}

	public void randomlyRollBonus() {
		rounds.get(MAX_ROUND_NUM - 1).randomlyBonusThrow();
	}
}
