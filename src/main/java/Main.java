import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {
	public static void main(String[] args) {
		final String URL_BASE = "https://www.gov.br/ans/pt-br";
		
		try {
			// Navegação
			System.out.println("Acessando página inicial (https://www.gov.br/ans/pt-br)");
			Document docInicial = Task1CrawlerTISS.pegarHtml(URL_BASE);
			Element linkPrestador = docInicial.selectFirst("a:contains(Espaço do Prestador de Serviços de Saúde)");
			String urlPrestador = linkPrestador.attr("href");
			
			System.out.println("Acessando Espaço do Prestador (https://www.gov.br/ans/pt-br/assuntos/prestadores)");
			Document docPrestador = Task1CrawlerTISS.pegarHtml(urlPrestador);
			Element linkTiss = docPrestador.selectFirst("a:contains(TISS - Padrão para Troca de Informação de Saúde Suplementar)");
			String urlTiss = linkTiss.attr("href");
			
			
			// --- TASK 1 ---
			Task1CrawlerTISS.executarTask1(urlTiss);
			
			// --- TASK 2 ---
			Task2CrawlerHistorico.executarTask2(urlTiss);

			// --- TASK 3 ---
			Task3CrawlerErros.executarTask3(urlTiss);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
