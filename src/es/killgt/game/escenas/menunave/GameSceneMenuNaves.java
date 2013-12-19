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
package es.killgt.game.escenas.menunave;

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
import es.killgt.game.decoration.BackgroundMenuManager;
import es.killgt.game.escenas.seleccionpartida.GameSceneServidores;

public class GameSceneMenuNaves extends GameScene {
	private int cargarNave = -1;

	private PanelNave[] paneles = new PanelNave[4];
	private BackgroundMenuManager background;

	public GameSceneMenuNaves() {
		super(new InputManagerMenuNaves(), null);
		((InputManagerMenuNaves) e).setMenu(this);
		interfaz = new Component[8];
		for (int a = 0; a < 4; a++)
			interfaz[a] = crearBoton("Nave " + a,
					"/images/menu/BotonElegir.png",
					"/images/menu/BotonElegirSel.png");
		interfaz[4] = crearLabel("Introduce tu nombre:");
		interfaz[5] = crearInputText("Introduce tu nombre:");
		interfaz[6] = crearBoton("Salir", "/images/menu/exit2.png",
				"/images/menu/exit1.png");
		interfaz[7] = new JSeparator();// el ultimo es de relleno para evitar
		// bug raro

	}

	public void cargarRecursos() {
		background = new BackgroundMenuManager(recursos, getI());
		for (int a = 0; a < paneles.length; a++)
			paneles[a] = new PanelNave(recursos, a, getI().getScreenWidth(),
					getI().getScreenHeigth(), (JButton) interfaz[a]);

		interfaz[4].setLocation(getI().getScreenWidth() / 2
				- interfaz[5].getWidth() / 2 - interfaz[4].getWidth(), getI()
				.getScreenHeigth()
				/ 2 - 310 - interfaz[4].getHeight());

		interfaz[5].setLocation(getI().getScreenWidth() / 2
				- interfaz[5].getWidth() / 2, getI().getScreenHeigth() / 2
				- 310 - interfaz[4].getHeight());

		interfaz[6].setLocation(getI().getScreenWidth() - 5
				- interfaz[6].getWidth(), 5);
		interfaz[7].setLocation(0, getI().getScreenHeigth());
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

	public JTextField crearInputText(String alt) {
		JTextField j = new JTextField(System.getProperty("user.name"));
		j.setToolTipText(alt);
		j.addActionListener(e);
		j.setIgnoreRepaint(true);
		j.setFocusable(true);
		j.setEnabled(true);
		j.setOpaque(false);
		j.setForeground(Color.white);
		// j.setBorder(null);
		j.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		j.setSize(200, 20);
		return j;

	}

	private JLabel crearLabel(String texto) {
		JLabel j = new JLabel(texto);
		j.setToolTipText(texto);
		j.setIgnoreRepaint(true);
		j.setFocusable(false);
		j.setEnabled(true);
		j.setForeground(Color.white);
		j.setBorder(null);
		j.setSize(150, 20);
		return j;
	}

	public void dibujarFondo(Graphics g, long tiempoTranscurrido) {
		background.actualizar(tiempoTranscurrido);
		background.dibujar(g);
	}

	public void dibujarInterfaz(Graphics g) {

		for (PanelNave p : paneles)
			p.dibujar(g);

		interfaz[0].getParent().paintComponents(g);
		interfaz[0].getParent().repaint();

	}

	public void iniciarJuego(int tipoNave) {
		stop();
		String nombre = ((JTextField) interfaz[5]).getText();
		this.getI().pushScene(new GameSceneServidores(tipoNave, nombre));
	}

	public void setCargarNave(int cargarNave) {
		this.cargarNave = cargarNave;
	}

	public void run() {
		long tini, tiempoTranscurrido = 0;
		getI().getScreen().getFullScreenWindow().setLayout(null);
		cargarNave = -1;
		while (isEjecutandose()) {
			tini = System.currentTimeMillis();
			Graphics g = getI().getScreen().getGraphics();
			dibujarFondo(g, tiempoTranscurrido);
			dibujarInterfaz(g);
			getI().getScreen().update();
			if (cargarNave != -1)
				iniciarJuego(cargarNave);
			tiempoTranscurrido = System.currentTimeMillis() - tini;
		}
	}

	public String toString() {
		return "Menu de naves";
	}
}
