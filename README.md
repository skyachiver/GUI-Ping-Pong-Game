# GUI-Ping-Pong-Game
This Game has Main Menu which Includes Basic option to run the Game and also it provides options like restart, Pause and Main Menu while  Playing the game \n

# Pong Game

A simple implementation of the classic Pong game. This project simulates a two-player game of Pong with a basic scoring system, paddle controls, and ball physics. It also includes features like game states, optional power-ups, and collision detection.

## Game Logic

### 1. Game Objects

#### Ball
- **Properties**:  
  - Position (`x`, `y`)  
  - Velocity (`dx`, `dy`)  
  - Size (radius)
- **Behavior**:  
  - Moves based on its velocity  
  - Bounces off walls and paddles  
  - Resets position when a point is scored

#### Paddles
- **Properties**:  
  - Position (`x`, `y`)  
  - Size (width, height)  
  - Speed
- **Behavior**:  
  - Moves up and down within the screen boundaries  
  - Collides with the ball

#### Score
- **Properties**:  
  - Player 1 score  
  - Player 2 score
- **Behavior**:  
  - Increments when a point is scored  
  - Resets when starting a new game

### 2. Game Loop
1. Update the ball's position.
2. Check for collisions (ball with walls and paddles).
3. Update paddle positions based on user input.
4. Check if a point is scored.
5. Update the score if necessary.
6. Redraw all game objects.
7. Repeat steps 1-6 until the game ends.

### 3. Collision Detection

#### Ball-Wall Collision
- If the ball hits the top or bottom wall, reverse its vertical velocity (`dy`).
- If the ball goes past the left or right edge, award a point and reset the ball.

#### Ball-Paddle Collision
- If the ball's `x`-position is within the paddle's `x`-range and its `y`-position is within the paddle's `y`-range:
  - Reverse the ball's horizontal velocity (`dx`).
  - Optionally, adjust the ball's vertical velocity (`dy`) based on where it hit the paddle for more dynamic gameplay.

### 4. Scoring System
- If the ball passes the left edge, Player 2 scores a point.
- If the ball passes the right edge, Player 1 scores a point.
- After scoring, reset the ball to the center with a random direction.

### 5. Paddle Movement
- **Player 1 (Left Paddle)**:  
  - Move up when the 'W' key is pressed.
  - Move down when the 'S' key is pressed.
- **Player 2 (Right Paddle)**:  
  - Move up when the Up Arrow key is pressed.
  - Move down when the Down Arrow key is pressed.
- Paddle movement is limited to stay within the screen boundaries.

### 6. Game States
- **Start**: Displays the title screen, waiting for user input to begin.
- **Play**: Main game loop is active.
- **Pause**: Freezes the game state, displays the pause menu.
- **Game Over**: Displays final scores with the option to restart or quit.

### 7. Additional Features (Optional)
- Gradually increase ball speed as the game progresses.
- Add power-ups (e.g., temporary paddle size increase, ball speed change).
- Implement AI for single-player mode.
- Add sound effects for ball hits, scoring, and game events.

