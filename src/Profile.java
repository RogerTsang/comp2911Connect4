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
	
	/**
	 * Create a new Profile
	 * @param name Name of profile to be created
	 */
	public Profile(String name) {
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
		
	}
	
	/**
	 * Get the wins against easy AI
	 * @return Number of wins
	 */
	public int getwAIE() {
		return wAIE;
	}
	
	/**
	 * Add a win against an easy AI
	 */
	public void addwAIE() {
		this.wAIE++;
	}
	
	/**
	 * Get name of profile
	 * @return Name of profile
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the win/loss ratio against human opponents
	 * @return Win/loss ratio against humans
	 */
	public float getWLRatioH() {
		if(this.getlP()+this.getdP()+this.getwP() == 0) return this.getwP();
		return this.getwP()/(this.getlP()+this.getdP()+this.getwP());
	}
	
	/**
	 * Get the win/loss ratio against novice AI
	 * @return Win/loss ratio against novice AI
	 */
	public float getWLRatioAINovice() {
		if(this.getwAIE()+this.getdAIE()+this.getwAIE() == 0) return this.getwAIE();
		return this.getwAIE()/(this.getlAIE()+this.getwAIE()+this.getdAIE());
	}
	
	/**
	 * Get the win/loss ratio against experienced AI
	 * @return Win/loss ratio against experienced AI
	 */
	public float getWLRatioAIExperienced() {
		if(this.getlAIH()+this.getdAIH()+this.getwAIH() == 0) return this.getwAIH();
		return this.getwAIH()/(this.getlAIH() + this.getwAIH() + this.getdAIH());
	}

	/**
	 * Get the number of games this player has played
	 * @return Number of games played
	 */
	public int getNumGamesPlayed() {
		return numGamesPlayed;
	}
	
	/**
	 * Add a game to the total number of games played
	 */
	public void addGamePlayed() {
		numGamesPlayed++;
	}
	
	/**
	 * Get number of losses against easy AI opponents
	 * @return Number of losses against easy AI opponents
	 */
	public float getlAIE() {
		return lAIE;
	}
	
	/**
	 * Add a loss against an easy AI opponent
	 */
	public void addlAIE() {
		this.lAIE++;
	}
	
	/**
	 * Get number of draws against easy AI opponent
	 * @return Number of draws against easy AI
	 */
	public float getdAIE() {
		return dAIE;
	}
	
	/**
	 * Add a draw against easy AI opponent
	 */
	public void adddAIE() {
		this.dAIE++;
	}
	
	/**
	 * Get the number of wins against hard AI opponent
	 * @return Number of wins against hard AI
	 */
	public float getwAIH() {
		return wAIH;
	}
	
	/**
	 * Add a win against hard AI opponent
	 */
	public void addwAIH() {
		this.wAIH++;
	}
	
	/**
	 * Get losses against hard AI opponents
	 * @return Number of losses against hard AI
	 */
	public float getlAIH() {
		return lAIH;
	}
	
	/**
	 * Add a loss against a hard AI opponent
	 */
	public void addlAIH() {
		this.lAIH++;
	}

	/**
	 * Get number of draws against hard AI opponents
	 * @return Number of draws against hard AI
	 */
	public float getdAIH() {
		return dAIH;
	}

	/**
	 * Add a draw against a hard AI opponent
	 */
	public void adddAIH() {
		this.dAIH++;
	}
	
	/**
	 * Get number of wins against human opponents
	 * @return Number of wins against other players
	 */
	public float getwP() {
		return wP;
	}
	
	/**
	 * Add a win to human opponents
	 */
	public void addwP() {
		this.wP++;
	}
	
	/**
	 * Get number of losses against human opponents
	 * @return Number of losses against other players
	 */
	public float getlP() {
		return lP;
	}
	
	/**
	 * Add loss against human opponent
	 */
	public void addlP() {
		this.lP++;
	}
	
	/**
	 * Add a draw against human opponent
	 */
	public void adddP(){
		this.dP++;
	}
	
	/**
	 * Get the number of draws against human opponents
	 * @return Number of draws against other players
	 */
	public float getdP() {
		return dP;
	}
}
