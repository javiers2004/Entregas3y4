import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Recursividad {
	public static ArrayList<ArrayList<String>> manosconfiltro1 = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> manosconfiltro2 = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> manosconfiltro3 = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> manosconfiltro4 = new ArrayList<ArrayList<String>>();

	public static int codigomano = 0;
	public static int codigomano2 = 0;
	public static void main(String[] args) {
		//Método 4.1.1
		System.out.println(invertirFrase("hola",0,""));
		System.out.println(invertirFrase("Prueba número 1 de recursividad",0,""));
		System.out.println(invertirFrase("Funciona?;_-.",0,""));
		
		//Método 4.2.
		System.out.println(invertirPalabras("Prueba número 2 de recursividad",0,"", ""));
		System.out.println(invertirPalabras("Prueba;de.los,diferentes:signos de puntuación",0,"", ""));
		System.out.println(invertirPalabras("Creo:;., que funciona",0,"", ""));

		//Método 4.3.
		ArrayList<String> baraja = new ArrayList<>();
		HashSet<String> cartasUsadas = new HashSet<>();
		baraja.add("2 de Corazones");
        baraja.add("3 de Corazones");
        baraja.add("4 de Corazones");
        baraja.add("5 de Corazones");
        baraja.add("6 de Corazones");      
        baraja.add("5 de Diamantes");
        baraja.add("6 de Tréboles");
        baraja.add("A de Corazones");
        baraja.add("A de Picas");
        baraja.add("A de Diamantes");
        baraja.add("A de Tréboles");
        
//		posiblesManos(1, baraja, new ArrayList<String>(), cartasUsadas);
//		posiblesManos(2, baraja, new ArrayList<String>(), cartasUsadas);
//		posiblesManos(3, baraja, new ArrayList<String>(), cartasUsadas);
		System.out.println("--------------------------------------------------------------");
		
		//Método 4.4.
		//filtrarManos(1, baraja, new ArrayList<String>(), cartasUsadas);		Los he comentado para que en ultimo ejercicio solo aparezcan manos de 3 cartas
		//filtrarManos(2, baraja, new ArrayList<String>(), cartasUsadas);
		filtrarManos(3, baraja, new ArrayList<String>(), cartasUsadas);			//AÑADE AL ARRAYLIST manosconfiltro1 todas las posibles manos
		filtrarManos2(4, baraja, new ArrayList<String>(), cartasUsadas);		//AÑADE AL ARRAYLIST manosconfiltro2 todas las posibles manos
		filtrarManos3(4, baraja, new ArrayList<String>(), cartasUsadas);		//AÑADE AL ARRAYLIST manosconfiltro3 todas las posibles manos
		filtrarManos4(5, baraja, new ArrayList<String>(), cartasUsadas);		//AÑADE AL ARRAYLIST manosconfiltro3 todas las posibles manos
		
//		System.out.println(manosconfiltro1);
//		System.out.println(manosconfiltro2);
//		System.out.println(manosconfiltro3);
//		System.out.println(manosconfiltro4);
		
		//Método 4.5.
		guardaManos();
		
	}
	public static String invertirFrase(String frase, int contador, String invertido) {
		if(contador == frase.length()) {
			return invertido;
		}
		else {
			return  invertirFrase(frase, contador + 1, frase.charAt(contador) + invertido)  ;
		}
		
		
		
	}
	public static String invertirPalabras(String frase, int contador, String invertido, String respuestafinal) {
		if(contador == frase.length()) {
			return respuestafinal;
		}
		else {
			if(!("" + frase.charAt(contador)).equals(" ") && !("" + frase.charAt(contador)).equals(";") && !("" + frase.charAt(contador)).equals(",") && !("" + frase.charAt(contador)).equals(":") && !("" + frase.charAt(contador)).equals(".") && !("" + frase.charAt(contador)).equals(" \n")) {
				if(contador == 0) {
					return  invertirPalabras(frase + " ", contador + 1, frase.charAt(contador) + invertido, respuestafinal);

				}
				else {
					return  invertirPalabras(frase , contador + 1, frase.charAt(contador) + invertido, respuestafinal);
				}
			}	
			else {
				return invertirPalabras(frase, contador + 1, "", respuestafinal +  invertido  + frase.charAt(contador) )    ;
			}
		}
	}
	public static void posiblesManos(int tamaño, ArrayList<String> cartas, ArrayList<String> manoActual, HashSet<String> cartasusadas ) {
		if(tamaño == 0) {
			System.out.println(manoActual);
		}
		else {
			for(String carta: cartas) {
				if (!cartasusadas.contains(carta)) {
	                manoActual.add(carta);
	                cartasusadas.add(carta);

	                posiblesManos(tamaño - 1, cartas, manoActual, cartasusadas);

	                manoActual.remove(manoActual.size() - 1);
	                cartasusadas.remove(carta);
	            }
			}
		}
	}
	
	public static void filtrarManos(int tamaño, ArrayList<String> cartas, ArrayList<String> manoActual, HashSet<String> cartasusadas ) {         //AL MENOS UN AS
		if(tamaño == 0 &&(manoActual.contains("A de Corazones") || manoActual.contains("A de Picas") || manoActual.contains("A de Tréboles") || manoActual.contains("A de Diamantes"))) {
			//System.out.println(manoActual);
			manosconfiltro1.add(new ArrayList<>(manoActual));
		}
		else {
			for(String carta: cartas) {
				if (!cartasusadas.contains(carta)) {
	                manoActual.add(carta);
	                cartasusadas.add(carta);

	                filtrarManos(tamaño - 1, cartas, manoActual, cartasusadas);

	                manoActual.remove(manoActual.size() - 1);
	                cartasusadas.remove(carta);
	            }
			}
		}
	}
	
	public static void filtrarManos2(int tamaño, ArrayList<String> cartas, ArrayList<String> manoActual, HashSet<String> cartasusadas ) {        //SI HAY 4 CON EL MISMO NÚMERO
		if(tamaño == 0 ) {
			HashMap<String, Integer> mapa = new HashMap<String, Integer>();
			for(String e :manoActual) {		
				if(!mapa.containsKey(e.charAt(0) + "")) {
					mapa.put(e.charAt(0) + "",1 );
				}
				else {
					mapa.put(e.charAt(0) + "",mapa.get(e.charAt(0) + "") + 1 );
				}
			}
			for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
				if(entry.getValue() == 4) {
					//System.out.println(manoActual);
					manosconfiltro2.add(new ArrayList<>(manoActual));
				}
			}
		}
		else {
			for(String carta: cartas) {
				if (!cartasusadas.contains(carta)) {
	                manoActual.add(carta);
	                cartasusadas.add(carta);

	                filtrarManos2(tamaño - 1, cartas, manoActual, cartasusadas);

	                manoActual.remove(manoActual.size() - 1);
	                cartasusadas.remove(carta);
	            }
			}
		}
	}
	
	public static void filtrarManos3(int tamaño, ArrayList<String> cartas, ArrayList<String> manoActual, HashSet<String> cartasusadas ) {        //SI HAY EXACTAMENTE 3 DEL MISMO PALO
		if(tamaño == 0 ) {
			HashMap<String, Integer> mapa = new HashMap<String, Integer>();
			for(String e :manoActual) {		
				if(!mapa.containsKey(e.substring(5))) {
					mapa.put(e.substring(5),1 );
				}
				else {
					mapa.put(e.substring(5),mapa.get(e.substring(5)) + 1 );
				}
			}
			for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
				if(entry.getValue() == 3) {
					//System.out.println(manoActual);
					manosconfiltro3.add(new ArrayList<>(manoActual));
				}
			}
		}
		else {
			for(String carta: cartas) {
				if (!cartasusadas.contains(carta)) {
	                manoActual.add(carta);
	                cartasusadas.add(carta);

	                filtrarManos3(tamaño - 1, cartas, manoActual, cartasusadas);

	                manoActual.remove(manoActual.size() - 1);
	                cartasusadas.remove(carta);
	            }
			}
		}
	}
	
	public static void filtrarManos4(int tamaño, ArrayList<String> cartas, ArrayList<String> manoActual, HashSet<String> cartasusadas ) {                              //5 CONSECUTIVOS DEL MISMO PALO(ESCALERA)
		if(tamaño == 0 ) {
			HashMap<String, Integer> mapa = new HashMap<String, Integer>();
			ArrayList<Integer> arraynumeros = new ArrayList<Integer>();
			for(String e :manoActual) {		
				if(!mapa.containsKey(e.substring(5))) {
					mapa.put(e.substring(5),1 );
				}
				else {
					mapa.put(e.substring(5),mapa.get(e.substring(5)) + 1 );
				}
				if((""+ e.charAt(0)).equals("A")) {
					arraynumeros.add(1);
				}
				else if((""+ e.charAt(0)).equals("J")) {
					arraynumeros.add(10);
				}
				else if((""+ e.charAt(0)).equals("Q")) {
					arraynumeros.add(11);
				}
				else if((""+ e.charAt(0)).equals("K")) {
					arraynumeros.add(12);
				}
				else {
				arraynumeros.add( Character.getNumericValue(e.charAt(0)));
			
				}
			}
			for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
				if(entry.getValue() == 5 && sonConsecutivos(arraynumeros)) {
					//System.out.println(manoActual);
					manosconfiltro4.add(new ArrayList<>(manoActual));
				}
			}
			
		}
		else {
			for(String carta: cartas) {
				if (!cartasusadas.contains(carta)) {
	                manoActual.add(carta);
	                cartasusadas.add(carta);

	                filtrarManos4(tamaño - 1, cartas, manoActual, cartasusadas);

	                manoActual.remove(manoActual.size() - 1);
	                cartasusadas.remove(carta);
	            }
			}
		}
	}
	
	 public static boolean sonConsecutivos(ArrayList<Integer> lista) {
	        // Verificar si la lista está vacía o tiene un solo elemento
	        if (lista.isEmpty() || lista.size() == 1) {
	            return true;
	        }

	        // Recorrer la lista y comparar cada elemento con el siguiente
	        for (int i = 0; i < lista.size() - 1; i++) {
	            if (lista.get(i) + 1 != lista.get(i + 1)) {
	                // Si dos elementos consecutivos no son consecutivos, la lista no es consecutiva
	                return false;
	            }
	        }

	        // Si se recorre toda la lista y no se encontró ninguna interrupción, la lista es consecutiva
	        return true;
	    }
	
	
//    public static ArrayList<ArrayList<String>> eliminarDuplicados(ArrayList<ArrayList<String>> manos) {
//        HashSet<ArrayList<String>> conjuntoManos = new HashSet<>();
//        Iterator<ArrayList<String>> iterador = manos.iterator();
//
//        while (iterador.hasNext()) {
//            ArrayList<String> manoActual = iterador.next();
//
//            // Si la mano ya está en el conjunto, es un duplicado y se elimina
//            if (!conjuntoManos.add(manoActual)) {
//                iterador.remove();
//            }
//        }
//        return new ArrayList<> (conjuntoManos);      
//    }
	
	public static void guardaManos() {
		try {
		Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("NO ESTÁ CONECTADA LA LIBRERIA");
		}
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:baraja.db");			//ESTA URL PUEDE SER TANTO LOCVAL COMO REMOTA
			java.sql.Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS CARTA");
			statement.executeUpdate("DROP TABLE IF EXISTS FILTRO");
			statement.executeUpdate("DROP TABLE IF EXISTS MANOFILTRO");
			statement.executeUpdate("DROP TABLE IF EXISTS CARTAMANO");
			ArrayList<String> barajacompleta = new ArrayList<String>();
			String[] palos = {"Corazones", "Diamantes", "Tréboles", "Picas"};
	        String[] numeros = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

	        // Crear la baraja añadiendo todas las combinaciones posibles de palos y números
	        for (String palo : palos) {
	            for (String numero : numeros) {
	                String carta = numero + " de " + palo;
	                barajacompleta.add(carta);
	            }
	        }
	        //CREAR TABLA CARTA (almacenamos un codigo por carta y CARTA que es el string que la describe)
			statement.executeUpdate("CREATE TABLE CARTA(CODIGO INT, CARTA VARCHAR, PRIMARY KEY(CODIGO));");

			String insertSQL = "INSERT INTO CARTA (CODIGO, CARTA) VALUES (?, ?)";						//CARTAS DE LA BARAJA INSERTADAS
			try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
			    for (int i = 0; i < barajacompleta.size(); i++) {
			        insertStatement.setInt(1, i);
			        insertStatement.setString(2, barajacompleta.get(i));
			        insertStatement.executeUpdate();
			    }
			}
			ResultSet resultSet = statement.executeQuery("SELECT * FROM CARTA");
			 while (resultSet.next()) {
				 	int codigo = Integer.parseInt(resultSet.getString("CODIGO"));
	                String carta = resultSet.getString("CARTA");
	                System.out.println(codigo + "  " + carta);
	           }
			 
			//CREAR TABLA FILTRO (almacenamos un codigo de cada filtro y su descripción)
			 statement.executeUpdate("CREATE TABLE FILTRO(CODIGO INT, DESCRIPCION VARCHAR, PRIMARY KEY(CODIGO));");
			 statement.executeUpdate("INSERT INTO FILTRO VALUES(1,'Solo selecciona aquellas manos que tengan un AS entre sus cartas')");
			 statement.executeUpdate("INSERT INTO FILTRO VALUES(2,'Poker: 4 cartas con la misma figura en 5 cartas')");
			 statement.executeUpdate("INSERT INTO FILTRO VALUES(3,'Full: 3 cartas con la misma figura, 2 cartas con otra figura, en manos de 5 cartas.')");
			 statement.executeUpdate("INSERT INTO FILTRO VALUES(4,'Escalera: 5 cartas consecutivas del mismo palo')"); 
			 
			 //CREAR TABLA MANOFILTRO (almacenamos un codigo de las manos y del filtro del que han salido)
			 statement.executeUpdate("CREATE TABLE MANOFILTRO(CODIGO_FILTRO INT, CODIGO_MANO, PRIMARY KEY(CODIGO_FILTRO, CODIGO_MANO), FOREIGN KEY(CODIGO_FILTRO) REFERENCES FILTRO(CODIGO));");
			 for(int i = 0; i<manosconfiltro1.size();i++) {
				 statement.executeUpdate("INSERT INTO MANOFILTRO VALUES(" + 1 + ", " + codigomano2 + ")");
				 codigomano2++;
			 }
			 for(int i = 0; i<manosconfiltro2.size();i++) {
				 statement.executeUpdate("INSERT INTO MANOFILTRO VALUES(" + 2 + ", " + codigomano2 + ")");
				 codigomano2++;

			 }
			 for(int i = 0; i<manosconfiltro3.size();i++) {
				 statement.executeUpdate("INSERT INTO MANOFILTRO VALUES(" + 3 + ", " + codigomano2 + ")");
				 codigomano2++;

			 }
			 for(int i = 0; i<manosconfiltro4.size();i++) {
				 statement.executeUpdate("INSERT INTO MANOFILTRO VALUES(" + 4 + ", " + codigomano2 + ")");
				 codigomano2++;

			 }
			 
			 //CREATE TABLE CARTAMANO (almacenamos agrupaciones carta-mano)(en una mano pueden haber varias cartas y una carta estar en más de una mano)(por ello es necesario guardar como primary key ambos valores)
			//estoy basicamente guardando de las 4 agrupaciones de manos que tenemos(una por filtro), un codigo por cada mano + sus cartas
			 statement.executeUpdate("CREATE TABLE CARTAMANO(CODIGO_CARTA INT, CODIGO_MANO, PRIMARY KEY(CODIGO_CARTA, CODIGO_MANO), FOREIGN KEY(CODIGO_CARTA) REFERENCES CARTA(CODIGO), FOREIGN KEY(CODIGO_MANO) REFERENCES MANOFILTRO_(CODIGO_MANO));");
			 for(int i = 0; i<manosconfiltro1.size();i++) {
				 for (int e = 0; e<manosconfiltro1.get(i).size();e++) {			//recorremos todas las manos y convertimos del string de la carta a su respectivo codigo y guardamos la mano y el codigo
					 ResultSet codigo = statement.executeQuery("SELECT CODIGO FROM CARTA WHERE CARTA LIKE '" + manosconfiltro1.get(i).get(e) + "';");
		             int resultcodigo = codigo.next() ? Integer.parseInt(codigo.getString(1)) : null;
		             statement.executeUpdate("INSERT INTO CARTAMANO VALUES(" + resultcodigo + ", " + codigomano + ")");
				 }
				 codigomano ++;
			 } 
			 for(int i = 0; i<manosconfiltro2.size();i++) {
				 for (int e = 0; e<manosconfiltro2.get(i).size();e++) {			//recorremos todas las manos y convertimos del string de la carta a su respectivo codigo y guardamos la mano y el codigo
					 ResultSet codigo = statement.executeQuery("SELECT CODIGO FROM CARTA WHERE CARTA LIKE '" + manosconfiltro2.get(i).get(e) + "';");
		             int resultcodigo = codigo.next() ? Integer.parseInt(codigo.getString(1)) : null;
		             statement.executeUpdate("INSERT INTO CARTAMANO VALUES(" + resultcodigo + ", " + codigomano + ")");
				 }
				 codigomano ++;
			 } 
			 for(int i = 0; i<manosconfiltro3.size();i++) {
				 for (int e = 0; e<manosconfiltro3.get(i).size();e++) {			//recorremos todas las manos y convertimos del string de la carta a su respectivo codigo y guardamos la mano y el codigo
					 ResultSet codigo = statement.executeQuery("SELECT CODIGO FROM CARTA WHERE CARTA LIKE '" + manosconfiltro3.get(i).get(e) + "';");
		             int resultcodigo = codigo.next() ? Integer.parseInt(codigo.getString(1)) : null;
		             statement.executeUpdate("INSERT INTO CARTAMANO VALUES(" + resultcodigo + ", " + codigomano + ")");
				 }
				 codigomano ++;
			 } 
			 for(int i = 0; i<manosconfiltro4.size();i++) {
				 for (int e = 0; e<manosconfiltro4.get(i).size();e++) {			//recorremos todas las manos y convertimos del string de la carta a su respectivo codigo y guardamos la mano y el codigo
					 ResultSet codigo = statement.executeQuery("SELECT CODIGO FROM CARTA WHERE CARTA LIKE '" + manosconfiltro4.get(i).get(e) + "';");
		             int resultcodigo = codigo.next() ? Integer.parseInt(codigo.getString(1)) : null;
		             statement.executeUpdate("INSERT INTO CARTAMANO VALUES(" + resultcodigo + ", " + codigomano + ")");
				 }
				 codigomano ++;
			 } 
			
			 
			//printeamos todos los codigos de las cartas y de sus manos ara comprobar
			ResultSet resultSet2 = statement.executeQuery("SELECT * FROM CARTAMANO");
			while (resultSet2.next()) {
				int codigoc = Integer.parseInt(resultSet2.getString("CODIGO_CARTA"));
				int codigom = Integer.parseInt(resultSet2.getString("CODIGO_MANO"));
		        System.out.println("Carta: " + codigoc + "  Pertenece a la mano:" + codigom + "  ");
		    }
			 
			
			//AL FINAL, HEMOS CREADO UNA BASE DE DATOS DE 4 TABLAS:
			//-LA PRIMERA 'CARTA' ALMACENA UN CODIGO IDENTIFICATIVO 0-51 POR CADA CARTA + SU NOMBRE
			//-LA SEGUNDA 'FILTRO' ALMACENA UN CODIGO POR FILTRO 1-4 Y LA DESCRIPCION DE LO QUE HACE
			//-LA TERCERA 'MANOFILTRO' ALMACENA EL CODIGO DE CADA MANO (SU IDENTIFICATIVO) + EL CODIGO DEL FILTRO DEL QUE HA SALIDO
			//-LA CUARTA 'CARTAMANO' ALMACENA TODOS LOS CODIGOS DE LAS CARTAS + LA MANO A LA QUE PERTENECEN, LA CLAVE PRIMARIA SERÁN AMBOS ELEMENTOS PUESTO QUE UNA CARTA SE PUEDE REPETIR ENTRE MANOS Y CADA MANO PUEDE TENER MÁS DE UNA CARTA
			//HE CREADO UN ARCHIVO .txt EN DONDE LLAMADO salida.txt DONDE HE PRINTEADO EL CONTENIDO DE LAS 4 TABLAS
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR EN LA GESTIÓN DE LA BASE DE DATOS");

		}
	}
	
	
	
}
