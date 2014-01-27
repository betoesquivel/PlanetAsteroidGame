/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyendodelasteroide;

import java.awt.Image;
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
    private int DEFAULT_COLLISION_CYCLES = 25;
    private int collision_cycles_counter;

    public Asteroid(URL image_url, URL collision_image_url, int posX, int posY, int speed) {
        super(posX, posY, speed);
        this.image_url = image_url;
        this.collision_image_url = collision_image_url;
        this.change_image = false;
        this.in_collision = false;
        this.collision_cycles_counter = -1;
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.image_url));
    }

    private void updateAsteroid(Planet followedPlanet) {
        if (change_image) {
            updateAsteroidImage();
        }
        if (!in_collision) {
            //get new x and y coordinates based on position of followedPlanet
            follow(followedPlanet);
        } else {
            //get new random position in upper quadrant
            
        }
    }

    /**
     * Method <I>updateCharacterImage</I>
     * updates the character image based on status
     */
    private void updateAsteroidImage() {
        if (in_collision) {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(collision_image_url));
        } else {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(image_url));
        }
        change_image = false;
    }
}
