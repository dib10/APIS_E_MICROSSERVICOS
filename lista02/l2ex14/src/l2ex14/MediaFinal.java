package l2ex14;

public class MediaFinal {
	
	public static double calcularMedia(double nota1, double nota2, double nota3) 
	{
		return(nota1+nota2+nota3)/3;
	}
	
	public static String Status(double media) {
		if(media > 7) {
			return "aprovado";
		}
		else if (media <3) {
			return "reprovado";
		}
		else {
			return "Exame";
		}
	}

}
