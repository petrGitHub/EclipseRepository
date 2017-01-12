import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Program vypíše - v urèeném formátu - faktoriál všech celých èísel pøeètených
 * ze vstupního souboru.
 *
 * Vstupní soubor je zadán jako první parametr, takže volání vypadá tøeba
 * takhle: java Faktorizer vstup.txt
 *
 * Soubor vstup.txt, pak mùže vypadat takto: "10,5.24 7" Výstup vypadá takto:
 * 10! = 3628800 5! = 120 24! = 620448401733239439360000 7! = 5040
 * 
 * Matouš, Jonášùv team leader, mìl ještì požadavek na výpoèet ve více vláknech
 * a na neopakování již existujícího výpoètu (pokud dostane na vstupu èísla 12 a
 * 10, bude pro dvanáctku násobit jen 2x).
 */
class Faktorizer {
	private static int zasobnik = 0; 
	private static BigInteger fakZasobnik = BigInteger.ONE;

	/** Faktorial
	 * @param n	vstupní èíslo
	 * @return vysl výstupní èíslo
	 */
	public static BigInteger nasobTo(long n) {
		BigInteger vysl = BigInteger.ONE;	// Inicializace vysl na hodnotu jedna
		for (long i = 2; i <= n; i++)		// pro i od 2 do n
	        vysl = vysl.multiply(BigInteger.valueOf(i));
	    return vysl;
	}
	
	/** Faktorial bez opakovani
	 * @param cisloAlfa vstupní èíslo
	 * @param cisloBeta vstupní èíslo
	 * @param faktBeta vypoèítane cislo cisla Beta 
	 * @return vysl faktorial
	 */
	public static BigInteger nasobTo(long cisloAlfa, long cisloBeta, BigInteger faktBeta) {
		BigInteger vysl = faktBeta;
		for (long i = ++cisloBeta; i <= cisloAlfa; i++) 
			vysl = vysl.multiply(BigInteger.valueOf(i));
		return vysl;
	}
	
	/**
	 * Ète ze souboru èísla a vypisuje faktoriály.
	 * @param fileName soubor ke zpracování
	 * @throws IOException
	 */
	protected static void zpracujVstup(String fileName) throws IOException {
		try {
			// Inicializace skeneru a naètení souboru
			Scanner scanner = new Scanner(new FileReader(fileName)); 
			scanner.useDelimiter("\\D"); // Nastavení rozdelovacu na cokoli nez cislo.
			
			while (scanner.hasNext()) {			// dokud jsou nalezeny symboly v souboru
				if (scanner.hasNextInt()) {		// pokud bylo nalezeno cislo, poèítej
					int nalezeno = scanner.nextInt(); // nalezene cislo ulozime do lok.prom.
					BigInteger fakt;			// lok.prom. pro výsledek faktoriálu
					if (nalezeno >= zasobnik) 	// pokud je vetsi nez pøedchozi cislo,
						fakt = nasobTo(nalezeno, zasobnik, fakZasobnik); // pak použijeme pøedchozí výpoèet
					else 
						fakt = nasobTo(nalezeno); // jinak pouzijeme klasicky vypocet

					System.out.println(nalezeno + "\t != " + fakt); // vypis vysledku do konzole
					zasobnik = nalezeno;	
					fakZasobnik = fakt;	
				} else
					scanner.next();		// jinak se presun na dalsi symbol
			} 
			scanner.close(); // uzavøení skeneru
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hlavní tøída, která se volá z command line. Jako jediný parametr oèekává
	 * soubor, ve kterém jsou èísla oddìlená znakem, který není èíslice.
	 * @param args vstupní parametr
	 */
	public static void main(String[] args) {
//		if (args.length == 0) { // Pokud neni zadny argument, vyhod vyjimku.
//			throw new IllegalArgumentException("\nMusite zadat nazev souboru do argumentu!");
//		}
		try {			
//			zpracujVstup(args[0]);
			zpracujVstup("vstup.txt");
		} catch (IOException ex) {
			Logger.getLogger(Faktorizer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}