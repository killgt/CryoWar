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

import java.util.Random;

/**
 * Esta clase contiene utilidades necesarias.
 * 
 * @author Agustín Rodriguez killgt@gmail.com
 * 
 */
public class Utils {
	public static Random azar = new Random();

	public static float posicionToAngulo(float x, float y) {
		return (float) ((Math.atan2(y, x) * 180) / Math.PI);
	}

	public static float posicionToAnguloInRadianes(float x, float y) {
		return (float) Math.atan2(y, x);
	}

	public static float radianesToAngulo(float angulo) {
		return (float) ((angulo * 180) / Math.PI);
	}

	public static void log(String cadena) {
		System.out.println(cadena);
	}

	public static float generarAngulo(float radianes) {
		while (radianes < -Math.PI)
			radianes += Math.PI * 2;
		while (radianes > Math.PI)
			radianes -= Math.PI * 2;
		return radianes;
	}

	public static float clamp(float valor, float min, float max) {
		if (valor > max)
			return max;
		if (valor < min)
			return min;
		return valor;
	}

	public static double sqrt(double x, int precision) {
		if (x == 0)
			return 0;
		double root = x / 2;
		for (int k = 0; k < precision; k++)
			root = (root + (x / root)) / 2;
		return root;
	}
}
