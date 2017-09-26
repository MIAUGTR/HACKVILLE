package tarifa;

public enum TarifaEspecial {

	MAÑANA("Mañana -> 8h a 12h"),
	TARDE("Tarde -> 16h a 20h"),
	NOCHE("Noche -> 22h a 6h");
	
	
	private String descripcion;

	private TarifaEspecial(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static TarifaEspecial getOpcion(int posicion) {
		return values()[posicion];
	}

	public static String getOpciones() {
		StringBuilder sb = new StringBuilder();
		for (TarifaEspecial opcion : TarifaEspecial.values()) {
			sb.append("\t");
			sb.append(opcion.ordinal());
			sb.append(".- ");
			sb.append(opcion.getDescripcion());
			sb.append("\n");
		}
		return sb.toString();
	}
}
