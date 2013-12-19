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
package es.killgt.game.actors;

import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;

public class Recolectable extends GameActor {
	public static enum TipoPowerUp {
		RecargaVida, MunicionLaser
	}

	private int cantidad;
	private TipoPowerUp tipo;

	public Recolectable(ResourceManager r, float x, float y, TipoPowerUp tipo,
			int cantidad, short ID) {
		super(r, x, y, ID);
		this.tipo = tipo;
		this.cantidad = cantidad;
		if (tipo == TipoPowerUp.MunicionLaser) {
			currentSprite.addFrame("/images/powerup/municion/frame1.png");
			currentSprite.addFrame("/images/powerup/municion/frame2.png");
			currentSprite.addFrame("/images/powerup/municion/frame3.png");
			currentSprite.addFrame("/images/powerup/municion/frame4.png");
			currentSprite.addFrame("/images/powerup/municion/frame5.png");
			currentSprite.addFrame("/images/powerup/municion/frame4.png");
			currentSprite.addFrame("/images/powerup/municion/frame3.png");
			currentSprite.addFrame("/images/powerup/municion/frame2.png");
		} else {
			currentSprite.addFrame("/images/powerup/vida/frame1.png");
			currentSprite.addFrame("/images/powerup/vida/frame2.png");
			currentSprite.addFrame("/images/powerup/vida/frame3.png");
			currentSprite.addFrame("/images/powerup/vida/frame4.png");
			currentSprite.addFrame("/images/powerup/vida/frame5.png");
			currentSprite.addFrame("/images/powerup/vida/frame4.png");
			currentSprite.addFrame("/images/powerup/vida/frame3.png");
			currentSprite.addFrame("/images/powerup/vida/frame2.png");
		}
		currentSprite.setDuracionFrame(100);

	}

	public void think(long tiempoTranscurrido) {

	}

	public void onColision(GameActor actor) {

	}

	public TipoPowerUp getTipo() {
		return tipo;
	}

	public int getCantidad() {
		return cantidad;
	}
}
