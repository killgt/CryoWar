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
package es.killgt.game.red;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Vector;

import es.killgt.game.red.paquetes.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Conexion {
	private Client cliente;
	private Vector<Paquete> colaPaquetes = new Vector<Paquete>(500);

	public Conexion() {
		cliente = new Client();
		cliente.start();

		cliente.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				procesarObjecto(connection, object);
			}
		});
		registrarClases();
	}

	private void registrarClases() {
		Kryo kryo = cliente.getKryo();
		kryo.register(PaqueteBala.class);
		kryo.register(PaqueteImpactoBala.class);
		kryo.register(PaqueteMuerte.class);
		kryo.register(PaqueteNuevoJugador.class);
		kryo.register(PaquetePosicion.class);
		kryo.register(PaquetePowerUp.class);
		kryo.register(PaqueteSalidaJugador.class);
		kryo.register(PaqueteMensaje.class);
		kryo.register(PaquetePuntuacion.class);
		kryo.register(PaqueteSolicitud.class);
		kryo.register(PaqueteDesconexion.class);

	}

	public boolean hayPaquetes() {
		return (colaPaquetes.size() > 0);
	}

	public boolean conectar(int puertoTCP, int puertoUDP, String IP) {
		try {
			cliente.connect(5000, IP, puertoTCP, puertoUDP);
		} catch (IOException e) {
			System.out.println("No se ha podido conectar");
			return false;
		}
		return true;
	}

	public void enviarObjeto(Object objeto, boolean UDP) {
		if (UDP)
			cliente.sendUDP(objeto);
		else
			cliente.sendTCP(objeto);
	}

	public Vector<Paquete> getPaquetes() {
		return colaPaquetes;
	}

	private void procesarObjecto(Connection connection, Object object) {
		if (object instanceof Paquete)
			colaPaquetes.add((Paquete) object);
		else
			System.out.println("No se que se ha recibido");
	}

	public String buscarServer(int puertoTCP) {
		InetAddress i = cliente.discoverHost(puertoTCP, puertoTCP + 1);
		System.out.println("buscando en el puerto " + puertoTCP);
		if (i != null)
			return i.getHostAddress();
		return null;
	}

	public short getID() {
		return (short) cliente.getID();
	}

}
