/*******************************************************************************
 * Copyright (c) 2009 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Agustin Rodr�guez killgt@gmail.com
 ******************************************************************************/
package es.killgt.game.escenas.menunave;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import es.killgt.engine.InputManager;

public class InputManagerMenuNaves extends InputManager {

	private GameSceneMenuNaves menu;

	public InputManagerMenuNaves() {

	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {
			JButton boton = (JButton) e.getSource();

			if (boton.getToolTipText().equalsIgnoreCase("Nave 0"))
				menu.setCargarNave(0);
			else if (boton.getToolTipText().equalsIgnoreCase("Nave 1"))
				menu.setCargarNave(1);
			else if (boton.getToolTipText().equalsIgnoreCase("Nave 2"))
				menu.setCargarNave(2);
			else if (boton.getToolTipText().equalsIgnoreCase("Nave 3"))
				menu.setCargarNave(3);
			else if (boton.getToolTipText().equalsIgnoreCase("Salir")) {
				menu.stop();
				Thread.currentThread().stop();
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			menu.stop();
		else if (e.getKeyCode() == KeyEvent.VK_F12)
			menu.getI().getScreen().screenshot();
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
			menu.setCargarNave((int)(Math.random()*3));

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

	public void setMenu(GameSceneMenuNaves menu) {
		this.menu = menu;
	}

}
