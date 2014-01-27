package huyendodelasteroide;

/**
 * @(#)Inteligencia.java
 *
 * Scores Applet application
 *
 * @author Antonio Mejorado
 * @version 1.00 2008/6/19
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

public class Inteligencia extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int direccion;    // Direccion del elefante
    private int incX;    // Incremento en x
    private int incY;    // Incremento en y
    private int vidas;    // vidas del elefante.
    private final int MIN = -5;    //Rango minimo al generar un numero al azar.
    private final int MAX = 6;    //Rango maximo al generar un numero al azar.
    private Image dbImage;    // Imagen a proyectar
    private Image gameover;    //Imagen a desplegar al acabar el juego.	 
    private Graphics dbg;	// Objeto grafico
    private AudioClip sonido;    // Objeto AudioClip
    private AudioClip rat;    // Objeto AudioClip
    private AudioClip bomb;    //Objeto AudioClip 
    private Elefante dumbo;    // Objeto de la clase Elefante
    private Raton raton;    //Objeto de la clase Raton

    private boolean object_clicked;

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        vidas = 3;    // Le asignamos un valor inicial a las vidas
        direccion = 4;    // Direccion hacia la derecha
        this.setSize(500, 500);
        int posX = (int) (Math.random() * (getWidth() / 4));    // posicion en x es un cuarto del applet
        int posY = (int) (Math.random() * (getHeight() / 4));    // posicion en y es un cuarto del applet
        URL eURL = this.getClass().getResource("/images/jupiter.gif");
        dumbo = new Elefante(posX, posY, Toolkit.getDefaultToolkit().getImage(eURL));
        int posrX = (int) (Math.random() * (getWidth() / 4)) + getWidth() / 2;    //posision x es tres cuartos del applet
        int posrY = (int) (Math.random() * (getHeight() / 4)) + getHeight() / 2;    //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("/images/asteroid.gif");
        raton = new Raton(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
        raton.setPosX(raton.getPosX() - raton.getAncho());
        raton.setPosY(raton.getPosY() - raton.getAlto());
        setBackground(Color.black);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        //Se cargan los sonidos.
        URL eaURL = this.getClass().getResource("elephant.wav");
        sonido = getAudioClip(eaURL);
        URL raURL = this.getClass().getResource("mice.wav");
        rat = getAudioClip(raURL);
        URL baURL = this.getClass().getResource("Explosion.wav");
        bomb = getAudioClip(baURL);
        URL goURL = this.getClass().getResource("gameover.jpg");
        gameover = Toolkit.getDefaultToolkit().getImage(goURL);
        object_clicked = false;
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
        while (vidas > 0) {
            actualiza();
            checaColision();

            // Se actualiza el <code>Applet</code> repintando el contenido.
            repaint();

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
    public void actualiza() {
        //Dependiendo de la direccion del elefante es hacia donde se mueve.

        //Acutalizo la posicion del raton
        if (dumbo.getPosX() > raton.getPosX()) {
            incX = 1;
            raton.setPosX(raton.getPosX() + incX);
        } else {
            incX = -1;
            raton.setPosX(raton.getPosX() + incX);
        }

        if (dumbo.getPosY() > raton.getPosY()) {
            incY = 1;
            raton.setPosY(raton.getPosY() + incY);
        } else {
            incY = -1;
            raton.setPosY(raton.getPosY() + incY);
        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton con
     * las orillas del <code>Applet</code>.
     */
    public void checaColision() {
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
            if (vidas > 0) {
                vidas--;
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
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {    //Presiono flecha arriba
            direccion = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {    //Presiono flecha abajo
            direccion = 2;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha izquierda
            direccion = 3;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha derecha
            direccion = 4;
        }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {

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
        if (vidas > 0) {
            if (dumbo != null && raton != null) {
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(dumbo.getImagenI(), dumbo.getPosX(), dumbo.getPosY(), this);
                g.drawImage(raton.getImagenI(), raton.getPosX(), raton.getPosY(), this);

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        } else {
            g.drawImage(gameover, 0, 0, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        dumbo.setMx(me.getX());
        dumbo.setMy(me.getY());
        object_clicked = dumbo.animal_is_clicked();
        if (object_clicked) {
            dumbo.calculateOffset();
        }
        me.consume();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        object_clicked = false;
        me.consume();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (object_clicked) {
            dumbo.drag(me);
        }
        me.consume();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
