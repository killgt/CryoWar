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
package es.killgt.game.decoration;

import java.awt.Rectangle;

import es.killgt.engine.CoreInterface;
import es.killgt.engine.GameActor;
import es.killgt.engine.ResourceManager;
import es.killgt.engine.Utils;

public class NaveDecoracion extends GameActor {
	private int destinoX, destinoY;
	private CoreInterface interfaz;

	private float anguloDeseado;
	private float ANGULAR_MAX_SPEED = 0.0008f;
	BackgroundMenuManager background;

	public NaveDecoracion(ResourceManager r, float x, float y, int tipoNave,
			CoreInterface interfaz, BackgroundMenuManager background) {
		super(r, x, y, (short) 0);
		this.interfaz = interfaz;
		this.background = background;
		switch (tipoNave) {
		case 0:
			currentSprite.addFrame("/images/naves/nave0/frame2.png");
			currentSprite.addFrame("/images/naves/nave0/frame3.png");
			currentSprite.addFrame("/images/naves/nave0/frame4.png");
			currentSprite.addFrame("/images/naves/nave0/frame5.png");
			currentSprite.setDuracionFrame(50);
			break;
		case 1:

			currentSprite.addFrame("/images/naves/nave1/frame2.png");
			currentSprite.addFrame("/images/naves/nave1/frame3.png");
			currentSprite.addFrame("/images/naves/nave1/frame4.png");
			currentSprite.addFrame("/images/naves/nave1/frame5.png");
			currentSprite.addFrame("/images/naves/nave1/frame6.png");
			currentSprite.addFrame("/images/naves/nave1/frame7.png");
			currentSprite.addFrame("/images/naves/nave1/frame6.png");
			currentSprite.addFrame("/images/naves/nave1/frame5.png");
			currentSprite.addFrame("/images/naves/nave1/frame4.png");
			currentSprite.setDuracionFrame(50);
			break;
		case 2:

			currentSprite.addFrame("/images/naves/nave2/frame2.png");
			currentSprite.addFrame("/images/naves/nave2/frame3.png");
			currentSprite.addFrame("/images/naves/nave2/frame4.png");
			currentSprite.addFrame("/images/naves/nave2/frame5.png");
			currentSprite.addFrame("/images/naves/nave2/frame6.png");
			currentSprite.addFrame("/images/naves/nave2/frame7.png");
			currentSprite.addFrame("/images/naves/nave2/frame6.png");
			currentSprite.addFrame("/images/naves/nave2/frame5.png");
			currentSprite.addFrame("/images/naves/nave2/frame4.png");
			currentSprite.addFrame("/images/naves/nave2/frame3.png");
			currentSprite.setDuracionFrame(50);
			break;
		case 3:
			currentSprite.addFrame("/images/naves/nave3/frame2.png");
			currentSprite.addFrame("/images/naves/nave3/frame3.png");
			currentSprite.addFrame("/images/naves/nave3/frame4.png");
			currentSprite.addFrame("/images/naves/nave3/frame5.png");
			currentSprite.addFrame("/images/naves/nave3/frame6.png");
			currentSprite.addFrame("/images/naves/nave3/frame5.png");
			currentSprite.addFrame("/images/naves/nave3/frame4.png");
			currentSprite.addFrame("/images/naves/nave3/frame3.png");
			currentSprite.setDuracionFrame(70);
			break;
		}
		setDestino();
	}

	public void setDestino() {
		destinoX = (int) (Math.random() * (interfaz.getScreenWidth() * 2) - interfaz
				.getScreenWidth());
		destinoY = (int) (Math.random() * (interfaz.getScreenHeigth() * 2) - interfaz
				.getScreenHeigth());
		anguloDeseado = (float) Math.atan2(destinoY - y, destinoX - x);
	}

	public void onColision(GameActor actor) {

	}

	public boolean enDestino() {
		Rectangle destino = new Rectangle(destinoX, destinoY, 100, 100);
		if (destino.intersects(new Rectangle((int) x, (int) y, (int) (this
				.getSprite().getWidth() * 1.5), (int) (this.getSprite()
				.getWidth() * 1.5))))
			return true;
		return false;
	}

	public void think(long tiempoTranscurrido) {
		if (enDestino())
			setDestino();
		anguloDeseado = (float) Math.atan2(destinoY - y, destinoX - x);
		float diferencia = Utils.generarAngulo(anguloDeseado - angulo);
		diferencia = Utils.clamp(diferencia,
				(float) (ANGULAR_MAX_SPEED * tiempoTranscurrido),
				(float) (-ANGULAR_MAX_SPEED * tiempoTranscurrido));

		angulo = Utils.generarAngulo(angulo - diferencia);

		movx = (float) Math.cos(angulo) * 0.2f;
		movy = (float) Math.sin(angulo) * 0.2f;

	}

}
