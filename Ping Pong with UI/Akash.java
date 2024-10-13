import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Akash extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int BALL_DIAMETER = 20;
    private static final int PADDLE_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 80;

    private int ballX = WIDTH / 2 - BALL_DIAMETER / 2;
    private int ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
    private int ballXSpeed = 3;
    private int ballYSpeed = 3;

    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddleSpeed = 5;

    private int score1 = 0;
    private int score2 = 0;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private Timer timer;
    private boolean gameRunning = false;
    private boolean gamePaused = false;

    private JButton startButton, settingsButton, quitButton;
    private JButton pauseButton, restartButton, mainMenuButton;
    private JPanel menuPanel, gameControlPanel;

    // Game settings
    private int winningScore = 5;
    private Color ballColor = Color.WHITE;
    private Color paddleColor = Color.WHITE;

    public Akash() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);

        createMenuButtons();
        createGameControlButtons();

        timer = new Timer(10, this);
    }

    private void createMenuButtons() {
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        startButton = createStyledButton("Start Game");
        settingsButton = createStyledButton("Settings");
        quitButton = createStyledButton("Quit");

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(startButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(settingsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(quitButton);
        menuPanel.add(Box.createVerticalGlue());

        add(menuPanel, BorderLayout.CENTER);
    }

    private void createGameControlButtons() {
        gameControlPanel = new JPanel();
        gameControlPanel.setOpaque(false);
        gameControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        pauseButton = createStyledButton("Pause");
        restartButton = createStyledButton("Restart");
        mainMenuButton = createStyledButton("Main Menu");

        gameControlPanel.add(pauseButton);
        gameControlPanel.add(restartButton);
        gameControlPanel.add(mainMenuButton);

        gameControlPanel.setVisible(false);
        add(gameControlPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEtchedBorder());
        button.addActionListener(this);
        return button;
    }

    public void startGame() {
        score1 = 0;
        score2 = 0;
        resetBall();
        gameRunning = true;
        gamePaused = false;
        menuPanel.setVisible(false);
        gameControlPanel.setVisible(true);
        timer.start();
        requestFocus(); // Request focus to ensure paddle controls work
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameRunning) {
            // Draw ball
            g.setColor(ballColor);
            g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);

            // Draw paddles
            g.setColor(paddleColor);
            g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
            g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

            // Draw scores
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(String.valueOf(score1), WIDTH / 4, 50);
            g.drawString(String.valueOf(score2), 3 * WIDTH / 4, 50);

            if (gamePaused) {
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("PAUSED", WIDTH / 2 - 100, HEIGHT / 2);
            }
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("PING PONG", WIDTH / 2 - 140, HEIGHT / 2 - 100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            if (gameRunning && !gamePaused) {
                moveBall();
                movePaddles();
                checkCollision();
            }
        } else if (e.getSource() == startButton) {
            startGame();
        } else if (e.getSource() == settingsButton) {
            showSettingsDialog();
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        } else if (e.getSource() == pauseButton) {
            gamePaused = !gamePaused;
            pauseButton.setText(gamePaused ? "Resume" : "Pause");
        } else if (e.getSource() == restartButton) {
            startGame();
        } else if (e.getSource() == mainMenuButton) {
            gameRunning = false;
            timer.stop();
            menuPanel.setVisible(true);
            gameControlPanel.setVisible(false);
        }
        repaint();
    }

    private void showSettingsDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        
        JTextField winningScoreField = new JTextField(String.valueOf(winningScore));
        panel.add(new JLabel("Winning Score:"));
        panel.add(winningScoreField);

        JButton ballColorButton = new JButton("Choose Ball Color");
        ballColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Ball Color", ballColor);
            if (newColor != null) {
                ballColor = newColor;
            }
        });
        panel.add(new JLabel("Ball Color:"));
        panel.add(ballColorButton);

        JButton paddleColorButton = new JButton("Choose Paddle Color");
        paddleColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Paddle Color", paddleColor);
            if (newColor != null) {
                paddleColor = newColor;
            }
        });
        panel.add(new JLabel("Paddle Color:"));
        panel.add(paddleColorButton);

        int result = JOptionPane.showConfirmDialog(this, panel, "Game Settings",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                winningScore = Integer.parseInt(winningScoreField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid winning score. Using default value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;
    }

    private void movePaddles() {
        if (wPressed && paddle1Y > 0) {
            paddle1Y -= paddleSpeed;
        }
        if (sPressed && paddle1Y < HEIGHT - PADDLE_HEIGHT) {
            paddle1Y += paddleSpeed;
        }
        if (upPressed && paddle2Y > 0) {
            paddle2Y -= paddleSpeed;
        }
        if (downPressed && paddle2Y < HEIGHT - PADDLE_HEIGHT) {
            paddle2Y += paddleSpeed;
        }
    }

    private void checkCollision() {
        // Ball collision with top and bottom walls
        if (ballY <= 0 || ballY >= HEIGHT - BALL_DIAMETER) {
            ballYSpeed = -ballYSpeed;
        }

        // Ball collision with paddles
        if (ballX <= PADDLE_WIDTH && ballY + BALL_DIAMETER >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }
        if (ballX >= WIDTH - PADDLE_WIDTH - BALL_DIAMETER && ballY + BALL_DIAMETER >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }

        // Scoring
        if (ballX < 0) {
            score2++;
            checkGameOver();
            resetBall();
        } else if (ballX > WIDTH) {
            score1++;
            checkGameOver();
            resetBall();
        }
    }

    private void checkGameOver() {
        if (score1 >= winningScore || score2 >= winningScore) {
            gameRunning = false;
            timer.stop();
            String winner = (score1 >= winningScore) ? "Player 1" : "Player 2";
            int choice = JOptionPane.showOptionDialog(this,
                    winner + " wins!\nDo you want to play again?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Yes", "No"},
                    "Yes");
            if (choice == JOptionPane.YES_OPTION) {
                startGame();
            } else {
                menuPanel.setVisible(true);
                gameControlPanel.setVisible(false);
            }
        }
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_DIAMETER / 2;
        ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
        ballXSpeed = 3;
        ballYSpeed = 3;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) wPressed = true;
        if (key == KeyEvent.VK_S) sPressed = true;
        if (key == KeyEvent.VK_UP) upPressed = true;
        if (key == KeyEvent.VK_DOWN) downPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) wPressed = false;
        if (key == KeyEvent.VK_S) sPressed = false;
        if (key == KeyEvent.VK_UP) upPressed = false;
        if (key == KeyEvent.VK_DOWN) downPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong Game Assignment 3 - 04 - Akash");
        Akash game = new Akash();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
