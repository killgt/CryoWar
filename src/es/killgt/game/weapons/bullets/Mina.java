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

public class Mina extends Bala {

	public Mina(ResourceManager r, GameActor lanzador) {
		super(r, lanzador);
		currentSprite.addFrame("/images/armas/mina.gif");
		currentSprite.addFrame("/images/armas/mina2.gif");
		currentSprite.setDuracionFrame(300);
		cargarExplosionGenerica();
		tipo = 2;
		damage = 30;
	}

	public void onColision(GameActor actor) {
		if (getLanzador() != actor) {
			this.destruir();
		}

	}

	public void update(long tiempoTranscurrido) {

	}

}
