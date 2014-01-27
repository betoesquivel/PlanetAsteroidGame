/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyendodelasteroide;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author ppesq
 */
public class Asteroid extends Follower {

    //icons
    private ImageIcon icon;
    private URL image_url;
    private URL collision_image_url;

    //icon control
    private boolean change_image;

    //collision control
    private boolean in_collision;
    private int DEFAULT_COLLISION_CYCLES = 10;
    private int collision_cycles_counter;

    public Asteroid(int posX, int posY, int speed, URL image_url, URL collision_image_url) {
        super(posX, posY, speed);
        this.image_url = image_url;
        this.collision_image_url = collision_image_url;
        this.change_image = false;
        this.in_collision = false;
        this.collision_cycles_counter = -1;
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.image_url));
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int getHeight() {
        return icon.getIconHeight();
    }

    public int getWidth() {
        return icon.getIconWidth();
    }

    public URL getImage_url() {
        return image_url;
    }

    public void setImage_url(URL image_url) {
        this.image_url = image_url;
    }

    public URL getCollision_image_url() {
        return collision_image_url;
    }

    public void setCollision_image_url(URL collision_image_url) {
        this.collision_image_url = collision_image_url;
    }

    public boolean isChange_image() {
        return change_image;
    }

    public void setChange_image(boolean change_image) {
        this.change_image = change_image;
    }

    public boolean isIn_collision() {
        return in_collision;
    }

    public void setIn_collision(boolean in_collision) {
        this.in_collision = in_collision;
    }

    public int getDEFAULT_COLLISION_CYCLES() {
        return DEFAULT_COLLISION_CYCLES;
    }

    public void setDEFAULT_COLLISION_CYCLES(int DEFAULT_COLLISION_CYCLES) {
        this.DEFAULT_COLLISION_CYCLES = DEFAULT_COLLISION_CYCLES;
    }

    public int getCollision_cycles_counter() {
        return collision_cycles_counter;
    }

    public void setCollision_cycles_counter(int collision_cycles_counter) {
        this.collision_cycles_counter = collision_cycles_counter;
    }

    /**
     * Method <I>decreaseCollisionCyclesCounter</I>
     * Decreases the collision cycles counter by 1. This method is used over
     * setCollision_cycles_counter for readability purposes.
     */
    public void decreaseCollisionCyclesCounter() {
        setCollision_cycles_counter(collision_cycles_counter - 1);
    }

    /**
     * Puts the object in a collision state.
     */
    public void collide() {
        setIn_collision(true);
        setChange_image(true);
        setCollision_cycles_counter(DEFAULT_COLLISION_CYCLES);
    }

    public void stopColliding() {
        in_collision = false;
        change_image = true;
        setCollision_cycles_counter(-1);
    }

    public void updateAsteroid(Planet followedPlanet) {
        if (change_image) {
            updateAsteroidImage();
        }
        if (!in_collision) {
            //get new x and y coordinates based on position of followedPlanet
            follow(followedPlanet);
        }
    }

    /**
     * Method <I>updateCharacterImage</I>
     * updates the character image based on status
     */
    public void updateAsteroidImage() {
        if (in_collision) {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(collision_image_url));
        } else {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(image_url));
        }
        change_image = false;
    }

    public Rectangle getPerimeter() {
        return new Rectangle(getPosX(), getPosY(), getWidth(), getHeight());
    }

}
