import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameTest {

	BowlingGame game;

	@Before
	public void initialize() {
		game = new BowlingGame();
	}

	// @Test
	// public void canRollGutterGame() {
	// rollMany(0, 20);
	// assertEquals(0, game.getScore());
	// }
	//
	// @Test
	// public void canRollAllOnes() {
	// rollMany(1, 20);
	// assertEquals(20, game.getScore());
	// }
	//

	//
	// @Test
	// public void canRollPerfectGame() {
	// rollMany(10, 12);
	// assertEquals(300, game.getScore());
	// }

	@Test
	public void canRollGutterGame() {
		rollMany(0, 20);
		assertEquals(0, game.getScore());
	}

	@Test
	public void canRollAllOnes() {
		rollMany(1, 20);
		assertEquals(20, game.getScore());
	}

	@Test
	public void canRollPerfectGame() {
		rollMany(10, 10);
		game.rollBonus(10, 10);
		assertEquals(300, game.getScore());
	}

	@Test
	public void canRandomlyRoll() {
		game.randomlyRoll();
	}

	@Test
	public void canPlay() {
		game.randomPlay();
	}

	@Test
	public void canPlayWithInput() {
		game.playWithInput();
	}

	// @Test

	private void rollMany(int pins, int rolls) {
		for (int i = 0; i < rolls; i++) {
			game.roll(pins);
		}
	}
}
