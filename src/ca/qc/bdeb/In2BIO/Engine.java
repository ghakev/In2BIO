/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.In2BIO;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
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

    private float x = 0, y = 0;
    private int direction = 0;
    private boolean moving = false;
    private Animation[] animations = new Animation[8];
    private GameContainer container;

    public Engine() {
        super("In2BIO");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        SpriteSheet spriteSheet = new SpriteSheet("char.png", 64, 64);
//        Animation animation = new Animation();
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

//        animations[0].addFrame(spriteSheet.getSprite(1, 1), 100);
//        animations[1].addFrame(spriteSheet.getSprite(2, 1), 100);
//        animations[2].addFrame(spriteSheet.getSprite(3, 1), 100);
//        animations[3].addFrame(spriteSheet.getSprite(4, 1), 100);
//        animations[4].addFrame(spriteSheet.getSprite(5, 1), 100);
//        animations[5].addFrame(spriteSheet.getSprite(6, 1), 100);
//        animations[6].addFrame(spriteSheet.getSprite(7, 1), 100);
//        animations[7].addFrame(spriteSheet.getSprite(8, 1), 100);
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
        switch (key) {
            case Input.KEY_UP:
                this.direction = 0;
                this.moving = true;
                break;
            case Input.KEY_LEFT:
                this.direction = 1;
                this.moving = true;
                break;
            case Input.KEY_DOWN:
                this.direction = 2;
                this.moving = true;
                break;
            case Input.KEY_RIGHT:
                this.direction = 3;
                this.moving = true;
                break;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
        this.moving = false;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (this.moving) {
            switch (this.direction) {
                case 0:
                    this.y -= .1f * i;
                    break;
                case 1:
                    this.x -= .1f * i;
                    break;
                case 2:
                    this.y += .1f * i;
                    break;
                case 3:
                    this.x += .1f * i;
                    break;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        
        grphcs.drawAnimation(animations[direction + (moving ? 4 : 0)], x, y);

    }

}
