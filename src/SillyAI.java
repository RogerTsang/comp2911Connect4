public class SillyAI implements Iai {
	IController control;
	
	public void makeMove() {
		int i = 0;
		while (!control.move(i)) {
			i++;
		}
	}

	public void getController(IController c) {
		this.control = c;
	}
	
	public void removeController() {
		this.control = null;
	}
	
	public String toString() {
		return "I'm silly, I'm sorry";
	}
}
