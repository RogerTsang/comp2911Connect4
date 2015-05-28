import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;


public class GameSystem implements IController, IGame, IGameOptions {
    
	private GameState state;
	private Player currentPlayer;
	private Player winner;
	private Board board;
	private int connectToWin;
	private int turnNumber;
	private int P1Score;
	private int P2Score;
	private Profile player1;
	private Profile player2;
	private Stack<Integer> UndoStack;
	private Stack<Integer> RedoStack;
	private Stack<Integer> winningDiscs;
	private Iai ai;
	private Sound soundEffects;
	
	public GameSystem() {
	    
		this.state = GameState.WAIT_FOR_START;
		this.currentPlayer = Player.P1;
		this.winner = Player.NOONE;
		this.board = new Board();
		this.connectToWin = 4;
		this.turnNumber = 1;
		this.P1Score = 0;
		this.P2Score = 0;
		this.UndoStack = new Stack<Integer>();
		this.RedoStack = new Stack<Integer>();
		this.winningDiscs = new Stack<Integer>();
		this.ai = null;
		this.soundEffects = new Sound();
		this.saveProfile(new Profile("Guest"));
		
	}
	
	public boolean newGame() {
	    
	    // if the game is all ready in a state to begin a new game
	    // there is no need to start a new game
		if (this.state == GameState.WAIT_FOR_START) {
		    return false;
		} else {
			this.state = GameState.WAIT_FOR_START;
			this.currentPlayer = Player.P1;
			this.winner = Player.NOONE;
			this.board.clear();
			this.turnNumber = 1;
			this.UndoStack.clear();
			this.RedoStack.clear();
			return true;
		}
		
	}
	
	public boolean startGame() {
		if (this.state == GameState.WAIT_FOR_START ) {
			soundEffects.play(Sound.RESTART);
			this.state = GameState.PLAYABLE;
			return true;
		} else {
			return false;
		}
	}
	
	public Player getCurrentPlayer() {
	    if (this.turnNumber % 2 == 1) {
	        return Player.P1;
	    } else {
	        return Player.P2;
	    }
	}
	
	public Board getBoard() {
        return this.board;
    }
	
	public boolean makeMove(int column) {
	    
	    if (this.state != GameState.PLAYABLE) {
            return false;
        }
	    
		if (this.board.insert(currentPlayer,column)) {
			if (currentPlayer == Player.P1) {
				soundEffects.play(Sound.Player1);
			} else {
				soundEffects.play(Sound.Player2);
			}
			this.UndoStack.add(column);
			this.RedoStack.clear();
			this.winner = checkWin(this.board,column,this.getCurrentPlayer());
			this.updateProfile();
			switch (this.winner){
				case P1: this.P1Score++; 
						 this.state = GameState.FINISH;
						 soundEffects.play(Sound.WIN);
						 break;
				case P2: this.P2Score++; 
						 this.state = GameState.FINISH;
						 soundEffects.play(Sound.WIN);
						 break;
				case DRAW: this.state = GameState.FINISH;
						 soundEffects.play(Sound.DRAW);
						 break;
				default: switchPlayer(); break;
			}
	        this.turnNumber++;
	        System.out.println("Move Made");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean undo() {
		if (this.state != GameState.PLAYABLE) {
			return false;
		}
		
		if (this.currentPlayer != Player.NOONE && this.ai == null) {
			if (!this.UndoStack.isEmpty()) {
				int lastMove = this.UndoStack.pop();
				this.RedoStack.add(lastMove);
				this.board.remove(lastMove);
				switchPlayer();
				this.turnNumber--;
				return true;
			}
		} else if (this.currentPlayer != Player.NOONE && this.ai != null) {
			if (!this.UndoStack.isEmpty()) {
				int lastMove = this.UndoStack.pop();
				this.RedoStack.add(lastMove);
				this.board.remove(lastMove);
				lastMove = this.UndoStack.pop();
				this.RedoStack.add(lastMove);
				this.board.remove(lastMove);
                this.turnNumber-=2;
				return true;
			}
		}
		return false;
	}
	/*
	/**
	 * Redo the last move, This will cause a player switch
	 * @return True if the redo can be done
	public boolean redo() {
		if (this.state != GameState.PLAYABLE) {
			return false;
		}
		
		if (this.currentPlayer != Player.NOONE && this.ai == null) {
			if (!this.RedoStack.isEmpty()) {
				int reMove = this.RedoStack.pop();
				this.UndoStack.add(reMove);
				this.board.insert(this.currentPlayer, reMove);
				switchPlayer();
				return true;
			}
		} else if (this.currentPlayer != Player.NOONE && this.ai != null) {
			if (!this.RedoStack.isEmpty()) {
				int reMove = this.RedoStack.pop();
				this.UndoStack.add(reMove);
				this.board.insert(this.currentPlayer, reMove);
				reMove = this.RedoStack.pop();
				this.UndoStack.add(reMove);
				this.currentPlayer = Player.P2;
				this.board.insert(this.currentPlayer, reMove);
				this.currentPlayer = Player.P1;
				return true;
			}
		}
		return false;
	}
	*/
	public Player getWinner() {
        return this.winner;
    }
	
	public Stack<Integer> getWinningDiscs() {
        return this.winningDiscs;
    }
	
	/**
     * This is a method to recordWinningDiscs
     * @param col
     * @param row
     * @param mode 0:Vertical 1:Horizontal 2:Up-Left 3:Up-Right 4:Draw
     */
    private void recordWinningDiscs(int col, int row, int mode) {
        this.winningDiscs.clear();
        //Draw
        if (mode == 4) {
            this.winningDiscs.push(0);this.winningDiscs.push(0);
            this.winningDiscs.push(0);this.winningDiscs.push(5);
            this.winningDiscs.push(6);this.winningDiscs.push(0);
            this.winningDiscs.push(6);this.winningDiscs.push(5);
            return;
        }
        //Win
        for (int i = 0; i < this.connectToWin; i++) {
            switch(mode) {
                case 0: this.winningDiscs.push(col); this.winningDiscs.push(row++); break;
                case 1: this.winningDiscs.push(col++); this.winningDiscs.push(row); break;
                case 2: this.winningDiscs.push(col++); this.winningDiscs.push(row++); break;
                case 3: this.winningDiscs.push(col++); this.winningDiscs.push(row--); break;
            }
        }
    }
    
	public boolean isFinish() {
        return (this.state == GameState.FINISH);
    }
	
	public boolean addAI(Iai bot) {
        if (this.state == GameState.WAIT_FOR_START) {
            this.ai = bot;
            return true;
        } else {
            return false;           
        }
    }

    public boolean removeAI() {
        if (this.state == GameState.WAIT_FOR_START && this.ai != null) {
            this.ai = null;
            return true;
        } else {
            return false;           
        }
    }
    
    public boolean hasAI() {
        boolean hasAI = true;
        if (this.ai == null) {
            hasAI =  false;
        }
        return hasAI;
    }
    
    public int getAITurn() {
        if (this.ai != null) {
            int AImoveColumn = this.ai.makeMove((IGame)this, this.board.clone());
            this.makeMove(AImoveColumn);
            System.out.println("CMP move made");
            return AImoveColumn;
        } else {
            return -1;
        }
    }
	
	public int getPlayerScore(Player p) {
		switch(p) {
			case P1: return this.P1Score;
			case P2: return this.P2Score;
			default: return -1;
		}			
	}
	
	/**
	 * This method will switch player between human P1 or P2(AI).
	 */
	private void switchPlayer() {
		if (this.currentPlayer == Player.P1) {
			this.currentPlayer = Player.P2;
		} else if (this.currentPlayer == Player.P2) {
			this.currentPlayer = Player.P1;
		}
	}	
	
    public Player checkWin(Board b, int column, Player p) {
        int numInARow = 0;
        Player[][] boardState = b.getState();
        
        //get location of last disc
        int rowOfLastDisc = 0;
        for (int row = 0; row < this.board.getRowSize(); row++) {
            if (boardState[column][row] != Player.NOONE) {
                rowOfLastDisc = row;
                break;
            }
        }
        
        //check if there is a vertical win
        for (int row = rowOfLastDisc; row < board.getRowSize(); row++) {
            if (boardState[column][row] == p) {
                numInARow++;
                if (numInARow == this.connectToWin) {
                	recordWinningDiscs(column, rowOfLastDisc, 0);
                	updateProfile();
                    return p;
                }
            } else {
                break;
            }
        }
        numInARow = 0;
        
        //check if there is a horizontal win
        //check by looking left to right, so find the left most piece
        //of the possible win connection
        int columnCheckStart = 0;
        for (int col = column; col >= 0; col--) {
            if (boardState[col][rowOfLastDisc] != p) {
                columnCheckStart = col+1;
                break;
            }
        }
        for (int col = columnCheckStart; col < this.board.getColumnSize(); col++) {
            if (boardState[col][rowOfLastDisc] == p) {
                numInARow++;
                if (numInARow == this.connectToWin) {
                	recordWinningDiscs(columnCheckStart, rowOfLastDisc, 1);
                	updateProfile();
                    return p;
                }
            } else {
                break;
            }
        }
        numInARow = 0;
        
        //check if there is a up-and-left diagonal win
        int rowCheckStart = 0;
        columnCheckStart = 0;
        int row = rowOfLastDisc;
        int col = column;
        while (col >= 0 && row >= 0) {
            if (boardState[col][row] != p) {
                columnCheckStart = col+1;
                rowCheckStart = row+1;
                break;
            }
            col--;
            row--;
            if (row < 0 || col < 0) {
                columnCheckStart = col+1;
                rowCheckStart = row+1;
                break;
            }
        }
        col = columnCheckStart;
        row = rowCheckStart;
        while (col < this.board.getColumnSize() && row < this.board.getRowSize()) {
            if (boardState[col][row] == p) {
                numInARow++;
                if (numInARow == this.connectToWin) {
                	recordWinningDiscs(columnCheckStart, rowCheckStart, 2);
                	updateProfile();
                    return p;
                }
                col++;
                row++;
            } else {
                break;
            }
        }
        numInARow = 0;
        
        //check if there is a up-and-right diagonal win
        rowCheckStart = 0;
        columnCheckStart = 0;
        row = rowOfLastDisc;
        col = column;
        while (col >= 0 && row < b.getRowSize()) {
            if (boardState[col][row] != p) {
                columnCheckStart = col+1;
                rowCheckStart = row-1;
                break;
            }
            col--;
            row++;
            if (row == b.getRowSize() || col < 0) {
                columnCheckStart = col+1;
                rowCheckStart = row-1;
                break;
            }
        }
        col = columnCheckStart;
        row = rowCheckStart;
        while (col < b.getColumnSize() && row >= 0) {
            if (boardState[col][row] == p) {
                numInARow++;
                if (numInARow == this.connectToWin) {
                	recordWinningDiscs(columnCheckStart, rowCheckStart, 3);
                	updateProfile();
                    return p;
                }
                col++;
                row--;
            } else {
                break;
            }
            
        }
        numInARow = 0;
        
        //otherwise we may have a draw
        for (col = 0; col < board.getColumnSize(); col++) {
            if (boardState[col][0] == Player.NOONE) break;
            if (col == board.getColumnSize()-1) { 
            	recordWinningDiscs(0, 0, 4);
            	updateProfile();
            	return Player.DRAW;
            }
        }
        return Player.NOONE;
    }
    
    public int getConnectToWin() {
        return this.connectToWin;
    }
    
	public boolean isLegalMove(Board b, int column) {
		if (column < 0 || column >= b.getColumnSize()) return false;
        if (b.getState()[column][0] != Player.NOONE) return false;
        return true;
	}
	
	public ArrayList<String> getProfileNames() {
		ArrayList<String> names = new ArrayList<String>();
		File f = new File("./profiles/");
		String[] fileNames = f.list();
		if (fileNames == null || fileNames.length == 0) return names;
		for (String s : fileNames) {
			s = s.replace(".ser", "");
			names.add(s);
		}
		return names;
	}
	
	public Profile getProfile(String name) {
		Profile p = null;
		try {
			//Deserialize profile
			FileInputStream fileIn = new FileInputStream("./profiles/" + name + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			p = (Profile) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		return p;
	}
	
	public void saveProfile(Profile p) {
		try {
			//Serialize the profile
			File f = new File("./profiles/");
			if(!f.exists()){
				f.mkdir();
			}
			FileOutputStream fileOut = new FileOutputStream("./profiles/" + p.getName() + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public void deleteProfile(String name) {
		File toDelete = new File("./profiles/" + name + ".ser");
		if (toDelete.exists()) toDelete.delete();
		return;
	}
	
	public void setProfile(int playerNumber, String name){
		if(playerNumber == 1) this.player1 = this.getProfile(name);
		else this.player2 = this.getProfile(name);
		return;
	}
	
	//assumes that the first player is always human.
	private void updateProfile(){
		Profile humanProfile1 = null;
		Profile humanProfile2 = null;
		String gameType = "other";
		
		if(this.ai != null){
			if(this.ai.getDifficulty().equals("Experienced")){
				gameType = "AIE";
			} else if(this.ai.getDifficulty().equals("Novice")) {
				gameType = "AIN";
			}
		} else {
			gameType = "human";
		}
		
		if(gameType == "human"){
			humanProfile1 = this.player1;
			humanProfile2 = this.player2;
		} else{
			humanProfile1 = this.player1;
		}
		
		switch(gameType){
			case "AIH":	
				humanProfile1.addGamePlayed();
				switch(this.getWinner()){
					case P1:humanProfile1.addwAIH();
						break;
					case P2:humanProfile1.addlAIH();
						break;
					case DRAW:humanProfile1.adddAIH();
						break;
					default:
						break;
					}
				break;
			case "AIE":	
				humanProfile1.addGamePlayed();
				switch(this.getWinner()){
					case P1:humanProfile1.addwAIE();
						break;
					case P2:humanProfile1.addlAIE();
						break;
					case DRAW:humanProfile1.adddAIE();
						break;
					default:
						break;
					}
				break;
			case "human": 
				switch(this.getWinner()){
					case P1:humanProfile2.addlP();
							humanProfile1.addwP();

							humanProfile1.addGamePlayed();
							humanProfile2.addGamePlayed();
						break;
					case P2:humanProfile2.addwP();
							humanProfile1.addlP();

							humanProfile1.addGamePlayed();
							humanProfile2.addGamePlayed();
						break;
					case DRAW:	humanProfile1.adddP();
								humanProfile2.adddP();

								humanProfile1.addGamePlayed();
								humanProfile2.addGamePlayed();
						break;
					default:
						break;
					}
				break;
			default:
				break;
		}
		this.saveProfile(humanProfile1);
		if(humanProfile2 != null){
			this.saveProfile(humanProfile2);
		}
		return;
	}
	
}
