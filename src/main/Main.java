package main;

import javax.swing.SwingUtilities;

import main.GameFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame());
    }
}