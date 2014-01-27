/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package huyendodelasteroide;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

/**
 *
 * @author ppesq
 */
public class HuyendoDelAsteroide extends Applet implements Runnable, MouseListener{
   /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        direccion = 4;

        this.setSize(500, 500);

        int posX = (int) (Math.random() * (getWidth() / 4));    // posicion en x es un cuarto del applet
        int posY = (int) (Math.random() * (getHeight() / 4));    // posicion en y es un cuarto del applet
        URL eURL = this.getClass().getResource("/images/elefante.gif");
        dumbo = new Elefante(posX, posY, Toolkit.getDefaultToolkit().getImage(eURL));
        int posrX = (int) (Math.random() * (getWidth() / 4)) + getWidth() / 2;    //posision x es tres cuartos del applet
        int posrY = (int) (Math.random() * (getHeight() / 4)) + getHeight() / 2;    //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("/images/mouse.gif");
        raton = new Raton(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
        raton.setPosX(raton.getPosX() - raton.getAncho());
        raton.setPosY(raton.getPosY() - raton.getAlto());
        setBackground(Color.yellow);
        addKeyListener(this);
        addMouseListener(this);
        //Se cargan los sonidos.
        URL eaURL = this.getClass().getResource("/sounds/elephant.wav");
        sonido = getAudioClip(eaURL);
        URL raURL = this.getClass().getResource("/sounds/mice.wav");
        rat = getAudioClip(raURL);
        URL baURL = this.getClass().getResource("/sounds/Explosion.wav");
        bomb = getAudioClip(baURL);
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
     * Metodo usado para actualizar la posicion de objetos elefante y raton.
     *
     */
    public void updateCharacters() {
        if (mouse_clicked) {
            dumbo.setPosX(new_pos_x);
            dumbo.setPosY(new_pos_y);
            mouse_clicked = false;
        }
        //Dependiendo de la direccion del elefante es hacia donde se mueve.
        switch (direccion) {
            case 1: {
                dumbo.setPosY(dumbo.getPosY() - 1);
                break;    //se mueve hacia arriba
            }
            case 2: {
                dumbo.setPosY(dumbo.getPosY() + 1);
                break;    //se mueve hacia abajo
            }
            case 3: {
                dumbo.setPosX(dumbo.getPosX() - 1);
                break;    //se mueve hacia izquierda
            }
            case 4: {
                dumbo.setPosX(dumbo.getPosX() + 1);
                break;    //se mueve hacia derecha	
            }
        }

        // genero un numero al azar en incx e incy de -5 a 5
        incX = ((int) (Math.random() * (MAX - MIN))) + MIN;
        incY = ((int) (Math.random() * (MAX - MIN))) + MIN;

        //Acutalizo la posicion del raton
        //raton.setPosX(raton.getPosX() + incX);
        //raton.setPosY(raton.getPosY() + incY);
        raton.follow(dumbo);

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton con
     * las orillas del <code>Applet</code>.
     */
    public void checkCollision() {
        //Colision del elefante con el Applet dependiendo a donde se mueve.
        switch (direccion) {
            case 1: { //se mueve hacia arriba con la flecha arriba.
                if (dumbo.getPosY() < 0) {
                    direccion = 2;
                    sonido.play();
                }
                break;
            }
            case 2: { //se mueve hacia abajo con la flecha abajo.
                if (dumbo.getPosY() + dumbo.getAlto() > getHeight()) {
                    direccion = 1;
                    sonido.play();
                }
                break;
            }
            case 3: { //se mueve hacia izquierda con la flecha izquierda.
                if (dumbo.getPosX() < 0) {
                    direccion = 4;
                    sonido.play();
                }
                break;
            }
            case 4: { //se mueve hacia derecha con la flecha derecha.
                if (dumbo.getPosX() + dumbo.getAncho() > getWidth()) {
                    direccion = 3;
                    sonido.play();
                }
                break;
            }
        }

        //checa colision con el applet
        if (raton.getPosX() + raton.getAncho() > getWidth()) {
            raton.setPosX(raton.getPosX() - incX);
            rat.play();
        }
        if (raton.getPosX() < 0) {
            raton.setPosX(raton.getPosX() - incX);
            rat.play();
        }
        if (raton.getPosY() + raton.getAlto() > getHeight()) {
            raton.setPosY(raton.getPosY() - incY);
            rat.play();
        }
        if (raton.getPosY() < 0) {
            raton.setPosY(raton.getPosY() - incY);
            rat.play();
        }

        //Colision entre objetos
        if (dumbo.intersecta(raton)) {
            bomb.play();    //sonido al colisionar
            //El elefante se mueve al azar en la mitad izquierda del applet.
            dumbo.setPosX((int) (Math.random() * (getWidth() / 2 - dumbo.getAncho())));
            dumbo.setPosY((int) (Math.random() * (getHeight() / 2 - dumbo.getAlto())));
            //El raton se mueve al azar en la mitad derecha del appler.
            raton.setPosX((int) (Math.random() * getWidth() / 2) + getWidth() / 2 - raton.getAncho());
            raton.setPosY((int) (Math.random() * getHeight() / 2) + getHeight() / 2 - raton.getAlto());
            raton.accelerate();
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
        if (dumbo != null && raton != null) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(dumbo.getImagenI(), dumbo.getPosX(), dumbo.getPosY(), this);
            g.drawImage(raton.getImagenI(), raton.getPosX(), raton.getPosY(), this);

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
