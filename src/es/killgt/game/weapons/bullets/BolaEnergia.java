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

final public class BolaEnergia extends Bala {

	public BolaEnergia(ResourceManager r, GameActor lanzador, float movx,
			float movy) {
		super(r, lanzador);

		currentSprite.addFrame("/images/armas/energia.gif");
		currentSprite.setDuracionFrame(1);
		tipo = 1;
		this.movx = movx;
		this.movy = movy;
		cargarExplosionGenerica();
		damage = 8;
	}

	public void onColision(GameActor actor) {
		if (getLanzador() != actor && !(actor instanceof BolaEnergia))
			this.destruir();
	}

	public void update(long tiempoTranscurrido) {
	}

}
