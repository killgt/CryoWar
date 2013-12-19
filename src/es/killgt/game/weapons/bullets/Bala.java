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
package es.killgt.game.weapons.bullets;

import java.awt.Rectangle;

import es.killgt.engine.CallBack;
import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.engine.Sprite;
import es.killgt.engine.Utils;
import es.killgt.game.escenas.jugando.GameSceneJuego;

public abstract class Bala extends GameActor implements CallBack {
	private Sprite explosion;
	private GameActor lanzador;
	private boolean destruyendose;
	private final static int margen = 1000;
	protected int damage;
	public int tipo;

	public Bala(ResourceManager r, GameActor lanzador) {
		super(r, lanzador.getCenterX(), lanzador.getCenterY(),
				(short) Utils.azar.nextInt(30000));
		this.lanzador = lanzador;
		explosion = new Sprite(r, this);
	}

	public Bala setID(short id) {
		this.ID_ACTOR = id;
		return this;
	}

	public void cargarExplosionGenerica() {
		for (int a = 1; a < 50; a++)
			explosion.addFrame("/images/armas/explosion/explosion00" + a
					+ ".png");
		explosion.setDuracionFrame(20);

	}

	/**
	 * Ponemos el frame de su explosion y cuando acabe llamamos al callback
	 */
	public void destruir() {
		currentSprite = explosion;
		destruyendose = true;
	}

	public Rectangle getBounds() {
		if (destruyendose)
			return rectanguloNulo;
		return super.getBounds();
	}

	public void llamada() {
		this.eliminar();
	}

	public void think(long tiempoTranscurrido) {
		if (x > GameSceneJuego.TAMANO_UNIVERSO + margen
				|| x < -GameSceneJuego.TAMANO_UNIVERSO - margen
				|| y > GameSceneJuego.TAMANO_UNIVERSO + margen
				|| y < -GameSceneJuego.TAMANO_UNIVERSO - margen)
			this.destruir();
		if (!destruyendose)
			update(tiempoTranscurrido);
	}

	public abstract void update(long tiempoTranscurrido);

	public boolean isDestruyendose() {
		return destruyendose;
	}

	public GameActor getLanzador() {
		return lanzador;
	}

	public final static Rectangle rectanguloNulo = new Rectangle(0, 0, 0, 0);

	public GameActor getAutor() {
		return lanzador;
	}

	public int getDamage() {
		return damage;
	}
}
