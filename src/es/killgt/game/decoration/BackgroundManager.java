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
import java.util.ArrayList;

import es.killgt.engine.GameObject;
import es.killgt.engine.ResourceManager;
import es.killgt.game.escenas.jugando.GameSceneJuego;

public class BackgroundManager {
	private ArrayList<Estrella> estrellas;
	private ArrayList<Planeta> planetas;
	private GameSceneJuego game;
	private int totalEstrellas;
	private ResourceManager r;

	public BackgroundManager(ResourceManager r, int nEstrellas, int nPlanetas,
			GameSceneJuego g) {
		game = g;
		this.r = r;

		estrellas = new ArrayList<Estrella>(nEstrellas);
		planetas = new ArrayList<Planeta>(nPlanetas);
		totalEstrellas = nEstrellas;
		for (int a = 0; a < nEstrellas; a++)
			estrellas.add(new Estrella(r, (float) (Math.random() * game.getI()
					.getScreenWidth())
					- game.getI().getScreenWidth() / 2,
					(float) (Math.random() * game.getI().getScreenHeigth())
							- game.getI().getScreenHeigth() / 2, (float) Math
							.random() * 1.5f));
		for (int a = 0; a < nPlanetas; a++)
			planetas.add(new Planeta(r, (float) (Math.random()
					* GameSceneJuego.TAMANO_UNIVERSO * 2)
					- GameSceneJuego.TAMANO_UNIVERSO, (float) (Math.random()
					* GameSceneJuego.TAMANO_UNIVERSO * 2)
					- GameSceneJuego.TAMANO_UNIVERSO));
	}

	public void dibujar(Graphics g, int offsetx, int offsety) {

		for (GameObject estrella : estrellas)
			estrella.dibujar(g, offsetx, offsety);
		for (GameObject planeta : planetas)
			planeta.dibujar(g, offsetx, offsety);
	}

	public void actualizar() {
		int anchoPantalla = game.getI().getScreenWidth();
		int altoPantalla = game.getI().getScreenHeigth();
		for (Estrella estrella : estrellas)
			estrella.think(game.getOffsetx(), game.getOffsety(), anchoPantalla,
					altoPantalla);
	}

	public void regenerar() {
		estrellas.removeAll(estrellas);
		for (int a = 0; a < totalEstrellas; a++)
			estrellas.add(new Estrella(r, (float) (game.getOffsetx() + Math
					.random()
					* game.getI().getScreenWidth())
					- game.getI().getScreenWidth() / 2, game.getOffsety()
					+ (float) (Math.random() * game.getI().getScreenHeigth())
					- game.getI().getScreenHeigth() / 2,
					(float) Math.random() * 1.3f));

	}
}

// =============================================================================================
class Estrella extends GameObject {
	public final int MARGEN_EXTERIOR = 200;
	private float distancia;

	public Estrella(ResourceManager r, float x, float y, float distancia) {
		super(r, x, y);
		this.distancia = distancia;
	}

	public void dibujar(Graphics g, int offsetx, int offsety) {
		g.setColor(Color.white);
		// g.drawLine((int)(x - offsetx*distancia), (int)(y -
		// offsety*distancia),(int)(x - offsetx*distancia ), (int) ( y -
		// offsety*distancia ) );
		if (distancia > 0.8f)
			g.fillRect((int) (x - offsetx * distancia), (int) (y - offsety
					* distancia), 2, 2);
		else
			g.fillRect((int) (x - offsetx * distancia), (int) (y - offsety
					* distancia), 1, 1);
	}

	/**
	 * Comprobamos si esta fuera de los margenes actuales, si es asi, lo
	 * generamos fuera de pantalla pero dentro de los margenes (500px)
	 * 
	 * @param offsetx
	 * @param offsety
	 */

	public void think(int offsetx, int offsety, int anchoPantalla,
			int altoPantalla) {
		if (x > offsetx * distancia + MARGEN_EXTERIOR + anchoPantalla)
			setX((float) (offsetx * distancia - (Math.random() * MARGEN_EXTERIOR)));
		else if (x < offsetx * distancia - MARGEN_EXTERIOR)
			setX((float) ((Math.random() * MARGEN_EXTERIOR) + offsetx
					* distancia + anchoPantalla));

		if (y > altoPantalla + offsety * distancia + MARGEN_EXTERIOR)
			setY((float) (offsety * distancia - (Math.random() * MARGEN_EXTERIOR)));
		else if (y < offsety * distancia - MARGEN_EXTERIOR)// ariba
			setY((float) ((Math.random() * MARGEN_EXTERIOR) + offsety
					* distancia + altoPantalla));
	}

	public void think(long tiempoTranscurrido) {

	}

}

// =============================================================================================
class Planeta extends GameObject {

	public Planeta(ResourceManager r, float x, float y) {
		super(r, x, y);
		currentSprite.addFrame("/images/decorado/planetas/planet"
				+ (int) (Math.random() * 8) + ".gif");
		currentSprite.setDuracionFrame(1);
	}

	public void dibujar(Graphics g, int offsetx, int offsety) {
		currentSprite.dibujar(g, (int) (x - offsetx * 1.7),
				(int) (y - offsety * 1.7));
	}

	public void think(long tiempoTranscurrido) {
	}

}
