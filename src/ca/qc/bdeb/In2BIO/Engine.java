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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

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
        creerEvenement();
        SpriteSheet spriteSheet = new SpriteSheet("char.png", 64, 64);
        Animation animation = new Animation();
        animation.addFrame(spriteSheet.getSprite(1, 1), 100);
        animation.addFrame(spriteSheet.getSprite(2, 1), 100);
        animation.addFrame(spriteSheet.getSprite(3, 1), 100);
        animation.addFrame(spriteSheet.getSprite(4, 1), 100);
        animation.addFrame(spriteSheet.getSprite(5, 1), 100);
        animation.addFrame(spriteSheet.getSprite(6, 1), 100);
        animation.addFrame(spriteSheet.getSprite(7, 1), 100);
        animation.addFrame(spriteSheet.getSprite(8, 1), 100);
        
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
        System.out.println("heighyt" + animations[0].getHeight());
    }
    
    Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }
    
// http://www.shionn.org/slick2d-lesson-1-window-creation
    
    
   public void creerEvenement(){
       
   }
   @Override
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        if(Input.KEY_ESCAPE == key){
            container.exit();  
        }
    }
    
    @Override
    public void update(GameContainer gc, int i) throws SlickException {
    }
    
    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        grphcs.drawAnimation(animations[direction + (moving ? 4 : 0)], x, y);
    }
    
}
