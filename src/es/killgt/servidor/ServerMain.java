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
package es.killgt.servidor;

//import java.util.Scanner;

public class ServerMain {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Servidor server;
		// Scanner teclado = new Scanner(System.in);
		// System.out.println("Bienvenido al servidor de CryoEngine.");
		// System.out.println("Introduce un puerto de escucha o introduce 0 para dejar el "
		// +
		// "puerto por defecto [5678]:");
		// short puerto = teclado.nextShort();
		// if (puerto!=0)
		// server = new Servidor(puerto,puerto+1);
		// else
		server = new Servidor(5678, 5679);
	}
}
