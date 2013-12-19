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
import es.killgt.engine.ResourceManager;

import es.killgt.game.actors.Enemigo;
import es.killgt.game.actors.Jugador;
import es.killgt.game.actors.Recolectable;
import es.killgt.game.actors.Recolectable.TipoPowerUp;
import es.killgt.game.red.Conexion;
import es.killgt.game.red.paquetes.*;
import es.killgt.game.weapons.bullets.Bala;
import es.killgt.game.weapons.bullets.BalaLaser;
import es.killgt.game.weapons.bullets.BolaEnergia;
import es.killgt.game.weapons.bullets.Cohete;
import es.killgt.game.weapons.bullets.Mina;

public class EventManager {
	private final static long frecuenciaActualizacion = 1000 / 10;

	private Conexion conexion;
	private ResourceManager recursos;
	private Vector<GameActor> enemigos;
	private Vector<GameActor> actores;
	private Vector<Paquete> colaPaquetes;
	private Chat chat;
	private PanelPuntuacion panel;
	private InterfaceSceneJuego core;

	private long ultimaActualizacion = 0;

	//
	public EventManager(Conexion c, Vector<GameActor> actores,
			Vector<GameActor> enemigos, ResourceManager recursos,
			PanelPuntuacion p, InterfaceSceneJuego interfaz) {
		conexion = c;
		core = interfaz;
		this.actores = actores;
		this.enemigos = enemigos;
		this.recursos = recursos;
		this.panel = p;
	}

	public void actualizar(long tiempoTranscurrido) {
		ultimaActualizacion += tiempoTranscurrido;
		if (frecuenciaActualizacion < ultimaActualizacion) {
			enviarPosicion();
			ultimaActualizacion = 0;
		}
		if (conexion.hayPaquetes()) {
			colaPaquetes = conexion.getPaquetes();
			while (!colaPaquetes.isEmpty())
				// mientras haya un paquete, seguimos dispachando.
				gestionar(colaPaquetes.remove(0));// la conexion ya ha
			// gestionado los no
			// paquetes
		}

	}

	private final static PaqueteMensaje paqueteMensaje = new PaqueteMensaje();

	public void enviarMensaje(String texto) {
		paqueteMensaje.setMensaje(texto);
		conexion.enviarObjeto(paqueteMensaje, true);
	}

	private final static PaqueteSalidaJugador paqueteDesc = new PaqueteSalidaJugador();

	public void desconectar() {
		paqueteDesc.id = getID();
		conexion.enviarObjeto(paqueteDesc, false);
	}

	private final static PaquetePosicion paquetePos = new PaquetePosicion();

	private void enviarPosicion() {// TIPO_POSICION
		Jugador jugador = (Jugador) actores.firstElement();
		paquetePos.autor = jugador.getID_ACTOR();
		paquetePos.x = (int) jugador.getX();
		paquetePos.y = (int) jugador.getY();
		paquetePos.movx = jugador.getMovx();
		paquetePos.movy = jugador.getMovy();
		paquetePos.angulo = jugador.getAngulo();
		paquetePos.acelerando = jugador.isAcelerando();
		conexion.enviarObjeto(paquetePos, true);
	}

	private final static PaqueteMuerte paqueteMuerte = new PaqueteMuerte();

	public void enviarMuerte(short idBalaAsesina, short IDautor, int x, int y) {// TIPO_MUERTE

		paqueteMuerte.idMuerto = getID();
		paqueteMuerte.asesino = IDautor;
		paqueteMuerte.x = x;
		paqueteMuerte.y = y;
		conexion.enviarObjeto(paqueteMuerte, true);
		enviarBalaColisionada(idBalaAsesina);

	}

	public void solicitarPuntuacion() {
		conexion.enviarObjeto(new PaqueteSolicitud(), true);
	}

	private final static PaqueteImpactoBala paqueteImpacto = new PaqueteImpactoBala();

	public void enviarBalaColisionada(short idBala) {// TIPO_IMPACTO_BALA
		paqueteImpacto.idBala = idBala;
		paqueteImpacto.idImpactado = getID();
		conexion.enviarObjeto(paqueteImpacto, true);
	}

	private final static PaqueteBala paqueteBala = new PaqueteBala();

	public void enviarDisparo(Bala bala) {
		enviarPosicion();
		// TIPO_NUEVA_BALA

		paqueteBala.autor = getID();
		paqueteBala.IDbala = bala.getID_ACTOR();
		paqueteBala.tipoBala = (byte) bala.tipo;
		// paqueteBala.x = bala.getX();
		// paqueteBala.y = bala.getY();
		paqueteBala.movx = bala.getMovx();
		paqueteBala.movy = bala.getMovy();
		if (bala.tipo == 3)
			paqueteBala.idTarget = ((Cohete) bala).getTarget().getID_ACTOR();
		else
			paqueteBala.idTarget = 0;
		conexion.enviarObjeto(paqueteBala, true);
	}

	public void gestionar(Paquete p) {
		switch (p.tipoPaquete) {
		case TipoPaquete.TIPO_POSICION:
			PaquetePosicion paquetePos = (PaquetePosicion) p;

			Enemigo g = (Enemigo) buscarEnemigo(paquetePos.autor);
			if (g != null)
				g.actualizar(paquetePos.x, paquetePos.y, paquetePos.movx,
						paquetePos.movy, paquetePos.angulo,
						paquetePos.acelerando);
			break;
		case TipoPaquete.TIPO_NUEVA_BALA:
			PaqueteBala paqueteBala = (PaqueteBala) p;
			GameActor autor = buscarEnemigo(paqueteBala.autor);
			if (autor != null) {
				switch (paqueteBala.tipoBala) {
				case 0:// laser
					actores.add(new BalaLaser(recursos, autor)
							.setID(paqueteBala.IDbala));
					break;
				case 1:// bolicas
					actores.add(new BolaEnergia(recursos, autor,
							paqueteBala.movx, paqueteBala.movy)
							.setID(paqueteBala.IDbala));
					break;
				case 2:// mina
					actores.add(new Mina(recursos, autor)
							.setID(paqueteBala.IDbala));
					break;
				case 3:// misil
					GameActor target = null;// buscamos al target...
					if (paqueteBala.idTarget == getID()) {// Si soy yo...
						target = actores.get(0);
					} else {
						target = buscarEnemigo(paqueteBala.idTarget);
					}
					if (target != null) {// si encontramos al target
						actores.add(new Cohete(recursos, autor, target)
								.setID(paqueteBala.IDbala));
					}
					break;
				}

			}
			break;
		case TipoPaquete.TIPO_IMPACTO_BALA:

			PaqueteImpactoBala paqueteImpacto = (PaqueteImpactoBala) p;

			GameActor actor = buscarActor(paqueteImpacto.idBala);
			if (actor != null) {
				if (actor instanceof Bala)
					((Bala) actor).destruir();
				else
					actor.eliminar();
				actor = buscarEnemigo(paqueteImpacto.idImpactado);
				if (actor != null)
					((Enemigo) actor).golpear();
			}
			break;
		case TipoPaquete.TIPO_NUEVO_JUGADOR:
			PaqueteNuevoJugador paqueteNuevo = (PaqueteNuevoJugador) p;
			enemigos
					.add(new Enemigo(recursos, paqueteNuevo.x, paqueteNuevo.y,
							paqueteNuevo.id, paqueteNuevo.tipoNave,
							paqueteNuevo.nombre));
			chat.addMessage(paqueteNuevo.nombre + " se ha unido a la partida.");
			break;
		case TipoPaquete.TIPO_SALIDA_JUGADOR:
			PaqueteSalidaJugador paquete = (PaqueteSalidaJugador) p;
			Enemigo enemigo = (Enemigo) buscarEnemigo(paquete.id);
			eliminarEnemigo(paquete.id);
			if (enemigo != null)
				chat.addMessage(enemigo.getNombre()
						+ " ha salido de la partida.");
			break;
		case TipoPaquete.TIPO_POWER_UP:
			PaquetePowerUp paquetePowerUp = (PaquetePowerUp) p;
			if (paquetePowerUp.tipo == 0)
				actores.add(new Recolectable(recursos, paquetePowerUp.x,
						paquetePowerUp.y, TipoPowerUp.RecargaVida,
						paquetePowerUp.cantidad, paquetePowerUp.IDpowerUp));
			else
				actores.add(new Recolectable(recursos, paquetePowerUp.x,
						paquetePowerUp.y, TipoPowerUp.MunicionLaser,
						paquetePowerUp.cantidad, paquetePowerUp.IDpowerUp));
			break;
		case TipoPaquete.TIPO_MENSAJE:
			PaqueteMensaje mensaje = (PaqueteMensaje) p;
			chat.addMessage(mensaje.getMensaje());
			break;
		case TipoPaquete.TIPO_PUNTUACION:
			PaquetePuntuacion puntuacion = (PaquetePuntuacion) p;
			panel.setPuntuacion(puntuacion);
			break;
		case TipoPaquete.TIPO_FIN_CONEXION:
			core.stop();
			break;
		}
	}

	public GameActor buscarEnemigo(int idEnemigo) {
		for (int a = 0; a < enemigos.size(); a++)
			if (enemigos.get(a).getID_ACTOR() == idEnemigo)
				return enemigos.get(a);
		return null;
	}

	public GameActor buscarActor(int idACTOR) {
		for (int a = 0; a < actores.size(); a++)
			if (actores.get(a).getID_ACTOR() == idACTOR)
				return actores.get(a);
		return null;
	}

	public void eliminarEnemigo(short ID_ACTOR) {
		for (int a = 0; a < enemigos.size(); a++)
			if (enemigos.get(a).getID_ACTOR() == ID_ACTOR) {
				enemigos.remove(a);
				return;
			}
	}

	public short getID() {

		return conexion.getID();
	}

	public void setChat(Chat chat) {
		this.chat = chat;

	}

}
