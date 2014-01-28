package huyendodelasteroide;

/**
 * Clase Elefante
 *
 * @author Antonio Mejorado
 * @version 1.00 2008/6/13
 */
import java.awt.Image;
import java.awt.event.MouseEvent;

public class Planeta extends CuerpoCeleste {

    int x_offset, y_offset; //contains difference between mouse coordinates and object.
    int mx, my;

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>CuerpoCeleste</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto elefante.
     * @param posY es el <code>posiscion en y</code> del objeto elefante.
     * @param image es la <code>imagen</code> del objeto elefante.
     */
    public Planeta(int posX, int posY, Image image) {
        super(posX, posY, image);
    }

    public int getX_offset() {
        return x_offset;
    }

    public void setX_offset(int x_offset) {
        this.x_offset = x_offset;
    }

    public int getY_offset() {
        return y_offset;
    }

    public void setY_offset(int y_offset) {
        this.y_offset = y_offset;
    }

    public int getMx() {
        return mx;
    }

    public void setMx(int mx) {
        this.mx = mx;
    }

    public int getMy() {
        return my;
    }

    public void setMy(int my) {
        this.my = my;
    }

    public void calculateOffset() {
        x_offset = getPosX() - mx;
        y_offset = getPosY() - my;
    }

    /**
     * Checks if the animals coordinates
     *
     * @param obj
     * @return
     */
    public boolean animal_is_clicked() {
        return getPerimetro().contains(mx, my);
    }

    public void drag(MouseEvent e) {
        setPosX(getPosX() + (e.getX() - mx));
        setPosY(getPosY() + (e.getY() - my));
        setMx(e.getX());
        setMy(e.getY());
    }

}
