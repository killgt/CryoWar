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
package es.killgt.game.weapons;

import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.game.escenas.jugando.EventManager;
import es.killgt.game.escenas.jugando.InterfaceSceneJuego;
import es.killgt.game.weapons.bullets.Mina;

public class SueltaMinas extends Arma {

	public SueltaMinas(InterfaceSceneJuego i, ResourceManager r,
			EventManager red) {
		super(i, r, red);
		this.tiempoRepeticion = 1000;
		this.municionInicial = Municiones.MINAS;
		this.municion = municionInicial;
		this.speed = 0.0f;
		miniatura.addFrame("/images/armas/miniatura_mina.gif");
		miniatura.setDuracionFrame(1);
	}

	public void disparar(GameActor lanzador) {
		if (disparable()) {
			Mina m = new Mina(r, lanzador);
			i.addGameActor(m);
			ultimoDisparo = 0;
			municion--;
			red.enviarDisparo(m);
		}
	}

}
