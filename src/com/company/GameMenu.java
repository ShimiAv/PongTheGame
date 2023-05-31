package com.company;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameMenu extends JPanel {
    com.company.GamePanel panel;
    private  static final int SPACE_LINE = 20;
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    private static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    private Player mainMenuMusicPlayer;
    private Player startGameWhistle;



    public GameMenu(GameFrame gf){
        this.setPreferredSize(SCREEN_SIZE);
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("התחלת המשחק");
        JButton instructionsButton = new JButton("הוראות המשחק");
        instructionsButton.addActionListener(e -> {
            JFrame frame = new JFrame();
            frame.setLayout(null);
            frame.setSize(700,500);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JLabel line1 = new JLabel("הוראות המשחק:");
            line1.setBounds(300,10,200,50);
            frame.add(line1);

            JLabel line2 = new JLabel("כללי המשחק פשוטים ודומים לאלו של טניס שולחן:");
            line2.setBounds(220,line1.getY()+SPACE_LINE+15,GAME_WIDTH,50);
            frame.add(line2);

            JLabel line3 = new JLabel("הכדור נע על המסך מצד לצד והשחקנים, אשר שולטים במחבטים, צריכים להזיז אותם מעלה או מטה כדי שהכדור יפגע במחבט.");
            line3.setBounds(20,line2.getY()+SPACE_LINE,GAME_WIDTH,50);
            frame.add(line3);

            JLabel line4 = new JLabel("במקרה ששחקן מסוים אינו מצליח לחבוט בכדור, השחקן השני זוכה בנקודה.");
            line4.setBounds(130,line3.getY()+SPACE_LINE,GAME_WIDTH,50);
            frame.add(line4);

            JLabel line5 = new JLabel("השחקן המנצח זה השחקן הראשון שהגיע ל5 נקודות.");
            line5.setBounds(line2.getX(),line4.getY()+SPACE_LINE,GAME_WIDTH,50);
            frame.add(line5);

            JLabel line6 = new JLabel("השחקן בצד ימין זז בעזרת החצים והשחקן בצד שמאל זז בעזרת W לעלות למעלה ו S כדי לרדת למטה.");
            line6.setBounds(110,line5.getY()+SPACE_LINE,GAME_WIDTH,50);
            frame.add(line6);

            JLabel line7 = new JLabel("המשחק הוא ברמת קושי עולה, מהירות הכדור הולכה ועולה עם החבטות בכדור.");
            line7.setBounds(130,line6.getY()+SPACE_LINE,GAME_WIDTH,50);
            frame.add(line7);

            frame.setBackground(Color.WHITE);
            frame.setTitle("הוראות המשחק");
            frame.setVisible(true);

        });
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.black);
        startButton.setBorder(null);
        instructionsButton.setBorder(null);
        instructionsButton.setForeground(Color.black);
        instructionsButton.setBackground(Color.white);
        buttonPanel.add(startButton);
        buttonPanel.add(instructionsButton);
        add(buttonPanel,BorderLayout.CENTER);
        ImageIcon imageIcon = new ImageIcon("background.jpeg");
        JLabel label = new JLabel(imageIcon);
        add(label);
        this.setBackground(Color.black);
        startMainMenuMusic();
        startButton.addActionListener(e -> {panel = new com.company.GamePanel(gf);
            stopMainMenuMusic();
            playWhistle();
            this.add(panel);
            startButton.setVisible(false);
            remove(buttonPanel);
            remove(label);
        });
    }

    private void startMainMenuMusic() {
        try {
            mainMenuMusicPlayer = new Player(new FileInputStream("src\\com\\company\\Sounds\\MainMenuMusic.mp3"));
            Thread musicThread = new Thread(() -> {
                try {
                    mainMenuMusicPlayer.play();
                } catch (JavaLayerException e) {
                    throw new RuntimeException(e);
                }
            });
            musicThread.start();
        } catch (JavaLayerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void stopMainMenuMusic() {
        if (mainMenuMusicPlayer != null) {
            mainMenuMusicPlayer.close();
        }
    }

    private void playWhistle(){
        try {
            try {
                startGameWhistle = new Player(new FileInputStream("src\\com\\company\\Sounds\\Whistle.mp3"));
            } catch (JavaLayerException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            startGameWhistle.play();
            startGameWhistle.close();
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }
}
