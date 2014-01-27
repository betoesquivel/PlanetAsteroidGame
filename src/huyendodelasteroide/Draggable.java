/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyendodelasteroide;

// Click and Drag an object

import java.awt.Rectangle;

// Author: Daniel Shiffman
// Source: http://www.learningprocessing.com/examples/chapter-5/draggable-shape/
// A class for a draggable thing
class Draggable extends Rectangle{

    boolean dragging = false; // Is the object being dragged?
    boolean rollover = false; // Is the mouse over the ellipse?

    int posX, posY, width, height;          // Location and size
    int offsetX, offsetY; // Mouseclick offset
    Rectangle mRectangle;
    
    public Draggable(int x, int y, int w, int h) {
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
        
        mRectangle = new Rectangle(this.posX,this.posY, this.height, this.width);
        
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isRollover() {
        return rollover;
    }

    public void setRollover(boolean rollover) {
        this.rollover = rollover;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public Rectangle getmRectangle() {
        return mRectangle;
    }

    public void setmRectangle(Rectangle mRectangle) {
        this.mRectangle = mRectangle;
    }

    
    void startDragging(int mx, int my) {
        dragging = true;
        // If so, keep track of relative location of click to corner of rectangle
        offsetX = posX - mx;
        offsetY = posY - my;
    }

    /**
     * Cheightecks if a point is clicked inside a rectangle...
     * Theighten it sets object on a clicked state.
     * @param mx
     * @param my 
     */
    void isClicked(int mx, int my) {
        if (mRectangle.contains(mx,my)) {
            startDragging(mx, my);
        }
    }

    // Stop dragging
    void stopDragging() {
        dragging = false;
    }

    // Drag the rectangle
    void drag(int mx, int my) {
        if (dragging) {
            posX = mx + offsetX;
            posY = my + offsetY;
        }
    }

}
