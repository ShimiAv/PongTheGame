package com.company;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameMenu menu;

    GameFrame(){
        menu = new GameMenu(this);
        this.add(menu);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}