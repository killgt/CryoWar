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

public class Explosion {
	private long maxTime, minTime;
	private long aliveTime;
	private Particula[] particulas;
	boolean explotando;
	private float minSpeed, maxSpeed;

	public Explosion(int numeroParticulas, long tiempoMinimo,
			long tiempoMaximo, float minSpeed, float maxSpeed) {
		maxTime = tiempoMaximo;
		minTime = tiempoMinimo;
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
		particulas = new Particula[numeroParticulas];
		for (int a = 0; a < particulas.length; a++)
			particulas[a] = new Particula();
	}

	public void explotar(float x, float y) {
		aliveTime = 0;
		explotando = true;
		float angulo = 0;
		Particula.color = Particula.original.brighter();
		for (int a = 0; a < particulas.length; a++, angulo += 6.14 / (float) particulas.length) {
			particulas[a].shoot(x, y, angulo, (long) (Math.random()
					* (maxTime - minTime) + minTime), (float) Math.random()
					* (maxSpeed - minSpeed) + minSpeed);
		}
	}

	public void actualizar(long tiempoTranscurrido) {
		if (maxTime > aliveTime && explotando) {
			aliveTime += tiempoTranscurrido;
			for (int a = 0; a < particulas.length; a++)
				particulas[a].actualizar(tiempoTranscurrido);
			if (Math.random() * 50 > 45)
				Particula.color = Particula.color.darker();
			else if (((float) aliveTime / (float) minTime > 0.9f)
					&& Math.random() * 5 > 3)
				Particula.color = Particula.color.darker();
		} else
			explotando = false;
	}

	public void dibujar(Graphics g, int offsetx, int offsety) {
		if (explotando) {
			g.setColor(Particula.color);
			for (int a = 0; a < particulas.length; a++)
				particulas[a].dibujar(g, offsetx, offsety);
		}
	}
}

class Particula {
	float x, y;
	float movx, movy;
	long maxAliveTime;
	long aliveTime;
	boolean alive;
	public static Color original = new Color(1f, 0.5f, 0f);
	public static Color color;

	public Particula() {
		super();

	}

	public void shoot(float x, float y, float angulo, long maxAliveTime,
			float speed) {
		this.x = x;
		this.y = y;
		this.movx = (float) Math.cos(angulo) * speed;
		this.movy = (float) Math.sin(angulo) * speed;
		this.maxAliveTime = maxAliveTime;
		aliveTime = 0;
		alive = true;
	}

	public void actualizar(long tiempoTranscurrido) {
		aliveTime += tiempoTranscurrido;
		if (aliveTime > maxAliveTime)
			alive = false;
		x += movx;
		y += movy;

	}

	public void dibujar(Graphics g, int offsetx, int offsety) {
		if (alive) {
			g.drawLine((int) (x - offsetx), (int) (y - offsety), (int) (x
					- offsetx + movx * 5), (int) (y - offsety + movy * 5));
		}
	}
}
