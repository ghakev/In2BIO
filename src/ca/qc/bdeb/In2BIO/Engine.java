/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.In2BIO;

import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class Engine extends BasicGame {

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new Engine(), 640, 480, false).start();

    }

    private float x = 150, y = 150;
    private float xCamera = x, yCamera = y;
    private int direction = 0;
    private boolean moving = false;
    private Animation[] animations = new Animation[8];
    private GameContainer container;
    private TiledMap map;
    private ArrayList<Integer> listeKeyCodes = new ArrayList<>();

    public Engine() {
        super("In2BIO");
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        this.container = container;
        container.setShowFPS(false);
        this.map = new TiledMap("ca/qc/bdeb/In2BIO/map.tmx");
        SpriteSheet spriteSheet = new SpriteSheet("char.png", 32, 32);

        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

    }

    Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }

// http://www.shionn.org/slick2d-lesson-1-window-creation
    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c); //To change body of generated methods, choose Tools | Templates.
        if (!listeKeyCodes.contains(key)) {
            listeKeyCodes.add(key);
        }
//        switch (key) {
//            case Input.KEY_UP:
//                this.direction = 0;
//                this.moving = true;
//                break;
//            case Input.KEY_LEFT:
//                this.direction = 1;
//                this.moving = true;
//                break;
//            case Input.KEY_DOWN:
//                this.direction = 2;
//                this.moving = true;
//                break;
//            case Input.KEY_RIGHT:
//                this.direction = 3;
//                this.moving = true;
//                break;
//        }
    }

    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
//        if (Input.KEY_ESCAPE == key) {
//            container.exit();
//        }
        listeKeyCodes.remove(new Integer(key));
        this.moving = false;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (this.moving) {
            float futurX = getFuturX(i);
            float futurY = getFuturY(i);
            boolean collision = isCollision(futurX, futurY);
            if (collision) {
                this.moving = false;
            } else {
                this.x = futurX;
                this.y = futurY;
            }
        }
        float futurX = this.x;
        float futurY = this.y;
        if (this.moving) {

            switch (this.direction) {
                case 0:
                    futurY = this.y - .1f * i;
                    break;
                case 1:
                    futurX = this.x - .1f * i;
                    break;
                case 2:
                    futurY = this.y + .1f * i;
                    break;
                case 3:
                    futurX = this.x + .1f * i;
                    break;
            }
            this.x = futurX;
            this.y = futurY;
        }
        Image tile = this.map.getTileImage(
                (int) futurX / this.map.getTileWidth(),
                (int) futurY / this.map.getTileHeight(),
                this.map.getLayerIndex("logic"));
        boolean collision = tile != null;
        if (collision) {
            // il y a toujours collision si il y a un pixel non transparent dans la tuile 
            Color color = tile.getColor(
                    (int) futurX % this.map.getTileWidth(),
                    (int) futurY % this.map.getTileHeight());
            collision = color.getAlpha() > 0;
        }
        if (collision) {
            this.moving = false;
        } else {
            this.x = futurX;
            this.y = futurY;
        }

        if (listeKeyCodes.contains(Input.KEY_UP)) {
            this.y -= .1f * i;
            this.direction = 0;
            this.moving = true;
        }
        if (listeKeyCodes.contains(Input.KEY_LEFT)) {
            this.x -= .1f * i;
            this.direction = 1;
            this.moving = true;
        }
        if (listeKeyCodes.contains(Input.KEY_DOWN)) {
            this.y += .1f * i;
            this.direction = 2;
            this.moving = true;
        }
        if (listeKeyCodes.contains(Input.KEY_RIGHT)) {
            this.x += .1f * i;
            this.direction = 3;
            this.moving = true;
        }

        int w = container.getWidth() / 4;
        if (this.x > this.xCamera + w) {
            this.xCamera = this.x - w;
        }
        if (this.x < this.xCamera - w) {
            this.xCamera = this.x + w;
        }
        int h = container.getHeight() / 4;
        if (this.y > this.yCamera + h) {
            this.yCamera = this.y - h;
        }
        if (this.y < this.yCamera - h) {
            this.yCamera = this.y + h;
        }
    }

    private boolean isCollision(float x, float y) {
        int tileW = this.map.getTileWidth();
        int tileH = this.map.getTileHeight();
        int logicLayer = this.map.getLayerIndex("logic");
        Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
        boolean collision = tile != null;
        if (collision) {
            Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            collision = color.getAlpha() > 0;
        }
        return collision;
    }

    private float getFuturX(int delta) {
        float futurX = this.x;
        switch (this.direction) {
            case 1:
                futurX = this.x - .1f * delta;
                break;
            case 3:
                futurX = this.x + .1f * delta;
                break;
        }
        return futurX;
    }

    private float getFuturY(int delta) {
        float futurY = this.y;
        switch (this.direction) {
            case 0:
                futurY = this.y - .1f * delta;
                break;
            case 2:
                futurY = this.y + .1f * delta;
                break;
        }
        return futurY;
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        grphcs.translate(container.getWidth() / 2 - (int) this.x,
                container.getHeight() / 2 - (int) this.y);
        this.map.render(0, 0);

        grphcs.drawAnimation(animations[direction + (moving ? 4 : 0)], x, y);
    }

}
