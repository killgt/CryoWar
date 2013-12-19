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

import java.io.IOException;
import java.util.Vector;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import es.killgt.game.red.paquetes.*;

public class Servidor {
	private Vector<Cliente> clientes = new Vector<Cliente>(32);
	private Server servidor;
	private final static String prefijoComando = "Co";
	private boolean generado;

	public Servidor(int puertoTCP, int puertoUDP) {
		servidor = new Server();
		servidor.start();
		try {
			servidor.bind(puertoTCP, puertoUDP);
		} catch (IOException e) {
			System.out
					.println("No se ha podido iniciar el servidor: puertos ocupados");
			System.exit(-1);
		}
		servidor.addListener(new Listener() {
			public void connected(Connection connection) {
				procesarConexion(connection);
			}

			public void disconnected(Connection connection) {
				procesarDesconexion(connection);
			}

			public void received(Connection connection, Object object) {
				if (object instanceof Paquete)
					procesarObjecto(connection, (Paquete) object);
			}
		});

		registrarClases();
	}

	public Cliente buscarCliente(short id) {
		Cliente c;
		for (int a = 0; a < clientes.size(); a++) {
			c = clientes.get(a);
			if (c.getID() == id)
				return c;
		}
		return null;
	}

	public void borrarCliente(int ID) {
		Cliente c;
		for (int a = 0; a < clientes.size(); a++) {
			c = clientes.get(a);
			if (c.getID() == ID) {
				clientes.remove(a);
				c = null;				
			}
		}
	}
	private final static PaqueteNuevoJugador pn = new PaqueteNuevoJugador();
	public void procesarConexion(Connection nuevaConexion) {
		clientes.add(new Cliente((short) nuevaConexion.getID()));
		Cliente c;
		for (int a = 0; a < clientes.size(); a++) {
			c = clientes.get(a);
			if (nuevaConexion.getID() != c.getID()) {				
				pn.id = (short) c.getID();
				pn.tipoNave = c.getTipoNave();
				pn.x = 0;
				pn.y = 0;
				pn.nombre = c.getNombre();
				servidor.sendToTCP(nuevaConexion.getID(), pn);
			}
		}

	}

	public void procesarDesconexion(Connection desconexion) {
		PaqueteSalidaJugador p = new PaqueteSalidaJugador();
		p.id = (short) desconexion.getID();
		servidor.sendToAllTCP(p);
		borrarCliente(p.id);
	}

	public void procesarObjecto(Connection origen, Paquete paqueteRecibido) {
		switch (paqueteRecibido.tipoPaquete) {
		case TipoPaquete.TIPO_POSICION:
			servidor.sendToAllExceptUDP(origen.getID(), paqueteRecibido);
			break;
		case TipoPaquete.TIPO_NUEVA_BALA:
			servidor.sendToAllExceptUDP(origen.getID(), paqueteRecibido);
			break;
		case TipoPaquete.TIPO_IMPACTO_BALA:
			servidor.sendToAllExceptUDP(origen.getID(), paqueteRecibido);
			break;
		case TipoPaquete.TIPO_NUEVO_JUGADOR:
			servidor.sendToAllExceptTCP(origen.getID(), paqueteRecibido);
			Cliente c = buscarCliente((short) origen.getID());
			c.config((PaqueteNuevoJugador) paqueteRecibido);
			break;
		case TipoPaquete.TIPO_MUERTE:
			addMuerte((PaqueteMuerte) paqueteRecibido);
			break;
		case TipoPaquete.TIPO_SALIDA_JUGADOR:
			servidor.sendToAllExceptTCP(origen.getID(), paqueteRecibido);
			break;
		case TipoPaquete.TIPO_POWER_UP:

			break;
		case TipoPaquete.TIPO_MENSAJE:
			if (((PaqueteMensaje) paqueteRecibido).getMensaje().indexOf(
					prefijoComando) == -1)
				servidor.sendToAllUDP(paqueteRecibido);
			else
				comando(((PaqueteMensaje) paqueteRecibido).getMensaje(), origen);
			break;
		case TipoPaquete.TIPO_SOLICITUD:
			servidor.sendToUDP(origen.getID(), generarPuntuacion());
			break;
		}
		if (Math.random() * 1000 * clientes.size() > 999 * clientes.size())
			mandarRecolectable((int) (Math.random() * 10000 - 5000),
					(int) (Math.random() * 10000 - 5000));

	}

	/**
	 * Buscamos al autor y le sumamos un punto, buscamos al muerto y le añadimos
	 * unamuerte
	 * 
	 * @param p
	 *            paquete con los datos de la muerte
	 */
	private final static PaqueteMensaje msj = new PaqueteMensaje();

	private void addMuerte(PaqueteMuerte p) {
		Cliente asesino = buscarCliente(p.asesino);
		if (asesino != null)
			asesino.addPoint();
		Cliente muerto = buscarCliente(p.idMuerto);
		if (muerto != null)
			muerto.addDeath();
		if (asesino != null && muerto != null) {
			msj.setMensaje(asesino.getNombre() + " ha matado a "
					+ muerto.getNombre() + ".");
			servidor.sendToAllUDP(msj);
			mandarRecolectable(p.x, p.y);
		}
		generado = false;
	}

	public final static PaquetePowerUp pu = new PaquetePowerUp();

	private void mandarRecolectable(int x, int y) {
		pu.IDpowerUp = (short) (Math.random() * 30000);
		pu.tipo = (byte) (Math.random() * 2);
		pu.x = x;
		pu.y = y;
		pu.cantidad = 1;
		servidor.sendToAllUDP(pu);
	}

	public void comando(String linea, Connection c) {
		String comando = linea.substring(linea.indexOf(prefijoComando)
				+ prefijoComando.length());

		if (comando.equals("exit"))
			System.exit(0);
		else if (comando.equals("getplayers"))
			for (Cliente cl : clientes) {
				msj.setMensaje(cl.toString());
				servidor.sendToUDP(c.getID(), msj);
			}
		else if (comando.equals("powerup")) {
			mandarRecolectable(0, 0);
		}

	}

	private final static PaquetePuntuacion paquetePuntuacion = new PaquetePuntuacion();

	public PaquetePuntuacion generarPuntuacion() {
		if (!generado) {
			// ordenamos los jugador por muertes...
			Cliente primero, segundo, temp;
			for (int a = 0; a < clientes.size(); a++) {
				primero = clientes.get(a);
				for (int b = 0; b < clientes.size(); b++) {
					segundo = clientes.get(b);
					if (primero.compareTo(segundo) > 0) {
						temp = segundo;
						clientes.set(b, primero);
						clientes.set(a, temp);
					}
				}
			}
			// Creamos nuestro paquete...
			paquetePuntuacion.puntuacion = "";
			for (int a = 0; a < clientes.size(); a++) {
				primero = clientes.get(a);
				paquetePuntuacion.puntuacion += primero.getNombre() + ";";
				paquetePuntuacion.puntuacion += primero.getAsesinatos() + ";";
				paquetePuntuacion.puntuacion += primero.getMuertes() + ";";
			}

			generado = true;
		}
		return paquetePuntuacion;
	}

	private void registrarClases() {
		Kryo kryo = servidor.getKryo();

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

	public void stop() {
		servidor.sendToAllTCP(new PaqueteDesconexion());
		servidor.close();
	}
}
