/*******************************************************************************
 * Copyright (c) 2009 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Agustin Rodr�guez killgt@gmail.com
 ******************************************************************************/
package es.killgt.game.red.paquetes;

public class PaqueteImpactoBala extends Paquete {
	public PaqueteImpactoBala() {
		this.tipoPaquete = TipoPaquete.TIPO_IMPACTO_BALA;
	}

	public short idBala;
	public short idImpactado;
}