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
package es.killgt.game;

import es.killgt.engine.GameCore;
import es.killgt.game.escenas.menu.GameSceneMenu;

/**
 * Para inicializar el juego, necesitamos al menos un GameScene, y el GameScene
 * necesitará quien controle sus eventos. Si se le pasa una cadena a GameScene
 * precargará en el caso de que encuentre el fichero de precarga, los recursos
 * indicados.
 * 
 * @author Kill
 * 
 */

public class GameMain {
	public static void main(String[] args) {
		new GameCore(new GameSceneMenu());
		System.exit(0);
	}

}
