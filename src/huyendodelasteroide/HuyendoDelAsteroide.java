/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyendodelasteroide;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author ppesq
 */
public class HuyendoDelAsteroide extends Applet implements Runnable, MouseListener {

    //Characters and important areas of the game
    private Planet jupiter;
    private Asteroid asteroid;
    private AudioClip sound;
    private boolean collided;
    private Image dbImage; 
    private Image dbg; 

    //mouse coordinates
    private int mouse_x;
    private int mouse_y;

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {

        this.setSize(500, 500);

        int posX = (int) (Math.random() * (getWidth() / 4));    // posicion en x es un cuarto del applet
        int posY = (int) (Math.random() * (getHeight() / 4));    // posicion en y es un cuarto del applet

        URL eURL = this.getClass().getResource("/images/jupiter.gif");
        jupiter = new Planet(posX, posY, eURL, eURL);

        int posrX = (int) (Math.random() * (getWidth() / 4)) + getWidth() / 2;    //posision x es tres cuartos del applet
        int posrY = (int) (Math.random() * (getHeight() / 4)) + getHeight() / 2;    //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("/images/asteroid.gif");
        asteroid = new Asteroid(posrX, posrY, 2, rURL, rURL);
        asteroid.setPosX(asteroid.getPosX() - asteroid.getWidth());
        asteroid.setPosY(asteroid.getPosY() - asteroid.getHeight());

        setBackground(Color.black);

        addMouseListener(this);
        //Se carga el sonido
        URL eaURL = this.getClass().getResource("/sounds/8-bit-explosion.wav");
        sound = getAudioClip(eaURL);

        //the explosion animation
        URL cURL = this.getClass().getResource("/images/explosion_bien.gif");
        ImageIcon explosion = new ImageIcon(cURL);
        collided = false;
    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        while (true) {
            updateCharacters();
            checkCollision();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos elefante y asteroid.
     *
     */
    public void updateCharacters() {

        jupiter.updatePlanet();
        asteroid.updateAsteroid(jupiter);

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y asteroid
     * con las orillas del <code>Applet</code>.
     */
    public void checkCollision() {
        //Colision entre objetos
        if (jupiter.isIn_collision() && asteroid.isIn_collision()) {
            if (jupiter.getCollision_cycles_counter() == 0) {

            } else {
                jupiter.decreaseCollisionCyclesCounter();
                asteroid.decreaseCollisionCyclesCounter();
            }
        } else {

        }
        if (jupiter.intersectsAsteroid(asteroid)) {
            if (jupiter.getCollision_cycles_counter() == jupiter.getDEFAULT_COLLISION_CYCLES()) {
                sound.play();    //sonido al colisionar
                jupiter.collide();
                asteroid.collide();
                jupiter.stopDragging();
            } else {
                if (jupiter.getCollision_cycles_counter() > 0) {
                    jupiter.decreaseCollisionCyclesCounter();
                    asteroid.decreaseCollisionCyclesCounter();
                } else {
                    jupiter.stopColliding();
                    asteroid.stopColliding();
                    //El planet se mueve al azar en la mitad izquierda del applet.
                    jupiter.setPosX((int) (Math.random() * (getWidth() / 2 - jupiter.getWidth())));
                    jupiter.setPosY((int) (Math.random() * (getHeight() / 2 - jupiter.getHeight())));
                    //El asteroid se mueve al azar en la mitad derecha del appler.
                    asteroid.setPosX((int) (Math.random() * getWidth() / 2) + getWidth() / 2 - asteroid.getWidth());
                    asteroid.setPosY((int) (Math.random() * getHeight() / 2) + getHeight() / 2 - asteroid.getHeight());
                    asteroid.accelerate();
                }
            }

        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void update(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        if (dumbo != null && asteroid != null) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(dumbo.getImagenI(), dumbo.getPosX(), dumbo.getPosY(), this);
            g.drawImage(asteroid.getImagenI(), asteroid.getPosX(), asteroid.getPosY(), this);

        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        new_pos_x = me.getX();
        new_pos_y = me.getY();
        if (!dumbo.animal_is_clicked(new_pos_x, new_pos_y)) {
            mouse_clicked = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
