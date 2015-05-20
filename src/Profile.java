import java.io.File;

/**
 * Stores information on the profile of a player
 * @author patrickgilfillan
 *
 */
public class Profile {
	private String name;
	private float wLRatioH;
	private float wLRatioAIEasy;
	private float wLRatioAIHard;
	private int numGamesPlayed;
	private File f;
	
	public Profile(String name) {
		f = null; //Until we implement saving to file
		if (f == null) {
			this.name = name;
			wLRatioH = 0;
			wLRatioAIEasy = 0;
			wLRatioAIHard = 0;
			numGamesPlayed = 0;
		} else {
			//Get profile with the given name from the file
		}
	}

	/*
	public saveProfileToFile() {
		WILL USE THIS METHOD FOR SAVING CURRENT PROFILE TO FILE
	}
	*/
	public String getName() {
		return name;
	}
	
	public float getWLRatioH() {
		return wLRatioH;
	}
	
	public float getWLRatioAIEasy() {
		return wLRatioAIEasy;
	}
	
	public float getWLRatioAIHard() {
		return wLRatioAIHard;
	}
	
	public float getNumGamesPlayed() {
		return numGamesPlayed;
	}
}
