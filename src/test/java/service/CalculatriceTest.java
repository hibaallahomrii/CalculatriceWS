package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests unitaires de la logique métier (sans serveur). */
class CalculatriceTest {

    private static final double DELTA = 1e-9;
    private Calculatrice calc;

    @BeforeEach
    void init() {
        calc = new Calculatrice();
    }

    @Test
    void somme() {
        assertEquals(15.0, calc.somme(10, 5), DELTA);
        assertEquals(-3.0, calc.somme(-1, -2), DELTA);
    }

    @Test
    void soustraction() {
        assertEquals(5.0, calc.soustraction(10, 5), DELTA);
    }

    @Test
    void multiplication() {
        assertEquals(50.0, calc.multiplication(10, 5), DELTA);
        assertEquals(0.0, calc.multiplication(0, 999), DELTA);
    }

    @Test
    void division() throws CalculException {
        assertEquals(2.5, calc.division(10, 4), DELTA);
    }

    @Test
    void divisionParZeroLeveUneExceptionTypee() {
        CalculException e = assertThrows(CalculException.class, () -> calc.division(1, 0));
        assertEquals(CalculException.ERR_DIV_ZERO, e.getFaultInfo().getCode());
    }

    @Test
    void puissance() throws CalculException {
        assertEquals(1024.0, calc.puissance(2, 10), DELTA);
    }

    @Test
    void racineCarree() throws CalculException {
        assertEquals(9.0, calc.racineCarree(81), DELTA);
        assertThrows(CalculException.class, () -> calc.racineCarree(-4));
    }

    @Test
    void modulo() throws CalculException {
        assertEquals(2.0, calc.modulo(17, 5), DELTA);
        assertThrows(CalculException.class, () -> calc.modulo(5, 0));
    }

    @Test
    void pourcentage() {
        assertEquals(50.0, calc.pourcentage(250, 20), DELTA);
    }

    @Test
    void moyenne() throws CalculException {
        assertEquals(13.5, calc.moyenne(new double[]{12, 15, 9, 18}), DELTA);
        assertThrows(CalculException.class, () -> calc.moyenne(new double[]{}));
    }

    @Test
    void factorielle() throws CalculException {
        assertEquals(1L, calc.factorielle(0));
        assertEquals(120L, calc.factorielle(5));
        assertEquals(2432902008176640000L, calc.factorielle(20));
        assertThrows(CalculException.class, () -> calc.factorielle(21));
        assertThrows(CalculException.class, () -> calc.factorielle(-1));
    }

    @Test
    void historiqueEnregistreLesOperationsEtSeVide() {
        calc.somme(1, 2);
        calc.multiplication(3, 4);
        assertEquals(2, calc.getHistorique().size());
        assertEquals("somme", calc.getHistorique().get(0).getType());
        assertEquals(2, calc.viderHistorique());
        assertTrue(calc.getHistorique().isEmpty());
    }

    @Test
    void historiqueLimiteA100Elements() {
        for (int i = 0; i < 150; i++) {
            calc.somme(i, 1);
        }
        assertEquals(100, calc.getHistorique().size());
    }
}
