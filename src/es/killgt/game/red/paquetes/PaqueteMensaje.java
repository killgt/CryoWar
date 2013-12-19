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

public class PaqueteMensaje extends Paquete {
	private String mensaje;

	public PaqueteMensaje() {
		this.tipoPaquete = TipoPaquete.TIPO_MENSAJE;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
		StringBuffer corregido = new StringBuffer(mensaje);
		for (int a = 0; a < mensaje.length(); a++)
			if (Character.getNumericValue(mensaje.charAt(a)) < 0
					&& mensaje.charAt(a) != ' ' && mensaje.charAt(a) != '['
					&& mensaje.charAt(a) != ']' && mensaje.charAt(a) != ':')
				corregido.setCharAt(a, ' ');
		this.mensaje = corregido.toString();

	}

	public String getMensaje() {
		return mensaje;
	}
}
