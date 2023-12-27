/*
* This code has been generated by the Rebel: a code generator for modern Java.
*
* Drop us a line or two at feedback@archetypesoftware.com: we would love to hear from you!
*/

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.*;

//Gestisce l'input dell'utente, come le pressioni dei tasti o i movimenti del mouse.
public class InputManager implements  KeyListener {
	public boolean upPressed, downPressed, leftPressed,rightPressed,spacePressed;

	GameEngine gp;
	Player pl;
	public InputManager(GameEngine gp) {
		this.gp=gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();

		//titolo
		if(gp.gameState == gp.titleState) {

			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum<0) {
					gp.ui.commandNum=1;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum>1) {
					gp.ui.commandNum=0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if(gp.ui.coommandNum==0) {
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum==1) {
					System.exit(0);
				}
			}
		}

		//fine
		if(gp.gameState == gp.endState) {
			if (code == KeyEvent.VK_SPACE) {
				gp.gameState=gp.titleState;
				gp.nextLevel();
			}
		}


		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		//tasto per la pausa
		if(code == KeyEvent.VK_P) {
			if(gp.gameState==gp.playState) {
				gp.gameState=gp.pauseState;
			}else if(gp.gameState==gp.pauseState) {
				gp.gameState=gp.playState;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();

		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}






}
