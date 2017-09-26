package menu;

public enum Opciones{
	ALTA_CLIENTE("DAR DE ALTA UN CLIENTE"),
	BORRA_CLIENTE("BORRAR UN CLIENTE"),
	CAMBIA_TARIFA_CLIENTE("CAMBIAR TARIFA DE UN CLIENTE"),
	RECUPERA_CLIENTE("RECUPERAR CLIENTE (NIF)"),
	MOSTRAR_CLIENTES("MOSTRAR CLIENTES"),
	ALTA_LLAMADA("DAR DE ALTA UNA LLAMADA"),
	LLAMADAS_CLIENTE("LISTAR LLAMADAS DE UN CLIENTE"),
	EMITIR_FACTURA("EMITIR FACTURA"),
	MOSTRAR_FACTURA("MOSTRAR FACTURA (POR CODIGO)"),
	MOSTRAR_FACTURAS("MOSTRAR FACTURAS CLIENTE"),
	CLIENTES_ALTA_FECHAS("MOSTRAR CLIENTES DADOS DE ALTA ENTRE DOS FECHAS"),
	LLAMADAS_CLIENTE_FECHAS("MOSTRAR LLAMADAS DE UN CLIENTE ENTRE DOS FECHAS"),
	FACTURAS_CLIENTE_FECHAS("MOSTRAR FACTURAS DE UN CLIENTE ENTRE DOS FECHAS"),
	SALIR("SALIR\n");
	
	private String descripcion;

	private Opciones(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static Opciones getOpcion(int posicion) {
		return values()[posicion];
	}

	public static String getOpciones() {
		StringBuilder sb = new StringBuilder();
		for (Opciones opcion : Opciones.values()) {
			sb.append("\t");
			sb.append(opcion.ordinal());
			sb.append(".- ");
			sb.append(opcion.getDescripcion());
			sb.append("\n");
		}
		return sb.toString();
	}
}
