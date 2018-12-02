// NAME: Caclulating Machine
// DESCRIPTION: Simple calculator based on Swing, regular expressions.

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.*;

public class CalcGUI{
	private JFrame frame;
	private JPanel buttonsPanel;
	private JTextField resultView;
	private HashMap<String,JButton> buttons;
	
	public void go() {
		CalcModel calculator = new CalcModel();
		
		frame=new JFrame("Calculating Machine");
		buttonsPanel=new JPanel(new GridLayout(4,5,3,3));
		
		buttons = new HashMap<String,JButton>();
		for(int i=0; i<10; i++) {
			buttons.put(Integer.toString(i),new JButton(Integer.toString(i)));
		}
		buttons.put("+",new JButton("+"));
		buttons.put("-",new JButton("-"));
		buttons.put("=",new JButton("="));
		buttons.put("*", new JButton("*"));
		buttons.put("/", new JButton("/"));
		buttons.put("C", new JButton("C"));
		buttons.put(".", new JButton("."));
		buttons.put("MR", new JButton("MR"));
		buttons.put("MC", new JButton("MC"));
		buttons.put("M+", new JButton("M+"));
		
		buttonsPanel.add(buttons.get("7"));
		buttonsPanel.add(buttons.get("8"));
		buttonsPanel.add(buttons.get("9"));
		buttonsPanel.add(buttons.get("+"));
		buttonsPanel.add(buttons.get("C"));
		
		buttonsPanel.add(buttons.get("4"));
		buttonsPanel.add(buttons.get("5"));
		buttonsPanel.add(buttons.get("6"));
		buttonsPanel.add(buttons.get("-"));
		buttonsPanel.add(buttons.get("MC"));
		
		buttonsPanel.add(buttons.get("1"));
		buttonsPanel.add(buttons.get("2"));
		buttonsPanel.add(buttons.get("3"));
		buttonsPanel.add(buttons.get("*"));
		buttonsPanel.add(buttons.get("MR"));
		
		buttonsPanel.add(buttons.get("0"));
		buttonsPanel.add(buttons.get("."));	
		buttonsPanel.add(buttons.get("="));
		buttonsPanel.add(buttons.get("/"));
		buttonsPanel.add(buttons.get("M+"));		
		
		resultView=new JTextField();	
		resultView.setEditable(false);
		resultView.setPreferredSize(new Dimension(0,50));
		resultView.setHorizontalAlignment(JTextField.RIGHT);
		
		// font size for resultView
		Font textFont=resultView.getFont();
		textFont=textFont.deriveFont((float)50);
		resultView.setFont(textFont);
		
		//Controller for button actions
		Action calcController = new AbstractAction(){	
		String prevCommand="";
			public void actionPerformed(ActionEvent e) {
				String actionCommand = e.getActionCommand();
				System.out.println(actionCommand);//DEBUG
				if(actionCommand.matches("[+*/=C-]")) {
					if(!resultView.getText().equals("")) { //Checking if there is input
						calculator.calculate(prevCommand, Double.parseDouble(resultView.getText()));
						resultView.setText("");
					}
					prevCommand=actionCommand;
					if(actionCommand.contains("C")) {
						prevCommand="";
						calculator.reset();
					}
					if(actionCommand.contains("=")) {
						resultView.setText(Double.toString(calculator.getResult()));
						prevCommand="";
						calculator.reset();
						System.out.println("= action performed"); //DEBUG
					}
				}
				else if(actionCommand.contains("MC")) {
					calculator.memoryClear();
				}
				else if(actionCommand.contains("M+")) {
					if(!resultView.getText().equals("")) {
						calculator.memoryAdd(Double.parseDouble(resultView.getText()));
					}
					resultView.setText("");
				}
				else if(actionCommand.contains("MR")) {
					resultView.setText(Double.toString(calculator.getMemory()));
				}
				else if(actionCommand.matches("[0-9.]")) {
					resultView.setText(resultView.getText().concat(actionCommand));
				}
			}
		};
		//END OF CONTROLLER
		
		//font size for buttons, setting actionListener for buttons, binding keys.
		Font buttonFont=resultView.getFont();
		buttonFont=buttonFont.deriveFont((float)18);
		for(String key : buttons.keySet()) {
			JButton button=buttons.get(key);
			button.setFont(buttonFont);
			button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
			if(key.matches("[0-9]")) {
				String numKey = "NUMPAD"+key;
				button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(numKey), key);
			}
			if(key.contains("+")) {
				button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ADD"), key);
			}
			if(key.contains("-")) {
				button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SUBTRACT"), key);
			}
			if(key.contains("*")) {
				button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("MULTIPLY"), key);
			}
			if(key.contains("/")) {
				button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DIVIDE"), key);
			}
			if(key.contains(".")) {
				button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DECIMAL"), key);
			}
			button.getActionMap().put(key, calcController);
			button.addActionListener(calcController);
		}
		
		//layout setup
		frame.getContentPane().add(BorderLayout.NORTH, resultView);
		frame.getContentPane().add(BorderLayout.CENTER, buttonsPanel);
		frame.setSize(350, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
		frame.setFocusable(true);
		frame.getRootPane().setDefaultButton(buttons.get("="));
	}
	
	public static void main(String[] args) {
		CalcGUI calc = new CalcGUI();
		calc.go();
	}
}
