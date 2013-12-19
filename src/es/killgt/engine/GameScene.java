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

import java.awt.Component;
import java.awt.Window;

/**
 * Cada escena del juego será un GameScene. En los metodos run se ejecutará el
 * bucle principal. En cargar recursos se cargaran los recursos solo la primera
 * vez. En las funciones load y unload se cargarán y descargarán los elementos
 * de las interfaces y los input manager, que se ejecutaran cada vez que se
 * inicie esta escena.
 * 
 * @author Kill
 * 
 */
public abstract class GameScene {
	protected boolean cargado;
	protected InputManager e;
	protected Component[] interfaz;
	protected ResourceManager recursos;

	private boolean ejecutandose;
	private CoreInterface i;

	/**
	 * Constructor unico de escena.
	 * 
	 * @param eventos
	 *            Manejador de eventos de esta escena.
	 * @param ruta
	 *            Ruta hacia el archivo de precarga de recursos de la escena
	 */
	public GameScene(InputManager eventos, String ruta) {
		this.e = eventos;
		recursos = new ResourceManager(ruta);
		start();
	}

	/**
	 * Metodo que requiere cada escena implementar, para cargar los recursos
	 * (una unica vez) necesarios para su ejecucion.
	 */
	public abstract void cargarRecursos();

	/**
	 * Retorna el manejador de eventos de esta escena.
	 */
	public InputManager getE() {
		return e;
	}

	/**
	 * Retorna la interface entre el nucleo del juego y el resto de la
	 * aplicacion.
	 */
	public CoreInterface getI() {
		return i;
	}

	/**
	 * @return Elementos de la interfaz de esta escena.
	 */
	public Component[] getInterfaz() {
		return interfaz;
	}

	/**
	 * @return Retorna si esta escena ha sido cargada o no.
	 */
	public boolean isCargado() {
		return cargado;
	}

	/**
	 * @return Retorna si esta en ejecucion esta escena.
	 */
	public boolean isEjecutandose() {
		return ejecutandose;
	}

	/**
	 * A este metodo se le llama cada vez que se inicie el GameScene, sea la
	 * primera vez o no.
	 * 
	 * @param i
	 *            Interfaz entre el nucleo del juego y el resto.
	 */
	public void load(CoreInterface i) {
		this.i = i;
		e.setInterface(i);

		i.getScreen().getFullScreenWindow().addKeyListener(e);
		i.getScreen().getFullScreenWindow().addMouseListener(e);

		Window w = i.getScreen().getFullScreenWindow();
		w.requestFocusInWindow();
		for (Component c : interfaz) {
			w.add(c);
			c.setFocusTraversalKeysEnabled(false);
		}

	}

	/**
	 * Esta funcion debe implementar su bucle principal y su bucle de dibujado.
	 */
	public abstract void run();

	/**
	 * @param cargado
	 *            No voy a explicar más los getters!
	 */
	public void setCargado(boolean cargado) {
		this.cargado = cargado;
	}

	/**
	 * Activa la escena.
	 */
	public void start() {
		this.ejecutandose = true;
	}

	/**
	 * Para la escena.
	 */
	public void stop() {
		this.ejecutandose = false;
	}

	/**
	 * Elimina del frame de la aplicacion los elementos de la interfaz grafica
	 * de esta escena.
	 */
	public void unload() {
		Window w = getI().getScreen().getFullScreenWindow();
		for (Component c : interfaz)
			w.remove(c);

		w.removeKeyListener(e);
		w.removeMouseListener(e);
	}
}
