package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import engine.Game;
import engine.Player;
import exceptions.InvalidActionException;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.buildings.Barracks;
import model.buildings.Building;
import model.buildings.Palace;
import model.buildings.Tower;
import model.buildings.Wall;
import model.units.Archer;
import model.units.Assassin;
import model.units.Cavalry;
import model.units.Diplomat;
import model.units.Footman;
import model.units.Hero;
import model.units.Monk;
import model.units.SupportUnit;
import model.units.Unit;
import model.units.Warchief;
import model.world.BuildingCell;
import model.world.Cell;
import model.world.EmptyCell;
import model.world.ResourceCell;
import model.world.UnitCell;

public class ui_Handler extends JFrame implements ActionListener {
	  JRadioButton[] HeroRadios;	
	  JButton[][] MapButtons = new JButton[10][10];
	  JPanel playerInfoPanel = new JPanel(new GridLayout(2, 2));  
	   ButtonGroup heroButtonGroup = new ButtonGroup();
	   Player player1;
	   Player player2;
	   JPanel heroPanel = new JPanel();
	   Hero player1SelcetedHero;
	   Hero player2SelcetedHero;
	   int currentPlayerFlag = 1;  
       JButton playButton = new JButton("Play");  
       Game game;
       JFrame heroSelectionFrame = new JFrame("Hero Selection");
	   boolean gameHasStartedFlag = false;
	  JButton startGameButton = new JButton("Start the game!");
	  boolean playerFlag = true;
	  // selection varaibles 
	 
	  
	  
	  Unit  selectedEnemyUnit;
	  Unit CurrentselectedUnit;
	  Building currentSelectedBuilding;
	  Building selectedEnemyBuilding;
	  
	  
	  
	  
	  JButton upButton = new JButton("UP");
	  JButton downButton = new JButton("DOWN");
	  JButton rightButton = new JButton("RIGHT");
	  JButton leftButton = new JButton("LEFT");
	  
	  JButton attackButton = new JButton("Attack");
	  JButton useSpecialButton = new JButton("UseSpecial");
	  JButton endButton = new JButton("EndTurn");
	  JButton buildButton = new JButton("Build");
	  JButton recruitButton = new JButton("Recruit");
	  JButton upgraButton = new JButton("Upgrade");
	  String[] unitTypes = {"Archer", "Cavalry", "Footman"};
	  JComboBox<String> unitTypeComboBox = new JComboBox<>(unitTypes);
	  JPanel recruitPanel = new JPanel(new BorderLayout());
	  
	  
	  String[] buildOptions = {"Tower", "Wall"};
	  JComboBox<String> buildComboBox = new JComboBox<>(buildOptions);
      JPanel buildPanel = new JPanel(new BorderLayout());
	  
	  boolean selectingBuilding = false;  
	    boolean selectingUnit = false;  
	    
	    boolean selectingEnemyBuilding = false;  
	    boolean selectingEnemyUnit = false;  
	    
	    
	    
	  JLabel currentTurnLabel = new JLabel("Current Turn player 1");
	 
	    public ui_Handler() {	
	 
	        setTitle("Start Game");
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setSize(400, 200);
	        setLocationRelativeTo(null); 

	        startGameButton.addActionListener(this);
            playButton.addActionListener(this);
	        JPanel panel = new JPanel();
	        panel.setLayout(new FlowLayout());
	        panel.add(startGameButton);

	        
	        JLabel objectiveLabel = new JLabel("Objective:");
	        JTextArea objectiveText = new JTextArea(
	                "  Clash of Clans:  The ultimate two player battle royale.Take command of heroes and support units, defend your Palace with tactical buildings, and gather vital resources. Your mission: Obliterate your opponent's Palace to secure victory! It's a high-stakes clash where strategy is key. Will you build for success or go all out with offense? It's a fight to the finish, where the last Palace standing wins it all. Prove your dominance, before ultimately Supercell sues us for using their property :)");

	        
	        objectiveText.setWrapStyleWord(true);
	        objectiveText.setLineWrap(true);
	        objectiveText.setOpaque(false);
	        objectiveText.setEditable(false);
	        objectiveText.setFocusable(false);
 
	        JScrollPane scrollPane = new JScrollPane(objectiveText);
	        scrollPane.setPreferredSize(new Dimension(600, 300));
	        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

	        
	        JPanel objectivePanel = new JPanel();
	        objectivePanel.setLayout(new BorderLayout());
	        objectivePanel.add(objectiveLabel, BorderLayout.NORTH);
	        objectivePanel.add(scrollPane, BorderLayout.CENTER);

	 
	        panel.add(objectivePanel);

	        add(panel);

	        setVisible(true);

	        this.setSize(1000,600);
	        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }      
	    public void actionPerformed(ActionEvent e) {
	    	 SelectionLogic(e);
	        if (e.getSource() == startGameButton) {
	                try {
						Game.loadHeroes("test_heros.csv");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                System.out.println("Game Has Started WOOO");
	               
	            openHeroSelectionWindow();
	        }
	        
	        if(e.getSource() == playButton) {
	            openGameWindow();
	        	System.out.println("Gameplay should start now");
	        	  heroSelectionFrame.dispose();
	        	 
	        	
	        }
	       
	        if (e.getSource() == rightButton) {
	            Point heroLocation = CurrentselectedUnit.getLocation();
	            int currentX = (int) heroLocation.getX();
	            int currentY = (int) heroLocation.getY();

	            try {
	                game.moveRight(currentX, currentY);
	                System.out.println("Player moved right");
	                updateMap();
	            } catch (InvalidActionException e1) {
	                JOptionPane.showMessageDialog(null, "Invalid action: The unit cannot move right.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NotEnoughActionsException e1) {
	                JOptionPane.showMessageDialog(null, "Not enough actions: You have exhausted your actions for this turn.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (MovementException e1) {
	                JOptionPane.showMessageDialog(null, "Movement error: The unit cannot move right.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            }
	            updateMap();
	        }

	        if (e.getSource() == leftButton) {
	            Point heroLocation = CurrentselectedUnit.getLocation();
	            int currentX = (int) heroLocation.getX();
	            int currentY = (int) heroLocation.getY();

	            try {
	                game.moveLeft(currentX, currentY);
	                System.out.println("Player moved left");
	                updateMap();
	            } catch (InvalidActionException e1) {
	                JOptionPane.showMessageDialog(null, "Invalid action: The unit cannot move left.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NotEnoughActionsException e1) {
	                JOptionPane.showMessageDialog(null, "Not enough actions: You have exhausted your actions for this turn.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (MovementException e1) {
	                JOptionPane.showMessageDialog(null, "Movement error: The unit cannot move left.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            }
	            updateMap();
	        }
	        if (e.getSource() == upButton) {
	            Point heroLocation = CurrentselectedUnit.getLocation();
	            int currentX = (int) heroLocation.getX();
	            int currentY = (int) heroLocation.getY();

	            try {
	                game.moveUp(currentX, currentY);
	                System.out.println("Player moved up");
	                updateMap();
	            } catch (InvalidActionException e1) {
	                JOptionPane.showMessageDialog(null, "Invalid action: The unit cannot move up.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NotEnoughActionsException e1) {
	                JOptionPane.showMessageDialog(null, "Not enough actions: You have exhausted your actions for this turn.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (MovementException e1) {
	                JOptionPane.showMessageDialog(null, "Movement error: The unit cannot move up.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            }
	            updateMap();
	        }

	        if (e.getSource() == downButton) {
	            Point heroLocation = CurrentselectedUnit.getLocation();
	            int currentX = (int) heroLocation.getX();
	            int currentY = (int) heroLocation.getY();

	            try {
	                game.moveDown(currentX, currentY);
	                System.out.println("Player moved down");
	                updateMap();
	            } catch (InvalidActionException e1) {
	                JOptionPane.showMessageDialog(null, "Invalid action: The unit cannot move down.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NotEnoughActionsException e1) {
	                JOptionPane.showMessageDialog(null, "Not enough actions: You have exhausted your actions for this turn.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (MovementException e1) {
	                JOptionPane.showMessageDialog(null, "Movement error: The unit cannot move down.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            }
	            updateMap();
	        }

	        if (e.getSource() == endButton) {
	        	  
	        	  playerFlag = !playerFlag;

	            game.endTurn();

	            currentTurnLabel.setText(playerFlag ? "Current Turn: Player 1" : "Current Turn: Player 2");
	         updateMap();
	        }
	        if (e.getSource() == attackButton) {
	            if (selectingEnemyUnit || selectingEnemyBuilding) {
	                Point heroLocation = CurrentselectedUnit.getLocation();
	                int currentX = (int) heroLocation.getX();
	                int currentY = (int) heroLocation.getY();
	                Point targetPosition = null;
	                
	                if (selectingEnemyUnit) {
	                    targetPosition = selectedEnemyUnit.getLocation();
	                } else if (selectingEnemyBuilding) {
	                    targetPosition = getBuildingPosition(selectedEnemyBuilding, game.map);
	                }
	                
	                if (targetPosition != null) {
	                    int tx = (int) targetPosition.getX();
	                    int ty = (int) targetPosition.getY();

	                    // Perform bounds checking here
	                    if (tx >= 0 && tx < game.map.length && ty >= 0 && ty < game.map[0].length) {
	                        try {
	                            game.attack(currentX, currentY, tx, ty);
	                        } catch (InvalidTargetException e1) {
	                            // Handle the case where the target is invalid
	                            JOptionPane.showMessageDialog(null, "Invalid target: The selected enemy cannot be attacked.", "Error", JOptionPane.ERROR_MESSAGE);
	                            e1.printStackTrace();
	                        } catch (InvalidActionException e1) {
	                            // Handle the case where the action is invalid
	                            JOptionPane.showMessageDialog(null, "Invalid action: The selected unit cannot perform an attack.", "Error", JOptionPane.ERROR_MESSAGE);
	                            e1.printStackTrace();
	                        } catch (NotEnoughActionsException e1) {
	                            // Handle the case where there are not enough actions
	                            JOptionPane.showMessageDialog(null, "Not enough actions: The selected unit does not have enough actions.", "Error", JOptionPane.ERROR_MESSAGE);
	                            e1.printStackTrace();
	                        }
	                    } else {
	                        // Handle the case where the target position is out of bounds
	                        JOptionPane.showMessageDialog(null, "Invalid target position: Target position is out of bounds.", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            }
	            updateMap();
	        }
	    
	        if (e.getSource() == upgraButton) {
	            if (selectingBuilding) {
	                // Upgrade the building
	                Point buildingPosition = getBuildingPosition(currentSelectedBuilding, game.map);
	                int currentX = (int) buildingPosition.getX();
	                int currentY = (int) buildingPosition.getY();
	                
	                try {
	                    game.upgrade(currentX, currentY);
	                    System.out.println("Building got upgraded");
	                } catch (InvalidTargetException e1) {
	                    // Handle the case where the target is invalid
	                    JOptionPane.showMessageDialog(null, "Invalid target: The selected building cannot be upgraded.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                } catch (NoAvailableResourcesException e1) {
	                    // Handle the case where there are no available resources
	                    JOptionPane.showMessageDialog(null, "No available resources: You don't have enough resources to upgrade the building.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                } catch (InvalidActionException e1) {
	                    // Handle the case where the action is invalid
	                    JOptionPane.showMessageDialog(null, "Invalid action: The selected building cannot be upgraded.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                }
	            } else if (selectingUnit) {
	                // Upgrade the unit
	                Point unitPosition = CurrentselectedUnit.getLocation();
	                int unitX = (int) unitPosition.getX();
	                int unitY = (int) unitPosition.getY();
	                
	                try {
	                    game.upgrade(unitX, unitY);
	                    System.out.println("Unit got upgraded");
	                } catch (InvalidTargetException e1) {
	                    // Handle the case where the target is invalid
	                    JOptionPane.showMessageDialog(null, "Invalid target: The selected unit cannot be upgraded.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                } catch (NoAvailableResourcesException e1) {
	                    // Handle the case where there are no available resources
	                    JOptionPane.showMessageDialog(null, "No available resources: You don't have enough resources to upgrade the unit.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                } catch (InvalidActionException e1) {
	                    // Handle the case where the action is invalid
	                    JOptionPane.showMessageDialog(null, "Invalid action: The selected unit cannot be upgraded.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                }
	            }
	            updateMap();
	        }

	       
	        if (e.getSource() == recruitButton) {
	            if (currentSelectedBuilding instanceof Barracks) {
	                Point buildingPosition = getBuildingPosition(currentSelectedBuilding, game.map);
	                int currentX = (int) buildingPosition.getX();
	                int currentY = (int) buildingPosition.getY();
	                String selectBuildingType = (String) buildComboBox.getSelectedItem();

	                try {
	                    game.recruitUnit(currentX, currentY, selectBuildingType);
	                    System.out.println("Recruiting a " + selectBuildingType);
	                } catch (InvalidTargetException e1) {
	                    // Handle the case where the target is invalid
	                    JOptionPane.showMessageDialog(null, "Invalid target: The selected unit cannot be recruited.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                } catch (NoAvailableResourcesException e1) {
	                    // Handle the case where there are no available resources
	                    JOptionPane.showMessageDialog(null, "No available resources: You don't have enough resources to recruit the unit.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                } catch (InvalidActionException e1) {
	                    // Handle the case where the action is invalid
	                    JOptionPane.showMessageDialog(null, "Invalid action: The selected unit cannot be recruited.", "Error", JOptionPane.ERROR_MESSAGE);
	                    e1.printStackTrace();
	                }
	            }
	            updateMap();
	        }

	        if (e.getSource() == buildButton) {
	            Point heroLocation = CurrentselectedUnit.getLocation();
	            int currentX = (int) heroLocation.getX();
	            int currentY = (int) heroLocation.getY();
	            int targetX = currentX + 1;
	            int targetY = currentY;
	            String selectedBuilding = (String) buildComboBox.getSelectedItem();

	            try {
	                game.build(currentX, currentY, targetX, targetY, selectedBuilding);
	                System.out.println("Building a " + selectedBuilding);
	            } catch (InvalidActionException e1) {
	                // Handle the case where the action is invalid
	                JOptionPane.showMessageDialog(null, "Invalid action: The selected building cannot be constructed.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NoAvailableResourcesException e1) {
	                // Handle the case where there are no available resources
	                JOptionPane.showMessageDialog(null, "No available resources: You don't have enough resources to construct the building.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NotEnoughActionsException e1) {
	                // Handle the case where there are not enough actions
	                JOptionPane.showMessageDialog(null, "Not enough actions: You have exhausted your actions for this turn.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (InvalidTargetException e1) {
	                // Handle the case where the target is invalid
	                JOptionPane.showMessageDialog(null, "Invalid target: The selected building cannot be constructed here.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            }
	            updateMap();
	        }

	        if (e.getSource() == buildButton) {
	            Point heroLocation = CurrentselectedUnit.getLocation();
	            int currentX = (int) heroLocation.getX();
	            int currentY = (int) heroLocation.getY();
	            int targetX = currentX + 1;
	            int targetY = currentY;
	            String selectedBuilding = (String) buildComboBox.getSelectedItem();

	            try {
	                game.build(currentX, currentY, targetX, targetY, selectedBuilding);
	                JOptionPane.showMessageDialog(null, "Successfully built a " + selectedBuilding, "Building Completed", JOptionPane.INFORMATION_MESSAGE);
	            } catch (InvalidActionException e1) {
	                JOptionPane.showMessageDialog(null, "Invalid action: The selected building cannot be constructed here.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NoAvailableResourcesException e1) {
	                JOptionPane.showMessageDialog(null, "No available resources: You don't have enough resources to construct the building.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (NotEnoughActionsException e1) {
	                JOptionPane.showMessageDialog(null, "Not enough actions: You have exhausted your actions for this turn.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            } catch (InvalidTargetException e1) {
	                JOptionPane.showMessageDialog(null, "Invalid target: The selected building cannot be constructed here.", "Error", JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
	            }
	            updateMap();
	        }
          if(e.getSource() == useSpecialButton) {
        	   Point heroLocation = CurrentselectedUnit.getLocation();
	            int currentX = (int) heroLocation.getX();
	            int currentY = (int) heroLocation.getY();
	            Point enemyUnitPosition = selectedEnemyUnit.getLocation();
                int tx = (int) enemyUnitPosition.getX();
                int ty = (int) enemyUnitPosition.getY();
                
	            
        	  
                try {
                    game.useSpecial(currentX, currentY, tx, ty);
                } catch (InvalidTargetException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid Target: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidActionException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid Action: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                updateMap();
	        }

          
          Player winner = game.checkGameOver();
          if(winner != null) {
        	  JOptionPane.showMessageDialog(null, "Player " + winner.getName() + " won!", "other player very ded ", JOptionPane.INFORMATION_MESSAGE);
          }
          
          
          
          
	       
	        }
	        
	        
	        
	   

	        
	  
	    private void openHeroSelectionWindow() {
	 
	        
	        heroSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        heroSelectionFrame.setSize(800, 600);
	        heroSelectionFrame.setLocationRelativeTo(this);  

	      
	        JPanel heroSelectionPanel = new JPanel();
	        heroSelectionPanel.setLayout(new GridLayout(0, 1));

	       
	        JButton[] heroButtons = new JButton[Game.availableHeroes.size()];  

	        for (int i = 0; i < heroButtons.length; i++) {
	            Hero hero = Game.availableHeroes.get(i);
	            JButton heroButton = new JButton(hero.getName());
	            heroButtons[i] = heroButton;  

	            final int currentIndex = i;  

	            heroButton.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    if (currentPlayerFlag == 1) {
	                        if (player2SelcetedHero != Game.availableHeroes.get(currentIndex)) {
	                            player1SelcetedHero = Game.availableHeroes.get(currentIndex);
	                            currentPlayerFlag = 2; 
	                            JOptionPane.showMessageDialog(null, "Player 1 controls: " + player1SelcetedHero.getName(), "Unit Control", JOptionPane.INFORMATION_MESSAGE);
	                            JOptionPane.showMessageDialog(null, "It's going to be Player 2's turn to choose", "Turn", JOptionPane.INFORMATION_MESSAGE);
	                        } else {
	                            JOptionPane.showMessageDialog(null, "This hero is already chosen by Player 2", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
	                            return;  
	                        }
	                    } else if (currentPlayerFlag == 2) {
	                        if (player1SelcetedHero != Game.availableHeroes.get(currentIndex)) {
	                            player2SelcetedHero = Game.availableHeroes.get(currentIndex);
	                            currentPlayerFlag = 1;  
	                            JOptionPane.showMessageDialog(null, "Player 2 controls: " + player2SelcetedHero.getName(), "Unit Control", JOptionPane.INFORMATION_MESSAGE);
	                            JOptionPane.showMessageDialog(null, "It's going to be Player 1's turn to choose", "Turn", JOptionPane.INFORMATION_MESSAGE);
	                        } else {
	                            JOptionPane.showMessageDialog(null, "This hero is already chosen by Player 1", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
	                            return;  
	                        }
	                    }

	                    
	                    String playerStats = getUnitStats(Game.availableHeroes.get(currentIndex));
	                    showPlayerStatsMessage(playerStats);

	            
	                    if (player1SelcetedHero != null && player2SelcetedHero != null) {
	                        playButton.setEnabled(true); 
	                    }
	                }
	            });

	 
	            heroSelectionPanel.add(heroButton);
	        }

	      
	        JScrollPane scrollPane = new JScrollPane(heroSelectionPanel);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	        heroSelectionFrame.add(scrollPane);

	       
 
	        playButton.setEnabled(false);

	        
	      

	  
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(playButton);
	        heroSelectionFrame.add(buttonPanel, BorderLayout.SOUTH);

	        heroSelectionFrame.setVisible(true);

	    
	        dispose();
	    }


	    
	    

	    private String getSupportUnitStats(SupportUnit selectedSupportUnit) {
	        StringBuilder statString = new StringBuilder();
	 	    if (selectedSupportUnit instanceof Archer) {
	 	        statString.append("Archer\n");
	 	        
	 	    } else if (selectedSupportUnit instanceof Cavalry) {
	 	        statString.append("Cavalry\n");
	 	     
	 	    } else if (selectedSupportUnit instanceof Footman) {
	 	        statString.append("Footman\n");
	 	       
	 	    }
	 	 
	  
	       
	        statString.append("HP: ").append(selectedSupportUnit.getCurrentHp()).append("\n");
	        statString.append("Atttack Dmg: ").append(selectedSupportUnit.getAttackDmg()).append("\n");
	        statString.append("Level: ").append(selectedSupportUnit.getLevel()).append("\n");
	        statString.append("Range: ").append(selectedSupportUnit.getRange()).append("\n").append("ActionsAvilable: ").append(selectedSupportUnit.getActionsAvailable());;
	          

	        return statString.toString();
	    }


 
	    public String getUnitStats(Hero h) {
	 	   StringBuilder statString = new StringBuilder();

	 	    if (h instanceof Assassin) {
	 	        statString.append("Assassin\n");
	 	        statString.append("Attack Dmg: ").append(((Assassin) h).getAttackDmg()).append("\n");
	 	    } else if (h instanceof Diplomat) {
	 	        statString.append("Diplomat\n");
	 	     
	 	    } else if (h instanceof Warchief) {
	 	        statString.append("Warchief\n");
	 	       statString.append("Attack Dmg: ").append(((Warchief) h).getAttackDmg()).append("\n");
	 	    }
	 	   else if (h instanceof Monk) {
	 	        statString.append("Monk\n");
	 	        
	 	    }


	 	    statString.append("Name: ").append(h.getName()).append("\n")
	 	            .append("HP: ").append(h.getCurrentHp()).append("\n")
	 	            .append("UnitType is: ").append(h.getType()).append("\n").append("ActionsAvilable: ").append(h.getActionsAvailable());
	 	           

	 	    return statString.toString();
	 }
	    
	    
	    private String getBuildingStats(Building building) {
	        StringBuilder statString = new StringBuilder();

	       
	        if (building instanceof Tower) {
	            statString.append("Name:").append("Tower\n").append("Attack Dmg: ").append(((Tower) building).getRange()).append("\n").append("Level: ").append(building.getLevel()).append("HP: ").append(building.getCurrentHp());;
	        } else if (building instanceof Palace) {
	        	   statString.append("Name: ").append("Palace\n").append(building.getLevel()).append("HP: ").append(building.getCurrentHp());;
	          
	        } else if (building instanceof Barracks) {
	            statString.append("Name: ").append("Barracks\n").append("Level: ").append(building.getLevel()).append("HP: ").append(building.getCurrentHp());;
	           
	        }
	        else if(building instanceof Wall) {
	        	 statString.append("Name: ").append("Wall").append("Level: ").append(building.getLevel()).append("HP: ").append(building.getCurrentHp());
	        }
	        

	   
	       //.  .append(building.getLevel()).append("\n")
	         //       .append("HP: ").append(building.getCurrentHp()).append("\n");

	        return statString.toString();
	    }


	
	    public void showPlayerStatsMessage(String playerStats) {
	        JTextArea textArea = new JTextArea(playerStats);
	        textArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(textArea);
	        scrollPane.setPreferredSize(new Dimension(300, 200));
	        JOptionPane.showMessageDialog(this, scrollPane, "Player Stats", JOptionPane.INFORMATION_MESSAGE);
	    }
	    private void openGameWindow() {
	        player1 = new Player("Player 1");
	        player2 = new Player("Player 2");
	        
	        game = new Game(player1, player2);
	        if (player1 != null && player2 != null && game != null)
			game.setMap(player1SelcetedHero, player2SelcetedHero);
	    	 
	        JFrame gameFrame = new JFrame("Game");
	        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        gameFrame.setSize(800, 600);
	        gameFrame.setLocationRelativeTo(null);

	        
	        JPanel mapPanel = new JPanel(new GridLayout(MapButtons.length, MapButtons[0].length));

	        for (int i = 0; i < MapButtons.length; i++) {
	            for (int j = 0; j < MapButtons[0].length; j++) {
	                MapButtons[i][j] = new JButton();
	                MapButtons[i][j].addActionListener(this);
	                mapPanel.add(MapButtons[i][j]);
	               
	                
	                
	                
	                
	                
	                
	            }
	        }
	        
 
	    
	        
	        
	        buildButton.addActionListener(this);
	        recruitButton.addActionListener(this);
	        attackButton.addActionListener(this);
	        useSpecialButton.addActionListener(this);
	        endButton.addActionListener(this);
	        upgraButton.addActionListener(this);
	   
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(attackButton);
	        buttonPanel.add(useSpecialButton);
	        buttonPanel.add(endButton);
            buttonPanel.add(recruitButton);
            buttonPanel.add(buildButton);
	        buttonPanel.add(upgraButton);
	        recruitPanel.add(recruitButton, BorderLayout.NORTH);
	        recruitPanel.add(unitTypeComboBox, BorderLayout.CENTER);
	        buildPanel.add(buildButton,BorderLayout.NORTH);
	        buildPanel.add(buildComboBox,BorderLayout.CENTER);
	      
	        buttonPanel.add(recruitPanel);
	        buttonPanel.add(buildPanel);
	        gameFrame.add(buttonPanel, BorderLayout.WEST);
	        gameFrame.add(buttonPanel,BorderLayout.WEST);
	        currentTurnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        currentTurnLabel.setVerticalAlignment(SwingConstants.TOP);
	        currentTurnLabel.setForeground(Color.RED);
	        JPanel labelPanel = new JPanel(new BorderLayout());
	        labelPanel.add(currentTurnLabel, BorderLayout.CENTER);
	        gameFrame.add(labelPanel, BorderLayout.EAST);
              
	        
	        
	        
	        
	        
	        upButton.addActionListener(this);
	        downButton.addActionListener(this);
	        rightButton.addActionListener(this);
	        leftButton.addActionListener(this);
	        
	        
	        
	        
	       
 
	        JLabel player1Label = new JLabel("Player 1");
	  
	        
	        JLabel player2Label = new JLabel("Player 2");
	        JLabel player2GoldLabel = new JLabel("Gold: " + player2.getGoldAmount());
	        JLabel player2ManpowerLabel = new JLabel("Manpower: " + player2.getManpowerAmount());
	        JLabel player1GoldLabel = new JLabel("Gold: " + player1.getGoldAmount());
	        JLabel player1ManpowerLabel = new JLabel("Manpower: " + player1.getManpowerAmount());


 
	        playerInfoPanel.add(player1Label);
	        playerInfoPanel.add(player1GoldLabel);
	        playerInfoPanel.add(player1ManpowerLabel);
	        playerInfoPanel.add(player2Label);
	        playerInfoPanel.add(player2GoldLabel);
	        playerInfoPanel.add(player2ManpowerLabel);

 
	        gameFrame.add(playerInfoPanel, BorderLayout.NORTH);  
	        
	        JPanel movementPanel = new JPanel();
	        movementPanel.add(upButton);
	        movementPanel.add(downButton);
	        movementPanel.add(leftButton);
	        movementPanel.add(rightButton);
 
	        gameFrame.add(movementPanel, BorderLayout.SOUTH);
	        
             updateMap();
	        gameFrame.add(mapPanel);  

	        gameFrame.setVisible(true);
	    }
 
	    private void updateMap() {
	        for (int i = 0; i < MapButtons.length; i++) {
	            for (int j = 0; j < MapButtons[0].length; j++) {
	                MapButtons[i][j].setIcon(null);
	                MapButtons[i][j].setBackground(Color.GRAY);  

	                if (game.map[i][j] instanceof UnitCell) {
	                    UnitCell currentCell = (UnitCell) game.map[i][j];
	                    if (currentCell.getUnit() != null) {
	                        if (player1.getUnits().contains(currentCell.getUnit())) {
	                            MapButtons[i][j].setBackground(Color.BLUE);  
	                        } else if (player2.getUnits().contains(currentCell.getUnit())) {
	                            MapButtons[i][j].setBackground(Color.RED); 
	                        }
	                    }
	                } else if (game.map[i][j] instanceof BuildingCell) {
	                    BuildingCell currentBuildingCell = (BuildingCell) game.map[i][j];
	                    if (currentBuildingCell.getBuilding() != null) {
	                        if (player1.getBuildings().contains(currentBuildingCell.getBuilding())) {
	                            MapButtons[i][j].setBackground(Color.BLUE);  
	                        } else if (player2.getBuildings().contains(currentBuildingCell.getBuilding())) {
	                            MapButtons[i][j].setBackground(Color.RED);  
	                        }
	                    }
	                } else if (game.map[i][j] instanceof ResourceCell) {
	                    ResourceCell currentResourceCell = (ResourceCell) game.map[i][j];
	                 
	                    MapButtons[i][j].setBackground(Color.YELLOW);
	                }
	            }
	        }
	    }
	    private void SelectionLogic(ActionEvent e) {
	        for (int i = 0; i < MapButtons.length; i++) {
	            for (int j = 0; j < MapButtons[0].length; j++) {
	                if (e.getSource() == MapButtons[i][j]) {
	                    if (game.map[i][j] instanceof UnitCell) {
	                        selectingUnit = true;
	                        selectingBuilding = false;
	                        // Handle unit selection
	                        // Deselect any selected building
	                        currentSelectedBuilding = null;
	                        
	                        UnitCell unitCell = (UnitCell) game.map[i][j];
	                        Unit unit = unitCell.getUnit();

	                        if (unit != null) {
	                            if (unit instanceof Hero) {
	                                Hero selectedHero = (Hero) unit;
	                                String playerStats = getUnitStats(selectedHero);
	                                displayInfoInGame("Player Stats", playerStats);
	                                CurrentselectedUnit = selectedHero;
	                            } else if (unit instanceof SupportUnit) {
	                                SupportUnit selectedSupportUnit = (SupportUnit) unit;
	                                String supportUnitStats = getSupportUnitStats(selectedSupportUnit);
	                                displayInfoInGame("Support Unit Stats", supportUnitStats);
	                                CurrentselectedUnit = selectedSupportUnit;
	                            }
	                            
	                            boolean belongsToCurrentPlayer = false;
	                            if (playerFlag) {
	                                belongsToCurrentPlayer = player1.getUnits().contains(unit);
	                            } else {
	                                belongsToCurrentPlayer = player2.getUnits().contains(unit);
	                            }

	                            if (!belongsToCurrentPlayer) {
	                                System.out.println("Clicked on other player's unit: " + unit);
	                                selectingEnemyBuilding = false;
	                                selectingEnemyUnit = true;
	                                selectedEnemyUnit = unit;
	                            }
	                        }
	                    } else if (game.map[i][j] instanceof BuildingCell) {
	                        selectingBuilding = true;
	                        selectingUnit = false;
	                        // Handle building selection
	                        // Deselect any selected unit
	                        CurrentselectedUnit = null;
	                        
	                        BuildingCell buildingCell = (BuildingCell) game.map[i][j];
	                        Building building = buildingCell.getBuilding();

	                        if (building != null) {
	                            String buildingStats = getBuildingStats(building);
	                            displayInfoInGame("Building Stats", buildingStats);
	                            currentSelectedBuilding = building;
	                            boolean belongsToCurrentPlayer = false;
	                            if (playerFlag) {
	                                belongsToCurrentPlayer = player1.getBuildings().contains(building);
	                            } else {
	                                belongsToCurrentPlayer = player2.getBuildings().contains(building);
	                            }

	                            if (!belongsToCurrentPlayer) {
	                                System.out.println("Clicked on other player's building: " + building);
	                                selectingEnemyBuilding = true;
	                                selectingEnemyUnit = false;
	                                selectedEnemyUnit = null;
	                                selectedEnemyBuilding = building;
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	   

	    private Point getBuildingPosition(Building building, Cell[][] map) {
	        for (int i = 0; i < map.length; i++) {
	            for (int j = 0; j < map[i].length; j++) {
	                if (map[i][j] instanceof BuildingCell) {
	                    BuildingCell buildingCell = (BuildingCell) map[i][j];
	                    if (buildingCell.getBuilding() == building) {
	                        return new Point(i, j);
	                    }
	                }
	            }
	        }
	        
	       
	        return new Point(-1, -1);
	    }

	    private void displayInfoInGame(String title, String message) {
	        JTextArea textArea = new JTextArea(message);
	        textArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(textArea);
	        scrollPane.setPreferredSize(new Dimension(300, 200));
	        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
	    }
	    
 
	    	 
	    
	public static void main(String[] args) {
		new ui_Handler();
	}
}
