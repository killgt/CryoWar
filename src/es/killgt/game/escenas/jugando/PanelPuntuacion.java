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

import es.killgt.game.red.paquetes.PaquetePuntuacion;

public class PanelPuntuacion {
	private int x, y, w, h;
	private Color color = new Color(0.1f, 0.1f, 0.8f, 0.3f);
	private boolean activo;
	private String[] nombres;
	private int[] muertes;
	private int[] asesinatos;
	private EventManager e;

	public PanelPuntuacion() {
		nombres = new String[0];
		muertes = new int[0];
		asesinatos = new int[0];
	}

	public void setPosicion(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

	}

	public void setEvent(EventManager e) {
		this.e = e;
	}

	public void dibujar(Graphics g) {
		g.setColor(Color.gray);
		g.drawRect(x, y, w, h);
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.setColor(Color.green);
		g.drawString("Nombre", x + w / 8, y + 25);
		g.drawString("Asesinatos", x + w / 3, y + 25);
		g.drawString("Muertes", x + (int) (w / 1.5), y + 25);
		for (int a = 0; a < nombres.length; a++) {
			g.setColor(Color.red);
			g.drawString(nombres[a], x + w / 8, y + a * 40 + 50);
			g.drawString(String.valueOf(asesinatos[a]), x + w / 3, y + a * 40
					+ 50);
			g.drawString(String.valueOf(muertes[a]), x + (int) (w / 1.5), y + a
					* 40 + 50);
			g.setColor(Color.white);
			g.drawLine(x + 20, y + a * 40 + 60, x + w - 10, y + a * 40 + 60);
		}
	}

	public synchronized void setPuntuacion(PaquetePuntuacion p) {

		int[] posiciones = new int[96];

		int contador = 0;
		for (int a = 0; a < p.puntuacion.length(); a++)
			if (p.puntuacion.charAt(a) == ';') {
				posiciones[contador] = a;
				contador++;
			}

		++contador;
		nombres = new String[contador / 3];
		muertes = new int[contador / 3];
		asesinatos = new int[contador / 3];

		if (posiciones[0] > 0) {
			nombres[0] = p.puntuacion.substring(0, posiciones[0]);
			asesinatos[0] = Integer.valueOf(p.puntuacion.substring(
					posiciones[0] + 1, posiciones[1]));
			muertes[0] = Integer.valueOf(p.puntuacion.substring(
					posiciones[1] + 1, posiciones[2]));
		}

		int a = 2;
		int num = 1;
		while (posiciones[a + 2] > 0) {
			nombres[num] = p.puntuacion.substring(posiciones[a] + 1,
					posiciones[a + 1]);
			asesinatos[num] = Integer.valueOf(p.puntuacion.substring(
					posiciones[a + 1] + 1, posiciones[a + 2]));
			muertes[num] = Integer.valueOf(p.puntuacion.substring(
					posiciones[a + 2] + 1, posiciones[a + 3]));
			num++;
			a += 3;
		}
	}

	public void trigger() {
		activo = !activo;
		if (activo)
			e.solicitarPuntuacion();
	}

	public boolean isMostrandose() {
		return activo;
	}
}
