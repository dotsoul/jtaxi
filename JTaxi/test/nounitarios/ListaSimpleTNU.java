/*
 * Copyright (c) 2011, Julio Jim�nez, Ren� Toro, Jos� Vargas. All rights reserved.
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
package nounitarios;

import cl.pucv.eii.jtaxi.utilidades.listas.IteradorSimple;
import cl.pucv.eii.jtaxi.utilidades.listas.ListaSimple;

public class ListaSimpleTNU {
	public static void main(String... args) {
		ListaSimple<Integer> li = new ListaSimple<>();
		for (int i = 0; i < 10; i++) 
			li.agregar(new Integer(i));
		System.out.println(li.tama�o());
		for(IteradorSimple<Integer> it = li.iteradorSimple();it.hasNext();){
			Integer i = it.next();
			System.out.println(i+"||"+it.nextIndex()+"||"+li.getIndice(i));
		}

	}
}
