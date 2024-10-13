# GUI-Ping-Pong-Game
This Game has Main Menu which Includes Basic option to run the Game and also it provides options like restart, Pause and Main Menu while  Playing the game \n

Game Logic
1. Game Objects:\n
Ball:\n
  •	Properties:\n 
              	Position (x, y)
              	Velocity (dx, dy)
              	Size (radius)
•	Behavior:\n 
	Move based on velocity
	Bounce off walls and paddles
	Reset position when a point is scored
Paddles
•	Properties: 
o	Position (x, y)
o	Size (width, height)
o	Speed
•	Behavior: 
o	Move up and down within screen boundaries
o	Collide with the ball
Score
•	Properties: 
o	Player 1 score
o	Player 2 score
•	Behavior: 
o	Increment when a point is scored
o	Reset when starting a new game
2. Game Loop
1.	Update ball position
2.	Check for collisions (ball with walls, paddles)
3.	Update paddle positions based on user input
4.	Check if a point is scored
5.	Update score if necessary
6.	Redraw all game objects
7.	Repeat steps 1-6 until the game ends
3. Collision Detection
Ball-Wall Collision
•	If the ball hits the top or bottom wall, reverse its vertical velocity (dy)
•	If the ball goes past the left or right edge, award a point and reset the ball
Ball-Paddle Collision
•	If the ball's x-position is within the paddle's x-range and its y-position is within the paddle's y-range: 
o	Reverse the ball's horizontal velocity (dx)
o	Optionally, adjust the ball's vertical velocity (dy) based on where it hit the paddle (for more dynamic gameplay)
4. Scoring System
•	If the ball passes the left edge, Player 2 scores a point
•	If the ball passes the right edge, Player 1 scores a point
•	After scoring, reset the ball to the center with a random direction
5. Paddle Movement
•	Player 1 (Left Paddle): 
o	Move up when 'W' key is pressed
o	Move down when 'S' key is pressed
•	Player 2 (Right Paddle): 
o	Move up when Up Arrow key is pressed
o	Move down when Down Arrow key is pressed
•	Limit paddle movement to stay within the screen boundaries
6. Game States
•	Start: Display title screen, wait for user input to begin
•	Play: Main game loop is active
•	Pause: Freeze game state, display pause menu
•	Game Over: Display final scores, option to restart or quit
7. Additional Features (Optional)
•	Increase ball speed gradually as the game progresses
•	Add power-ups (e.g., temporary paddle size increase, ball speed change)
•	Implement AI for single-player mode
•	Add sound effects for ball hits, scoring, and game events \n\n



