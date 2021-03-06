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
 * Esta interfaz la implementará todas las clases que quieran que otra clase que
 * la contenga, llame a un metodo suyo.
 * 
 * @author Agustín Rodríguez
 * 
 */
public interface CallBack {
	public void llamada();
}
