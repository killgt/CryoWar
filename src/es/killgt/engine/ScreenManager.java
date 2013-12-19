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

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Esta clase manejará la pantalla.
 * Esta clase esta basada en el libro Developing Games in Java
 * Se han añadido la funcion para crear screenshots,
 * devolver un vector con todos los modos posibles a usar,
 * cambiar el modo actual a otro y optimizacion de imagenes a
 * la pantalla actual.
 * @author Kill
 */
public class ScreenManager {

	private GraphicsDevice device;

	/**
	 * Este constructor obtendra del sistema el controlador del dispositivo
	 * gráfico.
	 */
	protected ScreenManager() {
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
	}

	/**
	 * Esta funcion retorna un array de modos disponible
	 * 
	 * @return DisplayModes Modos disponibles en la máquina actual.
	 */
	protected DisplayMode[] getCompatibleDisplayModes() {
		return device.getDisplayModes();
	}

	/**
	 * Esta funcion devolverá el mejor modo disponible de nuestra lista.
	 * 
	 * @param modes
	 *            Array de modos.
	 * @return Retorna el mejor modo compatible de nuestra lista de modos
	 *         {@link #MODOS_POSIBLES}
	 */
	protected DisplayMode findFirstCompatibleMode(DisplayMode modes[]) {
		DisplayMode modosDisponibles[] = device.getDisplayModes();
		for (int i = 0; i < modes.length; i++) {
			for (int j = 0; j < modosDisponibles.length; j++) {
				if (sonIguales(modes[i], modosDisponibles[j])) {
					return modes[i];
				}
			}

		}
		return null;
	}

	/**
	 * Devuelve un vector con todos los modos de nuestra lista que sean
	 * compatibles
	 * 
	 * @param modes
	 *            Array de modos
	 * @return Vector de modos posibles.
	 */
	public Vector<DisplayMode> getAllCompatibles(DisplayMode modes[]) {
		DisplayMode goodModes[] = device.getDisplayModes();
		Vector<DisplayMode> v = new Vector<DisplayMode>();
		for (int i = 0; i < modes.length; i++) {
			for (int j = 0; j < goodModes.length; j++) {
				if (sonIguales(modes[i], goodModes[j])) {
					v.add(modes[i]);
				}
			}

		}
		return v;
	}

	/**
	 * @return Modo actual.
	 */
	public DisplayMode getModoActual() {
		return device.getDisplayMode();
	}

	/**
	 * Retorna si 2 modos son iguales.
	 * 
	 * @param mode1
	 * @param mode2
	 * @return True en caso de que ambos sean iguales.
	 */
	private boolean sonIguales(DisplayMode mode1, DisplayMode mode2) {
		if (mode1.getWidth() != mode2.getWidth()
				|| mode1.getHeight() != mode2.getHeight()) {
			return false;
		}

		if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
				&& mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
				&& mode1.getBitDepth() != mode2.getBitDepth()) {
			return false;
		}

		if (mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
				&& mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
				&& mode1.getRefreshRate() != mode2.getRefreshRate()) {
			return false;
		}

		return true;
	}

	/**
	 * Esta funcion simplemente pondra la aplicacion a pantalla completa.
	 * 
	 * @param dm
	 */
	protected void setFullScreen(DisplayMode dm) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFont(new Font("Dialog", Font.PLAIN, 13));
		frame.setBackground(Color.BLACK);
		frame.setForeground(Color.white);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setFocusTraversalKeysEnabled(false);
		device.setFullScreenWindow(frame);

		if (dm != null && device.isDisplayChangeSupported()) {
			try {
				device.setDisplayMode(dm);
			} catch (IllegalArgumentException ex) {
				System.out
						.println("No se ha podido poner a pantalla completa.");
			}
		}
		frame.createBufferStrategy(2);

	}

	/**
	 * Esta funcion recibe un modo y lo pone si es posible.
	 * 
	 * @param dm
	 *            Modo nuevo.
	 */
	public void cambiarModo(DisplayMode dm) {
		try {
			if (device.isDisplayChangeSupported()) {
				device.setFullScreenWindow(getFullScreenWindow());
				device.setDisplayMode(dm);
			} else
				System.out.println("No se permite el cambio de resolucion");
		} catch (IllegalArgumentException ex) {
			System.out
					.println("Error de argumentos... No se ha podido poner a pantalla completa. ");
		}
	}

	/**
	 * @return Retorna el entorno de dibujado.
	 */
	public Graphics2D getGraphics() {
		Window window = device.getFullScreenWindow();
		if (window != null) {
			BufferStrategy strategy = window.getBufferStrategy();
			return (Graphics2D) strategy.getDrawGraphics();
		} else {
			return null;
		}
	}

	/**
	 * Actualiza la pantalla con el doble buffer e intenta sincronizar la
	 * imagen.
	 */
	public void update() {
		Window window = device.getFullScreenWindow();

		if (window != null) {
			BufferStrategy strategy = window.getBufferStrategy();
			if (!strategy.contentsLost()) {
				strategy.show();
			}
		}
		// Sync the display on some systems.
		// (on Linux, this fixes event queue problems)
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * @return Retorna la ventana en pantalla.
	 */
	public Window getFullScreenWindow() {
		return device.getFullScreenWindow();
	}

	/**
	 * @return Retorna el ancho de la pantalla.
	 */
	public int getWidth() {
		return (device.getFullScreenWindow() != null ? device
				.getFullScreenWindow().getWidth() : 0);
	}

	/**
	 * @return Retorna el alto de la pantalla.
	 */
	public int getHeight() {
		return (device.getFullScreenWindow() != null ? device
				.getFullScreenWindow().getHeight() : 0);
	}

	/**
	 * Recupera el modo de pantalla no completa
	 */
	public void recuperarPantalla() {
		Window window = device.getFullScreenWindow();
		if (window != null) {
			window.dispose();
		}
		device.setFullScreenWindow(null);
	}

	/*
	 * public BufferedImage createCompatibleImage(int w, int h, int
	 * transparency) { Window window = device.getFullScreenWindow(); if (window
	 * != null) { GraphicsConfiguration gc = window.getGraphicsConfiguration();
	 * return gc.createCompatibleImage(w, h, transparency); } return null; }
	 */

	public String toString() {
		BufferCapabilities b = device.getFullScreenWindow().getBufferStrategy()
				.getCapabilities();
		String cadena = "";
		cadena += "\nInformacion de video:\n" + "PageFlipping: "
				+ b.isPageFlipping()
				+ "\nEs necesario que sea fullscreen para ello: "
				+ b.isFullScreenRequired() + "\nMultiBuffer: "
				+ ((Boolean) (b.isMultiBufferAvailable())).toString()
				+ "\n Memoria de video disponible: "
				+ device.getAvailableAcceleratedMemory();
		return cadena;
	}

	/**
	 * Esta funcion adapta una imagen a la pantalla.
	 * 
	 * @param image
	 *            Imagen a convertir
	 * @return Imagen convertida.
	 */
	public static BufferedImage toCompatibleImage(BufferedImage image) {
		/**
		 * Obtenemos la configuracion grafica actual.
		 */
		GraphicsConfiguration gfx_config = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		/**
		 * Si la imagen ya esta en el mismo color que la pantalla, la
		 * devolvemos.
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel()))
			return image;

		/**
		 * Si no lo esta, creamos una nueva con el tamaño y la transparencia de
		 * la anterior.
		 */
		BufferedImage new_image = gfx_config.createCompatibleImage(image
				.getWidth(), image.getHeight(), image.getTransparency());

		/**
		 * Obtenemos el contexto gráfico de la nueva imagen.
		 */
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		/**
		 * Dibujamos sobre la nueva imagen, la vieja
		 */
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		/**
		 * Retornamos la imagen optimizada.
		 */
		return new_image;
	}

	static long ultimaFoto = (long) (Math.random() * 50d);

	public void screenshot() {

		if (System.currentTimeMillis() - ultimaFoto > 1000) {

			try {
				Robot robot = new Robot();
				Rectangle captureSize = new Rectangle(Toolkit
						.getDefaultToolkit().getScreenSize());
				BufferedImage bufferedImage = robot
						.createScreenCapture(captureSize);

				File outputfile = new File(System.getProperty("user.home")
						+ "\\Desktop\\ScreenShot" + ultimaFoto + ".jpg");
				ImageIO.write(bufferedImage, "jpg", outputfile);
			} catch (IOException e) {

			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ultimaFoto = System.currentTimeMillis();
		}
	}

	protected static final DisplayMode MODOS_POSIBLES[] = {
			// new DisplayMode(1920, 1080, 32, 60),
			// new DisplayMode(1920, 1080, 24, 60),
			new DisplayMode(1920, 1080, 16, 60),
			new DisplayMode(1920, 1080, 8, 60),
			// new DisplayMode(1680, 1050, 32, 60),
			// new DisplayMode(1680, 1050, 24, 60),
			new DisplayMode(1680, 1050, 16, 60),
			new DisplayMode(1680, 1050, 8, 60),
			// new DisplayMode(1440, 900, 32, 60),
			// new DisplayMode(1440, 900, 24, 60),
			new DisplayMode(1440, 900, 16, 60),
			new DisplayMode(1440, 900, 8, 60),
			// new DisplayMode(1280, 1024, 32, 60),
			// new DisplayMode(1280, 1024, 24, 60),
			new DisplayMode(1280, 1024, 16, 60),
			new DisplayMode(1280, 1024, 8, 60),
			// new DisplayMode(1024, 768, 32, 60),
			// new DisplayMode(1024, 768, 24, 60),
			new DisplayMode(1024, 768, 16, 60),
			new DisplayMode(1024, 768, 8, 60),
			// new DisplayMode(1024, 600, 32, 60),
			// new DisplayMode(1024, 600, 24, 60),
			new DisplayMode(1024, 600, 16, 60),
			new DisplayMode(1024, 600, 8, 60),
			// new DisplayMode(800, 600, 32, 60),
			// new DisplayMode(800, 600, 24, 60),
			new DisplayMode(800, 600, 16, 60), new DisplayMode(800, 600, 8, 60) };
}
