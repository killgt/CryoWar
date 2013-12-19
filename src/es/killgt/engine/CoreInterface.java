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
 * Esta interface permite a las subclases de GameCore acceder a ciertos métodos.
 * 
 * @author Agustín Rodríguez
 * 
 */
public interface CoreInterface {
	public void pushScene(GameScene nueva);

	public void popScene();

	public ScreenManager getScreen();

	public int getScreenWidth();

	public int getScreenHeigth();

	public void terminate();
}
