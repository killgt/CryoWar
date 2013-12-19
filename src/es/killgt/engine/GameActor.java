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
package es.killgt.engine;

import java.awt.Graphics;

/**
 * De esta clase hereda todo aquel elemento que actue en el juego. Ello incluye
 * colisiones, rotacion, eliminacion.
 * 
 * @author Usuario
 * 
 */
public abstract class GameActor extends GameObject {
	protected short ID_ACTOR;
	protected float angulo;
	protected boolean marcadoEliminacion;

	public GameActor(ResourceManager r, float x, float y, short ID_ACTOR) {
		super(r, x, y);
		this.ID_ACTOR = ID_ACTOR;
	}

	public short getID_ACTOR() {
		return ID_ACTOR;
	}

	public abstract void onColision(GameActor actor);

	public void dibujar(Graphics g, int offsetx, int offsety) {
		if (angulo != 0)
			currentSprite.dibujarRotado(g, (int) x - offsetx,
					(int) y - offsety, angulo);
		else
			currentSprite.dibujar(g, (int) x - offsetx, (int) y - offsety);
	}

	public float getAngulo() {
		return angulo;
	}

	public void setAngulo(float angulo) {
		this.angulo = angulo;
	}

	public boolean isMarcadoEliminacion() {
		return marcadoEliminacion;
	}

	public void eliminar() {
		marcadoEliminacion = true;
	}

}
