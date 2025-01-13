# Clash of Clans: Heroes

## Project Overview
This project is a console-based strategy game built using Java, inspired by "Clash of Clans." It utilizes Object-Oriented Programming (OOP) principles to create a robust game engine that simulates a dynamic, turn-based environment. Players manage heroes, support units, and buildings while strategizing to defeat their opponents.

## Features

### 1. Game Mechanics
- **Hero Special Actions:**
  - Each hero type (Monk, Diplomat, Assassin, Warchief) has unique abilities with cooldown periods.
- **Support Units:**
  - Units such as Archers, Footmen, and Cavalry can be recruited and upgraded.
- **Buildings:**
  - Includes Palaces, Walls, Towers, and Barracks, each with distinct functionalities and upgrade paths.

### 2. Gameplay
- **Turn-Based Strategy:**
  - Players alternate turns, managing units and buildings while executing strategies.
- **Resource Management:**
  - Collect and utilize gold and manpower to build, recruit, and upgrade.
- **Map Navigation:**
  - Units move across a grid-based map, interacting with buildings and other units.

### 3. Interactions
- **Combat System:**
  - Units and towers attack opponents based on range and action points.
- **Building Construction:**
  - Heroes can construct towers and walls on specific map locations.
- **Adaptive AI:**
  - The game ensures valid moves, enforces rules, and handles invalid actions with exceptions.

### 4. User Experience
- **Console Interface:**
  - Simple yet engaging gameplay through the console.
- **Error Handling:**
  - Robust exception handling for invalid moves and actions.

## Technology Stack
- **Programming Language:** Java
- **Paradigm:** Object-Oriented Programming (OOP)
- **Key Concepts Implemented:**
  - Inheritance, Polymorphism, Encapsulation, and Abstraction
  - Interfaces for upgradeable entities
  - Custom exception handling

## Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone [repository URL]
   ```
2. **Navigate to the Project Directory:**
   ```bash
   cd ClashOfClansHeroes
   ```
3. **Compile the Code:**
   ```bash
   javac -d bin src/*.java
   ```
4. **Run the Game:**
   ```bash
   java -cp bin Main
   ```

## Gameplay Instructions

1. **Start the Game:**
   - Players initialize with heroes, support units, and buildings on a grid-based map.
2. **Take Turns:**
   - Players alternate turns to move units, build structures, and attack opponents.
3. **Win Condition:**
   - The game ends when one player's palace is destroyed.

## Key Classes and Methods

- **Hero Class:**
  - `useSpecial(SupportUnit u)` - Executes the hero's special ability.
- **Player Class:**
  - `buildWall()` - Builds a wall.
  - `recruitUnit(Barracks b, String s)` - Recruits a new unit.
- **Game Class:**
  - `moveUp(int x, int y)` - Moves a unit upward.
  - `attack(int x, int y, int tx, int ty)` - Executes an attack.
  - `endTurn()` - Switches the turn to the next player.

## Future Enhancements
- Add multiplayer support for online gameplay.

