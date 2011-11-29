/*
 * Copyright (c) 2011, Julio Jiménez, René Toro, José Vargas. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This file is part of JTaxi.
 * 
 * JTaxi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTaxi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTaxi.  If not, see <http://www.gnu.org/licenses/>.
 */
package cl.pucv.eii.jtaxi.modelo;

import java.util.Iterator;

import cl.pucv.eii.jtaxi.utilidades.listas.Lista;
import cl.pucv.eii.jtaxi.utilidades.listas.ListaDoble;

public class Central {

	private String nombre;
	private ListaDoble<Flota> flotas;
	private ListaDoble<Sector> sectores;

	public Central(String nombre) {
		this.nombre = nombre;
		flotas = new ListaDoble<Flota>();
		sectores = new ListaDoble<Sector>();
	}

	public boolean agregarFlota(Flota f) {
		if (f != null && buscarFlota(f.getNombre().toLowerCase()) == null) {
			flotas.agregar(f);
			return true;
		}
		return false;
	}

	public boolean eliminarFlota(String nombre) {
		return flotas.eliminar(buscarFlota(nombre));
	}

	/**
	 * Retorna la flota que contiene al taxista con rut == rut.
	 * 
	 * @param rut
	 * @return
	 */
	public Flota buscarFlotaTaxista(Rut rut) {
		for (Flota f : flotas) {
			if (f.buscarTaxista(rut) != null)
				return f;
		}
		return null;
	}

	/**
	 * Retorna la flota que contiene al Taxi con patente p.
	 * 
	 * @param rut
	 * @return
	 */
	public Flota buscarFlotaTaxi(String p) {
		for (Flota f : flotas) {
			if (f.buscarTaxi(p) != null)
				return f;
		}
		return null;
	}

	/**
	 * Retorna la flota con nombre equivalente a nombre.
	 * 
	 * @param nombre
	 * @return
	 */
	public Flota buscarFlota(String nombre) {
		for (Flota f : flotas)
			if (f.getNombre().equalsIgnoreCase(nombre))
				return f;
		return null;
	}

	public void listarFlotas(Lista<Flota> lista) {
		for (Flota f : flotas)
			lista.agregar(f);
	}

	public boolean agregarSector(Sector s) {
		if (s != null && buscarSector(s.getNombre().toLowerCase()) == null) {
			sectores.agregar(s);
			return true;
		}
		return false;
	}

	public boolean eliminarSector(String nombre) {
		ListaDoble<Paradero> paraderos = new ListaDoble<>();
		Sector s = buscarSector(nombre);

		if (s == null)
			return false;
		s.listarParaderos(paraderos);

		for (Paradero p : paraderos)
			for (Flota f : flotas)
				f.eliminarParadero(p.getNombre());

		return sectores.eliminar(s);
	}

	public Sector buscarSector(String nombre) {
		for (Sector s : sectores)
			if (s.getNombre().equalsIgnoreCase(nombre))
				return s;
		return null; // en caso que no existe el sector con nombre n
	}

	public Sector buscarSector(Paradero p) {
		if (p == null)
			return null;
		for (Sector s : sectores)
			if (s.buscarParadero(p.getNombre()) != null)
				return s;
		return null;
	}

	public void listarSectores(Lista<Sector> lista) {
		for (Sector s : sectores)
			lista.agregar(s);
	}

	public Paradero buscarParadero(String nombre) {
		Paradero p;
		for (Sector s : sectores) {
			p = s.buscarParadero(nombre);
			if (p != null)
				return p;
		}
		return null;
	}

	public boolean agregarTaxiFlota(Taxi taxi, String flota) {
		if (taxi == null)
			return false;
		Flota f = buscarFlota(flota);
		if (f == null)
			return false;
		
		if (buscarFlotaTaxi(taxi.getPatente()) == null){
			f.agregarTaxi(taxi);
			return true;
		}
		
		return false;

	}
	
	public boolean agregarTaxiFlota(Taxi taxi, Flota flota){
		if(flota == null) return false;
		return agregarTaxiFlota(taxi, flota.getNombre());
	}
	
	public boolean agregarPasajeroTaxi(Pasajero pasajero, String patente){
		if(pasajero == null || patente == null)
			return false;
		
		for(Flota f: flotas)
			if(f.buscarTaxiPasajero(pasajero.getRut()) != null)
				return false;
		
		Flota f = buscarFlotaTaxi(patente);
		if (f == null)
			return false;
		return f.agregarPasajeroTaxi(pasajero, patente);
	}
	
	public boolean eliminarRut(Rut r){
		return (eliminarTaxista(r) || eliminarPasajero(r));
	}
	
	public boolean eliminarPasajero(Rut r){
		for (Flota f: flotas)
				if (f.eliminarPasajero(r))
					return true;
		return false;
	}

	public boolean agregarParaderoSector(Paradero p, String sector) {
		if (p == null)
			return false;

		for (Sector s : sectores)
			if (s.buscarParadero(p.getNombre()) != null)
				return false;

		buscarSector(sector).agregarParadero(p);
		return true;
	}

	public boolean eliminarParadero(String nombre) {
		boolean encontrado = false;
		for (Iterator<Sector> itr = sectores.iterator(); !encontrado
				&& itr.hasNext();) {
			encontrado = itr.next().eliminarParadero(nombre);
		}
		if (!encontrado)
			return false;
		for (Flota f : flotas) {
			f.eliminarParadero(nombre);
		}
		return true;
	}

	public boolean eliminarTaxista(Rut rut) {
		Flota f = buscarFlotaTaxista(rut);
		if (f == null)
			return false;
		return f.eliminarTaxista(rut);
	}

	public boolean agregarTaxistaFlota(Taxista nuevo, Flota flota) {
		if (nuevo == null)
			return false;
		Flota f = buscarFlotaTaxista(nuevo.getRut());

		if (f != null)
			return false;

		return flota.agregarTaxista(nuevo);
	}

	public boolean agregarTaxistaTaxi(String patente, Rut rut) {
		if (patente == null || rut == null)
			return false;
		Flota f = buscarFlotaTaxista(rut);
		Flota y = buscarFlotaTaxi(patente);

		if (f == null || y == null || f != y)
			return false;

		return f.setTaxistaTaxi(rut, patente);
	}

	public boolean eliminarTaxi(String patente) {
		Flota f = buscarFlotaTaxi(patente);
		if (f == null)
			return false;

		f.eliminarTaxi(patente);
		return true;
	}
	

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public Lista<Sector> getSectores() {
		return this.sectores;
	}

	public Lista<Flota> getFlotas() {
		return this.flotas;
	}
}
