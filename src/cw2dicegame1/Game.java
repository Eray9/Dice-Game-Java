/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cw2dicegame1;

//Packages
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author E SECURED
 */
public class Game extends JFrame {
    //J swing variables for labels and buttons
    	JPanel topPanel;
	JButton throwButton;
	JButton scoreButton;
	JPanel humanPlayerPanel;
	JPanel computerPlayerPanel;
	JPanel humanDicePanel;
	JPanel computerDicePanel;	
	JLabel humanScoreLabel;
	JLabel computerScoreLabel;
	JButton newGameButton;
	JButton setMaxButton;

	JLabel maxComputerScoreLabel;
	JLabel maxHumanScoreLabel;

	JPanel statusPanel;
	JLabel statusLabel;

	JLabel[] humanDiceImageLabel = new JLabel[5];
	JLabel[] computerDiceImageLabel = new JLabel[5];
	JCheckBox[] keepCheckBox = new JCheckBox[5];

        // Variables
	Dice dice = new Dice();
	Die[] dies;
	int humanScore = 0;
	int computerScore = 0;
	int maxScore = 101;
	int nextMaxScore = 101;
	int win, lose;

	boolean computerFinished = true;
	boolean humanScored = false;
        //Array List
	ArrayList<Integer> humanTurns = new ArrayList<Integer>(); 
	ArrayList<Integer> computerTurns = new ArrayList<Integer>();

	public Game()
	{
		try 
		{       //
			File inputFile = new File("gamestatistics.txt");
                    try (Scanner inputStream = new Scanner(inputFile)) {
                        win = inputStream.nextInt();
                        lose = inputStream.nextInt();
                    }
		}
		catch (IOException e)
		{
			System.out.println("Writing file error");
		}
		
	}

	private void createControls() // J swing controls and layout format
	{
		Container container = getContentPane();

		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		throwButton = new JButton("Throw");
		scoreButton = new JButton("Score");
		topPanel.add(throwButton);
		topPanel.add(scoreButton);
	        container.add(topPanel, BorderLayout.PAGE_START);

		scoreButton.setEnabled(false);

		humanPlayerPanel = new JPanel();
		computerPlayerPanel = new JPanel();
		humanDicePanel = new JPanel();
		computerDicePanel = new JPanel();
		statusPanel = new JPanel();

		JPanel statusTextPanel = new JPanel();
		JPanel statusButtonPanel = new JPanel();
		JButton newGameButton = new JButton("New Game");
                statusButtonPanel.add(newGameButton);
		newGameButton.addActionListener(new NewGameButtonListener(this));

		setMaxButton = new JButton("Set Max Score ("+nextMaxScore+")");
		statusButtonPanel.add(setMaxButton);
		setMaxButton.addActionListener(new SetMaxScoreButtonListener(this));

		statusPanel.add(statusTextPanel, BorderLayout.CENTER);
		statusPanel.add(statusButtonPanel, BorderLayout.LINE_END);
		statusLabel = new JLabel("");
		statusTextPanel.add(statusLabel);

		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new GridLayout(2,1));
		container.add(internalPanel, BorderLayout.CENTER);

		JPanel humanPanelHolder = new JPanel();
		JPanel computerPanelHolder = new JPanel();
		internalPanel.add(humanPanelHolder);
		internalPanel.add(computerPanelHolder);

		humanPanelHolder.add(humanPlayerPanel, BorderLayout.LINE_START);
		humanPanelHolder.add(humanDicePanel, BorderLayout.CENTER);
		computerPanelHolder.add(computerDicePanel, BorderLayout.CENTER);
		computerPanelHolder.add(computerPlayerPanel, BorderLayout.LINE_END);
		container.add(statusPanel, BorderLayout.PAGE_END);

		humanPlayerPanel.setLayout(new BoxLayout(humanPlayerPanel, BoxLayout.Y_AXIS));
		computerPlayerPanel.setLayout(new BoxLayout(computerPlayerPanel, BoxLayout.Y_AXIS));
		
		JLabel label1 = new JLabel(" You ");
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		humanPlayerPanel.add(label1);
		JLabel label2 = new JLabel(" Computer ");
		label2.setAlignmentX(Component.CENTER_ALIGNMENT);
		computerPlayerPanel.add(label2);

		JLabel label5 = new JLabel("---------------------");
		label5.setAlignmentX(Component.CENTER_ALIGNMENT);
		humanPlayerPanel.add(label5);
		JLabel label6 = new JLabel("---------------------");
		label6.setAlignmentX(Component.CENTER_ALIGNMENT);
		computerPlayerPanel.add(label6);

		JLabel label3 = new JLabel("    Total Score:    ");
		label3.setAlignmentX(Component.CENTER_ALIGNMENT);
		humanPlayerPanel.add(label3);
		JLabel label4 = new JLabel("    Total Score:    ");
		label4.setAlignmentX(Component.CENTER_ALIGNMENT);
		computerPlayerPanel.add(label4);

		
		JPanel humanScorePanel = new JPanel();		
		humanScoreLabel = new JLabel("0");
		humanScorePanel.add(humanScoreLabel);
		humanScorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		humanPlayerPanel.add(humanScorePanel);

		JPanel computerScorePanel = new JPanel();		
		computerScoreLabel = new JLabel("0");
		computerScorePanel.add(computerScoreLabel);
		computerScorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		computerPlayerPanel.add(computerScorePanel);

		maxComputerScoreLabel = new JLabel("/ "+maxScore);
		maxHumanScoreLabel = new JLabel("/ "+maxScore);

		humanScorePanel.add(maxHumanScoreLabel);
		computerScorePanel.add(maxComputerScoreLabel);

		Die[] d = dice.roll();

		for(int i=0; i<5; i++)
		{
			JLabel diceLabel1;
			JLabel diceLabel2;
			JPanel dicePanel;

			dicePanel = new JPanel();
			dicePanel.setLayout(new BoxLayout(dicePanel, BoxLayout.Y_AXIS));
			dicePanel.setBackground(Color.WHITE);
			dicePanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10)); 

			diceLabel1 = new JLabel("");
			diceLabel2 = new JLabel("Dice "+(i+1));
			diceLabel2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 
			keepCheckBox[i] = new JCheckBox("Keep");

			diceLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
			diceLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
			keepCheckBox[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			keepCheckBox[i].setBackground(Color.WHITE);
			keepCheckBox[i].setEnabled(false);
			keepCheckBox[i].addActionListener(new CheckboxListener(this));

			humanDiceImageLabel[i] = new JLabel();
			humanDiceImageLabel[i].setIcon(d[i].getDieImage());	
			humanDiceImageLabel[i].setAlignmentX(Component.CENTER_ALIGNMENT);

			dicePanel.add(diceLabel2);
			dicePanel.add(humanDiceImageLabel[i]);
			dicePanel.add(keepCheckBox[i]);
			humanDicePanel.add(dicePanel);
		}

		for(int i=0; i<5; i++)
		{
			JLabel diceLabel1;
			JLabel diceLabel2;
			JPanel dicePanel;

			dicePanel = new JPanel();
			dicePanel.setLayout(new BoxLayout(dicePanel, BoxLayout.Y_AXIS));
			dicePanel.setBackground(Color.WHITE);
			dicePanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10)); 

			diceLabel1 = new JLabel("");
			diceLabel2 = new JLabel("Dice "+(i+1));
			diceLabel2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 

			diceLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
			diceLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

			computerDiceImageLabel[i] = new JLabel();
			computerDiceImageLabel[i].setIcon(d[i].getDieImage());	
			computerDiceImageLabel[i].setAlignmentX(Component.CENTER_ALIGNMENT);

			dicePanel.add(diceLabel2);
			dicePanel.add(computerDiceImageLabel[i]);
			computerDicePanel.add(dicePanel);
		}

		throwButton.addActionListener(new ThrowButtonListener(this));
		scoreButton.addActionListener(new ScoreButtonListener(this));

		showStatus();
	}

	private void showStatus()//Showing game status.
	{
		statusLabel.setText("    Won games: "+win+"      Lost games: "+lose+"    "); 
	}

	int rerollCount = 0;

	private void computerThrowDices()//Computer dice roll. Random
	{
		Thread computerThread = new Thread()// Performing 2 seperate threads
		{
			public void run()
			{
				Random rnd = new Random();

				int maxJ = 3;
				synchronized (this) {
					computerFinished = false;
					if (computerScore>=maxScore)
						maxJ = 1;
				}

				boolean[] keep = new boolean[5];

				Die[] computerDies = new Die[5];
				for(int j=0; j<maxJ; j++)
				{		
					Die[] d = dice.roll();

					for(int i=0; i<5; i++)
					{
						if (!keep[i])
						{
							computerDies[i] = d[i];
							computerDiceImageLabel[i].setIcon(d[i].getDieImage());
						}
					}

					try {
						Thread.sleep(750); //thread suspends execution for a specified time 0.75 seconds
					} catch (InterruptedException e) {					
					}

					if (rnd.nextInt(2)==1)
					{
						break;
					}					

					for(int i=0; i<5; i++)
					{
						if (rnd.nextInt(2)==1)
						{
							keep[i] = !keep[i];
						}
					}
				}

				int summ = 0;
				for(int i=0; i<5; i++)
				{
					summ += computerDies[i].getValue();
				}		

				synchronized (this) {
					computerScore += summ;
					computerScoreLabel.setText(String.format("%d", computerScore));
					computerTurns.add(new Integer(computerScore));
					if (humanScored)
					{
						throwButton.setEnabled(true);
						checkWinner();
					}
					computerFinished = true;
				}
			}
		};
		computerThread.start();
	}

	public void newGame()
	{
		synchronized(this)
		{
			if (computerFinished)
			{
				maxScore = nextMaxScore;
				humanScore = 0;
				computerScore = 0;			
				humanScored = false;
				humanTurns = new ArrayList<Integer>();
				computerTurns = new ArrayList<Integer>();

				for(int i=0; i<5; i++)
				{
					keepCheckBox[i].setEnabled(false);				
					keepCheckBox[i].setSelected(false);
				}

				throwButton.setText("Throw");
				throwButton.setEnabled(true);
				scoreButton.setEnabled(false);
				humanScoreLabel.setText("0");
				computerScoreLabel.setText("0");
				maxComputerScoreLabel.setText("/ "+maxScore);
				maxHumanScoreLabel.setText("/ "+maxScore);
			} 	
		}
	}

	private void saveStatistics()//Saving to file win and lose score of user
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("gamestatistics.txt"));
	
			writer.write(win+" "+lose);
			
			writer.close();
		}
		catch (IOException e)
		{
		}
		
	}
	
	public void checkWinner()//Checking winner between human and computer
	{
		if (computerScore>=maxScore || humanScore>=maxScore)
		{
			if (computerScore<humanScore)
			{
				JOptionPane.showMessageDialog(null, "You win!", "Game is finished", JOptionPane.INFORMATION_MESSAGE);
				throwButton.setEnabled(false);
				scoreButton.setEnabled(false);
				win++;
				showStatus();
				saveStatistics();
			}
			else if (computerScore>humanScore)
			{
				JOptionPane.showMessageDialog(null, "You lose!", "Game is finished", JOptionPane.INFORMATION_MESSAGE);
				throwButton.setEnabled(false);
				scoreButton.setEnabled(false);
				lose++;
				showStatus();
				saveStatistics();
			}
			
		}
	}

	public void setMaxScore()//Setting maximum score
	{
	 		String s = JOptionPane.showInputDialog(null, "Input max score (Will only change on new game):", new Integer(nextMaxScore));
			nextMaxScore = Integer.parseInt(s);
			setMaxButton.setText("Set Max Score ("+nextMaxScore+")");
		
				
	}

	public void throwDices()// throwing dice with the option to reroll
	{
		boolean noReroll = false;

		synchronized (this)
		{
			humanScored = false;
			if (humanScore>=maxScore)
				noReroll = true;
		}

		if (throwButton.getText().equals("Throw")) 
		{
			computerThrowDices();

			dies = dice.roll();
			for(int i=0; i<5; i++)
			{
				humanDiceImageLabel[i].setIcon(dies[i].getDieImage());
				keepCheckBox[i].setEnabled(true);				
				keepCheckBox[i].setSelected(false);
			}

			if (noReroll)
			{
				scoreDices();				
			} 
                        else {
				rerollCount = 2;
				throwButton.setText("Reroll");
				scoreButton.setEnabled(true);
			}
		}
		else if (throwButton.getText().equals("Reroll")) 
		{
			Die[] d = dice.roll();
			for(int i=0; i<5; i++)
			{
				if (!keepCheckBox[i].isSelected())
				{
					dies[i] = d[i];
					humanDiceImageLabel[i].setIcon(dies[i].getDieImage());
				}
			}

			rerollCount--;
			if (rerollCount==0)
			{
				scoreDices();
			}
		}		
	}

	public void checkedBoxes()//Selected checkboxes keep the associated dice
	{
		int count = 0;
		for(int i=0; i<5; i++)
		{
			if (keepCheckBox[i].isSelected())
			{
				count++;
			}
		}
		
		throwButton.setEnabled(count!=5);		
	}

	public void scoreDices()//Scoring dices after dice rolls
	{
		int summ = 0;
		for(int i=0; i<5; i++)
		{
			summ += dies[i].getValue();
			keepCheckBox[i].setEnabled(false);		
			keepCheckBox[i].setSelected(false);
		}
		
		throwButton.setText("Throw");
		scoreButton.setEnabled(false);

		synchronized (this) {
			humanScore += summ;
			humanScoreLabel.setText(String.format("%d", humanScore));
			humanTurns.add(new Integer(humanScore));

			if (!computerFinished)
				throwButton.setEnabled(false);
			else
			{
				throwButton.setEnabled(true);
				checkWinner();
			}

			humanScored = true;
		}
	}
	
	public static void main(String[] args)//Game main method

	{
		Game game = new Game();
		game.createControls();	
		game.setTitle("Dice Game");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.pack();
		game.setVisible(true);
	}

         // Implementations
	private class ThrowButtonListener implements ActionListener { 
		private Game game;

		public ThrowButtonListener(Game game) {
			this.game = game;
		}

		public void actionPerformed(ActionEvent e) {
	
			Thread humanThread = new Thread()
			{
				public void run()
				{
					game.throwDices();
				}
			};

			humanThread.start();
		}
	}
        
       

	private class ScoreButtonListener implements ActionListener {
		private Game game;

		public ScoreButtonListener(Game game) {
			this.game = game;
		}

		public void actionPerformed(ActionEvent e) {
			game.scoreDices();
		}
	}

	private class NewGameButtonListener implements ActionListener {
		private Game game;

		public NewGameButtonListener(Game game) {
			this.game = game;
		}

		public void actionPerformed(ActionEvent e) {
			game.newGame();
		}
	}

	private class SetMaxScoreButtonListener implements ActionListener { 
		private Game game;

		public SetMaxScoreButtonListener(Game game) {
			this.game = game;
		}

		public void actionPerformed(ActionEvent e) {
			game.setMaxScore();
		}
		
	} 

	private class CheckboxListener implements ActionListener {
		private Game game;

		public CheckboxListener(Game game) {
			this.game = game;
		}

		public void actionPerformed(ActionEvent e) {
			game.checkedBoxes();
		}
	}
    
    
    
}
