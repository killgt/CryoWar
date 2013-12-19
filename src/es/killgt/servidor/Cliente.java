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
package es.killgt.servidor;

import es.killgt.game.red.paquetes.*;

public class Cliente implements Comparable<Object> {
	private short ID;
	private short tipoNave;
	private String nombre;

	private int muertes, asesinatos;

	public Cliente(short id) {
		ID = id;

	}

	public void config(PaqueteNuevoJugador p) {
		this.tipoNave = p.tipoNave;
		this.nombre = p.nombre;
	}

	public int getID() {
		return ID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String n) {
		nombre = n;
	}

	public byte getTipoNave() {
		return (byte) tipoNave;
	}

	public void addPoint() {
		++asesinatos;
	}

	public void addDeath() {
		++muertes;

	}

	public String toString() {
		return "\n[" + ID + "] '" + nombre + "' K:" + asesinatos + " M:"
				+ muertes;
	}

	@Override
	public int compareTo(Object o) {
		Cliente c = (Cliente) o;
		if (c.getAsesinatos() > getAsesinatos())
			return -1;
		return 1;
	}

	public int getAsesinatos() {
		return asesinatos;
	}

	public int getMuertes() {
		return muertes;
	}
}
