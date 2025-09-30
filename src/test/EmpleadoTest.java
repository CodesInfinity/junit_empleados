package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pkg.Empleado;


class EmpleadoTest {

	private Empleado empleado;

	@BeforeEach
	void setUp() {
		empleado = new Empleado();
	}

	// PRUEBAS PARA calculoNominaBruta

	@Test
	void testCalculoNominaBruta_Vendedor_VentasBajas() {
		// Límite inferior: ventas = 999 (sin prima)
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 999, 0);
		assertEquals(2000, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_Vendedor_VentasLimitePrima100() {
		// Límite: ventas = 1000 (prima 100€)
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 1000, 0);
		assertEquals(2100, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_Vendedor_VentasJustoArribaPrima100() {
		// Límite: ventas = 1001 (prima 100€)
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 1001, 0);
		assertEquals(2100, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_Vendedor_VentasLimitePrima200() {
		// Límite: ventas = 1499 (prima 100€ todavía)
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 1499, 0);
		assertEquals(2100, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_Vendedor_VentasPrima200() {
		// Límite: ventas = 1500 (prima 200€)
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 1500, 0);
		assertEquals(2200, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_Vendedor_VentasArribaPrima200() {
		// Límite: ventas = 1501 (prima 200€)
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 1501, 0);
		assertEquals(2200, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_Encargado_VentasBajas() {
		// Encargado sin prima
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Encargado, 500, 0);
		assertEquals(2500, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_Encargado_VentasPrima200() {
		// Encargado con prima máxima
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Encargado, 2000, 0);
		assertEquals(2700, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_HorasExtraPositivas() {
		// Horas extra positivas
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 0, 5);
		assertEquals(2000 + (5 * 30), resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_HorasExtraCero() {
		// Horas extra = 0
		float resultado = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 0, 0);
		assertEquals(2000, resultado, 0.01);
	}

	@Test
	void testCalculoNominaBruta_VentasNegativas() {
		// Validación de ventas negativas
		assertThrows(IllegalArgumentException.class, () -> {
			empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, -100, 0);
		});
	}

	@Test
	void testCalculoNominaBruta_HorasExtraNegativas() {
		// Validación de horas extra negativas
		assertThrows(IllegalArgumentException.class, () -> {
			empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 0, -5);
		});
	}

	// PRUEBAS PARA calculoNominaNeta

	@Test
	void testCalculoNominaNeta_SinRetencion() {
		// Límite inferior: 2099€ (sin retención)
		float resultado = empleado.calculoNominaNeta(2099);
		assertEquals(2099, resultado, 0.01);
	}

	@Test
	void testCalculoNominaNeta_LimiteInferiorRetencion15() {
		// Límite: 2100€ (15% retención)
		float resultado = empleado.calculoNominaNeta(2100);
		assertEquals(2100 * 0.85, resultado, 0.01);
	}

	@Test
	void testCalculoNominaNeta_JustoArribaLimite15() {
		// Límite: 2101€ (15% retención)
		float resultado = empleado.calculoNominaNeta(2101);
		assertEquals(2101 * 0.85, resultado, 0.01);
	}

	@Test
	void testCalculoNominaNeta_LimiteSuperiorRetencion15() {
		// Límite: 2499€ (15% retención)
		float resultado = empleado.calculoNominaNeta(2499);
		assertEquals(2499 * 0.85, resultado, 0.01);
	}

	@Test
	void testCalculoNominaNeta_LimiteInferiorRetencion18() {
		// Límite: 2500€ (18% retención)
		float resultado = empleado.calculoNominaNeta(2500);
		assertEquals(2500 * 0.82, resultado, 0.01);
	}

	@Test
	void testCalculoNominaNeta_ArribaLimite18() {
		// Límite: 2501€ (18% retención)
		float resultado = empleado.calculoNominaNeta(2501);
		assertEquals(2501 * 0.82, resultado, 0.01);
	}

	@Test
	void testCalculoNominaNeta_ValorAlto() {
		// Valor alto con retención máxima
		float resultado = empleado.calculoNominaNeta(5000);
		assertEquals(5000 * 0.82, resultado, 0.01);
	}

	@Test
	void testCalculoNominaNeta_NominaNegativa() {
		// Validación de nómina negativa
		assertThrows(IllegalArgumentException.class, () -> {
			empleado.calculoNominaNeta(-100);
		});
	}

	@Test
	void testCalculoNominaNeta_NominaCero() {
		// Caso borde: nómina = 0
		float resultado = empleado.calculoNominaNeta(0);
		assertEquals(0, resultado, 0.01);
	}

	// PRUEBAS INTEGRADAS (ambos métodos)

	@Test
	void testCasoCompleto_VendedorConPrimaYHorasExtra() {
		// Vendedor con ventas > 1500 y horas extra
		float nominaBruta = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Vendedor, 1600, 10);
		float nominaNeta = empleado.calculoNominaNeta(nominaBruta);

		// Bruta esperada: 2000 + 200 + 300 = 2500
		assertEquals(2500, nominaBruta, 0.01);
		// Neta esperada: 2500 * 0.82 = 2050
		assertEquals(2050, nominaNeta, 0.01);
	}

	@Test
	void testCasoCompleto_EncargadoSinPrima() {
		// Encargado sin prima y sin horas extra
		float nominaBruta = empleado.calculoNominaBruta(Empleado.TipoEmpleado.Encargado, 500, 0);
		float nominaNeta = empleado.calculoNominaNeta(nominaBruta);

		// Bruta esperada: 2500
		assertEquals(2500, nominaBruta, 0.01);
		// Neta esperada: 2500 * 0.82 = 2050
		assertEquals(2050, nominaNeta, 0.01);
	}

}
