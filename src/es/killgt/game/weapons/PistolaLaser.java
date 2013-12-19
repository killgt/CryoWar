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
import es.killgt.engine.Sonido;

import es.killgt.game.escenas.jugando.EventManager;
import es.killgt.game.escenas.jugando.InterfaceSceneJuego;
import es.killgt.game.weapons.bullets.BalaLaser;

public class PistolaLaser extends Arma {
	Sonido s = new Sonido(r,"/sounds/laser.wav",false);
	public PistolaLaser(InterfaceSceneJuego i, ResourceManager r,
			EventManager red) {
		super(i, r, red);
		this.tiempoRepeticion = 200;
		this.municionInicial = Municiones.LASER;
		this.municion = municionInicial;
		miniatura.addFrame("/images/armas/miniatura_laser.gif");
		miniatura.setDuracionFrame(1);
	}

	public void disparar(GameActor lanzador) {
		BalaLaser m = new BalaLaser(r, lanzador);
		if (disparable()) {
			s.reproducir();
			i.addGameActor(m);
			--municion;
			ultimoDisparo = 0;
			red.enviarDisparo(m);
		}

	}

}
