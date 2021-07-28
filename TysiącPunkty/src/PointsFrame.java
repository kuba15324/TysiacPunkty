import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PointsFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private MyScrollPane scrollPane;
	private JButton newRoundButton = new JButton("Nowa runda"),
					exitButton = new JButton("Zako\u0144cz");
	private JLabel meldunekLabel = new JLabel("Na meldunku:");
		
	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width, 
				screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	private int playersCount = 0, naMeldunku = 0, roundNr = 1;
	private String[] playerNames;
	
	public PointsFrame(StartFrame parent, String[] playerNames) {
		//TODO warotœci meldunków
		super("Liczenie punktów w Tysi¹cu");
		setSize(screenWidth, screenHeight);
		if(StartFrame.icon != null)
			setIconImage(StartFrame.icon);
		setUndecorated(true);
		getContentPane().setLayout(null);
		this.playerNames = playerNames;
		setFrameParameters(parent);
		for(@SuppressWarnings("unused") String name : playerNames)
			playersCount++;
		naMeldunku = new Random().nextInt(playersCount);
		setMeldunek();
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				parent.setVisible(true);
				dispose();
			}
		});
	}

	private void setFrameParameters(StartFrame parent) {
		scrollPane = new MyScrollPane(this, playerNames);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Tabela punktów:", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		((TitledBorder)scrollPane.getBorder()).setTitleFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		List<Integer> firstRow = new ArrayList<>();
		for(@SuppressWarnings("unused") String i : playerNames)
			firstRow.add(0);
		scrollPane.addRowToTable(firstRow.toArray(new Integer[0]));
		
		newRoundButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		meldunekLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		
		scrollPane.setBounds(100, 100, screenWidth - 200, screenHeight - 250);
		exitButton.setBounds(screenWidth / 3 * 2 - 200, screenHeight - 100, 400, 50);
		newRoundButton.setBounds(screenWidth / 3 - 200, screenHeight - 100, 400, 50);
		meldunekLabel.setBounds(100, 50, 400, 25);
		
		getContentPane().add(scrollPane);
		getContentPane().add(newRoundButton);
		getContentPane().add(exitButton);
		getContentPane().add(meldunekLabel);
		
		newRoundButton.addActionListener((action) -> {
			scrollPane.addRowToTable(RoundDialog.showRoundDialog(this, playerNames, scrollPane.getLastRow(), naMeldunku, roundNr++));
			if(scrollPane.getWinner() >= 0) {
				int tmp = scrollPane.getWinner();
				newRoundButton.setEnabled(false);
				new WinnerDialog(this, playerNames[tmp], scrollPane.getLastRow()[tmp]);
			}
			else {
				naMeldunku++;
				if(naMeldunku >= playersCount)
					naMeldunku = 0;
				setMeldunek();
			}
		});
		exitButton.addActionListener((action) -> {
			parent.setVisible(true);
			dispose();
		});
	}
	
	private void setMeldunek() {
		meldunekLabel.setText("Na meldunku: " + playerNames[naMeldunku]);
	}
	
	public int getMeldunek() {
		return naMeldunku;
	}
}

class MyScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel tableModel;
	private String[] playerNames;
	private PointsFrame parent;
	
	private JTable table;
	
	MyScrollPane(PointsFrame parent, String [] playerNames) {
		this.playerNames = playerNames;
		this.parent = parent;
		tableModel = new DefaultTableModel(playerNames, 0);
		table = new JTable(tableModel){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; 
			}
		};
		table.setRowHeight(30);
		table.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 22));
		table.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3 && table.getSelectedRow() == tableModel.getRowCount() - 1)
					createPopupMenu(e);
			}
		});
		setViewportView(table);
		for(int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer () {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if(row == table.getRowCount() - 1) {
						if(column == parent.getMeldunek())
							c.setBackground(new Color(179, 179, 179));
						else c.setBackground(Color.white);
					}
					else c.setBackground(Color.white);
					return c;
				}
			});
		}
	}
	
	int getWinner() {
		for(int i = 0; i < tableModel.getColumnCount(); i++) {
			if((Integer) tableModel.getValueAt(tableModel.getRowCount() - 1, i) >= 1000){
				return i;
			}
		}
		return -1;
	}

	void createPopupMenu(MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem editRound = new JMenuItem("Edytuj rundê");
		editRound.addActionListener((action) -> {
			Integer[] score = PointsSummaryDialog.editPoints(parent, playerNames, getLastRow());
			tableModel.removeRow(tableModel.getRowCount() - 1);
			tableModel.addRow(score);
		});
		menu.add(editRound);
		menu.show(e.getComponent(), e.getX(), e.getY());
		
	}

	Integer[] getLastRow() {
		Integer[] data = new Integer[tableModel.getColumnCount()];
		for(int i = 0; i < tableModel.getColumnCount(); i++) {
			data[i] = (Integer) tableModel.getValueAt(tableModel.getRowCount() - 1, i);
		}
		return data;
	}

	void addRowToTable(Integer[] data) {
		tableModel.addRow(data);
	}
}