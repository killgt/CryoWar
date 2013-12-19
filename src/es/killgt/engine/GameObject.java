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

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * El elemento básico de un juego. Se encargará de gestionar la posicion,
 * administrar los sprites y actualizar la posicion del objeto.
 * 
 * @author Agustin killgt@gmail.com
 * 
 */
public abstract class GameObject {
	protected Sprite currentSprite;
	protected boolean dibujado;
	protected float movx;
	protected float movy;

	protected ResourceManager r;

	protected float x;
	protected float y;

	/**
	 * El constructor básico para cualquier objeto del juego.
	 * 
	 * @param r
	 *            Administrador de recursos al que esta asociado el objeto
	 * @param x
	 *            Posicion en el eje X inicial.
	 * @param y
	 *            Posicion en el eje Y inicial.
	 */
	public GameObject(ResourceManager r, float x, float y) {
		super();
		this.x = x;
		this.y = y;
		currentSprite = new Sprite(r);
		this.dibujado = true;
		this.r = r;
	}

	/**
	 * Esta funcion mueve el objeto en funcion de su movx y movy. Llama al
	 * metodo {@link #think(long)} que debe de ser implementado en las clases
	 * que hereden de GameObject, para que el objeto que herede pueda realizar
	 * sus acciones propias.
	 * 
	 * @param tiempoTranscurrido
	 *            Tiempo transcurrido desde el ultimo GameLoop.
	 */
	public void actualizar(long tiempoTranscurrido) {
		currentSprite.update(tiempoTranscurrido);
		think(tiempoTranscurrido);
		x += movx * tiempoTranscurrido;
		y += movy * tiempoTranscurrido;

	}

	/**
	 * Esta funcion comprueba si colisiona un objecto con otro.
	 * 
	 * @param objecto
	 *            Objecto con el que se compara para saber si hay colision
	 * @return True/false dependiendo de si ambos objectos colisionan.
	 */
	public boolean colisionan(GameObject objecto) {
		return this.getBounds().intersects(objecto.getBounds());
	}

	/**
	 * Funcion básica de dibujado.
	 * 
	 * @param g
	 *            Buffer en el que dibujar.
	 * @param offsetx
	 *            Posicion offset de la pantalla
	 * @param offsety
	 *            Posicion offset de la pantalla
	 */
	public void dibujar(Graphics g, int offsetx, int offsety) {
		if (dibujado)
			currentSprite.dibujar(g, (int) x - offsetx, (int) y - offsety);
	}

	/**
	 * @return Retorna un rectangulo con la posicion del objeto y el tamaño del
	 *         frame actual.
	 */
	private final Rectangle rect = new Rectangle();

	public Rectangle getBounds() {
		rect.setBounds((int) x, (int) y, currentSprite.getWidth(),
				currentSprite.getHeight());
		return rect;
	}

	/**
	 * @return Posicion central del eje X respecto al frame actual.
	 */
	public float getCenterX() {
		return x + currentSprite.getWidth() / 2;
	}

	/**
	 * @return Posicion central del eje X respecto al frame actual pero
	 *         restandole el offset.
	 */
	public float getCenterX(int offsetx) {
		return getCenterX() - offsetx;
	}

	/**
	 * @return Posicion central del eje Y respecto al frame actual.
	 */
	public float getCenterY() {
		return y + currentSprite.getHeight() / 2;
	}

	/**
	 * @return Posicion central del eje Y respecto al frame actual pero
	 *         restandole el offset.
	 */
	public float getCenterY(int offsety) {
		return getCenterY() - offsety;
	}

	/**
	 * @return Retorna la direccion de movimiento X.
	 */
	public float getMovx() {
		return movx;
	}

	/**
	 * @return Retorna la direccion de movimiento Y.
	 */
	public float getMovy() {
		return movy;
	}

	/**
	 * @return Retorna el Sprite actual.
	 */
	public Sprite getSprite() {
		return currentSprite;
	}

	/**
	 * @return Retorna la posicion X.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return Retorna la posicion X menos el offset.
	 */
	public float getX(int offsetx) {
		return x - offsetx;
	}

	/**
	 * @return Retorna la posicion Y.
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return Retorna la posicion Y menos el offset.
	 */
	public float getY(int offsety) {
		return y - offsety;
	}

	/**
	 * @return Retorna si el objeto actualmente debe ser dibujado.
	 */
	public boolean isDibujado() {
		return dibujado;
	}

	/**
	 * @param dibujado
	 *            Indica si el objeto debe ser dibujado.
	 */
	public void setDibujado(boolean dibujado) {
		this.dibujado = dibujado;
	}

	/**
	 * @param movx
	 *            Direccion del eje X.
	 */
	public void setMovx(float movx) {
		this.movx = movx;
	}

	/**
	 * @param movx
	 *            Direccion del eje Y.
	 */
	public void setMovy(float movy) {
		this.movy = movy;
	}

	/**
	 * @param x
	 *            Posicion x.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            Posicion Y.
	 */
	public void setY(float y) {
		this.y = y;
	}

	public float getDistance(GameObject g) {
		return (float) (Utils.sqrt(Math.pow(x - g.x, 2d)
				+ Math.pow(y - g.y, 2d), 5));
	}

	/**
	 * Funcion que se debe de implementar para realizar las acciones propias.
	 * 
	 * @param tiempoTranscurrido
	 */
	public abstract void think(long tiempoTranscurrido);

}
