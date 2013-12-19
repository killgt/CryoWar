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
import java.awt.Rectangle;
import java.util.Vector;

import es.killgt.engine.GameActor;
import es.killgt.game.actors.Recolectable;
import es.killgt.game.weapons.bullets.Bala;

public class MiniMap {
	private Vector<GameActor> actores;
	private Vector<GameActor> enemigos;
	private Rectangle miniMap;
	private final int MARGEN = 10;
	private final Color color = new Color(1f, 1f, 1f, 0.1f);
	private int worldSize;

	public MiniMap(Vector<GameActor> actores, Vector<GameActor> enemigos,
			GameSceneJuego escena) {
		super();
		this.actores = actores;
		this.enemigos = enemigos;
		int ancho = escena.getI().getScreenWidth() / 6;
		int alto = escena.getI().getScreenHeigth() / 6;
		worldSize = GameSceneJuego.TAMANO_UNIVERSO * 2;
		miniMap = new Rectangle(MARGEN, escena.getI().getScreenHeigth() - alto
				- MARGEN, ancho, alto);
	}

	public void dibujar(Graphics g) {
		g.setColor(color);
		g.fillRect((int) miniMap.getX(), (int) miniMap.getY(), (int) miniMap
				.getWidth(), (int) miniMap.getHeight());
		g.setColor(Color.WHITE);
		g.drawRect((int) miniMap.getX(), (int) miniMap.getY(), (int) miniMap
				.getWidth(), (int) miniMap.getHeight());

		int x, y;

		for (GameActor actor : actores) {
			x = (int) (actor.getX() / (worldSize / miniMap.getWidth()) + miniMap
					.getCenterX());
			y = (int) (actor.getY() / (worldSize / miniMap.getHeight()) + miniMap
					.getCenterY());
			if (actor instanceof Bala) {
				g.setColor(Color.red);
				if (!((Bala) actor).isDestruyendose())
					g.drawRect(x, y, 1, 1);
			} else if (actor instanceof Recolectable) {
				g.setColor(Color.pink);
				g.drawOval(x, y, 4, 4);
			} else {
				g.setColor(Color.white);
				g.fillOval(x, y, 4, 4);
			}

		}
		for (GameActor actor : enemigos) {
			x = (int) (actor.getX() / (worldSize / miniMap.getWidth()) + miniMap
					.getCenterX());
			y = (int) (actor.getY() / (worldSize / miniMap.getHeight()) + miniMap
					.getCenterY());
			g.setColor(Color.orange);
			g.fillRect(x, y, 4, 4);

		}
	}
}
