package testen;


import domein.Speler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SpelerTest {
	
	private final static String GELDIGE_NAAM = "Jens";
	private Speler speler;
	private final static int GELDIGE_GEBOORTEDATUM = 2003;
	
	@BeforeEach
	public void setUp() 
	{
		speler = new Speler(GELDIGE_NAAM,GELDIGE_GEBOORTEDATUM);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {" ", "\t \t "})
	public void maakSpeler_naamIsLeegGeboorteDatumOudGenoeg_throwException(String waarden)
	{
		Assertions.assertThrows(IllegalArgumentException.class, ()-> new Speler(waarden,GELDIGE_GEBOORTEDATUM));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2023, 2025, 2029})
	public void maakSpeler_geldigeNaamGeboorteDatumTeJong_throwsException(int waarde)
	{//Test voor als de gebruiker een geldige gebruikersnaam heeft maar te jong is => throw Exceptions
		Assertions.assertThrows(IllegalArgumentException.class, ()-> new Speler(GELDIGE_NAAM, waarde));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {GELDIGE_NAAM})
	public void maakSpeler_geldigeNaamGeboorteDatumOudGenoeg_doesNotThrowException(String geldigeNaam)
	{
		Assertions.assertDoesNotThrow(()-> new Speler(geldigeNaam, GELDIGE_GEBOORTEDATUM));
	}
	
}
