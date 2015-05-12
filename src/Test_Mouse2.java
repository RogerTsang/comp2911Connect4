import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Test_Mouse2  implements MouseMotionListener,MouseListener{
	
	public Test_Mouse2(JPanel panel){
		Label label = new Label("1");
		panel.add(label, "South");
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.setVisible(true);
	}
	
	
	
	    
	   
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.print("mouse click----" + "\t");
		if (e.getClickCount()==1) {
			System.out.println("single click！");
		} else if (e.getClickCount()==2) {
			System.out.println("double click！");
		} else if (e.getClickCount()==3) {
			System.out.println("triple click！！");
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("press mouse");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("loose mouse");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}