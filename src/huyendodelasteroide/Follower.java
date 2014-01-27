/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyendodelasteroide;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author ppesq
 */
public class Follower {

    private int DEFAULT_SPEED = 2;
    private int DEFAULT_POSX = 0;
    private int DEFAULT_POSY = 0;

    private int posX;
    private int posY;
    private int speed;

    /**
     * Default constructor
     */
    public Follower() {
        this.posX = DEFAULT_POSX;
        this.posY = DEFAULT_POSY;
        this.speed = DEFAULT_SPEED;
    }

    /**
     * Constructor with default speed
     * @param posX specified position in x
     * @param posY specified position in y
     */
    public Follower(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.speed = DEFAULT_SPEED;
    }

    /**
     * Constructor with all attributes set
     * @param posX specified position in x
     * @param posY specified position in y
     * @param speed specified speed at which it will follow some object.
     */
    public Follower(int posX, int posY, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
    }

    public int getDEFAULT_SPEED() {
        return DEFAULT_SPEED;
    }

    public void setDEFAULT_SPEED(int DEFAULT_SPEED) {
        this.DEFAULT_SPEED = DEFAULT_SPEED;
    }

    public int getDEFAULT_POSX() {
        return DEFAULT_POSX;
    }

    public void setDEFAULT_POSX(int DEFAULT_POSX) {
        this.DEFAULT_POSX = DEFAULT_POSX;
    }

    public int getDEFAULT_POSY() {
        return DEFAULT_POSY;
    }

    public void setDEFAULT_POSY(int DEFAULT_POSY) {
        this.DEFAULT_POSY = DEFAULT_POSY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    
    /**
     * This method updates the followers speed, increased in one.
     */
    public void accelerate() {
        speed += 1;
    }

    /**
     * Source: Anas Mosaad
     * http://bytes.com/topic/java/answers/946160-make-object-follow-another-object-slowly
     *
     * @param followed object to be followed, Planet object... 
     * Eventually I will make a common basic object, but I need more classes 
     * to determine the common denominator.
     */
    public void follow(Planet followed) {
        int change_in_y = followed.getPosY() - getPosY();
        int change_in_x = followed.getPosX() - getPosX();

        double h = (int) Math.sqrt(Math.pow(change_in_x, 2) + Math.pow(change_in_y, 2));

        int xMove = (int) (change_in_x / h * speed);
        int yMove = (int) (change_in_y / h * speed);

        setPosX(getPosX() + xMove);
        setPosY(getPosY() + yMove);
    }
}
