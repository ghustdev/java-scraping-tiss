import groovyx.net.http.HttpBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Task1CrawlerTISS {
	
	public static void executarTask1 (String URL) {
		System.out.println("\nIniciando Web Scraping da Task 1...\n");
		
		try {
			System.out.println("Acessando página do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss)");
			Document docTiss = Task1CrawlerTISS.pegarHtml(URL);
			String urlRecente = extrairUrlVersaoRecente(docTiss);
			
			
			System.out.println("Acessando a versão mais recente do TISS (https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-marco-2026)");
			Document docArquivos = pegarHtml(urlRecente);
			String urlPadraoTISSDownload = extrairUrlDownloadComponente(docArquivos);
			
			System.out.println("Sucesso! Link de download encontrado: " + urlPadraoTISSDownload);
			
			baixarArquivo(urlPadraoTISSDownload, "Componente_Comunicacao.zip");
			
			System.out.println("\nTask 1 concluída com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Ocorreu um erro na navegação: " + e.getMessage());
			e.printStackTrace();
		}
	}

	static String extrairUrlVersaoRecente(Document docTiss) {
		Element linkRecente = docTiss.selectFirst("a:contains(Clique aqui para acessar a versão )");
		if (linkRecente == null) {
			throw new IllegalStateException("Link da versão mais recente do TISS não encontrado.");
		}
		return linkRecente.attr("href");
	}

	static String extrairUrlDownloadComponente(Document docArquivos) {
		Element linhaComponente = docArquivos.selectFirst("tr:contains(Componente de Comunicação)");
		if (linhaComponente == null) {
			throw new IllegalStateException("Linha de download do componente de comunicação não encontrada.");
		}

		Element linkDownload = linhaComponente.selectFirst("a");
		if (linkDownload == null) {
			throw new IllegalStateException("Link de download do componente de comunicação não encontrado.");
		}

		return linkDownload.attr("href");
	}
	
	public static Document pegarHtml(String url) {
		HttpBuilder http = HttpBuilder.configure(config -> {
			config.getRequest().setUri(url);
		});
		
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
