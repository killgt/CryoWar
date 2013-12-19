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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.Vector;

import es.killgt.engine.CallBack;
import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.engine.Sonido;
import es.killgt.engine.Sprite;
import es.killgt.engine.Utils;
import es.killgt.game.decoration.CirculoExpandible;
import es.killgt.game.decoration.Explosion;
import es.killgt.game.escenas.jugando.EventManager;
import es.killgt.game.escenas.jugando.GameSceneJuego;
import es.killgt.game.escenas.jugando.InterfaceSceneJuego;

import es.killgt.game.weapons.Arma;
import es.killgt.game.weapons.LanzaCohetes;
import es.killgt.game.weapons.PistolaEnergia;
import es.killgt.game.weapons.PistolaLaser;
import es.killgt.game.weapons.SueltaMinas;
import es.killgt.game.weapons.bullets.Bala;

public class Jugador extends GameActor implements CallBack {
	public final static int TIEMPO_MUERTO = 5000;
	//private Sonido movimiento;
	private float aceleracion;
	private float aceleracionX;
	private float aceleracionY;
	private int armaActual = 0;

	private Vector<Arma> armas = new Vector<Arma>();
	private Sprite backgroundArma;
	private Sprite barraVida;
	private Sonido s;
	
	private CirculoExpandible circulo = new CirculoExpandible(50, 500);

	private EventManager eventos;
	private boolean frenar;
	private Explosion exp = new Explosion(50, 500l, 1000l, 5f, 10f);

	private InterfaceSceneJuego i;
	private float MAX_SPEED = 0.7f;
	private boolean muerto;
	private Sprite spriteAceleracion;

	private Sprite spriteNormal;

	private long tiempoMuerto;
	private int vida;
	private int vidaInicial;

	public Jugador(ResourceManager r, int tipoNave, float x, float y,
			InterfaceSceneJuego i, short ID, EventManager eventos) {
		super(r, x, y, ID);
		this.i = i;
		this.eventos = eventos;
		cargarNave(tipoNave);
		s = new Sonido (r,"/sounds/impacto.wav",false);
		//movimiento = new Sonido (r,"/sounds/nave.wav",true,-15f);
		
		backgroundArma = new Sprite(r);
		backgroundArma.addFrame("/images/armas/fondoarma.png");
		backgroundArma.setDuracionFrame(1);

		barraVida = new Sprite(r);
		barraVida.addFrame("/images/interfaz/barraVida.gif");

		spriteNormal = currentSprite;

		Arma a = new PistolaLaser(i, r, eventos);
		a.setCall(this);
		armas.add(a);

		a = new SueltaMinas(i, r, eventos);
		a.setCall(this);
		armas.add(a);

		a = new PistolaEnergia(i, r, eventos);
		a.setCall(this);
		armas.add(a);

		a = new LanzaCohetes(i, r, eventos);
		a.setCall(this);
		armas.add(a);

		armaActual = 0;
		setVida(vidaInicial);
	}

	public void acelerar() {
		this.aceleracionX = (float) (Math.cos(angulo) * aceleracion);
		this.aceleracionY = (float) (Math.sin(angulo) * aceleracion);
		currentSprite = spriteAceleracion;
		this.desacelerar(false);
		//movimiento.reproducir();
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
			spriteAceleracion.setDuracionFrame(50);
			vidaInicial = 80;
			aceleracion = 0.0013f;
			MAX_SPEED = 0.6f;
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
			vidaInicial = 90;
			aceleracion = 0.0008f;
			MAX_SPEED = 0.8f;

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
			vidaInicial = 85;
			aceleracion = 0.0011f;
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
			vidaInicial = 180;
			aceleracion = 0.0005f;
			MAX_SPEED = 1.3f;
			break;
		}
		vida = vidaInicial;
	}

	public void desacelerar(boolean frenar) {
		this.frenar = frenar;
	}

	public void dibujar(Graphics g, int offsetx, int offsety) {
		circulo
				.dibujar(g, (int) getCenterX(offsetx),
						(int) getCenterY(offsety));
		currentSprite.dibujarRotado(g, (int) x - offsetx, (int) y - offsety,
				angulo);
		backgroundArma.dibujar(g, i.getScreenWidth() - 57, armaActual * 50 + 20
				+ armaActual * 12);
		for (int a = 0; a < armas.size(); a++)
			armas.get(a).dibujarMiniatura(g, i.getScreenWidth() - 50,
					a * 50 + 20 + a * 12, (a == armaActual));

		barraVida.dibujar(g, 10, 10);
		g.setColor(Color.red);
		g.fillRect(105, 56, (int) (75 * ((float) vida / (float) vidaInicial)),
				6);
		g.drawString(String.valueOf(vida), 20, 40);
		exp.dibujar(g, offsetx, offsety);

	}

	public void disparar() {
		armas.get(armaActual).disparar(this);
	}

	public void frenar() {

		this.aceleracionY = 0;
		this.aceleracionX = 0;
		currentSprite.reset();
		currentSprite = spriteNormal;
		//movimiento.parar();
	}

	public int getVida() {
		return vida;
	}

	public boolean isAcelerando() {

		return (currentSprite == spriteAceleracion);
	}

	public void llamada() {
		nextWeapon();
	}

	private void morir(Bala bala) {

		vida = vidaInicial;
		this.muerto = true;
		eventos.enviarMuerte(bala.getID_ACTOR(), bala.getAutor().getID_ACTOR(),
				(int) x, (int) y);

		movx = 0;
		movy = 0;
		aceleracionX = 0;
		aceleracionY = 0;

		x = -GameSceneJuego.TAMANO_UNIVERSO - 1500;
		y = -GameSceneJuego.TAMANO_UNIVERSO - 500;
		for (int a = 0; a < armas.size(); a++)
			armas.get(a).resetMunicion();
	}

	public boolean isMuerto() {
		return muerto;
	}

	public int getDeadTimeLeft() {
		return (int) this.tiempoMuerto;
	}

	public void nextWeapon() {
		if (armaActual + 1 >= armas.size())
			armaActual = 0;
		else
			armaActual++;
		if (armas.get(armaActual).getMunicion() == 0 && armaActual != 0)
			nextWeapon();
	}

	public void onColision(GameActor actor) {
		if (actor instanceof Bala) {
			
			Bala bala = (Bala) actor;
			if (bala.getLanzador() != this) {
				s.reproducir();
				exp.explotar(getCenterX(), getCenterY());
				this.setVida(getVida() - bala.getDamage());
				if (vida <= 0) {
					morir(bala);
				} else
					eventos.enviarBalaColisionada(bala.getID_ACTOR());
				circulo.play();
				this.movx += actor.getMovx() / 4;
				this.movy += actor.getMovy() / 4;
			}
		} else if (actor instanceof Recolectable) {
			Recolectable r = (Recolectable) actor;
			switch (r.getTipo()) {
			case MunicionLaser:
				for (Arma arma : armas)
					arma.resetMunicion();
				break;
			case RecargaVida:
				this.vida = vidaInicial;

				break;
			}
			eventos.enviarBalaColisionada(actor.getID_ACTOR());
			r.eliminar();
		}
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public void think(long tiempoTranscurrido) {
		if (!muerto) {
			circulo.actualizar(tiempoTranscurrido);
			movx += aceleracionX * tiempoTranscurrido;
			movy += aceleracionY * tiempoTranscurrido;

			if (frenar) {
				movx = movx > 0 ? movx - (tiempoTranscurrido * 0.001f) : movx
						+ (tiempoTranscurrido * 0.001f);
				movy = movy > 0 ? movy - (tiempoTranscurrido * 0.001f) : movy
						+ (tiempoTranscurrido * 0.001f);
			}

			angulo = (float) Math.atan2(MouseInfo.getPointerInfo()
					.getLocation().y
					- this.getCenterY(GameSceneJuego.offsety), MouseInfo
					.getPointerInfo().getLocation().x
					- this.getCenterX(GameSceneJuego.offsetx));
			if (Math.abs(movx) > MAX_SPEED)
				movx = movx > 0 ? MAX_SPEED : -MAX_SPEED;
			if (Math.abs(movy) > MAX_SPEED)
				movy = movy > 0 ? MAX_SPEED : -MAX_SPEED;
			for (Arma arma : armas)
				arma.actualizar(tiempoTranscurrido);
		} else {
			tiempoMuerto += tiempoTranscurrido;
			if (tiempoMuerto > TIEMPO_MUERTO) {
				muerto = false;
				x = Utils.azar.nextInt(GameSceneJuego.TAMANO_UNIVERSO * 2)
						- GameSceneJuego.TAMANO_UNIVERSO;
				y = Utils.azar.nextInt(GameSceneJuego.TAMANO_UNIVERSO * 2)
						- GameSceneJuego.TAMANO_UNIVERSO;
				GameSceneJuego.offsetx = (int) x;
				GameSceneJuego.offsety = (int) y;
				i.regenerarFondo();
				tiempoMuerto = 0;
			}
		}
		exp.actualizar(tiempoTranscurrido);

	}
}
