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

import java.awt.Graphics;

import java.util.Vector;

import es.killgt.engine.CoreInterface;
import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.engine.Sprite;

/**
 * Esta clase se encargará de animar el fondo de los menus.
 * 
 * @author Kill
 * 
 */
public class BackgroundMenuManager {
	@SuppressWarnings("unused")
	private ResourceManager recursos;
	private Sprite fondo;
	private Sprite barraSuperior;
	private CoreInterface core;
	private Vector<GameActor> actores = new Vector<GameActor>(7);

	public BackgroundMenuManager(ResourceManager recursos,
			CoreInterface interfaz) {
		this.recursos = recursos;
		this.core = interfaz;
		fondo = new Sprite(recursos);
		fondo.addFrame("/images/menu/fondo.gif");
		fondo.setDuracionFrame(1);

		barraSuperior = new Sprite(recursos);
		barraSuperior.addFrame("/images/menu/barrita.png");
		barraSuperior.setDuracionFrame(1);

		for (int a = 0; a < actores.capacity(); a++)
			actores.add(new NaveDecoracion(recursos, (int) (Math.random()
					* interfaz.getScreenWidth() * 2), (int) (Math.random()
					* interfaz.getScreenHeigth() * 2),
					(int) (Math.random() * 4), interfaz, this));
	}

	public void actualizar(long tiempoTranscurrido) {
		for (GameActor a : actores)
			a.actualizar(tiempoTranscurrido);
	}

	public void dibujar(Graphics g) {
		int width = 1 + core.getScreenWidth() / fondo.getWidth();
		int heigth = 1 + core.getScreenHeigth() / fondo.getHeight();
		for (int a = 0; a < heigth; a++)
			for (int b = 0; b < width; b++)
				fondo.dibujar(g, b * fondo.getWidth(), a * fondo.getHeight());
		for (GameActor a : actores)
			a.dibujar(g, 0, 0);

		barraSuperior.dibujar(g, core.getScreenWidth() / 2
				- barraSuperior.getWidth() / 2,
				core.getScreenHeigth() / 2 - 335);
	}
}
