package com.petsu.theexile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Sprites {
    public HashMap sprites;

    public Sprites() {
        sprites = new HashMap();
    }

    private BufferedImage loadImage(String url) {
        File image = new File(url);
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            System.err.println("Blad odczytu");
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getSprite(String url) {
        BufferedImage img = (BufferedImage)sprites.get(url);
        if (img == null) {
            img = loadImage("graphics/" +url);
            sprites.put(url,img);
        }
        return img;
    }
}