package ohtu.verkkokauppa;

// import ohtu.verkkokauppa.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class KauppaTest {
    
        Pankki pankki;
        Viitegeneraattori viite;
        Varasto varasto;
        Kauppa k;

    @Before
    public void Setup() {
    // luodaan mock-oliot
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        k = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {

    // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());   
    // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }
    
    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanJaOikeatArvot() {

    // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    //     tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa) {
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   
    }
    
    @Test
    public void kahdenEriTuotteenOstonJalkeenOikeatArvot() {

    // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    // määritellään että tuote numero 2 on akhvi jonka hinta on 2 ja saldo 8
        when(varasto.saldo(2)).thenReturn(8); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 2));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // ostetaan tuotetta numero 2 eli kahvia
        k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    //     tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa) {
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(7));   
    }
    
    @Test
    public void kahdenSamanTuotteenOstonJalkeenOikeatArvot() {

    // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa lisää
        k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    //     tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa) {
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(10));
    }
    
    @Test
    public void kahdenEriTuotteenJoistaToinenLoppuOstonJalkeenOikeatArvot() {

    // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    // määritellään että tuote numero 2 on akhvi jonka hinta on 2 ja saldo 0
        when(varasto.saldo(2)).thenReturn(8); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 0));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // ostetaan tuotetta numero 2 eli kahvia
        k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    //     tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa) {
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(),eq(5));   
    }

    @Test
    public void aloitaAsiointiNollaaEdellisenOstoksenTiedot() {

    // määritellään että viitegeneraattori palauttaa viitten 43
        when(viite.uusi()).thenReturn(43);

    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.aloitaAsiointi();   // uusi asiointi, vanha jäi silleen
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    //     tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa) {
        verify(pankki).tilisiirto(eq("pekka"), eq(43), eq("12345"), anyString(),eq(5));
    }

    @Test
    public void tarkistaUusiViitenumeroUudestaMaksutapahtumasta() {

        when(viite.uusi()).thenReturn(42).thenReturn(43);

    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 15
        when(varasto.saldo(1)).thenReturn(15).thenReturn(14);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("jukka", "12344");

        k.aloitaAsiointi();   // uusi asiointi
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("jukka", "12344");

        verify(viite,times(2)).uusi();
    }

    @Test
    public void poistaTuoteKorista() {

        when(viite.uusi()).thenReturn(43);

    // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

    // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.poistaKorista(1);
        k.tilimaksu("jukka", "12344");

    // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
    //     tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa) {
        verify(pankki).tilisiirto(eq("jukka"), anyInt(), eq("12344"), anyString(), eq(0));
    //
    }
    
}
