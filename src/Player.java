
public enum Player {
	P1,
	P2,
	NOONE,
	DRAW;
	
	@Override
	public String toString() {
	   switch(this) {
	   case P1: return "Player_1";
	   case P2: return "Player_2";
	   case NOONE: return "NoOne";
	   case DRAW: return "Draw";
	   default: throw new IllegalArgumentException();
	   }
	}
}
