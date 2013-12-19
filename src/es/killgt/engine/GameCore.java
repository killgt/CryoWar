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

import java.util.Stack;

/**
 * Esta clase serï¿½ la que gestione las escenas del juego. En primera
 * instancia, si no ha sido cargado aun, cargarï¿½ los recursos iniciales que
 * necesite. Si se aï¿½ade una escena, esta pasarï¿½ a ejecucion, si se popea,
 * la que estaba anteriormente pasarï¿½ a ejecucion. Al cargar una escena,
 * aï¿½adira su array de elementos de la interfaz, y al popearla, los quitarï¿½,
 * igual ocurrirï¿½ con los inputmanager. m Ademï¿½s gestionarï¿½ la pantalla.
 * 
 * @author Agustï¿½n Rodrï¿½guez
 * 
 */
public class GameCore implements CoreInterface {

	private Stack<GameScene> escenas = new Stack<GameScene>();

	private ScreenManager screen = new ScreenManager();

	/**
	 * Constructor del juego, requiere almenos una escena para iniciarse.
	 * 
	 * @param escenaInicial
	 *            Escena de inicio
	 */
	public GameCore(GameScene escenaInicial) {
		config();
		pushScene(escenaInicial);
		escenaInicial.start();
		run();
	}

	/**
	 * Añade una escena, descargando la actual y cargando la nueva.
	 */
	public void pushScene(GameScene nueva) {

		if (!escenas.isEmpty()) {
			GameScene actual = escenas.peek();
			// Quitamos los controladores de eventos de la escena anterior
			actual.unload();
			System.out.println("Descargando: " + actual);
		}
		System.out.println("Cargando: " + nueva);
		escenas.push(nueva);
		nueva.load(this);

	}

	/**
	 * Elimina la escena actual y pone la siguiente disponible. Si no hay, acaba
	 * el juego.
	 */
	public void popScene() {
		if (!escenas.isEmpty()) {
			GameScene actual = escenas.pop();
			actual.stop();
			actual.unload();
			System.out.println("Eliminando: " + actual);
			actual.recursos.finalize();
			actual = null;
			System.gc();
			if (!escenas.isEmpty()) {
				escenas.peek().load(this);
				escenas.peek().start();
			}

		}
	}

	/**
	 * Bucle principal de la aplicacion. Mientras haya alguna Scena, se
	 * ejecutara la actual y se eliminara cuando termine su ejecucion.
	 */
	private void run() {

		while (!escenas.isEmpty()) {
			while (escenas.peek().isEjecutandose()) {
				if (!escenas.peek().isCargado())
					escenas.peek().cargarRecursos();
				escenas.peek().run();
			}
			popScene();
		}
		screen.recuperarPantalla();
		System.out.println("No quedan mï¿½s escenas, aplicacion terminada.");
		System.exit(0);

	}

	/**
	 * Inicializa las cosas básicas del engine.
	 */
	public void config() {
		screen.setFullScreen(screen
				.findFirstCompatibleMode(ScreenManager.MODOS_POSIBLES));
		NullRepaintManager.install();
	}

	/**
	 * Devuelve la pantalla.
	 */
	public ScreenManager getScreen() {
		return screen;
	}

	public int getScreenHeigth() {
		return screen.getHeight();
	}

	public int getScreenWidth() {
		return screen.getWidth();
	}

	/**
	 * Vacia el Stack de escenas para terminar la aplicacion.
	 */
	public void terminate() {
		while (!escenas.isEmpty())
			escenas.pop();
	}
}
