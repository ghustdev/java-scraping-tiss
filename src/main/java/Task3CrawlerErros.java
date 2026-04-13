import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task3CrawlerErros {
	
	public static void executartask3(String URL) {
		System.out.println("\nIniciando Web Scraping da Task 3...\n");
		
		try {
			System.out.println("Acessando página do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss)");
			Document docTiss = Task1CrawlerTISS.pegarHtml(URL);
			Element linkErros = docTiss.selectFirst("a:contains(Clique aqui para acessar as planilhas)");
			String urlErros = linkErros.attr("href");
			
			System.out.println("Acessando página Tabelas Relacionadas (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-tabelas-relacionadas)");
			Document docErros = Task1CrawlerTISS.pegarHtml(urlErros);
			Element linkDownload = docErros.selectFirst("a:contains(Clique aqui para acessar as planilhas)");
			String urlDownload = linkDownload.attr("href");
			
			Document docFinal = Task1CrawlerTISS.pegarHtml(urlDownload);
			Element tabela = docFinal.selectFirst("table");
			
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
	
//	Clique aqui para acessar as planilhas
}
