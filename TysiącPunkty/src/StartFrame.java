import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class StartFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static Image icon;
	
	private List<JTextField> playerNameTextFields = new ArrayList<>();
	private JSpinner playersCountSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
	private JLabel playersLabel = new JLabel("Liczba graczy");
	private List<JLabel> playerLabels = new ArrayList<>();
	private Font labelFont = new Font("Comic Sans MS", Font.PLAIN, 13),
				 textFieldFont = new Font("Dialog", Font.PLAIN, 12);
	private JPanel panel = new JPanel();
	private JButton startButton = new JButton("Rozpocznij");

	public StartFrame() {
		super("Liczenie punktów w Tysi¹cu");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(220, 290);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		try {
			icon = ImageIO.read(getClass().getResource("/icon.gif"));
		} catch (IOException e1) {
			icon = null;
		}
		if(icon != null)
			setIconImage(icon);
		for(int i = 0; i < 4; i++) {
			JLabel label = new JLabel("Gracz " + (i + 1) + ":");
			label.setFont(labelFont);
			label.setBounds(20, 30 * i + 80, 60, 20);
			add(label);
			playerLabels.add(label);
			
			JTextField field = new JTextField("Gracz " + (i + 1));
			field.setFont(textFieldFont);
			field.setBounds(80, 30 * i + 80, 96, 20);
			field.setColumns(10);
			field.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(field.getText().length() >= 10)
						e.consume();
				}
			});
			field.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {}
				@Override
				public void focusGained(FocusEvent e) {
					field.selectAll();
				}
			});
			add(field);
			playerNameTextFields.add(field);
			if(i > 1) {
				label.setVisible(false);
				field.setVisible(false);
			}
		}
		
		startButton.setFont(labelFont);
		startButton.setBounds(40, 215, 120, 25);
		add(startButton);
		startButton.addActionListener((action) -> {
			String[] names = new String[(int) playersCountSpinner.getValue()];
			for(int i = 0; i < (int) playersCountSpinner.getValue(); i++) {
				names[i] = playerNameTextFields.get(i).getText();
			}
			new PointsFrame(this, names);
			setVisible(false);
		});
		
		playersLabel.setFont(labelFont);
		playersLabel.setBounds(20, 15, 100, 30);
		add(playersLabel);
		
		panel.setBounds(10, 55, 180, 150);
		add(panel);
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Nazwy graczy:", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		playersCountSpinner.setFont(textFieldFont);
		playersCountSpinner.setBounds(140, 20, 32, 20);
		add(playersCountSpinner);
		playersCountSpinner.addChangeListener((event) -> {
				int value = (int) playersCountSpinner.getValue();
				for(int i = 0; i < 4; i++) {
					playerLabels.get(i).setVisible(i < value ? true : false);
					playerNameTextFields.get(i).setVisible(i < value ? true : false);
				}
		});
		setVisible(true);
	}

	public static void main(String[] args) {
		try{
			new StartFrame();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getStackTrace(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
