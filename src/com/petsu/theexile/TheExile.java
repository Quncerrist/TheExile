package com.petsu.theexile;

import com.petsu.sound.BGM;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TheExile extends Canvas implements KeyListener, MouseListener, MouseMotionListener {
    private int WIDTH = 800, widthtmp = 800;
    private int HEIGHT = 600, heighttmp = 600;
    private int choiceY = 190, loadP = 1, settingsP = 1 , aboutP = 1;
    private int bgm = WIDTH / 2 + 140;
    private int sfx = WIDTH / 2 + 140;
    private boolean up, down, left, right, space, enter;
    private boolean menu, load, settings, about;
    private boolean systemsounds = true, showfps = false;
    private boolean resolution = false, draggingBGM = false, draggingSFX = false;
    public BufferStrategy strategy;
    private Sprites Sprites;
    private JFrame frame;
    private JPanel panel;

    public TheExile() throws FileNotFoundException {
        frame = new JFrame("The Exile");
        panel = (JPanel)frame.getContentPane();

        File cfg = new File("settings/config.cfg");
        if (cfg.isFile()) {
            Scanner in = new Scanner(cfg);
            for (int i = 0; i < 6; i++) {
                String write = in.nextLine();
                switch (i) {
                    case 0:
                        WIDTH = Integer.parseInt(write);
                        widthtmp = WIDTH;
                        break;
                    case 1:
                        HEIGHT = Integer.parseInt(write);
                        heighttmp = HEIGHT;
                        choiceY = HEIGHT / 2 - 135;
                        break;
                    case 2:
                        bgm = Integer.parseInt(write);
                        bgm = WIDTH / 2 - 130 + 3 * bgm;
                        break;
                    case 3:
                        sfx = Integer.parseInt(write);
                        sfx = WIDTH / 2 - 130 + 3 * sfx;
                        break;
                    case 4:
                        systemsounds = Boolean.parseBoolean(write);
                        break;
                    case 5:
                        showfps = Boolean.parseBoolean(write);
                        break;
                }
            }
        }
        else config();

        setBounds(0, 0, WIDTH, HEIGHT);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);
        panel.add(this);

        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        Image icon = new ImageIcon("graphics/icon.png").getImage();
        frame.setIconImage(icon);
        panel.setCursor(getDefaultCursor());

        Sprites = new Sprites();
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public static Cursor getDefaultCursor() {
        Cursor defaultCursor;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursor = new ImageIcon("graphics/cursor.png").getImage();
        defaultCursor = toolkit.createCustomCursor(cursor, new Point(15, 15),"cursor");
        return defaultCursor;
    }

    public void start() {
        Graphics g = strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        strategy.show();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 20; i++) {
           // g.drawImage(Sprites.getSprite("pw.png"), (WIDTH / 2) - 200, (HEIGHT / 2) - 38, this);
            strategy.show();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 20; i++) {
           // g.drawImage(Sprites.getSprite("presents.png"), (WIDTH / 2) - 74, (HEIGHT / 2) + 35, this);
            strategy.show();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        menu();
        menu = true;
    }

///////////////////////////////////////////// KEY LISTENER START ///////////////////////////////////////////////////////
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP : up = true; break;
            case KeyEvent.VK_DOWN : down = true; break;
            case KeyEvent.VK_LEFT : left = true; break;
            case KeyEvent.VK_RIGHT : right = true; break;
            case KeyEvent.VK_SPACE : space = true; break;
            case KeyEvent.VK_ENTER : enter = true; break;
        }
        updateChoice();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP : up = false; break;
            case KeyEvent.VK_DOWN : down = false; break;
            case KeyEvent.VK_LEFT : left = false; break;
            case KeyEvent.VK_RIGHT : right = false; break;
            case KeyEvent.VK_SPACE : space = false; break;
            case KeyEvent.VK_ENTER : enter = false; break;
        }
        updateChoice();
    }
///////////////////////////////////////////// KEY LISTENER END /////////////////////////////////////////////////////////

///////////////////////////////////////////// MOUSE LISTENER START /////////////////////////////////////////////////////
    @Override
    public void mouseClicked(MouseEvent e) {
        if (menu) {
            if (com.petsu.theexile.Menu.mouseClickedMenu(e.getX(), e.getY(), HEIGHT) > 0) {
                switch (com.petsu.theexile.Menu.mouseClickedMenu(e.getX(), e.getY(), HEIGHT)) {
                    case 1: newgame(); break;
                    case 2: load(); break;
                    case 3: settings(); break;
                    case 4: about(); break;
                    case 5: exit(); break;
                }
                menu = false;
            }
        }
        else if (load) {
            switch (com.petsu.theexile.Menu.mouseClickedButtons(e.getX(), e.getY(), WIDTH, HEIGHT)) {
                case 1: loadLoad(); break;
                case 2: loadDelete(); break;
                case 3: loadBack(); break;
            }
        }
        else if (settings) {
            switch (com.petsu.theexile.Menu.mouseClickedButtons(e.getX(), e.getY(), WIDTH, HEIGHT)) {
                case 1: settingsApply(); break;
                case 2: settingsDefault(); break;
                case 3: settingsBack(); break;
            }
        }
        else if (about) {
            switch (com.petsu.theexile.Menu.mouseClickedButtons(e.getX(), e.getY(), WIDTH, HEIGHT)) {
                case 1: aboutPrev(); break;
                case 2: aboutNext(); break;
                case 3: aboutBack(); break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (settings) {
            Graphics g = strategy.getDrawGraphics();
            if (e.getX() > WIDTH / 2 - 70 && e.getX() < WIDTH / 2 + 185 && e.getY() > HEIGHT / 2 - 105
                    && e.getY() < HEIGHT / 2 - 80) {
                resolution = true; settings = false;
                g.drawImage(Sprites.getSprite("menu/chooseresolution.png"), WIDTH / 2 - 70, HEIGHT / 2 - 85, this);
                strategy.show();
            }
            if (!resolution) {
                if (e.getY() > HEIGHT / 2 - 65 && e.getY() < HEIGHT / 2 - 50) draggingBGM = true;
                if (e.getY() > HEIGHT / 2 - 30 && e.getY() < HEIGHT / 2 - 15) draggingSFX = true;
                if (e.getX() > WIDTH / 2 - 225 && e.getX() < WIDTH / 2 - 205 && e.getY() > HEIGHT / 2 + 7
                        && e.getY() < HEIGHT / 2 + 27) {
                    systemsounds = !systemsounds;
                    settings();
                    g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 7, this);
                    strategy.show();
                }
                if (e.getX() > WIDTH / 2 - 225 && e.getX() < WIDTH / 2 - 205 && e.getY() > HEIGHT / 2 + 42
                        && e.getY() < HEIGHT / 2 + 62) {
                    showfps = !showfps;
                    settings();
                    g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 42, this);
                    strategy.show();
                }
            }
        }
        else if (resolution) {
            Graphics g = strategy.getDrawGraphics();
            switch (com.petsu.theexile.Menu.mousePressedResolution(e.getX(), e.getY(), WIDTH, HEIGHT)) {
                case 1:
                    settings();
                    g.drawImage(Sprites.getSprite("menu/" +widthtmp +"x" +heighttmp +".png"), WIDTH / 2 - 70,
                            HEIGHT / 2 - 105, this);
                    break;
                case 2:
                    widthtmp = 800; heighttmp = 600;
                    settings();
                    g.drawImage(Sprites.getSprite("menu/800x600.png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
                    break;
                case 3:
                    widthtmp = 1024; heighttmp = 768;
                    settings();
                    g.drawImage(Sprites.getSprite("menu/1024x768.png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
                    break;
                case 4:
                    widthtmp = 1280; heighttmp = 800;
                    settings();
                    g.drawImage(Sprites.getSprite("menu/1280x800.png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
                    break;
                case 5:
                    widthtmp = 1366; heighttmp = 768;
                    settings();
                    g.drawImage(Sprites.getSprite("menu/1366x768.png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
                    break;
            }
            if (com.petsu.theexile.Menu.mousePressedResolution(e.getX(), e.getY(), WIDTH, HEIGHT) > 0) {
                resolution = false; settings = true;
            }
            g.drawImage(Sprites.getSprite("menu/selectresolution.png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
            strategy.show();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (settings) {
            draggingBGM = false;
            draggingSFX = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
///////////////////////////////////////////// MOUSE LISTENER END ///////////////////////////////////////////////////////

///////////////////////////////////////////// MOUSE MOTION LISTENER START //////////////////////////////////////////////
    @Override
    public void mouseDragged(MouseEvent e) {
        if (settings) {
            int ballX = e.getX();
            if (draggingBGM && ballX > WIDTH / 2 - 124 && ballX < WIDTH / 2 + 178) {
                bgm = ballX - 7;
            }
            if (draggingSFX && ballX > WIDTH / 2 - 124 && ballX < WIDTH / 2 + 178) {
                sfx = ballX - 7;
            }
            settings();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (menu) {
            choiceY = com.petsu.theexile.Menu.mouseMovedMenu(e.getX(), e.getY(), choiceY, HEIGHT);
            menu();
        }
        else if (settings) {
            if (com.petsu.theexile.Menu.mouseMovedSettings(e.getX(), e.getY(), WIDTH, HEIGHT) > 0) {
                settingsMove(com.petsu.theexile.Menu.mouseMovedSettings(e.getX(), e.getY(), WIDTH, HEIGHT));
            }
        }
        else if (resolution) {
            settingsResolutionMove (com.petsu.theexile.Menu.mouseMovedResolution(e.getX(), e.getY(), WIDTH, HEIGHT));
        }
    }
///////////////////////////////////////////// MOUSE MOTION LISTENER END ////////////////////////////////////////////////

    public void updateChoice() {
        if (menu) {
            if (up) menuChoiceUp();
            if (down) menuChoiceDown();
            if (enter) menuChoiceEnter();
        }
        if (settings) {
            if (up) settingsChoiceUp();
            if (down) settingsChoiceDown();
            if (left) settingsChoiceLeft();
            if (right) settingsChoiceRight();
            if (space) settingsChoiceSpace();
            if (enter) settingsChoiceEnter();
        }
        if (resolution) {
            if (up) settingsResolutionUp();
            if (down) settingsResolutionDown();
            if (space) settingsResolutionSpace();
        }
    }

///////////////////////////////////////////// MENU - METHODS START /////////////////////////////////////////////////////
    public void menu() {
        Graphics g = strategy.getDrawGraphics();
        g.drawImage(Sprites.getSprite("menu/background.png"), 0, 0, WIDTH, HEIGHT, this);
        g.drawImage(Sprites.getSprite("menu/title.png"), 50, 50, this);
        g.drawImage(Sprites.getSprite("menu/buttons.png"), 50, HEIGHT / 2 - 125, this);
        g.drawImage(Sprites.getSprite("menu/choice.png"), 20, choiceY, this);
        strategy.show();
    }

    public void menuChoiceUp() {
        if (choiceY > HEIGHT / 2 - 135) {
            choiceY -= 50;
            menu();
        }
    }

    public void menuChoiceDown() {
        if (choiceY < HEIGHT / 2 + 65) {
            choiceY += 50;
            menu();
        }
    }

    public void menuChoiceEnter() {
        if (choiceY == HEIGHT / 2 - 135) newgame();
        else if (choiceY == HEIGHT / 2 - 85) load();
        else if (choiceY == HEIGHT / 2 - 35) settings();
        else if (choiceY == HEIGHT / 2 + 15) about();
        else if (choiceY == HEIGHT / 2 + 65) exit();
        menu = false;
    }
///////////////////////////////////////////// MENU - METHODS END ///////////////////////////////////////////////////////

///////////////////////////////////////////// NEW GAME - METHODS START /////////////////////////////////////////////////
    public void newgame() {
        Graphics g = strategy.getDrawGraphics();
        for (int i = 0; i < 50; i++) {
            g.drawImage(Sprites.getSprite("menu/black.png"), 0, 0, WIDTH, HEIGHT, this);
            BGM.volume((bgm - (WIDTH / 2 - 130)) / 3 - i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            strategy.show();
        }
        g.drawImage(Sprites.getSprite("ui/energy.png"), 20, 20, this);
        strategy.show();
        BGM.stop();
        try {
            BGM.play("sound/faded.wav");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
///////////////////////////////////////////// NEW GAME - METHODS END ///////////////////////////////////////////////////

///////////////////////////////////////////// LOAD - METHODS START /////////////////////////////////////////////////////
    public void load() {
        menu = false;
        load = true;
        loadP = 1;
        Graphics g = strategy.getDrawGraphics();
        g.drawImage(Sprites.getSprite("menu/load.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
        g.setFont(new Font("Calibri",Font.ITALIC, 15));
        g.setColor(Color.black);
        g.drawString("No saves to show.",WIDTH / 2 - 50, HEIGHT / 2);
        strategy.show();
    }

    public void loadLoad() {}

    public void loadDelete() {}

    public void loadBack() {
        load = false;
        menu = true;
        menu();
    }
///////////////////////////////////////////// LOAD - METHODS END ///////////////////////////////////////////////////////

///////////////////////////////////////////// SETTINGS METHODS START ///////////////////////////////////////////////////
    public void settings() {
        menu = false;
        settings = true;
        settingsP = 1;
        Graphics g = strategy.getDrawGraphics();
        g.drawImage(Sprites.getSprite("menu/settings.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
        g.drawImage(Sprites.getSprite("menu/" +widthtmp +"x" +heighttmp +".png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
        g.drawImage(Sprites.getSprite("menu/ball.png"), bgm, HEIGHT / 2 - 65, this);
        if (draggingBGM) g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 65, this);
        if ((bgm - WIDTH / 2 + 130) / 3 == 100) {
            g.drawImage(Sprites.getSprite("font/1.png"), WIDTH / 2 + 180, HEIGHT / 2 - 75, this);
            g.drawImage(Sprites.getSprite("font/0.png"), WIDTH / 2 + 190, HEIGHT / 2 - 75, this);
            g.drawImage(Sprites.getSprite("font/0.png"), WIDTH / 2 + 210, HEIGHT / 2 - 75, this);
        }
        else if ((bgm - WIDTH / 2 + 130) / 3 > 9 && (bgm - WIDTH / 2 + 130) / 3 < 100) {
            g.drawImage(Sprites.getSprite("font/" +(bgm - WIDTH / 2 + 130) / 3 / 10 +".png"), WIDTH / 2 + 180,
                    HEIGHT / 2 - 75, this);
            g.drawImage(Sprites.getSprite("font/" +(bgm - WIDTH / 2 + 130) / 3 % 10 +".png"), WIDTH / 2 + 200,
                    HEIGHT / 2 - 75, this);
        }
        else if ((bgm - WIDTH / 2 + 130) / 3 < 10) {
            g.drawImage(Sprites.getSprite("font/" +(bgm - WIDTH / 2 + 130) / 3 +".png"), WIDTH / 2 + 180,
                    HEIGHT / 2 - 75, this);
        }
        com.petsu.sound.BGM.volume((bgm - (WIDTH / 2 - 130)) / 3);
        g.drawImage(Sprites.getSprite("menu/ball.png"), sfx, HEIGHT / 2 - 30, this);
        if (draggingSFX) g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 30, this);
        if ((sfx - WIDTH / 2 + 130) / 3 == 100) {
            g.drawImage(Sprites.getSprite("font/1.png"), WIDTH / 2 + 180, HEIGHT / 2 - 40, this);
            g.drawImage(Sprites.getSprite("font/0.png"), WIDTH / 2 + 190, HEIGHT / 2 - 40, this);
            g.drawImage(Sprites.getSprite("font/0.png"), WIDTH / 2 + 210, HEIGHT / 2 - 40, this);
        }
        else if ((sfx - WIDTH / 2 + 130) / 3 > 9 && (sfx - WIDTH / 2 + 130) / 3 < 100) {
            g.drawImage(Sprites.getSprite("font/" +(sfx - WIDTH / 2 + 130) / 3 / 10 +".png"), WIDTH / 2 + 180,
                    HEIGHT / 2 - 40, this);
            g.drawImage(Sprites.getSprite("font/" +(sfx - WIDTH / 2 + 130) / 3 % 10 +".png"), WIDTH / 2 + 200,
                    HEIGHT / 2 - 40, this);
        }
        else if ((sfx - WIDTH / 2 + 130) / 3 < 10) {
            g.drawImage(Sprites.getSprite("font/" +(sfx - WIDTH / 2 + 130) / 3 +".png"), WIDTH / 2 + 180,
                    HEIGHT / 2 - 40, this);
        }
        g.drawImage(Sprites.getSprite("menu/" +systemsounds +".png"), WIDTH / 2 - 225, HEIGHT / 2 + 7, this);
        g.drawImage(Sprites.getSprite("menu/" +showfps +".png"), WIDTH / 2 - 225, HEIGHT / 2 + 42, this);
        strategy.show();
    }

    public void settingsMove(int choice) {
        Graphics g = strategy.getDrawGraphics();
        g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
        switch (choice) {
            case 1:
                g.drawImage(Sprites.getSprite("menu/selectresolution.png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
                break;
            case 2:
                g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 65, this);
                break;
            case 3:
                g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 30, this);
                break;
            case 4:
                g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 7, this);
                break;
            case 5:
                g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 42, this);
                break;
            case 6:
                g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 - 90, HEIGHT / 2 + 125, this);
                break;
            case 7:
                g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 + 20, HEIGHT / 2 + 125, this);
                break;
            case 8:
                g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 + 130, HEIGHT / 2 + 125, this);
                break;
        }
        settingsP = choice;
        strategy.show();
    }

    public void settingsChoiceUp() {
        Graphics g = strategy.getDrawGraphics();
        if (settingsP > 1) {
            settingsP -= 1;
            g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
            switch (settingsP) {
                case 1:
                    g.drawImage(Sprites.getSprite("menu/selectresolution.png"), WIDTH / 2 - 70, HEIGHT / 2 - 105, this);
                    break;
                case 2:
                    g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 65, this);
                    break;
                case 3:
                    g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 30, this);
                    break;
                case 4:
                    g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 7, this);
                    break;
                case 5: case 6: case 7:
                    g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 42, this);
                    settingsP = 5;
                    break;
            }
            strategy.show();
        }
    }

    public void settingsChoiceDown() {
        Graphics g = strategy.getDrawGraphics();
        if (settingsP < 6) {
            settingsP += 1;
            g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
            switch (settingsP) {
                case 2:
                    g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 65, this);
                    break;
                case 3:
                    g.drawImage(Sprites.getSprite("menu/selectslider.png"), WIDTH / 2 - 130, HEIGHT / 2 - 30, this);
                    break;
                case 4:
                    g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 7, this);
                    break;
                case 5:
                    g.drawImage(Sprites.getSprite("menu/selectfield.png"), WIDTH / 2 - 225, HEIGHT / 2 + 42, this);
                    break;
                case 6:
                    g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 - 90, HEIGHT / 2 + 125, this);
                    break;
            }
            strategy.show();
        }
    }

    public void settingsChoiceLeft() {
        Graphics g = strategy.getDrawGraphics();
        if (settingsP > 6) {
            settingsP -= 1;
            g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
            switch (settingsP) {
                case 6:
                    g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 - 90, HEIGHT / 2 + 125, this);
                    break;
                case 7:
                    g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 + 20, HEIGHT / 2 + 125, this);
                    break;
            }
            strategy.show();
        }
    }

    public void settingsChoiceRight() {
        Graphics g = strategy.getDrawGraphics();
        if (settingsP < 8 && settingsP > 5) {
            settingsP += 1;
            g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
            switch (settingsP) {
                case 7:
                    g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 + 20, HEIGHT / 2 + 125, this);
                    break;
                case 8:
                    g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 + 130, HEIGHT / 2 + 125, this);
                    break;
            }
            strategy.show();
        }
    }

    public void settingsChoiceSpace() {

    }

    public void settingsChoiceEnter() {

    }

    public void settingsResolutionMove(int choice) {
        Graphics g = strategy.getDrawGraphics();
        g.drawImage(Sprites.getSprite("menu/chooseresolution.png"), WIDTH / 2 - 70, HEIGHT / 2 - 85, this);
        switch (choice) {
            case 1:
                g.drawImage(Sprites.getSprite("menu/select800x600.png"), WIDTH / 2 - 70, HEIGHT / 2 - 81, this);
                break;
            case 2:
                g.drawImage(Sprites.getSprite("menu/select1024x768.png"), WIDTH / 2 - 70, HEIGHT / 2 - 58, this);
                break;
            case 3:
                g.drawImage(Sprites.getSprite("menu/select1280x800.png"), WIDTH / 2 - 70, HEIGHT / 2 - 34, this);
                break;
            case 4:
                g.drawImage(Sprites.getSprite("menu/select1366x768.png"), WIDTH / 2 - 70, HEIGHT / 2 - 10, this);
                break;
        }
        strategy.show();
    }

    public void settingsResolutionUp() {

    }

    public void settingsResolutionDown() {

    }

    public void settingsResolutionSpace() {

    }

    public void settingsApply() {
        Graphics g = strategy.getDrawGraphics();
        if (WIDTH != widthtmp && HEIGHT != heighttmp) {
            try {
                config();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            File cfg = new File("settings/config.cfg");
            if (cfg.isFile()) {
                Scanner in = null;
                try {
                    in = new Scanner(cfg);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                for (int i = 0; i < 6; i++) {
                    assert in != null;
                    String write = in.nextLine();
                    switch (i) {
                        case 0:
                            WIDTH = Integer.parseInt(write);
                            widthtmp = WIDTH;
                            break;
                        case 1:
                            HEIGHT = Integer.parseInt(write);
                            heighttmp = HEIGHT;
                            choiceY = HEIGHT / 2 - 35;
                            break;
                        case 2:
                            bgm = Integer.parseInt(write);
                            bgm = WIDTH / 2 - 130 + 3 * bgm;
                            break;
                        case 3:
                            sfx = Integer.parseInt(write);
                            sfx = WIDTH / 2 - 130 + 3 * sfx;
                            break;
                        case 4:
                            systemsounds = Boolean.parseBoolean(write);
                            break;
                        case 5:
                            showfps = Boolean.parseBoolean(write);
                            break;
                    }
                }
            }
            setBounds(0, 0, WIDTH, HEIGHT);
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            panel.setLayout(null);
            panel.setVisible(true);

            frame.setBounds(0, 0, WIDTH, HEIGHT);
            frame.setVisible(true);
            menu();
            settings();
        }
        else {
            try {
                config();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        g.drawImage(Sprites.getSprite("menu/apply.png"), (WIDTH / 2) - 150, (HEIGHT / 2) - 20, this);
        strategy.show();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ev) {
            ev.printStackTrace();
        }
        settings();
        g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
        g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 - 90, HEIGHT / 2 + 125, this);
        strategy.show();
    }

    public void settingsDefault() {
        Graphics g = strategy.getDrawGraphics();
        widthtmp = 800;
        heighttmp = 600;
        bgm = WIDTH / 2 + 140;
        sfx = WIDTH / 2 + 140;
        systemsounds = true;
        showfps = false;
        settings();
        g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
        g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 + 20, HEIGHT / 2 + 125, this);
        g.drawImage(Sprites.getSprite("menu/settingsmsg2.png"), (WIDTH / 2) - 150, (HEIGHT / 2) - 20, this);
        strategy.show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ev) {
            ev.printStackTrace();
        }
        settings();
        g.drawImage(Sprites.getSprite("menu/settingsmask.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
        g.drawImage(Sprites.getSprite("menu/selectbutton.png"), WIDTH / 2 + 20, HEIGHT / 2 + 125, this);
        strategy.show();
    }

    public void settingsBack() {
        File cfg = new File("settings/config.cfg");
        Scanner in = null;
        try {
            in = new Scanner(cfg);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        for (int i = 0; i < 6; i++) {
            assert in != null;
            String write = in.nextLine();
            switch (i) {
                case 0:
                    WIDTH = Integer.parseInt(write);
                    widthtmp = WIDTH;
                    break;
                case 1:
                    HEIGHT = Integer.parseInt(write);
                    heighttmp = HEIGHT;
                    choiceY = HEIGHT / 2 - 135;
                    break;
                case 2:
                    bgm = Integer.parseInt(write);
                    bgm = WIDTH / 2 - 130 + 3 * bgm;
                    com.petsu.sound.BGM.volume((bgm - (WIDTH / 2 - 130)) / 3);
                    break;
                case 3:
                    sfx = Integer.parseInt(write);
                    sfx = WIDTH / 2 - 130 + 3 * sfx;
                    break;
                case 4:
                    systemsounds = Boolean.parseBoolean(write);
                    break;
                case 5:
                    showfps = Boolean.parseBoolean(write);
                    break;
            }
        }
        settings = false;
        menu = true;
        choiceY = HEIGHT / 2 - 35;
        menu();
    }

    private void config() throws FileNotFoundException {
        PrintWriter save = new PrintWriter("settings/config.cfg");
        save.println(widthtmp);
        save.println(heighttmp);
        save.println((bgm - (WIDTH / 2 - 130)) / 3);
        save.println((sfx - (WIDTH / 2 - 130)) / 3);
        save.println(systemsounds);
        save.println(showfps);
        save.close();
    }
///////////////////////////////////////////// SETTINGS - METHODS END ///////////////////////////////////////////////////

///////////////////////////////////////////// ABOUT - METHODS START ////////////////////////////////////////////////////
    public void about() {
        menu = false;
        about = true;
        loadP = 1;
        Graphics g = strategy.getDrawGraphics();
        g.drawImage(Sprites.getSprite("menu/about.png"), WIDTH / 2 - 250, HEIGHT / 2 - 175, this);
        g.setFont(new Font("Calibri",Font.ITALIC, 15));
        g.setColor(Color.black);
        g.drawString("\"The Exile\" was created by ...",WIDTH / 2 - 225, HEIGHT / 2 - 100);
        strategy.show();
    }

    public void aboutPrev() {}

    public void aboutNext() {}

    public void aboutBack() {
        about = false;
        menu = true;
        menu();
    }
///////////////////////////////////////////// ABOUT - METHODS END //////////////////////////////////////////////////////

    public void exit() {
        Graphics g = strategy.getDrawGraphics();
        for (int i = 0; i < 50; i++) {
            g.drawImage(Sprites.getSprite("menu/black.png"), 0, 0, WIDTH, HEIGHT, this);
            g.drawImage(Sprites.getSprite("menu/exitmsg.png"), (WIDTH / 2) - 150, (HEIGHT / 2) - 20, this);
                com.petsu.sound.BGM.volume((bgm - (WIDTH / 2 - 130)) / 3 - i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            strategy.show();
        }
        com.petsu.sound.BGM.clip.close();
        System.exit(0);
    }

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        File cfg = new File("settings/config.cfg");
        int BGMVolume = 90;
        Scanner in = new Scanner(cfg);
        for (int i = 0; i < 3; i++) {
            String write = in.nextLine();
            if (i == 2) BGMVolume = Integer.parseInt(write);
        }
        com.petsu.sound.BGM.play("sound/riseagain.wav");
        com.petsu.sound.BGM.volume(BGMVolume);
        TheExile inv = new TheExile();
        inv.start();
    }
}