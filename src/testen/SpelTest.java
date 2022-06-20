package testen;

import java.util.List;

//import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import domein.Spel;
import domein.Speler;

class SpelTest {

	private Spel spel;
	
	private List<Speler> spelerslijst;
	
	public void spelersToevoegen()
	{
		spelerslijst.add(new Speler("Laurens", 2001));
		
	}
	
	@BeforeEach
	public void setUp()
	{
		spelersToevoegen();//Eerst gaan we de spelers gaan toevoegen doormiddel van de hulpmethode
		spel = new Spel(spelerslijst);
	}
	
	@Test
	public void spel_1spelerInDeLijst_throwException()
	{
		Assertions.assertThrows(IllegalArgumentException.class,()-> new Spel(spelerslijst));
	}
	
	@Test
	public void spel_2spelersInDeLijstRandWaarde_doesNotThrow()
	{
		spelerslijst.add(new Speler("Alexander", 2003));
		Assertions.assertDoesNotThrow( ()-> new Spel(spelerslijst));
	}
	
	
	
	@Test
	public void spel_spelerHeeftGeenSpeelkansMeer_throwException()
	{
		spelerslijst.add(new Speler("Joris", 2001, 0));
		Assertions.assertThrows(IllegalArgumentException.class, ()-> new Spel(spelerslijst));
	}
	
	@Test
	public void spel_wijzigSpeelkans_equals()
	{
		spelerslijst.add(new Speler("Jens", 2003));
		Assertions.assertEquals(spelerslijst.get(3).getSpeelkans(), 4);
	}
	
	
	
	
}
