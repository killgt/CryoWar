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

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JTextField;

public class Chat {
	private final int MAX_MENSAJES = 7;

	private LinkedList<String> mensajes = new LinkedList<String>();
	private int x, y;
	private JTextField inputBarra;
	private String nombre;
	private EventManager event;
	private InterfaceSceneJuego interfaz;

	public Chat(JTextField component, EventManager event,
			InterfaceSceneJuego e, String nombre) {
		this.inputBarra = component;
		this.event = event;
		this.interfaz = e;
		this.nombre = nombre;
		addMessage("Bienvenido a la partida. Pulsa intro para conversar.");
		addMessage("Pulsa 'Q' para mostrar/ocultar la puntuacion.");

	}

	public void setPosicion(int x, int y) {

		this.x = x / 6 + 30;
		this.y = y - y / 6 - 30;
		inputBarra.setLocation(this.x, this.y + 170);
	}

	public void addMessage(String texto) {
		mensajes.addFirst(texto);
		if (mensajes.size() > MAX_MENSAJES)
			mensajes.remove(MAX_MENSAJES);

	}

	public void mostrarBarra() {
		inputBarra.setVisible(true);
		inputBarra.requestFocus();
	}

	public void ocultarBarra() {
		interfaz.recuperarFocus();
		inputBarra.setVisible(false);
		inputBarra.setText("");
	}

	public void enviarMensaje() {

		if (inputBarra.getText().length() > 0)
			event.enviarMensaje("[" + nombre + "]: " + inputBarra.getText());
		ocultarBarra();

	}

	public void dibujar(Graphics g) {
		g.setColor(Color.WHITE);
		for (int a = 0; a < mensajes.size(); a++) {

			g.drawString(mensajes.get(a), x, y - a * 20 + 150);
		}
	}

}
