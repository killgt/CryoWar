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

import java.awt.Color;
import java.awt.Graphics;

import es.killgt.engine.CallBack;
import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.engine.Sprite;
import es.killgt.game.escenas.jugando.EventManager;
import es.killgt.game.escenas.jugando.InterfaceSceneJuego;

/**
 * Esta clase gestiona el disparo de un arma. Evita que se dispare si no hay
 * balas o el tiempo repeticion no ha pasado.
 * 
 * @author Agustín Rodríguez
 * 
 */
public abstract class Arma {
	protected int municion;
	protected Sprite miniatura;
	protected long tiempoRepeticion;
	protected long ultimoDisparo;
	protected int municionInicial;
	protected float speed = 1f;

	protected InterfaceSceneJuego i;
	protected ResourceManager r;
	protected CallBack call;
	protected EventManager red;

	public void setCall(CallBack call) {
		this.call = call;
	}

	public Arma(InterfaceSceneJuego i, ResourceManager r, EventManager e) {
		this.i = i;
		this.r = r;
		this.red = e;
		miniatura = new Sprite(r);
	}

	public void actualizar(long tiempoTranscurrido) {
		if (ultimoDisparo <= tiempoRepeticion)
			ultimoDisparo += tiempoTranscurrido;
	}

	public void addMunicion(int cantidad) {
		municion += cantidad;
	}

	public int getMunicion() {
		return municion;
	}

	public void dibujarMiniatura(Graphics g, int x, int y, boolean seleccionado) {
		miniatura.dibujar(g, x, y);
		if (seleccionado)
			g.setColor(Color.red);
		else
			g.setColor(Color.white);
		g.drawString(String.valueOf(municion), x + 10, y + 46);
	}

	public boolean disparable() {
		if (municion == 0 && call != null)
			call.llamada();
		return (ultimoDisparo > tiempoRepeticion && municion > 0);
	}

	public void resetMunicion() {
		this.municion = municionInicial;
	}

	public abstract void disparar(GameActor lanzador);

}
