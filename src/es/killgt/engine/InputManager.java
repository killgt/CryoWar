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

import java.awt.event.ActionListener;

import java.awt.event.KeyListener;

import java.awt.event.MouseListener;

/**
 * Esta clase manejará los eventos que ocurran en las escenas.
 * 
 * @author Kill
 * 
 */
public abstract class InputManager implements ActionListener, KeyListener,
		MouseListener {

	protected CoreInterface i;

	public void setInterface(CoreInterface c) {
		i = c;
	}

}
