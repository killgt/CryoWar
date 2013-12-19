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

/**
 * Esta clase controla las acciones del juego, si estan pulsadas, si se han
 * soltado.
 * 
 * @author Agustín Rodríguez
 * 
 */
public class GameAction {
	private String nombre;
	private boolean pulsado;
	private byte accion;
	private boolean realizado = true;

	public GameAction(String nombre, byte accion) {
		this.nombre = nombre;
		this.accion = accion;
	}

	public synchronized void pulsar() {
		pulsado = true;
		realizado = false;
	}

	public synchronized void soltar() {
		pulsado = false;
	}

	public synchronized boolean isPulsado() {
		return pulsado;
	}

	public String toString() {
		return nombre;
	}

	public byte getAccion() {
		return accion;
	}

	public void realizar() {
		realizado = true;
	}

	public boolean isProcesado() {
		if (realizado)
			return true;
		else {
			realizado = true;
			return false;
		}
	}

}
