import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Recursividad {
	public static ArrayList<ArrayList<String>> manosconfiltro = new ArrayList<ArrayList<String>>();
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
        baraja.add("5 de Diamantes");
        baraja.add("6 de Tréboles");
        baraja.add("7 de Corazones");
        baraja.add("8 de Diamantes");
        baraja.add("A de Corazones");
        baraja.add("A de Picas");
		posiblesManos(1, baraja, new ArrayList<String>(), cartasUsadas);
		posiblesManos(2, baraja, new ArrayList<String>(), cartasUsadas);
		posiblesManos(3, baraja, new ArrayList<String>(), cartasUsadas);
		System.out.println("--------------------------------------------------------------");
		
		//Método 4.4.
		//filtrarManos(1, baraja, new ArrayList<String>(), cartasUsadas);		Los he comentado para que en ultimo ejercicio solo aparezcan manos de 3 cartas
		//filtrarManos(2, baraja, new ArrayList<String>(), cartasUsadas);
		filtrarManos(3, baraja, new ArrayList<String>(), cartasUsadas);
		//manosconfiltro = eliminarDuplicados(manosconfiltro);
		System.out.println("--------------------------------------------------------------");
		
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
	
	public static void filtrarManos(int tamaño, ArrayList<String> cartas, ArrayList<String> manoActual, HashSet<String> cartasusadas ) {
		if(tamaño == 0 &&(manoActual.contains("A de Corazones") || manoActual.contains("A de Picas") || manoActual.contains("A de Tréboles") || manoActual.contains("A de Diamantes"))) {
			System.out.println(manoActual);
			manosconfiltro.add(new ArrayList<>(manoActual));
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
			
			 //CREAR TABLA MANOFILTRO (almacenamos un codigo de las manos y del filtro del que han salido)
			 statement.executeUpdate("CREATE TABLE MANOFILTRO(CODIGO_FILTRO INT, CODIGO_MANO, PRIMARY KEY(CODIGO_MANO), FOREIGN KEY(CODIGO_FILTRO) REFERENCES FILTRO(CODIGO));");
			 for(int i = 0; i<manosconfiltro.size();i++) {
				 statement.executeUpdate("INSERT INTO MANOFILTRO VALUES(" + 1 + ", " + i + ")");
			 }
			 
			 //CREATE TABLE CARTAMANO (almacenamos agrupaciones carta-mano)(en una mano pueden haber varias cartas y una carta estar en más de una mano)(por ello es necesario guardar como primary key ambos valores)
			 statement.executeUpdate("CREATE TABLE CARTAMANO(CODIGO_CARTA INT, CODIGO_MANO, PRIMARY KEY(CODIGO_CARTA, CODIGO_MANO), FOREIGN KEY(CODIGO_CARTA) REFERENCES CARTA(CODIGO), FOREIGN KEY(CODIGO_MANO) REFERENCES MANOFILTRO_(CODIGO_MANO));");
			 for(int i = 0; i<manosconfiltro.size();i++) {
				 for (int e = 0; e<manosconfiltro.get(i).size();e++) {			//recorremos todas las manos y convertimos del string de la carta a su respectivo codigo y guardamos la mano y el codigo
					 ResultSet codigo = statement.executeQuery("SELECT CODIGO FROM CARTA WHERE CARTA LIKE '" + manosconfiltro.get(i).get(e) + "';");
		             int resultcodigo = codigo.next() ? Integer.parseInt(codigo.getString(1)) : null;
		             statement.executeUpdate("INSERT INTO CARTAMANO VALUES(" + resultcodigo + ", " + i + ")");
				 }
			 } 
			//printeamos todos los codigos de las cartas y de sus manos ara comprobar
			ResultSet resultSet2 = statement.executeQuery("SELECT * FROM CARTAMANO");
			while (resultSet2.next()) {
				int codigoc = Integer.parseInt(resultSet2.getString("CODIGO_CARTA"));
				int codigom = Integer.parseInt(resultSet2.getString("CODIGO_MANO"));
		        System.out.println("Carta: " + codigoc + "  Pertenece a la mano:" + codigom + "  ");
		    }
			 
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR EN LA GESTIÓN DE LA BASE DE DATOS");

		}
	}
	
	
	
}
