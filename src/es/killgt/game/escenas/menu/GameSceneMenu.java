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
package es.killgt.game.escenas.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.RenderingHints;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JSeparator;

import es.killgt.engine.GameScene;
import es.killgt.engine.Sprite;
import es.killgt.game.escenas.menunave.GameSceneMenuNaves;

public class GameSceneMenu extends GameScene {

	public int cargarEscena;
	public boolean creditos;

	private Sprite fondo;
	private Sprite logo;
	private Sprite controles;
	private Font fuente;

	public byte opcionActual;
	public byte movimiento;

	public GameSceneMenu() {
		super(new InputManagerMenu(), null);
		interfaz = new Component[4];
		((InputManagerMenu) e).setInputManagerMenu(this);

		try {
			InputStream entrada;

			entrada = getClass().getResourceAsStream("/fuente.ttf");
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, entrada);
			fuente = dynamicFont.deriveFont(32f);
		} catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado la fuente");
		} catch (FontFormatException e) {
		} catch (IOException e) {
		}

		interfaz[0] = crearLabel("Jugar");
		interfaz[1] = crearLabel("Creditos");
		interfaz[2] = crearLabel("Salir");
		interfaz[3] = new JSeparator();

	}

	public void cargar(int escena) {
		if (escena == 0) {
			this.stop();
			this.getI().pushScene(new GameSceneMenuNaves());
		} else if (escena == 1)
			creditos = true;
		else
			this.stop();
	}

	public void cargarRecursos() {
		logo = new Sprite(recursos);
		logo.addFrame("/images/menu/titulo.png");
		logo.setDuracionFrame(1);

		fondo = new Sprite(recursos);
		fondo.addFrame("/images/menu/fondo.gif");
		fondo.setDuracionFrame(1);

		controles = new Sprite(recursos);
		controles.addFrame("/images/menu/credito.gif");
		controles.setDuracionFrame(1);

		for (int a = 0; a < interfaz.length - 1; a++)
			interfaz[a]
					.setLocation(
							(int) (getI().getScreenWidth() / 2 - interfaz[a]
									.getWidth() / 2), (int) (getI()
									.getScreenHeigth() / 2.3)
									+ a * 70);
		interfaz[3].setLocation(0, getI().getScreenHeigth() - 1);
	}

	private JButton crearLabel(String texto) {
		JButton j = new JButton(texto);
		j.setToolTipText(texto);
		j.addActionListener(getE());
		j.setText(texto);
		j.setBackground(new Color(0, 0, 0, 0));
		j.setIgnoreRepaint(true);
		j.setFocusable(false);
		j.setEnabled(true);
		j.setOpaque(false);
		j.setForeground(Color.red);
		j.setBorder(null);
		j.setSize(300, 50);
		j.setFont(fuente);
		return j;
	}

	public void dibujar() {
		Graphics g = getI().getScreen().getGraphics();
		int width = 1 + getI().getScreenWidth() / fondo.getWidth();
		int heigth = 1 + getI().getScreenHeigth() / fondo.getHeight();
		for (int a = 0; a < heigth; a++)
			for (int b = 0; b < width; b++)
				fondo.dibujar(g, b * fondo.getWidth(), a * fondo.getHeight());
		if (!creditos) {
			for (int a = 0; a < interfaz.length - 1; a++)
				if (a == opcionActual)
					interfaz[a].setForeground(Color.red);
				else
					interfaz[a].setForeground(Color.cyan);

			interfaz[0].getParent().paintComponents(g);
			logo.dibujar(g, getI().getScreenWidth() / 2 - logo.getWidth() / 2,
					(int) (getI().getScreenHeigth() / 6));
		} else
			controles.dibujar(g, getI().getScreenWidth() / 2
					- controles.getWidth() / 2,
					(int) (getI().getScreenHeigth() / 2)
							- controles.getHeight() / 2);
		
		getI().getScreen().update();
	}

	public void run() {
		long tini, tiempoTranscurrido = 0;
		getI().getScreen().getFullScreenWindow().setLayout(null);
		cargarEscena = -1;
		creditos = false;
		getI().getScreen().getGraphics().setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		while (isEjecutandose()) {
			tini = System.currentTimeMillis();
			dibujar();
			actualizar(tiempoTranscurrido);
			tiempoTranscurrido = System.currentTimeMillis() - tini;
			if (cargarEscena > -1)
				cargar(cargarEscena);
		}
	}

	private static long lastMovement = 0;

	private void actualizar(long tiempoTranscurrido) {
		lastMovement += tiempoTranscurrido;
		if (movimiento != 0 && lastMovement > 200) {
			lastMovement = 0;
			if (movimiento == 1 && opcionActual > 0)
				--opcionActual;
			else if (movimiento == 2 && opcionActual < 2)
				++opcionActual;
			movimiento = 0;
		}
	}

	public String toString() {
		return "Menu inicial";
	}
}
