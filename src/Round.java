import java.util.concurrent.ThreadLocalRandom;
/**
 * 
 * @author Jixiang
 * This class defines the round, or frame, in a bowling game.
 * 
 */
public class Round {

	/* the number of pins knocked down in first toss. */
	public int firstToss = 0;
	
	/* the number of pins knocked down in second toss. Does not necessarily exit */
	public int secondToss = 0;
	
	/* the number of pins knocked down in the first and second bonus toss after all regular 
	 * rounds are finished. Does not necessarily exit */
	public int firstBonusToss = 0;
	public int secondBonusToss = 0;
	
	/* the result represents the score in a real world way. For example, if firstToss == 10, 
	 * then we donate the result as "X" for this round, and "/" for a spare. The rest scores are
	 * donated simply by its number, e.g., if the first toss got 5, and second toss got 4, then 
	 * the result for this round is "45"
	 */
	public String result;
	
	/* the result represents the score during the bonus tosses. Maybe not exist. */
	public String bonusResult;
	
	/* a boolean flag indicates if the round is finished or not. */
	public boolean isFinished = false;
	
	/* a boolean flag indicates if the first toss is finished or not */
	public boolean firstTossFinished = false;
	
	/* perform the first toss */
	public void firstThrow (int pins) {
		firstToss = pins;
		firstTossFinished = true;
		if (firstToss == 10) {
			isFinished = true;
		}
	}
	
	/* perform the second toss if needed. */
	public void secondThrow(int pins) {
		secondToss = pins;
		isFinished = true;
	}
	
	/* decide the status of current round, whether standard, strike or spare. */
	public boolean isStrike() {
		return (firstToss == 10);
	}
	
	public boolean isSpare() {
		return (firstToss != 10) & (firstToss + secondToss == 10);
	}
	
	/* randomly generate some tosses */
	public void randomlyThrow() {
		int random = ThreadLocalRandom.current().nextInt(0, 11);
		firstThrow(random);
		if (!isFinished)
			secondThrow(ThreadLocalRandom.current().nextInt(0, 11 - firstToss));
	}
	
	public String getResult() {
		if (isStrike()) {
			result = "X";
		} else if (isSpare()) {
			result = Integer.toString(firstToss);
			result += "/";
		} else {
			result = Integer.toString(firstToss);
			result += Integer.toString(secondToss);
		}
		return result;
	}

	/* randomly generate some result for bonus tosses */
	public void randomlyBonusThrow() {
		firstBonusToss = ThreadLocalRandom.current().nextInt(0, 11);
		if (firstBonusToss == 10)
			bonusResult = "X";
		else
			bonusResult = Integer.toString(firstBonusToss);
		if (isStrike()) {
			if (firstBonusToss == 10) {
				secondBonusToss = ThreadLocalRandom.current().nextInt(0, 11);
				if (secondBonusToss == 10)
					bonusResult += "X";
				else
					bonusResult += Integer.toString(secondBonusToss);
			} else {
				secondBonusToss = ThreadLocalRandom.current().nextInt(0, 11 - firstBonusToss);
				if (secondBonusToss + firstBonusToss == 10)
					bonusResult += "/";
				else
					bonusResult += Integer.toString(secondBonusToss);
			}	
		}
	}
	
	/* get the sum of scores in this round. (Bonus ae not included) */
	public int getScore() {
		return firstToss + secondToss;
	}
}
