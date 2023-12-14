import java.util.ArrayList;
import java.util.LinkedList;

public class Mejorcompra {
	private static int[] precios = {200,76,112};
	private static ArrayList<Integer> precios2 = new ArrayList<Integer>();
	private static int mejorDineroRestante = Integer.MAX_VALUE;
	private static int mejorDineroRestante2 = Integer.MAX_VALUE;
	public static void main(String[] args) {
		mejorCompra(899, precios, new LinkedList<Integer>());
		System.out.println("-------------------------");
		precios2.add(200);
		precios2.add(76);
		precios2.add(112);
		precios2.add(200);
		precios2.add(10);
		precios2.add(13);
		precios2.add(200);
		precios2.add(3);
		mejorCompraSinRepetir(800, precios2, new LinkedList<Integer>());
	}
	
	
	
	
//	--si el dinero es 0, mejor compra, caso base
//	--si es menor que 0, no es posible la compra, caso base
//	--si es mayor que 0, caso recursivo
	public static void mejorCompra(int dineroRestante, int[] precios, LinkedList<Integer> listaCompras) {
		if(dineroRestante == 0) {
			System.out.println("Mejor compra posible es" + listaCompras);
		}
		else if(dineroRestante < 0) {
			
		}
		else {
			for(int precio : precios) {
				listaCompras.addLast(precio);
				if(dineroRestante-precio < mejorDineroRestante && (dineroRestante-precio>0)) {
					mejorDineroRestante = dineroRestante-precio;		
					System.out.println("Nueva mejor compra encontrada. Resto: " + (dineroRestante-precio) + " Lista compra: " + listaCompras);
				}
				mejorCompra(dineroRestante-precio, precios, listaCompras);
				listaCompras.removeLast();
			}
		}
	}
	
	public static void mejorCompraSinRepetir(int dineroRestante, ArrayList<Integer> precios, LinkedList<Integer> listaCompras) {
		if(dineroRestante == 0 && dineroRestante < mejorDineroRestante2) {
			System.out.println("Mejor compra posible es" + listaCompras);
			mejorDineroRestante2 = 0;
			System.out.println(dineroRestante);
			return;
		}
		else if(dineroRestante < 0) {
			
		}
		else {
			for(int i = 0; i< precios.size(); i++) {
					listaCompras.addLast(precios.get(i));
					if(dineroRestante-precios.get(i) < mejorDineroRestante2 && (dineroRestante-precios.get(i)>0) && precios.size() > 0) {
						mejorDineroRestante2 = dineroRestante-precios.get(i);		
						System.out.println("Nueva mejor compra encontrada. Resto: " + (dineroRestante-precios.get(i)) + " Lista compra: " + listaCompras);
					}
					int guardarprecio = precios.get(i);
					precios.remove(i);
					
					mejorCompraSinRepetir(dineroRestante-guardarprecio, precios, listaCompras);	
					
					precios.add(i, guardarprecio);
					listaCompras.removeLast();
				}	
		}
	}
}
