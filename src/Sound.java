import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Sound effects for Connect4 game
 */
public class Sound {
	private boolean enableToggle;
	public static final int Player1 = 0;
	public static final int Player2 = 1;
	public static final int WIN = 2;
	public static final int DRAW = 3;
	public static final int RESTART = 4;
	public static final int LOSS = 5;
	private final AudioClip player1_drop;
	private final AudioClip player2_drop;
	private final AudioClip winGame;
	private final AudioClip draw;
	private final AudioClip restart;
	private final AudioClip loseGame;
	
	/**
	 * Create a new set of sounds
	 */
	public Sound() {
		enableToggle = true;
		player1_drop = Applet.newAudioClip(getClass().getResource("/realisticdrop1.wav"));
		player2_drop = Applet.newAudioClip(getClass().getResource("/realisticdrop2.wav"));
		winGame = Applet.newAudioClip(getClass().getResource("/normalwin.wav"));
		draw = Applet.newAudioClip(getClass().getResource("/draw.wav"));
		restart = Applet.newAudioClip(getClass().getResource("/restart.wav"));
		loseGame = Applet.newAudioClip(getClass().getResource("/loss.wav"));
	}
	
	/**
	 * Turn the sound on
	 */
	public void Soundon() {
		enableToggle = true;
	}
	
	/**
	 * Turn the sound off
	 */
	public void Soundoff() {
		enableToggle = false;
	}
	
	/**
	 * Finds if the sound is currently on or off
	 * @return If the sound if on or off
	 */
	public boolean getSoundToggle() {
		return enableToggle;
	}
	
	/**
	 * Play a sound effect
	 * @param type Type of sound to play
	 */
	public synchronized void play(final int type) {
		if (enableToggle == false) {
			return;
		} else {
			try {
				new Thread() {
					public void run() {
						switch (type) {
						case Player1 : player1_drop.play(); break;
						case Player2 : player2_drop.play();; break;
						case WIN: winGame.play(); break;
						case DRAW: draw.play(); break;
						case RESTART: restart.play(); break;
						case LOSS: loseGame.play(); break;
						}
					}
				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}