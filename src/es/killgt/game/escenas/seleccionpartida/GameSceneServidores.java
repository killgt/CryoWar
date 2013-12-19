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
package es.killgt.game.escenas.seleccionpartida;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import es.killgt.engine.GameScene;
import es.killgt.engine.Sprite;
import es.killgt.game.escenas.jugando.GameSceneJuego;
import es.killgt.game.red.Conexion;
import es.killgt.game.red.paquetes.PaqueteNuevoJugador;
import es.killgt.servidor.Servidor;

public class GameSceneServidores extends GameScene {
	private boolean cargar;
	private Conexion conexion = new Conexion();

	public JButton crear, conectar;

	private Sprite fondo;

	private String nombre;

	private Sprite panel;

	public Servidor server;

	private int tipoNave;

	public GameSceneServidores(int tipoNave, String nombre) {
		super(new InputManagerServidores(), null);
		this.tipoNave = tipoNave;
		this.nombre = nombre;
		// quitamos los acentos...
		this.nombre = nombre.replace('à', 'a').replace('é', 'e').replace('í',
				'i').replace('ó', 'o').replace('ú', 'u');

		interfaz = new Component[6];
		interfaz[0] = crearInputText("127.0.0.1", "IP DEL SERVIDOR", 150);
		interfaz[1] = crearInputText("5678", "Puerto", 50);
		interfaz[2] = crearBoton("Conectar con el servidor.",
				"/images/menu/botonconectar.png",
				"/images/menu/botonconectarsel.png");
		interfaz[3] = crearBoton("Buscar servidores en la red local.",
				"/images/menu/botonbuscar.png",
				"/images/menu/botonbuscarsel.png");
		interfaz[4] = crearLabel("");
		interfaz[5] = new JSeparator();

		crear = (JButton) interfaz[3];
		conectar = (JButton) interfaz[2];

		((InputManagerServidores) e).setServidores(this);
	}

	private void actualizar(long tiempoTranscurrido) {

	}

	public void cargar() {
		PaqueteNuevoJugador p = new PaqueteNuevoJugador();
		p.id = conexion.getID();
		p.tipoNave = (byte) tipoNave;
		p.x = 0;
		p.y = 0;
		p.nombre = nombre;
		conexion.enviarObjeto(p, false);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		cargar = true;
	}

	public void cargarJuego() {
		this.stop();
		getI().pushScene(
				new GameSceneJuego("/recursosGameSceneJuego.cfg", tipoNave,
						conexion, nombre));
	}

	public void cargarRecursos() {
		panel = new Sprite(recursos);
		panel.addFrame("/images/menu/panelservidores.png");
		panel.setDuracionFrame(1);

		fondo = new Sprite(recursos);
		fondo.addFrame("/images/menu/fondo.gif");
		fondo.setDuracionFrame(1);

		interfaz[0].setLocation(getI().getScreenWidth() / 2
				- interfaz[0].getWidth() + 20,
				getI().getScreenHeigth() / 2 - 20);// ip
		interfaz[1].setLocation(interfaz[0].getX() + interfaz[0].getWidth()
				+ 30, interfaz[0].getY());
		interfaz[2].setLocation(interfaz[0].getX() + interfaz[0].getWidth()
				- 39, interfaz[0].getY() + 30);// conectar
		interfaz[3].setLocation(
				interfaz[2].getX() - interfaz[3].getWidth() - 1, interfaz[2]
						.getY());
		interfaz[4].setLocation(interfaz[0].getX() - 20, interfaz[0].getY()
				+ panel.getHeight() + 10);
		interfaz[5].setLocation(0, getI().getScreenHeigth());// relleno
	}

	public JButton crearBoton(String comentario, String rutaImagen,
			String rutaImagen2) {
		Icon icono = recursos.getIcon(rutaImagen);
		Icon icono2 = recursos.getIcon(rutaImagen2);
		JButton boton = new JButton();
		boton.addActionListener(e);
		boton.setIgnoreRepaint(true);
		boton.setToolTipText(comentario);
		boton.setFocusable(false);
		boton.setEnabled(true);
		boton.setBorder(null);
		boton.setContentAreaFilled(false);
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton.setIcon(icono);
		boton.setRolloverIcon(icono2);
		boton.setSize(icono.getIconWidth(), icono.getIconHeight());

		return boton;
	}

	public JTextField crearInputText(String contenido, String alt, int ancho) {
		JTextField j = new JTextField(contenido);
		j.setToolTipText(alt);
		j.addActionListener(e);
		j.setIgnoreRepaint(true);
		j.setFocusable(true);
		j.setEnabled(true);
		j.setOpaque(false);
		j.setForeground(Color.white);
		j.setBorder(null);
		j.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		j.setSize(ancho, 20);
		return j;

	}

	private JLabel crearLabel(String texto) {
		JLabel j = new JLabel(texto);
		j.setToolTipText(texto);
		j.setIgnoreRepaint(true);
		j.setFocusable(false);
		j.setEnabled(true);
		j.setForeground(Color.red);
		j.setBorder(null);
		j.setSize(300, 20);
		return j;
	}

	public void crearServidor() {
		int puerto = Integer.valueOf(((JTextField) interfaz[1]).getText());
		server = new Servidor(puerto, puerto + 1);
		System.out.println("Servidor abierto en " + puerto + "tcp , "
				+ (puerto + 1) + "udp");
		intentarConectar(true);
	}

	private void dibujar() {
		Graphics g = getI().getScreen().getGraphics();
		int width = 1 + getI().getScreenWidth() / fondo.getWidth();
		int heigth = 1 + getI().getScreenHeigth() / fondo.getHeight();
		for (int a = 0; a < heigth; a++)
			for (int b = 0; b < width; b++)
				fondo.dibujar(g, b * fondo.getWidth(), a * fondo.getHeight());
		panel.dibujar(g, interfaz[0].getX() - 40, interfaz[0].getY() - 2);
		interfaz[0].getParent().paintComponents(g);
		getI().getScreen().update();
	}

	public void intentarConectar(boolean local) {
		int puerto = Integer.valueOf(((JTextField) interfaz[1]).getText());
		String ip;
		if (!local)
			ip = ((JTextField) interfaz[0]).getText();
		else
			ip = "127.0.0.1";
		mensaje("Intentando conectar con " + ip + ":" + "5678");
		if (conexion.conectar(puerto, puerto + 1, ip))
			cargar();
		else
			mensaje("No se ha podido conectar con " + ip + ":" + "5678");

	}

	public void mensaje(String mensaje) {
		((JLabel) interfaz[4]).setText(mensaje);
	}

	public void run() {
		if (server != null)
			server.stop();
		long tini, tiempoTranscurrido = 0;
		getI().getScreen().getFullScreenWindow().setLayout(null);
		cargar = false;
		while (isEjecutandose()) {
			tini = System.currentTimeMillis();
			dibujar();
			actualizar(tiempoTranscurrido);
			tiempoTranscurrido = System.currentTimeMillis() - tini;
			if (cargar)
				cargarJuego();
		}

	}

	public void setIp(String ip) {
		((JTextField) interfaz[0]).setText(ip);
	}

	public String toString() {
		return "Menu de seleccion de partida";
	}

}
