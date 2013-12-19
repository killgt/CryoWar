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

import java.util.Vector;

import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.engine.Sonido;
import es.killgt.game.escenas.jugando.EventManager;
import es.killgt.game.escenas.jugando.InterfaceSceneJuego;
import es.killgt.game.weapons.bullets.Cohete;

public class LanzaCohetes extends Arma {
	private Sonido s;
	public LanzaCohetes(InterfaceSceneJuego i, ResourceManager r,
			EventManager red) {		
		super(i, r, red);
		s = new Sonido (r,"/sounds/misil.wav",false);
		this.tiempoRepeticion = 700;
		this.municionInicial = Municiones.COHETES;
		this.municion = municionInicial;
		miniatura.addFrame("/images/armas/miniatura_missile.gif");
		miniatura.setDuracionFrame(1);
	}

	public GameActor getTarget(GameActor lanzador) {
		Vector<GameActor> actores = i.getEnemigos();
		if (!actores.isEmpty()) {
			double minDistancia = 9999999d, tempD;
			GameActor target = null, temp;
			for (int a = 0; a < actores.size(); a++) {
				temp = actores.get(a);
				tempD = temp.getDistance(lanzador);
				if (tempD < minDistancia) {
					target = temp;
					minDistancia = tempD;
				}
			}
			return target;
		}
		return null;
	}

	public void disparar(GameActor lanzador) {
		GameActor target = getTarget(lanzador);
		if (target != null)
			if (disparable()) {
				s.reproducir();
				Cohete m = new Cohete(r, lanzador, target);
				i.addGameActor(m);
				--municion;
				ultimoDisparo = 0;
				red.enviarDisparo(m);
			}

	}

}
