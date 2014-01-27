/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyendodelasteroide;

import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author ppesq
 */
public class Planet extends Draggable {

    //icons
    private ImageIcon icon;
    private URL image_url;
    private URL collision_image_url;

    //icon control
    private boolean change_image;

    //collision control
    private boolean in_collision;
    private int DEFAULT_COLLISION_CYCLES = 25;
    private int collision_cycles_counter;

    public Planet(int x, int y, int w, int h, URL image_url, URL collision_image_url) {
        super(x, y, w, h);
        this.image_url = image_url;
        this.collision_image_url = collision_image_url;
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.image_url));
        in_collision = false;
        collision_cycles_counter = -1;
        change_image = false;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
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

    //ACTIONS
    /**
     * Method <I>stopColliding</I>
     * Takes the character out of the collision state.
     */
    public void collide() {
        in_collision = true;
        change_image = true;
    }

    /**
     * Method <I>stopColliding</I>
     * Takes the character out of the collision state.
     */
    public void stopColliding() {
        in_collision = false;
        change_image = true;
    }

    /**
     * Method <I>decreaseCollisionCyclesCounter</I>
     * Decreases the collision cycles counter by 1. This method is used over
     * setCollision_cycles_counter for readability purposes.
     */
    public void decreaseCollisionCyclesCounter() {
        setCollision_cycles_counter(collision_cycles_counter - 1);
    }

    private void updatePlanet(int mx, int my) {
        if (change_image) {
            updatePlanetImage();
        }
        if (dragging) {
            updatePlanetPosition(mx, my);

        }
    }

    /**
     * Updates the planet position in based
     * @param mx
     * @param my 
     */
    private void updatePlanetPosition(int mx, int my) {
        drag(mx, my);
    }

    /**
     * Method <I>updateCharacterImage</I>
     * updates the character image based on status
     */
    private void updatePlanetImage() {
        if (in_collision) {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(collision_image_url));
        } else {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(image_url));
        }
        change_image = false;
    }
}
