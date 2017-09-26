package tarifa;

public enum TarifaFinDe {
	
	SABADO("Sábado"),
	DOMINGO("Domingo");
	
	private String descripcion;

	private TarifaFinDe(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static TarifaFinDe getOpcion(int posicion) {
		return values()[posicion];
	}

	public static String getOpciones() {
		StringBuilder sb = new StringBuilder();
		for (TarifaFinDe opcion : TarifaFinDe.values()) {
			sb.append("\t");
			sb.append(opcion.ordinal());
			sb.append(".- ");
			sb.append(opcion.getDescripcion());
			sb.append("\n");
		}
		return sb.toString();
	}

}
