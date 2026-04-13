import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Task3CrawlerErros {
	
	public static void executarTask3(String URL) {
		System.out.println("\nIniciando Web Scraping da Task 3...\n");
		
		try {
			System.out.println("Acessando página do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss)");
			Document docTiss = Task1CrawlerTISS.pegarHtml(URL);
			Element linkErros = docTiss.selectFirst("a:contains(Clique aqui para acessar as planilhas)");
			String urlErros = linkErros.attr("href");
			
			System.out.println("Acessando página Tabelas Relacionadas (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-tabelas-relacionadas)");
			Document docErros = Task1CrawlerTISS.pegarHtml(urlErros);
			Element linkDownload = docErros.selectFirst("a:contains(Clique aqui para baixar a tabela de erros no envio para a ANS (.xlsx))");
			String urlDownloadErros = linkDownload.attr("href");
			
			Task1CrawlerTISS.baixarArquivo(urlDownloadErros, "Erros_Envio_ANS.xlsx");
			
			System.out.println("\nTask 3 concluída com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Erro ao extrair a tabela: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
