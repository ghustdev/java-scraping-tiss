import groovyx.net.http.HttpBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Task1CrawlerTISS {
	
	public static final String URL_BASE = "https://www.gov.br/ans/pt-br";
	
	public static void main(String[] args) {
		System.out.println("Iniciando Web Scraping da Task 1...");
		
		try {
			System.out.println("1. Acessando página inicial (https://www.gov.br/ans/pt-br)");
			Document docInicial = pegarHtml(URL_BASE);
			Element linkPrestador = docInicial.selectFirst("a:contains(Espaço do Prestador de Serviços de Saúde)");
			String urlPrestador = linkPrestador.attr("href");
			
			
			System.out.println("2. Acessando Espaço do Prestador (https://www.gov.br/ans/pt-br/assuntos/prestadores)");
			Document docPrestador = pegarHtml(urlPrestador);
			Element linkTiss = docPrestador.selectFirst("a:contains(TISS - Padrão para Troca de Informação de Saúde Suplementar)");
			String urlTiss = linkTiss.attr("href");
			
			
			System.out.println("3. Acessando página do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss)");
			Document docTiss = pegarHtml(urlTiss);
			// A busca mais recente do TISS - Padrão para Troca de Informação de Saúde Suplementar é o priemiro link com nome: Clique aqui para acessar a versão Março/2026
			Element linkVersaoRecente = docTiss.selectFirst("a:contains(Clique aqui para acessar a versão )");
			String urlVersaoRecente = linkVersaoRecente.attr("href");
			
			
			System.out.println("4. Acessando a versão mais recente do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-marco-2026)");
			Document docArquivos = pegarHtml(urlVersaoRecente);
			Element linkPadraoTISS = docArquivos.selectFirst("tr:contains(Componente de Comunicação)");
			Element linkDownload = linkPadraoTISS.selectFirst("a");
			String urlPadraoTISSDownload = linkDownload.attr("href");
			
			System.out.println("Sucesso! Link de download encontrado: " + urlPadraoTISSDownload);
			
			baixarArquivo(urlPadraoTISSDownload, "Componente_Comunicacao.zip");
			
			System.out.println("\nTask 1 concluída com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Ocorreu um erro na navegação: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Document pegarHtml(String url) {
		HttpBuilder http = HttpBuilder.configure(config -> {
			config.getRequest().setUri(url);
		});
		
		// Agora convertemos para Document, pois é isso que o HttpBuilder está devolvendo
		return (Document) http.get();
	}
	
	public static void baixarArquivo(String url, String nomeArquivo) throws Exception {
		Path diretorio = Paths.get("/home/gustavo/IdeaProjects/java-projetcs/java-scraping-tiss/docs/Arquivos_padrao_TISS");
		
		if (!Files.exists(diretorio)) {
			Files.createDirectories(diretorio);
		}
		
		Path caminhoArquivo = diretorio.resolve(nomeArquivo);
		
		try (InputStream in = URI.create(url).toURL().openStream()) {
			Files.copy(in, caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Arquivo salvo em: " + caminhoArquivo.toAbsolutePath());
		}
	}
}