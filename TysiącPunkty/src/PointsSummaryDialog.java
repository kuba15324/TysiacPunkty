import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

public class PointsSummaryDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private Integer[] score;
	private JLabel titleLabel = new JLabel("Podsumowanie punkt\u00F3w");
	private List<JLabel> playerLabels = new ArrayList<>();
	private List<JSpinner> pointSpinners = new ArrayList<>();

	private PointsSummaryDialog(Window parent, String[] playerNames, Integer[] score, int playerIndex, int value) {
		super(parent, ModalityType.DOCUMENT_MODAL);
		this.score = score;
		setDefaultOptions(parent, playerNames);
		int i = 0;
		for(String name : playerNames) {
			JLabel label = new JLabel(name);
			JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 300, 10));
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
			spinner.setFont(new Font("DialogInput", Font.PLAIN, 12));
			label.setBounds(20, 50 + 30 * i, 70, 20);
			spinner.setBounds(90, 50 + 30 * i, 70, 20);
			if(score[i] >= 900)
				spinner.setEnabled(false);
			playerLabels.add(label);
			pointSpinners.add(spinner);
			getContentPane().add(label);
			getContentPane().add(spinner);
			i++;
		}
		
		JButton confirmButton = new JButton("Zatwierd\u017A");
		confirmButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		confirmButton.setBounds(50, 180, 100, 25);
		getContentPane().add(confirmButton);
		confirmButton.addActionListener((action) -> {
			try {
				for(int j = 0; j < 4; j++) 
					score[j] += (int)(pointSpinners.get(j).isEnabled() ? pointSpinners.get(j).getValue() : 0);
			} catch (IndexOutOfBoundsException e) {}
			dispose();
		});
		pointSpinners.get(playerIndex).setValue(value);
		pointSpinners.get(playerIndex).setEnabled(false);
		setVisible(true);
	}

	private PointsSummaryDialog(Window parent, String[] playerNames, Integer[] score) {
		super(parent, ModalityType.DOCUMENT_MODAL);
		this.score = score;
		setDefaultOptions(parent, playerNames);
		int i = 0;
		for(String name : playerNames) {
			JLabel label = new JLabel(name);
			JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, -1000, 1000, 10));
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
			spinner.setFont(new Font("DialogInput", Font.PLAIN, 12));
			label.setBounds(20, 50 + 30 * i, 70, 20);
			spinner.setBounds(90, 50 + 30 * i, 70, 20);
			spinner.setValue(score[i]);
			playerLabels.add(label);
			pointSpinners.add(spinner);
			getContentPane().add(label);
			getContentPane().add(spinner);
			i++;
		}
		JButton confirmButton = new JButton("Zatwierd\u017A");
		confirmButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		confirmButton.setBounds(50, 180, 100, 25);
		getContentPane().add(confirmButton);
		confirmButton.addActionListener((action) -> {
			try {
				for(int j = 0; j < 4; j++) 
					score[j] = (int) pointSpinners.get(j).getValue();
			} catch (IndexOutOfBoundsException e) {}
			dispose();
		});
		setVisible(true);
	}
	
	private void setDefaultOptions(Window parent, String[] playerNames) {
		setTitle("Podsumowanie rundy");
		setSize(200, 250);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(parent);
		setLayout(null);
		if(StartFrame.icon != null)
			setIconImage(StartFrame.icon);
		
		titleLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 20, 190, 20);
		add(titleLabel);
	}

	public static Integer[] doSummary(Window parent, String[] playerNames, Integer[] score, int playerIndex, int value) {
		return new PointsSummaryDialog(parent, playerNames, score, playerIndex, value).score;
	}
	
	public static Integer[] editPoints(Window parent, String[] playerNames, Integer[] score) {
		return new PointsSummaryDialog(parent, playerNames, score).score;
	}
}
