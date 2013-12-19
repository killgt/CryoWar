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
package es.killgt.game.escenas.jugando;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import es.killgt.engine.GameAction;
import es.killgt.engine.InputManager;

public class InputManagerJuego extends InputManager {

	private InterfaceSceneJuego juego;
	private GameAction[] accionesTeclado = new GameAction[200]; // Alojamos 600
																// acciones
	private GameAction[] accionesRaton = new GameAction[10];
	public static final byte SALIR = 0;
	public static final byte DISPARAR = 1;
	public static final byte AVANZAR = 2;
	public static final byte CAMBIAR_ARMA = 5;
	public static final byte FRENAR = 6;
	public static final byte MOSTRAR_BARRA = 7;
	public static final byte CAMBIAR_CAMARA = 100;
	public static final byte VER_PUNTUACION = 101;
	public static final byte SCREENSHOT = 120;

	/**
	 * En el constructor de este input manager se añadiran todas las
	 * accionesTeclado disponibles;
	 */
	public void bindearAccionTeclado(int codigoBoton, GameAction accion) {
		accionesTeclado[codigoBoton] = accion;
	}

	public void bindearAccionRaton(int codigoBoton, GameAction accion) {
		accionesRaton[codigoBoton] = accion;
	}

	public GameAction[] getaccionesTeclado() {
		return accionesTeclado;
	}

	public GameAction[] getaccionesRaton() {
		return accionesRaton;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JTextField)
			juego.getChat().enviarMensaje();

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < 201)
			if (accionesTeclado[e.getKeyCode()] != null)
				accionesTeclado[e.getKeyCode()].pulsar();
		e.consume();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < 201)
			if (accionesTeclado[e.getKeyCode()] != null)
				accionesTeclado[e.getKeyCode()].soltar();
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		e.consume();

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent e) {
		accionesRaton[e.getButton()].pulsar();
		e.consume();
	}

	public void mouseReleased(MouseEvent e) {
		accionesRaton[e.getButton()].soltar();
		e.consume();
	}

	public void setJuego(InterfaceSceneJuego juego) {
		this.juego = juego;
	}

}
