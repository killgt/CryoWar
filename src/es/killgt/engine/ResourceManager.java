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

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 * Esta clase gestiona que no haya 2 veces en memoria el mismo recurso. * TODO:
 * Hacer que tambien se gestionen los sonidos
 * 
 * @author Kill
 * 
 */
public class ResourceManager {

	private HashMap<String, BufferedImage> imagenes;
	private HashMap<String, AudioInputStream> sonidos;
	public static boolean debug = true;

	// private HashMap sonidos;

	/**
	 * Constructor básico. La ruta recibida se leerá y se precargarán las
	 * imagenes y sonidos..
	 */
	public ResourceManager(String ruta) {
		imagenes = new HashMap<String, BufferedImage>();
		sonidos = new HashMap<String, AudioInputStream>();
		if (ruta != null) {
			try {
				String sCadena;
				int posSeparador = 0;
				InputStream is = getClass().getResourceAsStream(ruta);
				if (is == null)
					return;
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader bf = new BufferedReader(isr);
				while ((sCadena = bf.readLine()) != null) {
					// obtenemos los 2 valores separados por ; 'tipo;ruta;
					if (sCadena.charAt(0) == '#') {
						if (debug)
							System.out.println("------ Cargando: " + sCadena
									+ " -------");
						continue;
					}
					posSeparador = sCadena.indexOf(';');
					if (sCadena.substring(0, posSeparador).equalsIgnoreCase(
							"imagen"))
						getImage(sCadena.substring(posSeparador + 1));
					else
						;// Se carga el sonido
				}
			} catch (FileNotFoundException e) {
				System.out
						.println("No se ha hayado el fichero de pre-carga de texturas");
			} catch (IOException e) {
			}

		}
	}

	/**
	 * Esta funcion retorna la imagen desde disco (Carpeta con los class del
	 * proyecto)
	 * 
	 * @param ruta
	 *            Ruta donde se haya la imagen
	 * @return Image Imagen cargada
	 */
	private BufferedImage cargarImagen(String ruta) {
		try {
			if (debug)
				System.out.println("Cargando:'" + ruta + "'.");
			URL r = getClass().getResource(ruta);
			if (r != null)
				return ScreenManager.toCompatibleImage(ImageIO.read(r));
			else
				return ImageIO
						.read(getClass().getResource("/images/error.png"));
		} catch (IOException e) {
			System.out.println("Error al cargar imagen '" + ruta + "'.");
		} catch (IllegalArgumentException e) {
		}
		return null;
	}

	public ImageIcon getIcon(String ruta) {
		ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
		return icon;
	}

	/**
	 * Esta funcion devolvera una imagen desde memoria si ya esta cargada si no,
	 * la carga.
	 * 
	 * @param ruta
	 *            Ruta hacia la imagen, servirï¿½ de identificador en el hashmap
	 * @return Image Imagen desde memoria
	 */
	public BufferedImage getImage(String ruta) {

		BufferedImage i = imagenes.get(ruta);
		if (i == null) {// si aun no esta cargada
			i = cargarImagen(ruta);
			imagenes.put(ruta, i);
		}
		return i;
	}

	/**
	 * Vacia el hash de imagenes.
	 */
	public void finalize() {
		imagenes.clear();
		System.out.println("Descargando resourceManager");
	}

	public AudioInputStream  getSonido(String ruta) throws UnsupportedAudioFileException, IOException {
		AudioInputStream  f = sonidos.get( ruta);
		if (f != null)
			return f;
		f = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(ruta));
		sonidos.put(ruta, f);
		return f;
	}
}
