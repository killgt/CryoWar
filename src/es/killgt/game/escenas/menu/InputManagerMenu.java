/*******************************************************************************
 * Copyright (c) 2009 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Agustin Rodríguez killgt@gmail.com
 ******************************************************************************/
package es.killgt.game.escenas.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import es.killgt.engine.InputManager;

public class InputManagerMenu extends InputManager {

	GameSceneMenu menu;

	public void setInputManagerMenu(GameSceneMenu menu) {
		this.menu = menu;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (((JButton) arg0.getSource()).getText().equalsIgnoreCase("Jugar"))
			menu.cargarEscena = 0;
		else if (((JButton) arg0.getSource()).getText().equalsIgnoreCase(
				"Creditos"))
			menu.cargarEscena = 1;
		else
			menu.cargarEscena = 2;
		System.out.println("evento");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			if (!menu.creditos)
				menu.stop();
			else {
				menu.creditos = false;
				menu.cargarEscena = -1;
			}
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			menu.movimiento = 1;
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
			menu.cargarEscena = menu.opcionActual;
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			menu.movimiento = 2;
		else if (e.getKeyCode() == KeyEvent.VK_F12)
			menu.getI().getScreen().screenshot();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
