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
package es.killgt.game.decoration;

import java.awt.Color;
import java.awt.Graphics;

public class CirculoExpandible {
	private float maxRadio;
	private float radio;
	private long maxLifeTime;
	private long aliveTime;

	private boolean reproduciendose;

	Color color = new Color(255, 64, 64);

	public CirculoExpandible(int maxRadio, long maxLifeTime) {
		super();
		this.maxRadio = maxRadio;
		this.maxLifeTime = maxLifeTime;
		radio = maxRadio / 100;
	}

	public void actualizar(long tiempoTranscurrido) {
		if (reproduciendose) {
			aliveTime += tiempoTranscurrido;
			if (aliveTime > maxLifeTime)
				reproduciendose = false;

			if (((float) aliveTime) / ((float) maxLifeTime) > 0.3f
					&& Math.random() * 4 > 3)
				color = color.darker();

			if (radio < maxRadio)
				radio += tiempoTranscurrido * (maxRadio / maxLifeTime);

		}

	}

	public void dibujar(Graphics g, int x, int y) {
		if (reproduciendose) {
			g.setColor(color);
			g.drawOval(x - (int) radio, y - (int) radio, (int) radio * 2,
					(int) radio * 2);
		}
	}

	public void play() {
		reproduciendose = true;
		reset();
	}

	public void reset() {
		aliveTime = 0;
		radio = 0;
		color = new Color(255, 64, 64);
	}

}
