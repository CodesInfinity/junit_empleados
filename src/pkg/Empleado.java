package pkg;

public class Empleado {

	public enum tipoEmpleado { Vendedor, Encargado };
	float salarioBase = 0;
	float primas = 0;
	float extras = 0;
	
	public float calculoNominaBruta(tipoEmpleado tipo, float ventasMes, float hora) {
		
		if (tipo == tipoEmpleado.Vendedor)
			salarioBase = 2000;
		else if (tipo == tipoEmpleado.Encargado)
			salarioBase = 2500;
		
		// det. prima
		if (ventasMes >= 1500)
			primas = 200;
		else if (ventasMes >= 1000)
			primas = 100;
		
		// det. extras 
		extras = hora * 30;
		
		return salarioBase + primas + extras;
	}
	
}
