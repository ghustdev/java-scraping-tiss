import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Task3CrawlerErros {
	
	public static void executarTask3(String URL) {
		System.out.println("\nIniciando Web Scraping da Task 3...\n");
		
		try {
			System.out.println("Acessando página do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss)");
			Document docTiss = Task1CrawlerTISS.pegarHtml(URL);
			String urlErros = extrairUrlPlanilhas(docTiss);
			
			System.out.println("Acessando página Tabelas Relacionadas (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-tabelas-relacionadas)");
			Document docErros = Task1CrawlerTISS.pegarHtml(urlErros);
			String urlDownloadErros = extrairUrlDownloadErros(docErros);
			
			Task1CrawlerTISS.baixarArquivo(urlDownloadErros, "Erros_Envio_ANS.xlsx");
			
			System.out.println("\nTask 3 concluída com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Erro ao extrair a tabela: " + e.getMessage());
			e.printStackTrace();
		}
	}

	static String extrairUrlPlanilhas(Document docTiss) {
		Element linkErros = docTiss.selectFirst("a:contains(Clique aqui para acessar as planilhas)");
		if (linkErros == null) {
			throw new IllegalStateException("Link para a página de planilhas não encontrado.");
		}
		return linkErros.attr("href");
	}

	static String extrairUrlDownloadErros(Document docErros) {
		Element linkDownload = docErros.selectFirst("a:contains(Clique aqui para baixar a tabela de erros no envio para a ANS (.xlsx))");
		if (linkDownload == null) {
			throw new IllegalStateException("Link de download da planilha de erros não encontrado.");
		}
		return linkDownload.attr("href");
	}
	
}
