import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static final int Player1 = 0;
	public static final int Player2 = 1;
	public static final int WIN = 2;
	public static final int DRAW = 3;
	public static final int RESTART = 4;
	private final AudioClip player1_drop;
	private final AudioClip player2_drop;
	private final AudioClip winGame;
	private final AudioClip draw;
	private final AudioClip restart;
	
	public Sound() {
		player1_drop = Applet.newAudioClip(Sound.class.getResource("realisticdrop1.wav"));
		player2_drop = Applet.newAudioClip(Sound.class.getResource("realisticdrop2.wav"));
		winGame = Applet.newAudioClip(Sound.class.getResource("normalwin.wav"));
		draw = Applet.newAudioClip(Sound.class.getResource("draw.wav"));
		restart = Applet.newAudioClip(Sound.class.getResource("restart.wav"));
	}
	
	public synchronized void play(final int type) {
		try {
			new Thread() {
				public void run() {
					switch (type) {
					case Player1 : player1_drop.play(); break;
					case Player2 : player2_drop.play();; break;
					case WIN: winGame.play(); break;
					case DRAW: draw.play(); break;
					case RESTART: restart.play(); break;
					}
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}