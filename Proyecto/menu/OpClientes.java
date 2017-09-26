package menu;

public enum OpClientes{
	
	CLIENTE_PARTICULAR("Particular"),
	CLIENTE_EMPRESA("Empresa");
	
	private String descripcion;

	private OpClientes (String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static OpClientes getOpcion(int posicion) {
		return values()[posicion];
	}

	public static String getOpciones() {
		StringBuilder sb = new StringBuilder();
		for (OpClientes opcion : OpClientes.values()) {
			sb.append("\t");
			sb.append(opcion.ordinal());
			sb.append(".- ");
			sb.append(opcion.getDescripcion());
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
