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
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Esta clase se encarga de dibujar y cargar las imagenes, y gestionar que frame
 * se tiene que dibujar en un determinado momento.
 * 
 * @author Usuario
 * 
 */
public class Sprite {

	private int actual;
	private long animTime;
	private CallBack c;

	private long duracionFrame;
	private long duracionTotal;

	private ArrayList<BufferedImage> frames;
	private ResourceManager r;

	public Sprite(ResourceManager r) {
		animTime = 0;
		actual = 0;
		frames = new ArrayList<BufferedImage>();
		this.r = r;
	}

	public Sprite(ResourceManager r, CallBack llamada) {
		this(r);
		this.c = llamada;
	}

	/**
	 * Este metodo cargará un frame en el array de frames.
	 * 
	 * @param ruta
	 *            Frame a cargar.
	 */
	public void addFrame(String ruta) {
		frames.add(r.getImage(ruta));
		duracionTotal = frames.size() * duracionFrame;
	}

	/**
	 * Dibuja el frame actual.
	 * 
	 * @param g
	 *            Donde dibujar.
	 * @param x
	 *            Posicion X
	 * @param y
	 *            Posicion Y
	 */
	public void dibujar(Graphics g, int x, int y) {
		g.drawImage(getActualFrame(), x, y, null);
	}

	/**
	 * Esta funcion dibuja rotado.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param angulo
	 */
	public void dibujarRotado(Graphics g, int x, int y, double angulo) {
		AffineTransform rotado = new AffineTransform();
		rotado.setToIdentity();
		rotado.translate(x, y);
		rotado.rotate(angulo + 1.5708d, getWidth() / 2, getHeight() / 2);
		((Graphics2D) g).drawImage(getActualFrame(), rotado, null);
	}

	/**
	 * @return Retorna la imagen actual
	 */
	public BufferedImage getActualFrame() {
		return frames.get(actual);
	}

	/**
	 * @return Retorna la duracion total de la animacion en ms.
	 */
	public long getDuracionTotal() {
		return duracionTotal;
	}

	/**
	 * @return Retorna la altura del frame actual.
	 */
	public int getHeight() {
		return frames.get(actual).getHeight(null);

	}

	/**
	 * @return El numero de frames de la animacion.
	 */
	public int getNumFrames() {
		return frames.size();
	}

	/**
	 * @return El ancho de la imagen actual.
	 */
	public int getWidth() {
		return frames.get(actual).getWidth(null);
	}

	/**
	 * Reinicia la animacion y llama al metodo de callback si existe.
	 */
	public void reset() {
		animTime = 0;
		actual = 0;
		if (c != null)
			c.llamada();
	}

	/**
	 * Si se pone un callback a esta clase, cuando se termine la animacion, este
	 * será llamado.
	 * 
	 * @param c
	 *            Callback al que llamar.
	 */
	protected void setC(CallBack c) {
		this.c = c;
	}

	/**
	 * Indica el tiempo que debe pasar para cambiar de un frame a otro.
	 * 
	 * @param duracionFrame
	 *            El tiempo en ms.
	 */
	public void setDuracionFrame(long duracionFrame) {
		this.duracionFrame = duracionFrame;
		duracionTotal = frames.size() * duracionFrame;
	}

	/**
	 * Actualiza el sprite en funcion del tiempo transcurrido.
	 * 
	 * @param tiempoTranscurrido
	 *            El tiempo en ms.
	 */
	public void update(long tiempoTranscurrido) {
		animTime += tiempoTranscurrido;
		if (animTime >= duracionTotal)
			reset();
		else
			actual = (int) (animTime / duracionFrame);

	}

}
