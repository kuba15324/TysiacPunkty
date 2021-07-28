import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WinnerDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public WinnerDialog(Frame parent, String name, int points) {
		super(parent, ModalityType.DOCUMENT_MODAL);
		setTitle("Koniec gry");
		setSize(300, 250);
		setResizable(false);
		setLocationRelativeTo(parent);
		setContentPane(new WinnerPanel(this, name, points));
		if(StartFrame.icon != null)
			setIconImage(StartFrame.icon);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new WinnerDialog(null, "Player name", 1000);
	}
}

class WinnerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel winnerLabel = new JLabel("Zwyci\u0119zca:"),
				   nameLabel = new JLabel("<nazwa>"),
				   pointsLabel = new JLabel("Ilo\u015B\u0107 punkt\u00F3w:"),
				   playerPointsLabel = new JLabel("<punkty>"),
				   backgroundLabel;
	
	public WinnerPanel(Window parent, String name, int points) {
		super();
		setSize(parent.getWidth(), parent.getHeight());
		setLayout(null);
		backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/gif.gif")));
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setForeground(new Color(255, 0, 0));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerPointsLabel.setForeground(new Color(255, 0, 0));
		playerPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		winnerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		pointsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		playerPointsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		
		winnerLabel.setBounds(0, 30, 290, 30);
		nameLabel.setBounds(0, 70, 290, 30);
		pointsLabel.setBounds(0, 110, 290, 30);
		playerPointsLabel.setBounds(0, 150, 290, 30);
		
		nameLabel.setText(name);
		playerPointsLabel.setText(points + "");
		
		backgroundLabel.setBounds(0, 0, 300, 250);
		
		add(winnerLabel);
		add(nameLabel);
		add(pointsLabel);
		add(playerPointsLabel);
		add(backgroundLabel);
	}
}
