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
package es.killgt.game.actors;

import java.awt.Graphics;

import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.engine.Sprite;
import es.killgt.game.decoration.Explosion;

public class Enemigo extends GameActor {

	private Sprite spriteAceleracion;
	private Sprite spriteNormal;
	private Explosion exp = new Explosion(50, 500l, 1000l, 5f, 10f);

	String nombre;

	public Enemigo(ResourceManager r, int x, int y, short ID_ACTOR,
			int tipoNave, String nombre) {
		super(r, x, y, ID_ACTOR);
		cargarNave(tipoNave);
		this.nombre = nombre;
	}

	public void onColision(GameActor actor) {

	}

	public String getNombre() {
		return nombre;
	}

	public void acelerando(boolean isAcelerando) {
		if (isAcelerando)
			currentSprite = spriteAceleracion;
		else
			currentSprite = spriteNormal;
	}

	public void actualizar(int x, int y, float movx, float movy, float angulo,
			boolean isAcelerando) {
		this.x = x;
		this.y = y;
		this.movx = movx;
		this.movy = movy;
		this.angulo = angulo;
		acelerando(isAcelerando);
	}

	public void golpear() {
		exp.explotar(getCenterX(), getCenterY());
	}

	public void dibujar(Graphics g, int offsetx, int offsety) {
		currentSprite.dibujarRotado(g, (int) x - offsetx, (int) y - offsety,
				angulo);
		exp.dibujar(g, offsetx, offsety);
		g.drawString(nombre, (int) x - offsetx, (int) y - offsety - 10);
	}

	public void cargarNave(int tipoNave) {
		switch (tipoNave) {
		case 0:
			currentSprite.addFrame("/images/naves/nave0/frame1.png");
			currentSprite.setDuracionFrame(1);
			spriteAceleracion = new Sprite(r);
			spriteAceleracion.addFrame("/images/naves/nave0/frame2.png");
			spriteAceleracion.addFrame("/images/naves/nave0/frame3.png");
			spriteAceleracion.addFrame("/images/naves/nave0/frame4.png");
			spriteAceleracion.addFrame("/images/naves/nave0/frame5.png");

			break;
		case 1:
			currentSprite.addFrame("/images/naves/nave1/frame1.png");
			currentSprite.setDuracionFrame(1);
			spriteAceleracion = new Sprite(r);
			spriteAceleracion.addFrame("/images/naves/nave1/frame2.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame3.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame4.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame5.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame6.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame7.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame6.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame5.png");
			spriteAceleracion.addFrame("/images/naves/nave1/frame4.png");
			spriteAceleracion.setDuracionFrame(50);
			break;
		case 2:
			currentSprite.addFrame("/images/naves/nave2/frame1.png");
			currentSprite.setDuracionFrame(1);
			spriteAceleracion = new Sprite(r);
			spriteAceleracion.addFrame("/images/naves/nave2/frame2.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame3.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame4.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame5.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame6.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame7.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame6.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame5.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame4.png");
			spriteAceleracion.addFrame("/images/naves/nave2/frame3.png");
			spriteAceleracion.setDuracionFrame(50);
			break;
		case 3:
			currentSprite.addFrame("/images/naves/nave3/frame1.png");
			currentSprite.setDuracionFrame(1);
			spriteAceleracion = new Sprite(r);
			spriteAceleracion.addFrame("/images/naves/nave3/frame2.png");
			spriteAceleracion.addFrame("/images/naves/nave3/frame3.png");
			spriteAceleracion.addFrame("/images/naves/nave3/frame4.png");
			spriteAceleracion.addFrame("/images/naves/nave3/frame5.png");
			spriteAceleracion.addFrame("/images/naves/nave3/frame6.png");
			spriteAceleracion.addFrame("/images/naves/nave3/frame5.png");
			spriteAceleracion.addFrame("/images/naves/nave3/frame4.png");
			spriteAceleracion.addFrame("/images/naves/nave3/frame3.png");
			spriteAceleracion.setDuracionFrame(70);
			break;
		}
		spriteNormal = currentSprite;
	}

	public void think(long tiempoTranscurrido) {
		exp.actualizar(tiempoTranscurrido);
	}
}
