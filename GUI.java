import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener{
	private Game game = new Game();

	// components
	private JLabel imgLabel = new JLabel(new ImageIcon("image/chess.png"), JLabel.CENTER);
	private JLabel txtLabel = new JLabel("text goes here", SwingConstants.CENTER);
	private JButton btn1 = new JButton("Start");
	private JButton btn2 = new JButton("Help");
	private JButton menuBtn = new JButton("Menu");
	private JButton saveBtn = new JButton("Save");
	private JButton[][] squares = new JButton[8][5];
	private JPanel jp1 = new JPanel(new BorderLayout());	// top menu panel
	private JPanel jp2 = new JPanel(new BorderLayout());	// bottom menu panel
	private JPanel jp3 = new JPanel(new GridLayout(8, 5));	// top board panel
	private JPanel jp4 = new JPanel(new BorderLayout());	// bottom board panel

	public GUI(){
		super("Barsoomian Chess");

		// add components to menu JPanel
		jp1.add(imgLabel, BorderLayout.CENTER);
		jp2.add(btn1, BorderLayout.NORTH);
		jp2.add(btn2, BorderLayout.SOUTH);
		btn1.addActionListener(this);
		btn2.addActionListener(this);

		// add components to board JPanel
		for(int i=0; i<8; i++){
			for(int j=0; j<5; j++){
				squares[i][j] = new JButton();
				squares[i][j].setBackground(Color.WHITE);
				jp3.add(squares[i][j]);
				squares[i][j].addActionListener(this);
			}
		}
		jp4.add(menuBtn, BorderLayout.WEST);
		jp4.add(txtLabel, BorderLayout.CENTER);
		jp4.add(saveBtn, BorderLayout.EAST);
		menuBtn.addActionListener(this);
		saveBtn.addActionListener(this);

		// add Jpanels to JFrame
		this.setLayout(new BorderLayout());
		this.add(jp1, BorderLayout.CENTER);
		this.add(jp2, BorderLayout.SOUTH);

		// Size and display window
		setSize(360,640);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Function displays menu panel
	public void menu(){
		getContentPane().removeAll();
		btn1.setText("Start");
		btn2.setText("Help");
		this.add(jp1, BorderLayout.CENTER);
		this.add(jp2, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}

	// Function displays board panel
	public void board(){
		getContentPane().removeAll();
		this.add(jp3, BorderLayout.CENTER);
		this.add(jp4, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}

	// Function refreshes board button icons. To be called after any change is made to the piecesArray in Game
	public void refreshBoard(Piece[][] piecesArray){
		for(int i=0; i<8; i++){
			for(int j=0; j<5; j++){
				if(piecesArray[i][j] == null){
					squares[i][j].setIcon(null);
				}
				else{
					squares[i][j].setIcon(new ImageIcon(piecesArray[i][j].iconPath));
				}
			}
		}
	}

	// The most important function in the whole wide world!
	public static void main(String[] a){
		new GUI();
	}

	// mouse click event
	@Override
	public void actionPerformed(ActionEvent e){
		JButton temp = (JButton)e.getSource();
		// menu panel buttons
		if (temp == btn1){
			if(btn1.getText() == "Start"){
			btn1.setText("New Game");
			btn2.setText("Load Game");
			}
			else if(btn1.getText() == "New Game"){
				board();
				game.newGame();
				txtLabel.setText(game.curPlayer + "'s turn");
				refreshBoard(game.piecesArray);
			}
		}
		if (temp == btn2){
			if(btn2.getText() == "Help"){
				JOptionPane.showMessageDialog(this,game.helpTxt(), "Help", JOptionPane.INFORMATION_MESSAGE);
			}
			else if(btn2.getText() == "Load Game"){
				board();
				game.loadGame();
				txtLabel.setText(game.curPlayer + "'s turn");
				refreshBoard(game.piecesArray);
			}
		}

		// board panel buttons
		for(int i=0; i<8; i++){
			for(int j=0; j<5; j++){
				if(temp == squares[i][j]){
					Piece piece = game.piecesArray[i][j];

					// if piece is already selected
					if(game.isPieceSelected){
						if((game.selectedPiece[0] == i && game.selectedPiece[1] == j) || (piece != null && piece.pieceColor == game.curPlayer)){
							game.isPieceSelected = false;
							squares[game.selectedPiece[0]][game.selectedPiece[1]].setBackground(Color.WHITE);
						}
						else{
							boolean test = false;

							// get direction and max step to know which test to use
							String direction = game.piecesArray[game.selectedPiece[0]][game.selectedPiece[1]].direction;
							int maxStep = game.piecesArray[game.selectedPiece[0]][game.selectedPiece[1]].maxStep;

							// move validation tests
							if(direction == "up")
								test = game.upTest(game.selectedPiece[0], game.selectedPiece[1], i, j);
							else if(direction == "down")
								test = game.downTest(game.selectedPiece[0], game.selectedPiece[1], i, j);
							else if(direction == "diagonal")
								test = game.diagonalTest(game.selectedPiece[0], game.selectedPiece[1], i, j);
							else if(direction == "any")
								test = game.anyTest(game.selectedPiece[0], game.selectedPiece[1], i, j, maxStep);

							// if test is true move piece
							if(test){
								if(piece == null || (piece != null && piece.pieceColor != game.curPlayer)){

									// If selected box is not empty, call winTest()
									// Game ends if winTest return true
									if (piece != null && game.winTest(i, j) == true){
										if(game.winTest(i, j) == true){
											String text = game.winTxt();
											game.movePiece(game.selectedPiece[0], game.selectedPiece[1], i, j);
											squares[game.selectedPiece[0]][game.selectedPiece[1]].setBackground(Color.WHITE);
											refreshBoard(game.piecesArray);
											JOptionPane.showMessageDialog(this, text, "Game Over", JOptionPane.INFORMATION_MESSAGE);
											menu();
											game.isPieceSelected = false;
											squares[game.selectedPiece[0]][game.selectedPiece[1]].setBackground(Color.WHITE);
											break;
										}
									}

									// Moving piece
									String prevPlayer = game.curPlayer;
									game.movePiece(game.selectedPiece[0], game.selectedPiece[1], i, j);
									squares[game.selectedPiece[0]][game.selectedPiece[1]].setBackground(Color.WHITE);

									// swap stars and cross if 4turntest returns true
									if(game.fourTurnTest() == true){
										// swap function here
										//game.CrossToStar();
										game.StarToCross();
										refreshBoard(game.piecesArray);
									}

									// fliparrowbox if arrowbox reaches end of board test returns true
									if(game.piecesArray[i][j].type == "arrowbox" && game.arrowBoxReachEndTest(i) == true){
										// flip function here
										game.flipArrowBox(i,j, prevPlayer);
										refreshBoard(game.piecesArray);
									}

									refreshBoard(game.piecesArray);
									txtLabel.setText(game.curPlayer + "'s turn");
								}
							}
							else{
								game.isPieceSelected = false;
								squares[game.selectedPiece[0]][game.selectedPiece[1]].setBackground(Color.WHITE);
							}
						}
					}
					// if no piece is selected
					else{
						game.selectPiece(i,j);
						if(game.isPieceSelected){
							if(piece.pieceColor == "RED")
								squares[i][j].setBackground(Color.RED);
							else if(piece.pieceColor == "GREEN")
								squares[i][j].setBackground(Color.GREEN);
						}
					}
				}
			}
		}

		if (temp == menuBtn){	menu();}
		if (temp == saveBtn){	game.saveGame("save.sav");}
	}
}
