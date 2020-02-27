package minesweeper;

import java.util.Scanner;

public class PlayerInput {
	private WorldBuilder world;
	private String rawInput;
	private int xPoint;
	private int yPoint;
	
	
	private Scanner scanner;
	
	public PlayerInput() {
		scanner = new Scanner(System.in);
	}
	
	public void setWorld(WorldBuilder w) {
		this.world = w;
	}

	private PlayerAction action;
	private int xAction;
	private int yAction;
	
	public void takeTurn(GameState gameState) {
		action = PlayerAction.NONE;
		
		// prompt player according to current gameState
		if(gameState == GameState.WIN) {
			System.out.println("You did it! Press ENTER to play again.");
		} else if(gameState == GameState.LOSE) {
			System.out.println("BOOM! Too bad. Press ENTER to try again.");
		} else if (gameState == GameState.HELP) { 
			System.out.println("Press ENTER to continue.");
		} else {
			System.out.print("Type an action, or ? for help: ");
		}
		
		
		String input = scanner.nextLine();
		parsePlayerInput( input );
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			//single thread program
			//no action needed
		}
		
		if(gameState == GameState.PLAY) {
			switch(action) {
			case CLICK:
				world.clickCell(xAction, yAction, true);
				break;
			case MARK:
				world.markCell(xAction, yAction);
				break;
			case HELP:
				world.printHelp();
				break;
			case QUIT:
				System.out.println("Thanks for playing!");
				System.exit(0);
				break;
			case NONE:
			default:
				System.out.println("Sorry, I diddn't quite get that.");
			}
		} else {
			world.setupWorld();
			gameState = GameState.PLAY;
		}
	}
	
	private void parsePlayerInput(String string) {
		string = string.toLowerCase();
		char[] cString = string.toCharArray();
		
		if(string.equals("")) {string = " ";}
		
		if(Character.isDigit( string.charAt(0) )) {
			action = PlayerAction.CLICK;
		} else {
			String firstWord = "";
			
			// pull the first word out of the input string
			for(char c : cString) {
				if(c == ' ') { break; }
				if(Character.isDigit(c) ) { break; }
				
				firstWord += c;
			}
			
			switch(firstWord) {
				case "mark":
					action = PlayerAction.MARK;
					break;
				case "?":
				case "help":
					action = PlayerAction.HELP;
					break;
				case "done":
				case "quit":
				case "exit":
					action = PlayerAction.QUIT;
					break;
				case "click":
				case "mine":
					action = PlayerAction.CLICK;
					break;
				default:
					action = PlayerAction.NONE;
			}
			
		}
		
		// pull the first 2 numbers out of the input string
		// default to -1
		String num1 = "";
		String num2 = "";
		int currentNumber = 0;
		boolean onNumber = false;
		for(char c : cString) {
			
			if(!onNumber) {
				if(Character.isDigit(c) ) {
					onNumber = true;
					currentNumber ++;
				}
			} else {
				if(!Character.isDigit(c) ) {
					onNumber = false;
				}
			}
			
			if(onNumber && currentNumber == 1) {
				num1 += c;
			}
			
			if(onNumber && currentNumber == 2) {
				num2 += c;
			}
			
			try {
				xAction = Integer.parseInt(num1);
				yAction = Integer.parseInt(num2);
			} catch (NumberFormatException e) {
				xAction = -1;
				yAction = -1;
			}
		}
		
		//System.out.println(action + ": " + xAction + ", " + yAction);
	}
	
	
	
	
	
	int n;
	public int getNumber(String string, int min, int max) {
		System.out.print(string + " ");
		String input = scanner.nextLine();
		
		
		try {
			n = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("Sorry, I diddn't quite get that.");
			System.out.println("Please type a single whole number.");
			n = getNumber(string, min, max);
		}
		
		if(n < min || n > max) {
			System.out.println("Please choose a number from " + min + 
					" to " + max + ".");
			n = getNumber(string, min, max);
		}
		
		
		return n; 
	}
	
	
	
	
	
	
}






enum PlayerAction {
	NONE,
	CLICK,
	MARK,
	QUIT,
	HELP;
}
