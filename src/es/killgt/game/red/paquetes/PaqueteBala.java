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
package es.killgt.game.red.paquetes;

public class PaqueteBala extends Paquete {
	public PaqueteBala() {
		this.tipoPaquete = TipoPaquete.TIPO_NUEVA_BALA;
	}

	public short autor;
	public short IDbala;
	public byte tipoBala;
	public float movx;
	public float movy;
	public short idTarget;
}
