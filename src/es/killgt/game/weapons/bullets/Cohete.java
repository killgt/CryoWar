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
import es.killgt.engine.Utils;

public class Cohete extends Bala {
	private final static long RECALCTIME = 500;
	private final static long MAXLIFETIME = 15000;
	private final static float ANGULAR_MAX_SPEED = 0.002f;
	private final static float SPEED = 1.15f;

	private long lastCalculated;
	private long life;
	private float anguloDeseado;
	GameActor target;

	public Cohete(ResourceManager r, GameActor lanzador, GameActor target) {
		super(r, lanzador);
		currentSprite.addFrame("/images/armas/missile.gif");
		currentSprite.setDuracionFrame(1);
		this.target = target;
		angulo = Utils.generarAngulo(lanzador.getAngulo());
		movx = (float) Math.cos(angulo);
		movy = (float) Math.sin(angulo);
		apuntar();
		cargarExplosionGenerica();
		tipo = 3;
		damage = 8;
	}

	public void onColision(GameActor actor) {
		if (actor != getLanzador() && !(actor instanceof Cohete))
			this.destruir();
	}

	public void apuntar() {
		anguloDeseado = (float) Math.atan2(target.getCenterY() - y, target
				.getCenterX()
				- x);
		lastCalculated = 0;
	}

	public GameActor getTarget() {
		return target;
	}

	
	
	public void update(long tiempoTranscurrido) {
		life += tiempoTranscurrido;
		lastCalculated += tiempoTranscurrido;

		if (lastCalculated > RECALCTIME)
			apuntar();

		float diferencia = Utils.generarAngulo(anguloDeseado - angulo);
		diferencia = Utils.clamp(diferencia,
				(float) (ANGULAR_MAX_SPEED * tiempoTranscurrido),
				(float) (-ANGULAR_MAX_SPEED * tiempoTranscurrido));

		angulo = Utils.generarAngulo(angulo - diferencia);

		movx = (float) (Math.cos(angulo) * SPEED);
		movy = (float) (Math.sin(angulo) * SPEED);
		if (life > MAXLIFETIME)
			this.destruir();
	}

}
