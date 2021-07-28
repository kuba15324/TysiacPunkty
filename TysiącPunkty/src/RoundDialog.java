import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

public class RoundDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private Integer[] score;
	private Font labelFont = new Font("Comic Sans MS", Font.PLAIN, 14),
				 dialogFont = new Font("DialogInput", Font.PLAIN, 13);
	
	private JComboBox<String> playerComboBox;
	private JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(100, 100, 300, 10));
	private JButton confirmButton = new JButton("Zatwierd\u017A");
	private JLabel whoPlaysLabel = new JLabel("Kto gra?:"),
				   roundLabel = new JLabel("Runda: "),
				   meldunekLabel = new JLabel("Na meldunku: "),
				   howMuchLabel = new JLabel("Za ile?:");

	public RoundDialog(Window parent, String[] playerNames, Integer[] playerScores, int naMeldunku, int roundNr) {
		super(parent, ModalityType.DOCUMENT_MODAL);
		setTitle("Nowa runda");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(250, 250);
		setResizable(false);
		setLocationRelativeTo(parent);
		setLayout(null);
		if(StartFrame.icon != null)
			setIconImage(StartFrame.icon);
		score = playerScores;
		
		playerComboBox = new JComboBox<>(playerNames);
		playerComboBox.setSelectedIndex(naMeldunku);
		playerComboBox.setFont(dialogFont);
		playerComboBox.setBounds(90, 80, 120, 25);
		add(playerComboBox);
		
		whoPlaysLabel.setFont(labelFont);
		whoPlaysLabel.setBounds(20, 80, 90, 25);
		add(whoPlaysLabel);
		
		roundLabel.setFont(labelFont);
		roundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roundLabel.setBounds(0, 10, 240, 30);
		roundLabel.setText("Runda: " + roundNr);
		add(roundLabel);
		
		meldunekLabel.setFont(labelFont);
		meldunekLabel.setHorizontalAlignment(SwingConstants.CENTER);
		meldunekLabel.setBounds(0, 40, 240, 30);
		meldunekLabel.setText(meldunekLabel.getText() + playerNames[naMeldunku]);
		add(meldunekLabel);
		
		valueSpinner.setFont(dialogFont);
		valueSpinner.setBounds(90, 120, 120, 25);
		add(valueSpinner);
		
		howMuchLabel.setFont(labelFont);
		howMuchLabel.setBounds(20, 120, 90, 25);
		add(howMuchLabel);
		
		confirmButton.setFont(labelFont);
		confirmButton.setBounds(60, 160, 120, 30);
		add(confirmButton);
		confirmButton.addActionListener((action) -> {
			int playerIndex = (int) playerComboBox.getSelectedIndex(), value = (int) valueSpinner.getValue();
			JOptionPane.showMessageDialog(this, "Gra: " + playerNames[playerIndex] + " za " + value, "Gra w toku", JOptionPane.INFORMATION_MESSAGE);
			int ifWin = JOptionPane.showConfirmDialog(this, "Czy " + playerNames[playerIndex] + " ugra³ " + value + "?", "Koniec rundy", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(ifWin == JOptionPane.YES_OPTION) 
				score[playerIndex] += value;
			else score[playerIndex] -= value;
			setVisible(false);
			score = PointsSummaryDialog.doSummary(this, playerNames, score, playerIndex, value);
			dispose();
		});
			
		setVisible(true);
	}
	
	public static Integer [] showRoundDialog(Window parent, String[] playerNames, Integer[] playerScores, int naMeldunku, int roundNr) {
		return new RoundDialog(parent, playerNames, playerScores, naMeldunku, roundNr).score;
	}
}
