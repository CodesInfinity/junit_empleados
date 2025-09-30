package pkg;

public class Empleado {

    public enum TipoEmpleado { Vendedor, Encargado };
    
    // Constantes datos del enunciado
    private static final float SALARIO_BASE_VENDEDOR = 2000f;
    private static final float SALARIO_BASE_ENCARGADO = 2500f;
    private static final float PRIMA_BAJA = 100f;
    private static final float PRIMA_ALTA = 200f;
    private static final float PRECIO_HORA_EXTRA = 30f;
    private static final float UMBRAL_PRIMA_BAJA = 1000f;
    private static final float UMBRAL_PRIMA_ALTA = 1500f;
    private static final float UMBRAL_RETENCION_BAJA = 2100f;
    private static final float UMBRAL_RETENCION_ALTA = 2500f;
    private static final float RETENCION_NULA = 0f;
    private static final float RETENCION_MEDIA = 0.15f;
    private static final float RETENCION_ALTA = 0.18f;

    public float calculoNominaBruta(TipoEmpleado tipo, float ventasMes, float horasExtra) {
        
        // Validación de parámetros
        if (ventasMes < 0 || horasExtra < 0) {
            throw new IllegalArgumentException("Las ventas y horas extra no pueden ser negativas");
        }
        
        float salarioBase;
        
        // Determinar salario base según tipo de empleado
        if (tipo == TipoEmpleado.Vendedor) {
            salarioBase = SALARIO_BASE_VENDEDOR;
        } else if (tipo == TipoEmpleado.Encargado) {
            salarioBase = SALARIO_BASE_ENCARGADO;
        } else {
            throw new IllegalArgumentException("Tipo de empleado no válido");
        }
        
        // Calcular prima por ventas
        float prima = 0;
        if (ventasMes >= UMBRAL_PRIMA_ALTA) {
            prima = PRIMA_ALTA;
        } else if (ventasMes >= UMBRAL_PRIMA_BAJA) {
            prima = PRIMA_BAJA;
        }
        
        // Calcular pago por horas extra
        float pagoHorasExtra = horasExtra * PRECIO_HORA_EXTRA;
        
        return salarioBase + prima + pagoHorasExtra;
    }
    
    public float calculoNominaNeta(float nominaBruta) {
        
        // Validación del parámetro
        if (nominaBruta < 0) {
            throw new IllegalArgumentException("La nómina bruta no puede ser negativa");
        }
        
        float retencion;
        
        // Determinar porcentaje de retención según tramos
        if (nominaBruta < UMBRAL_RETENCION_BAJA) {
            retencion = RETENCION_NULA;
        } else if (nominaBruta < UMBRAL_RETENCION_ALTA) {
            retencion = RETENCION_MEDIA;
        } else {
            retencion = RETENCION_ALTA;
        }
        
        return nominaBruta * (1 - retencion);
    }
	
}
