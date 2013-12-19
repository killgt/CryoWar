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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JSeparator;
import javax.swing.JTextField;

import es.killgt.engine.GameAction;
import es.killgt.engine.GameActor;
import es.killgt.engine.GameScene;
import es.killgt.engine.Sonido;

import es.killgt.game.actors.Jugador;
import es.killgt.game.red.Conexion;
import es.killgt.game.decoration.BackgroundManager;

public class GameSceneJuego extends GameScene implements InterfaceSceneJuego {

	private long tini;

	private long tiempoTranscurrido;
	private Sonido backgroundSound;
	public static int offsetx, offsety;
	private int tipoNave;

	private Vector<GameActor> actores;
	private Vector<GameActor> enemigos;

	private Jugador player;

	private EventManager gestorPaquetes;

	private BackgroundManager universo;

	private PanelPuntuacion panel;

	private MiniMap mapa;

	private Chat chat;

	public final static int TAMANO_UNIVERSO = 10000;

	public GameSceneJuego(String ruta, int tipoNave, Conexion conexion,
			String nombre) {
		super(new InputManagerJuego(), ruta);
		actores = new Vector<GameActor>(100);
		enemigos = new Vector<GameActor>(10);
		interfaz = new Component[2];
		panel = new PanelPuntuacion();
		
		((InputManagerJuego) e).setJuego(this);
		gestorPaquetes = new EventManager(conexion, actores, enemigos,
				recursos, panel, this);
		panel.setEvent(gestorPaquetes);

		this.tipoNave = tipoNave;
		interfaz[0] = crearInputText("Texto:");
		interfaz[1] = new JSeparator();
		chat = new Chat((JTextField) interfaz[0], gestorPaquetes, this, nombre);
		gestorPaquetes.setChat(chat);
	}

	public void cargarRecursos() {
		player = new Jugador(recursos, tipoNave, 0, 0, this, gestorPaquetes
				.getID(), gestorPaquetes);
		actores.add(player);
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_ESCAPE,
				new GameAction("Salir", InputManagerJuego.SALIR));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_SPACE,
				new GameAction("Disparar", InputManagerJuego.DISPARAR));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_UP,
				new GameAction("Avanzando", InputManagerJuego.AVANZAR));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_W,
				new GameAction("Avanzando", InputManagerJuego.AVANZAR));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_DOWN,
				new GameAction("Avanzando", InputManagerJuego.FRENAR));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_S,
				new GameAction("Avanzando", InputManagerJuego.FRENAR));
		((InputManagerJuego) e)
				.bindearAccionTeclado(KeyEvent.VK_C, new GameAction(
						"Girar derecha", InputManagerJuego.CAMBIAR_ARMA));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_F12,
				new GameAction("Screenshot", InputManagerJuego.SCREENSHOT));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_F1,
				new GameAction("Camara", InputManagerJuego.CAMBIAR_CAMARA));
		((InputManagerJuego) e)
				.bindearAccionTeclado(KeyEvent.VK_ENTER, new GameAction(
						"Mostrar barra", InputManagerJuego.MOSTRAR_BARRA));
		((InputManagerJuego) e).bindearAccionTeclado(KeyEvent.VK_Q,
				new GameAction("Mostrar PANEL",
						InputManagerJuego.VER_PUNTUACION));

		((InputManagerJuego) e).bindearAccionRaton(MouseEvent.BUTTON1,
				new GameAction("Disparar", InputManagerJuego.DISPARAR));
		((InputManagerJuego) e).bindearAccionRaton(MouseEvent.BUTTON3,
				new GameAction("Disparar", InputManagerJuego.CAMBIAR_ARMA));

		offsetx = (int) (-getI().getScreenWidth() / 2);
		offsety = (int) (-getI().getScreenHeigth() / 2);
		universo = new BackgroundManager(recursos, 100, 30, this);
		mapa = new MiniMap(actores, enemigos, this);
		chat.setPosicion(getScreenWidth(), getScreenHeight());
		panel.setPosicion(getScreenWidth() - 280, getScreenHeight()
				- getScreenHeight() / 4 - 10, 275, getScreenHeight() / 4);
		backgroundSound = new Sonido(recursos,"/sounds/background.wav",true);
		
	}

	public void run() {
		backgroundSound.reproducir();
		this.getI().getScreen().getFullScreenWindow().setCursor(
				Toolkit.getDefaultToolkit().createCustomCursor(
						recursos.getImage("/images/interfaz/cursor.gif"),
						(new Point(0, 0)), "HiddenM"));
		getI().getScreen().getFullScreenWindow().setLayout(null);
		while (isEjecutandose()) {
			tini = System.nanoTime();// posible cambio a nanosegundos
			actualizar(tiempoTranscurrido);
			comprobarInputs();
			dibujar(getI().getScreen().getGraphics());
			
			tiempoTranscurrido = (System.nanoTime() - tini)/1000000;
		}
		gestorPaquetes.desconectar();
		backgroundSound.terminar();
	}

	public void dibujar(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getI().getScreenWidth(), getI().getScreenHeigth());

		universo.dibujar(g, offsetx, offsety);
		for (int a = actores.size() - 1; a > -1; a--) {
			actores.get(a).dibujar(g, offsetx, offsety);
		}

		for (int a = 0; a < enemigos.size(); a++) {
			enemigos.get(a).dibujar(g, offsetx, offsety);
		}
		mapa.dibujar(g);
		dibujarInterfaz(g);
		if (player.isMuerto())
			dibujarMuerto(g);

		getI().getScreen().update();

	}

	private static Color colorTransparente = new Color(0.8f, 0f, 0.0f, 0.7f);

	private void dibujarMuerto(Graphics g) {
		g.setColor(colorTransparente);
		g.fillRect(0, 0, getScreenWidth(), getScreenHeight());
		g.setColor(Color.white);
		g.drawString("Espera: \n"
				+ String.valueOf(5 - player.getDeadTimeLeft() / 1000),
				getScreenWidth() / 2, getScreenHeight() / 2);
	}

	public void dibujarInterfaz(Graphics g) {
		chat.dibujar(g);
		interfaz[0].getParent().paintComponents(g);
		if (panel.isMostrandose())
			panel.dibujar(g);

	}

	public void actualizar(long t) {
		GameActor actor;
		gestorPaquetes.actualizar(t);
		for (int a = 0; a < actores.size(); a++) {
			actor = actores.get(a);
			if (actor.isMarcadoEliminacion()) {
				actor = null;
				actores.remove(a);
			} else
				actor.actualizar(t);
		}
		for (int a = 0; a < enemigos.size(); a++) {
			actor = enemigos.get(a);
			if (actor.isMarcadoEliminacion()) {
				actor = null;
				actores.remove(a);
			} else
				actor.actualizar(t);
		}
		if (!player.isMuerto()) {
			universo.actualizar();
			moverPantalla();
		}

		comprobarColisiones();
	}

	private static boolean camara = true;

	public void moverPantalla() {
		int width = getI().getScreenWidth();
		int height = getI().getScreenHeigth();

		int bordeDerecho;
		int bordeIzquierdo;
		int bordeSuperior;
		int bordeInferior;

		if (camara) {

			bordeDerecho = offsetx + width - width / 3;
			bordeIzquierdo = offsetx + width / 3;
			bordeSuperior = offsety + height / 3;
			bordeInferior = offsety + height - height / 3;
		} else {
			width = getI().getScreenWidth();
			height = getI().getScreenHeigth();
			bordeDerecho = offsetx + width / 2;
			bordeIzquierdo = offsetx + width / 2;
			bordeSuperior = offsety + height / 2;
			bordeInferior = offsety + height / 2;
		}
		if (player.getX() > bordeDerecho)
			offsetx += (int) (player.getX() - bordeDerecho);
		else if (player.getX() < bordeIzquierdo)
			offsetx += (int) (player.getX() - bordeIzquierdo);

		if (player.getY() < bordeSuperior)
			offsety += (int) (player.getY() - bordeSuperior);
		else if (player.getY() > bordeInferior)
			offsety += (int) (player.getY() - bordeInferior);

		if (player.getX() > TAMANO_UNIVERSO) {
			player.setX(TAMANO_UNIVERSO);
			player.setMovx(0);
		} else if (player.getX() < -TAMANO_UNIVERSO) {
			player.setX(-TAMANO_UNIVERSO);
			player.setMovx(0);
		}
		if (player.getY() > TAMANO_UNIVERSO) {
			player.setY(TAMANO_UNIVERSO);
			player.setMovy(0);
		} else if (player.getY() < -TAMANO_UNIVERSO) {
			player.setY(-TAMANO_UNIVERSO);
			player.setMovy(0);
		}

	}

	public int getOffsetx() {
		return offsetx;
	}

	public int getOffsety() {
		return offsety;
	}

	public void comprobarInputs() {
		GameAction[] array = ((InputManagerJuego) e).getaccionesTeclado();
		if (!player.isMuerto())
			for (GameAction g : array) {
				if (g != null) {
					switch (g.getAccion()) {
					case InputManagerJuego.AVANZAR:
						if (g.isPulsado())
							player.acelerar();
						else if (!g.isProcesado())
							player.frenar();
						break;
					case InputManagerJuego.FRENAR:
						if (g.isPulsado())
							player.desacelerar(true);
						else if (!g.isProcesado())
							player.desacelerar(false);
						break;
					case InputManagerJuego.SCREENSHOT:
						if (g.isPulsado())
							;
						else if (!g.isProcesado()) {
							getI().getScreen().screenshot();
						}
						break;
					case InputManagerJuego.DISPARAR:
						if (g.isPulsado())
							player.disparar();
						break;

					case InputManagerJuego.CAMBIAR_ARMA:
						if (g.isPulsado())
							;
						else if (!g.isProcesado())
							player.nextWeapon();
						break;
					case InputManagerJuego.SALIR:
						if (g.isPulsado())
							stop();
						break;
					case InputManagerJuego.CAMBIAR_CAMARA:
						if (g.isPulsado())
							;
						else if (!g.isProcesado()) {
							camara = !camara;
						}
						break;
					case InputManagerJuego.MOSTRAR_BARRA:
						if (g.isPulsado())
							;
						else if (!g.isProcesado()) {
							chat.mostrarBarra();
						}
						break;
					case InputManagerJuego.VER_PUNTUACION:
						if (g.isPulsado())
							;
						else if (!g.isProcesado()) {
							panel.trigger();
						}
						break;
					}
				}
			}
		array = ((InputManagerJuego) e).getaccionesRaton();
		for (GameAction g : array) {
			if (g != null) {
				switch (g.getAccion()) {
				case InputManagerJuego.DISPARAR:
					if (g.isPulsado())
						player.disparar();
					break;
				case InputManagerJuego.CAMBIAR_ARMA:
					if (g.isPulsado())
						;
					else if (!g.isProcesado())
						player.nextWeapon();

					break;
				}
			}

		}
	}

	public String toString() {
		return "GameSceneJuego: Bucle de juego";
	}

	public void comprobarColisiones() {
		// Comprobamos colisiones
		GameActor actor, actor2;
		for (int i = 0; i < actores.size(); i++) {
			actor = actores.get(i);
			for (int j = i + 1; j < actores.size(); j++) {
				actor2 = actores.get(j);
				if (actor.colisionan(actor2)) {
					actor.onColision(actor2);
					actor2.onColision(actor);
				}
			}
		}

	}

	public void addGameActor(GameActor actor) {
		actores.add(actor);
	}

	public int getScreenHeight() {
		return this.getI().getScreenHeigth();
	}

	public int getScreenWidth() {
		return this.getI().getScreenWidth();
	}

	public Vector<GameActor> getActors() {
		return actores;
	}

	public Vector<GameActor> getEnemigos() {
		return enemigos;
	}

	public JTextField crearInputText(String alt) {
		JTextField j = new JTextField("");
		j.setToolTipText(alt);
		j.addActionListener(e);
		j.setIgnoreRepaint(true);
		j.setFocusable(true);
		j.setEnabled(true);
		j.setVisible(false);
		j.setOpaque(true);
		j.setForeground(Color.black);
		// j.setBorder(null);
		j.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		j.setSize(200, 20);
		return j;

	}

	public Chat getChat() {
		return chat;

	}

	public void recuperarFocus() {
		getI().getScreen().getFullScreenWindow().requestFocus();
	}

	public void regenerarFondo() {
		universo.regenerar();

	}
}
