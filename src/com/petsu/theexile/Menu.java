package com.petsu.theexile;

public class Menu {

    public static int mouseClickedMenu(int x, int y, int HEIGHT) {
        int choice = 0;
        if (x > 20 && x < 280 && y > HEIGHT / 2 - 135 && y < HEIGHT / 2 + 115) {
            if (y > HEIGHT / 2 - 135 && y < HEIGHT / 2 - 85) choice = 1;
            if (y > HEIGHT / 2 - 85 && y < HEIGHT / 2 - 35) choice = 2;
            if (y > HEIGHT / 2 - 35 && y < HEIGHT / 2 + 15) choice = 3;
            if (y > HEIGHT / 2 + 15 && y < HEIGHT / 2 + 65) choice = 4;
            if (y > HEIGHT / 2 + 65 && y < HEIGHT / 2 + 115) choice = 5;
        }
        return choice;
    }

    public static int mouseClickedButtons(int x, int y, int WIDTH, int HEIGHT) {
        int choice = 0;
        if (x > WIDTH / 2 - 90 && x < WIDTH / 2 + 230 && y > HEIGHT / 2 + 125 && y < HEIGHT / 2 + 155) {
            if (x > WIDTH / 2 - 90 && x < WIDTH / 2 + 10) choice = 1;
            if (x > WIDTH / 2 + 20 && x < WIDTH / 2 + 120) choice = 2;
            if (x > WIDTH / 2 + 130 && x < WIDTH / 2 + 230) choice = 3;
        }
        return choice;
    }

    public static int mousePressedResolution(int x, int y, int WIDTH, int HEIGHT) {
        int choice = 0;
        if (x > WIDTH / 2 - 70 && x < WIDTH / 2 + 185 && y > HEIGHT / 2 - 105 && y < HEIGHT / 2 - 80) choice = 1;
        if (x > WIDTH / 2 - 70 && x < WIDTH / 2 + 160) {
            if (y > HEIGHT / 2 - 81 && y < HEIGHT / 2 - 58) choice = 2;
            if (y > HEIGHT / 2 - 58 && y < HEIGHT / 2 - 34) choice = 3;
            if (y > HEIGHT / 2 - 34 && y < HEIGHT / 2 - 10) choice = 4;
            if (y > HEIGHT / 2 - 10 && y < HEIGHT / 2 + 14) choice = 5;
        }
        return choice;
    }

    public static int mouseMovedMenu(int x, int y, int choiceY, int HEIGHT) {
        if (x > 20 && x < 310) {
            if (y > HEIGHT / 2 - 135 && y < HEIGHT / 2 - 85) choiceY = HEIGHT / 2 - 135;
            if (y > HEIGHT / 2 - 85 && y < HEIGHT / 2 - 35) choiceY = HEIGHT / 2 - 85;
            if (y > HEIGHT / 2 - 35 && y < HEIGHT / 2 + 15) choiceY = HEIGHT / 2 - 35;
            if (y > HEIGHT / 2 + 15 && y < HEIGHT / 2 + 65) choiceY = HEIGHT / 2 + 15;
            if (y > HEIGHT / 2 + 65 && y < HEIGHT / 2 + 115) choiceY = HEIGHT / 2 + 65;
        }
        return choiceY;
    }

    public static int mouseMovedSettings(int x, int y, int WIDTH, int HEIGHT) {
        int select = 0;
        if (x > WIDTH / 2 - 70 && x < WIDTH / 2 + 185 && y > HEIGHT / 2 - 105 && y < HEIGHT / 2 - 80) select =  1;
        if (x > WIDTH / 2 - 130 && x < WIDTH / 2 + 185 && y > HEIGHT / 2 - 65 && y < HEIGHT / 2 - 50) select = 2;
        if (x > WIDTH / 2 - 130 && x < WIDTH / 2 + 185 && y > HEIGHT / 2 - 30 && y < HEIGHT / 2 - 15) select = 3;
        if (x > WIDTH / 2 - 225 && x < WIDTH / 2 - 205 && y > HEIGHT / 2 + 7 && y < HEIGHT / 2 + 27) select = 4;
        if (x > WIDTH / 2 - 225 && x < WIDTH / 2 - 205 && y > HEIGHT / 2 + 42 && y < HEIGHT / 2 + 62) select = 5;
        if (x > WIDTH / 2 - 90 && x < WIDTH / 2 + 230 && y > HEIGHT / 2 + 125 && y < HEIGHT / 2 + 155) {
            if (x > WIDTH / 2 - 90 && x < WIDTH / 2 + 10) select = 6;
            if (x > WIDTH / 2 + 20 && x < WIDTH / 2 + 120) select = 7;
            if (x > WIDTH / 2 + 130 && x < WIDTH / 2 + 230) select = 8;
        }
        return select;
    }

    public static int mouseMovedResolution(int x, int y, int WIDTH, int HEIGHT) {
        int select = 0;
        if (x > WIDTH / 2 - 70 && x < WIDTH / 2 + 160) {
            if (y > HEIGHT / 2 - 81 && y < HEIGHT / 2 - 58) select = 1;
            if (y > HEIGHT / 2 - 58 && y < HEIGHT / 2 - 34) select = 2;
            if (y > HEIGHT / 2 - 34 && y < HEIGHT / 2 - 10) select = 3;
            if (y > HEIGHT / 2 - 10 && y < HEIGHT / 2 + 14) select = 4;
        }
        return select;
    }
}