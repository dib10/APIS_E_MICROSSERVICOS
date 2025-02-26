package cadastro;

public class Data {
	int dia;
	int mes;
	int ano;
	
	public Data(int dia, int mes, int ano) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}
	@Override //formatando a exibição da data
	
	public String toString() {
		return dia + "/" + mes + "/" + ano;
	}
	

}
