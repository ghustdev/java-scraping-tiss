import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskCrawlerTest {

	@Test
	void deveExtrairUrlDaVersaoRecenteDoTiss() {
		Document doc = Jsoup.parse("""
				<html>
				  <body>
				    <a href="https://exemplo.com/outra">Outro link</a>
				    <a href="https://exemplo.com/tiss-marco-2026">Clique aqui para acessar a versão Março/2026</a>
				  </body>
				</html>
				""");

		assertEquals(
				"https://exemplo.com/tiss-marco-2026",
				Task1CrawlerTISS.extrairUrlVersaoRecente(doc)
		);
	}

	@Test
	void deveExtrairUrlDoDownloadDoComponenteDeComunicacao() {
		Document doc = Jsoup.parse("""
				<table>
				  <tbody>
				    <tr>
				      <td>Componente de Comunicação</td>
				      <td><a href="https://exemplo.com/componente.zip">Baixar</a></td>
				    </tr>
				  </tbody>
				</table>
				""");

		assertEquals(
				"https://exemplo.com/componente.zip",
				Task1CrawlerTISS.extrairUrlDownloadComponente(doc)
		);
	}

	@Test
	void deveConverterTabelaHistoricoParaCsvAteJan2016() {
		Document doc = Jsoup.parse("""
				<table>
				  <tbody>
				    <tr>
				      <td>mar/2026</td>
				      <td>01/03/2026</td>
				      <td>15/03/2026</td>
				    </tr>
				    <tr>
				      <td>jan/2016</td>
				      <td>01/01/2016</td>
				      <td>15/01/2016</td>
				    </tr>
				    <tr>
				      <td>dez/2015</td>
				      <td>01/12/2015</td>
				      <td>15/12/2015</td>
				    </tr>
				  </tbody>
				</table>
				""");
		Element tabela = doc.selectFirst("table");

		assertEquals(
				"""
				Competencia,Publicacao,Inicio_Vigencia
				mar/2026,01/03/2026,15/03/2026
				jan/2016,01/01/2016,15/01/2016
				""",
				Task2CrawlerHistorico.converterTabelaHistoricoParaCsv(tabela)
		);
	}

	@Test
	void deveExtrairLinksDaTask3() {
		Document docTiss = Jsoup.parse("""
				<a href="https://exemplo.com/planilhas">Clique aqui para acessar as planilhas</a>
				""");
		Document docErros = Jsoup.parse("""
				<a href="https://exemplo.com/erros.xlsx">Clique aqui para baixar a tabela de erros no envio para a ANS (.xlsx)</a>
				""");

		assertEquals("https://exemplo.com/planilhas", Task3CrawlerErros.extrairUrlPlanilhas(docTiss));
		assertEquals("https://exemplo.com/erros.xlsx", Task3CrawlerErros.extrairUrlDownloadErros(docErros));
	}

	@Test
	void deveFalharQuandoTabelaHistoricoNaoExistir() {
		IllegalStateException exception = assertThrows(
				IllegalStateException.class,
				() -> Task2CrawlerHistorico.converterTabelaHistoricoParaCsv(null)
		);

		assertEquals("Tabela de histórico não encontrada.", exception.getMessage());
	}
}
