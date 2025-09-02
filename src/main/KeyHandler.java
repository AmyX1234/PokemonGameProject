package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {//the listener interface for receiving keyboard events

    public boolean upPressed, downPressed, leftPressed, rightPressed, switchCharacterPressed;


    public void keyTyped(KeyEvent e) {


    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //returns the int keycode associated with the key in this event

        if (code == KeyEvent.VK_W) { //if the key is w then...
            upPressed = true;
        } if (code == KeyEvent.VK_S) { //if the key is s then...
            downPressed = true;
        } if (code == KeyEvent.VK_A) { //if the key is a then...
            leftPressed = true;
        } if (code == KeyEvent.VK_D) { //if the key is d then...
            rightPressed = true;
        } if (code == KeyEvent.VK_V){
            switchCharacterPressed = true;
        }

    }

    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { //if the key is w then...
            upPressed = false;
        } if (code == KeyEvent.VK_S) { //if the key is s then...
            downPressed = false;
        } if (code == KeyEvent.VK_A) { //if the key is a then...
            leftPressed = false;
        } if (code == KeyEvent.VK_D) { //if the key is d then...
            rightPressed = false;
        } if (code == KeyEvent.VK_V){
            switchCharacterPressed = false;
        }
    }
}