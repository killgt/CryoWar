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

public class TipoPaquete {
	public final static byte TIPO_POSICION = 1; // RECEPCION Y ENVIO
	public final static byte TIPO_NUEVA_BALA = 2; // RECEPCION Y ENVIO
	public final static byte TIPO_IMPACTO_BALA = 3; // RECEPCION Y ENVIO
	public final static byte TIPO_NUEVO_JUGADOR = 4; // SOLO RECEPCION
	public final static byte TIPO_SALIDA_JUGADOR = 5; // SOLO RECEPCION
	public final static byte TIPO_POWER_UP = 6; // SOLO RECEPCION
	public final static byte TIPO_MUERTE = 7; // SOLO ENVIO
	public final static byte TIPO_MENSAJE = 8; // ENVIO Y RECEPCION
	public final static byte TIPO_PUNTUACION = 9;// RECEPCION
	public final static byte TIPO_SOLICITUD = 10;// envio
	public final static byte TIPO_FIN_CONEXION = 11;// rECEPCION
}
