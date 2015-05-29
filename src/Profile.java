import java.io.File;
import java.io.Serializable;

/**
 * Stores information on the profile of a player
 * @author patrickgilfillan
 *
 */
public class Profile implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int wAIE;	//win against AI Easy
	private int lAIE;	//loss against AI Easy
	private int dAIE;	//draw against AI Easy
	private int wAIH;	//win against AI Hard
	private int lAIH;	//loss against AI Hard
	private int dAIH;	//draw against AI Hard
	private int wP;		//win against Player
	private int lP;		//loss against Player
	private int dP;		//draw against Player
	private int numGamesPlayed;		//overall games played (redundant?)
	private File f;		//I dunno what this is.
	
	public Profile(String name) {
		f = null; //Until we implement saving to file
		if (f == null) {
			this.name = name;
			wAIE=0;
			lAIE=0;
			dAIE=0;
			wAIH=0;
			lAIH=0;
			dAIH=0;
			wP=0;
			lP=0;
			dP=0;
			numGamesPlayed = 0;
		} else {
			//Get profile with the given name from the file
		}
	}
	
	public int getwAIE() {
		return wAIE;
	}

	public void addwAIE() {
		this.wAIE++;
	}

	public String getName() {
		return name;
	}
	
	public float getWLRatioH() {
		if(this.getlP()+this.getdP()+this.getwP() == 0) return this.getwP();
		return this.getwP()/(this.getlP()+this.getdP()+this.getwP());
	}
	
	public float getWLRatioAINovice() {
		if(this.getwAIE()+this.getdAIE()+this.getwAIE() == 0) return this.getwAIE();
		return this.getwAIE()/(this.getlAIE()+this.getwAIE()+this.getdAIE());
	}
	
	public float getWLRatioAIExperienced() {
		if(this.getlAIH()+this.getdAIH()+this.getwAIH() == 0) return this.getwAIH();
		return this.getwAIH()/(this.getlAIH() + this.getwAIH() + this.getdAIH());
	}

	public float getNumGamesPlayed() {
		return numGamesPlayed;
	}
	
	public void addGamePlayed() {
		numGamesPlayed++;
	}

	public float getlAIE() {
		return lAIE;
	}

	public void addlAIE() {
		this.lAIE++;
	}

	public float getdAIE() {
		return dAIE;
	}

	public void adddAIE() {
		this.dAIE++;
	}

	public float getwAIH() {
		return wAIH;
	}

	public void addwAIH() {
		this.wAIH++;
	}

	public float getlAIH() {
		return lAIH;
	}

	public void addlAIH() {
		this.lAIH++;
	}

	public float getdAIH() {
		return dAIH;
	}

	public void adddAIH() {
		this.dAIH++;
	}

	public float getwP() {
		return wP;
	}

	public void addwP() {
		this.wP++;
	}

	public float getlP() {
		return lP;
	}

	public void addlP() {
		this.lP++;
	}

	public void adddP(){
		this.dP++;
	}
	
	public float getdP() {
		return dP;
	}
}
