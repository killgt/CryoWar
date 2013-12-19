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

import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;

final public class BalaLaser extends Bala {
	private final float SPEED = 1.5f;

	public BalaLaser(ResourceManager r, GameActor lanzador) {
		super(r, lanzador);
		currentSprite.addFrame("/images/armas/laser.gif");
		currentSprite.setDuracionFrame(1);
		tipo = 0;
		angulo = lanzador.getAngulo();
		movx = (float) Math.cos(angulo) * SPEED;
		movy = (float) Math.sin(angulo) * SPEED;
		damage = 5;
		cargarExplosionGenerica();
	}

	public void onColision(GameActor actor) {
		if (getLanzador() != actor) {
			this.destruir();
		}
	}

	public void update(long tiempoTranscurrido) {

	}

}
