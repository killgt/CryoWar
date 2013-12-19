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
import es.killgt.game.weapons.bullets.BolaEnergia;

public class PistolaEnergia extends Arma {
	private final int BALAS_POR_DISPARO = 8;
	
	public PistolaEnergia(InterfaceSceneJuego i, ResourceManager r,
			EventManager red) {
		super(i, r, red);
		
		
		this.tiempoRepeticion = 1000;
		this.municionInicial = Municiones.ENERGIA;
		this.municion = municionInicial;
		this.speed = 1.2f;
		miniatura.addFrame("/images/armas/miniatura_energia.gif");
		miniatura.setDuracionFrame(1);
	}

	public void disparar(GameActor lanzador) {
		if (disparable()) {
			
			for (float a = 0; a < 6.18; a += (6.18 / BALAS_POR_DISPARO)) {
				BolaEnergia m = new BolaEnergia(r, lanzador, (float) Math
						.cos(a)
						* speed, (float) Math.sin(a) * speed);
				i.addGameActor(m);
				red.enviarDisparo(m);
			}
			--municion;
			ultimoDisparo = 0;
		}

	}
}
