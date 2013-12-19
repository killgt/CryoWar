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
package es.killgt.game.escenas.jugando;

import java.util.Vector;

import es.killgt.engine.GameActor;

public interface InterfaceSceneJuego {
	public void addGameActor(GameActor actor);

	public Vector<GameActor> getActors();

	public int getScreenWidth();

	public int getScreenHeight();

	public Vector<GameActor> getEnemigos();

	public Chat getChat();

	public void recuperarFocus();

	public void regenerarFondo();

	public void stop();

}
