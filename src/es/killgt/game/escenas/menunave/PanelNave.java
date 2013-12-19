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

import java.awt.Graphics;

import javax.swing.JButton;

import es.killgt.engine.ResourceManager;
import es.killgt.engine.Sprite;

public class PanelNave {

	private Sprite panel;
	private Sprite cara;
	private Sprite nave;

	private int x, y, tipoNave;
	private JButton boton;

	public PanelNave(ResourceManager recursos, int tipoNave, int width,
			int height, JButton boton) {
		Sprite temp = new Sprite(recursos);

		panel = new Sprite(recursos);
		cara = new Sprite(recursos);
		nave = new Sprite(recursos);
		this.tipoNave = tipoNave;
		cara.addFrame("/images/menu/pilotos/personaje" + tipoNave + ".gif");
		temp.addFrame("/images/menu/panel.png");
		nave.addFrame("/images/naves/nave" + tipoNave + "/frame1.png");

		y = height / 2 - temp.getHeight() / 2;
		int centro = width / 2;

		switch (tipoNave) {
		case 0:
			x = centro - temp.getWidth() * 2;
			panel.addFrame("/images/menu/panelI.png");

			break;
		case 1:
			x = centro - temp.getWidth();
			panel.addFrame("/images/menu/panel.png");

			break;
		case 2:
			x = centro;
			panel.addFrame("/images/menu/panel.png");

			break;
		case 3:
			x = centro + temp.getWidth();
			panel.addFrame("/images/menu/panelD.png");

			break;
		}
		panel.setDuracionFrame(1);
		cara.setDuracionFrame(1);
		this.boton = boton;
		this.boton.setLocation(x + panel.getWidth() / 2 - boton.getWidth() / 2,
				y + panel.getHeight() - panel.getHeight() / 8 - 10);
		this.boton.validate();

	}

	public void dibujar(Graphics g) {

		panel.dibujar(g, x, y);
		cara.dibujar(g, x + 40, y + 40);
		nave
				.dibujar(g, x + panel.getWidth() / 2 - nave.getWidth() / 2,
						y + 200);
		switch (tipoNave) {
		case 0:
			g.drawString("Nave de aceleración", x + 35, y + 360);
			g.drawString("Aceleracion: 10.0", x + 35, y + 390);
			g.drawString("Velocidad Max.: 6.0", x + 35, y + 410);
			g.drawString("Vida: 80", x + 35, y + 430);
			break;
		case 1:
			g.drawString("Nave persecución", x + 35, y + 360);
			g.drawString("Aceleracion: 7.0", x + 35, y + 390);
			g.drawString("Velocidad Max.: 8.0", x + 35, y + 410);
			g.drawString("Vida: 90", x + 35, y + 430);
			break;
		case 2:
			g.drawString("Nave intermedia", x + 35, y + 360);
			g.drawString("Aceleracion: 8.8", x + 35, y + 390);
			g.drawString("Velocidad Max.: 7.5", x + 35, y + 410);
			g.drawString("Vida: 85", x + 35, y + 430);
			break;
		case 3:
			g.drawString("Destructor", x + 35, y + 360);
			g.drawString("Aceleracion: 3.0", x + 35, y + 390);
			g.drawString("Velocidad Max.: 12.0", x + 35, y + 410);
			g.drawString("Vida: 180", x + 35, y + 430);
			break;
		}

	}

}
