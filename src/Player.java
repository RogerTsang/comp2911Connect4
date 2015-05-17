
public enum Player {
	P1,
	P2,
	P1WIN,
	P2WIN,
	NOONE,
	DRAW;
	
	@Override
	public String toString() {
	   switch(this) {
	   case P1: return "Player_1";
	   case P2: return "Player_2";
	   case P1WIN: return "Player_1_winDiscs";
	   case P2WIN: return "Player_2_winDiscs";
	   case NOONE: return "NoOne";
	   case DRAW: return "Draw";
	   default: throw new IllegalArgumentException();
	   }
	}
}
