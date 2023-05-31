package com.company;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    private static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private static final int BALL_DIAMETER = 20;
    private static final int PADDLE_WIDTH = 25;
    private  static final int PADDLE_HEIGHT = 100;
    private static final int GAME_MINIMUM_WIDTH = 0 ;
    private Thread gameThread;
    private Image image;
    private Graphics graphics;
    private com.company.Paddle paddle1;
    private com.company.Paddle paddle2;
    private com.company.Ball ball;
    private com.company.Score score;
    private GameFrame gf;


    GamePanel(GameFrame gf) {
        newPaddles();
        newBall();
        score = new com.company.Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        this.gf=gf;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        //random = new Random();
        ball = new com.company.Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (BALL_DIAMETER / 2), BALL_DIAMETER, BALL_DIAMETER);


    }

    public void newPaddles() {
        paddle1 = new com.company.Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new com.company.Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);


    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);


    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();


    }

    public void checkCollision(){
        if(paddle1.y <= 0)
            paddle1.y=0;
        if(paddle1.y >= GAME_HEIGHT - PADDLE_HEIGHT)
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if(paddle2.y <= 0)
            paddle2.y=0;
        if(paddle2.y >= GAME_HEIGHT - PADDLE_HEIGHT)
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        if(ball.y <= 0)
            ball.setYDirection(-ball.getyVelocity());
        if(ball.y >= GAME_HEIGHT - BALL_DIAMETER)
            ball.setYDirection(-ball.getyVelocity());

        if(ball.intersects(paddle1)) {
            ball.setxVelocity(Math.abs(ball.getxVelocity())) ;
            ball.setxVelocity(ball.getxVelocity()+1);
            if (ball.getyVelocity() > 0)
                ball.setyVelocity(ball.getyVelocity()+1);
            else
                ball.setyVelocity(ball.getyVelocity()-1);
            ball.setXDirection(ball.getxVelocity());
            ball.setYDirection(ball.getyVelocity() );
        }

        if(ball.intersects(paddle2)) {
            ball.setxVelocity(Math.abs(ball.getxVelocity())) ;
            ball.setxVelocity(ball.getxVelocity()+1);
            if (ball.getyVelocity()  > 0)
                ball.setyVelocity(ball.getyVelocity()+1);
            else
                ball.setyVelocity(ball.getyVelocity()-1);
            ball.setXDirection(-ball.getxVelocity());
            ball.setYDirection(ball.getyVelocity() );
        }

        if(ball.x <=GAME_MINIMUM_WIDTH) {
            score.addScore(false);
            newPaddles();
            newBall();
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER){
            score.addScore(true);
            newPaddles();
            newBall();
        }
    }


    public void run() {
        final int FINISH_SCORE = 5;
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while ((this.score.getPlayer1() < FINISH_SCORE && this.score.getPlayer2() < FINISH_SCORE)) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
            if (this.score.getPlayer1() == FINISH_SCORE) {
                winnerScreen(true);
            } else if (this.score.getPlayer2() == FINISH_SCORE) {
                winnerScreen(false);
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);

        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

        }

    }

    private void winnerScreen(boolean playerOneWinner) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        String winner = "is the winner";
        if (playerOneWinner) {
            winner = "Blue " + winner;
        } else winner = "Red " + winner;
        JLabel winnerLabel = new JLabel();
        winnerLabel.setText(winner);
        winnerLabel.setBounds(100, 15, 150, 50);
        frame.add(winnerLabel);
        JButton rematch = new JButton("Rematch");
        rematch.setBounds(winnerLabel.getX(),winnerLabel.getY()+50,100,20);
        frame.add(rematch);
        ImageIcon imageIcon = new ImageIcon("winnerIMG.jpg");
        JLabel label = new JLabel(imageIcon);
        label.setBounds(50,winnerLabel.getY()+80,200,140);
        frame.add(label);
        frame.setVisible(true);
        rematch.addActionListener(e -> {
            new GameFrame();
            frame.dispose();
            this.gf.dispose();

        });
        playWinMusic();
    }

    private void playWinMusic() {
        try {
            Player victoryMusic = new Player(new FileInputStream("src\\com\\company\\Sounds\\Victory.mp3"));
            victoryMusic.play();
            victoryMusic.close();
        } catch (JavaLayerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void goalScoreMusic() {
        try {
            Player goalScoreSound = new Player(new FileInputStream("src\\com\\company\\Sounds\\Goal.wav'"));
            goalScoreSound.play();
            goalScoreSound.close();
        } catch (JavaLayerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void ballHitSound() {
        try {
            Player hitSound = new Player(new FileInputStream("src\\com\\company\\Sounds\\BallHit.mp3"));
            hitSound.play();
            hitSound.close();
        } catch (JavaLayerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
