import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task2CrawlerHistorico {
	
	// Método para ser chamado no seu fluxo principal
	public static void executarTask2(String URL) {
		System.out.println("\nIniciando Web Scraping da Task 2...\n");
		
		try {
			System.out.println("Acessando página do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss)");
			Document docTiss = Task1CrawlerTISS.pegarHtml(URL);
			Element linkTodas = docTiss.selectFirst("a:contains(Clique aqui para acessar todas as versões dos Componentes)");
			String urlTodas = linkTodas.attr("href");
			
			Document docHistorico = Task1CrawlerTISS.pegarHtml(urlTodas);
			Element tabela = docHistorico.selectFirst("table");
			
			StringBuilder conteudoCsv = new StringBuilder();
			conteudoCsv.append("Competencia,Publicacao,Inicio_Vigencia\n");
			Elements linhas = tabela.select("tbody tr");
			
			for (Element linha : linhas) {
				Elements colunas = linha.select("td");
				
				if (colunas.size() >= 3) {
					String competencia = colunas.get(0).text();
					String publicacao = colunas.get(1).text();
					String vigencia = colunas.get(2).text();
					
					conteudoCsv.append(competencia).append(",")
							.append(publicacao).append(",")
							.append(vigencia).append("\n");
					
					if (competencia.toLowerCase().contains("jan/2016") ||
							competencia.toLowerCase().contains("janeiro/2016")) {
						break;
					}
				}
			}
			
			Path caminhoArquivo = Paths.get("/home/gustavo/IdeaProjects/java-projetcs/java-scraping-tiss/docs/Arquivos_padrao_TISS/Historico_Versoes.csv");
			
			Files.writeString(caminhoArquivo, conteudoCsv.toString());
			
			System.out.println("Tabela extraída com sucesso! Arquivo salvo em: " + caminhoArquivo.toAbsolutePath());
			
			System.out.println("\nTask 2 concluída com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Erro ao extrair a tabela: " + e.getMessage());
			e.printStackTrace();
		}
	}
}