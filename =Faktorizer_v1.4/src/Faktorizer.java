import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Program vyp�e - v ur�en�m form�tu - faktori�l v�ech cel�ch ��sel p�e�ten�ch
 * ze vstupn�ho souboru.
 *
 * Vstupn� soubor je zad�n jako prvn� parametr, tak�e vol�n� vypad� t�eba
 * takhle: java Faktorizer vstup.txt
 *
 * Soubor vstup.txt, pak m��e vypadat takto: "10,5.24 7" V�stup vypad� takto:
 * 10! = 3628800 5! = 120 24! = 620448401733239439360000 7! = 5040
 * 
 * Matou�, Jon��v team leader, m�l je�t� po�adavek na v�po�et ve v�ce vl�knech
 * a na neopakov�n� ji� existuj�c�ho v�po�tu (pokud dostane na vstupu ��sla 12 a
 * 10, bude pro dvan�ctku n�sobit jen 2x).
 */
class Faktorizer {
	private static int zasobnik = 0; 
	private static BigInteger fakZasobnik = BigInteger.ONE;

	/** Faktorial
	 * @param n	vstupn� ��slo
	 * @return vysl v�stupn� ��slo
	 */
	public static BigInteger nasobTo(long n) {
		BigInteger vysl = BigInteger.ONE;	// Inicializace vysl na hodnotu jedna
		for (long i = 2; i <= n; i++)		// pro i od 2 do n
	        vysl = vysl.multiply(BigInteger.valueOf(i));
	    return vysl;
	}
	
	/** Faktorial bez opakovani
	 * @param cisloAlfa vstupn� ��slo
	 * @param cisloBeta vstupn� ��slo
	 * @param faktBeta vypo��tane cislo cisla Beta 
	 * @return vysl faktorial
	 */
	public static BigInteger nasobTo(long cisloAlfa, long cisloBeta, BigInteger faktBeta) {
		BigInteger vysl = faktBeta;
		for (long i = ++cisloBeta; i <= cisloAlfa; i++) 
			vysl = vysl.multiply(BigInteger.valueOf(i));
		return vysl;
	}
	
	/**
	 * �te ze souboru ��sla a vypisuje faktori�ly.
	 * @param fileName soubor ke zpracov�n�
	 * @throws IOException
	 */
	protected static void zpracujVstup(String fileName) throws IOException {
		try {
			// Inicializace skeneru a na�ten� souboru
			Scanner scanner = new Scanner(new FileReader(fileName)); 
			scanner.useDelimiter("\\D"); // Nastaven� rozdelovacu na cokoli nez cislo.
			
			while (scanner.hasNext()) {			// dokud jsou nalezeny symboly v souboru
				if (scanner.hasNextInt()) {		// pokud bylo nalezeno cislo, po��tej
					int nalezeno = scanner.nextInt(); // nalezene cislo ulozime do lok.prom.
					BigInteger fakt;			// lok.prom. pro v�sledek faktori�lu
					if (nalezeno >= zasobnik) 	// pokud je vetsi nez p�edchozi cislo,
						fakt = nasobTo(nalezeno, zasobnik, fakZasobnik); // pak pou�ijeme p�edchoz� v�po�et
					else 
						fakt = nasobTo(nalezeno); // jinak pouzijeme klasicky vypocet

					System.out.println(nalezeno + "\t != " + fakt); // vypis vysledku do konzole
					zasobnik = nalezeno;	
					fakZasobnik = fakt;	
				} else
					scanner.next();		// jinak se presun na dalsi symbol
			} 
			scanner.close(); // uzav�en� skeneru
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hlavn� t��da, kter� se vol� z command line. Jako jedin� parametr o�ek�v�
	 * soubor, ve kter�m jsou ��sla odd�len� znakem, kter� nen� ��slice.
	 * @param args vstupn� parametr
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