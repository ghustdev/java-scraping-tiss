# Web Crawler ANS - Padrão TISS

Este projeto consiste num **Web Crawler** desenvolvido em **Java 21 (com dependência do Groovy 3)** para automatizar a recolha de dados e documentos do Padrão TISS (Troca de Informações na Saúde Suplementar) diretamente do portal da Agência Nacional de Saúde (ANS).

## 🚀 Sobre o Projeto

O objetivo foi criar um bot capaz de simular a navegação humana no site do Governo, acedendo a múltiplas páginas, realizando o *parsing* de HTML para extrair links específicos e tabelas de dados, e organizando os ficheiros descarregados localmente.

### Funcionalidades (Tasks)
- **Task 1:** Navegação até à versão mais recente do Padrão TISS e download do *Componente de Comunicação*.
- **Task 2:** Extração de dados da tabela de "Histórico de Versões", exportação para formato **CSV** e implementação de lógica de paragem na competência de Janeiro de 2016.
- **Task 3:** Navegação e download automático da *Tabela de erros no envio para a ANS*.

## 🛠 Tecnologias Utilizadas
* **Java 21:** Versão moderna da linguagem, aproveitando funcionalidades como a API de ficheiros (`Files.writeString`).
* **HTTPBuilder (NG):** Biblioteca utilizada para realizar as requisições HTTP e navegar entre as URLs do portal.
* **Jsoup:** Utilizada como o motor de "leitura" do robô, permitindo selecionar elementos HTML via seletores CSS de forma precisa.

## 🧠 Aprendizados e Superação de Desafios

Durante o desenvolvimento deste projeto, enfrentei desafios técnicos que foram fundamentais para a minha evolução:

1.  **URL vs. Conteúdo HTML:** Um dos meus maiores aprendizados ocorreu quando tentei extrair uma tabela passando apenas a String da URL para o Jsoup. Recebi um erro de `null`, o que me fez entender que o Jsoup não "viaja" até à internet sozinho; ele precisa que o **HTTPBuilder** vá buscar o HTML primeiro. Só depois de ter o conteúdo em memória é que posso fazer o *parsing*.
2.  **Substring e Seletores:** Inicialmente, pensei em manipular o HTML como se fosse um texto simples usando `indexOf` e `substring`. Percebi rapidamente que isso seria inviável num site complexo. Aprender a usar seletores CSS no Jsoup (como `tr:contains(...)`) mudou completamente a forma como vejo o Web Scraping.
3.  **Modularização e Independência:** Estruturei o projeto para que cada Task fosse independente. Aprendi que, embora a navegação inicial seja partilhada (relacionada), os métodos de extração devem ser isolados para que, se um link de download mudar, o resto do robô continue a funcionar.
4.  **Facilidades do Java 21:** Utilizar a API `nio.file` simplificou drasticamente a gestão de pastas e gravação de ficheiros, permitindo criar diretórios e salvar planilhas CSV com pouquíssimas linhas de código.

## ⚠️ Dificuldades Enfrentadas

Nem tudo foi um mar de rosas. A configuração da arquitetura do projeto trouxe alguns quebra-cabeças:

* **Integração Java vs. Groovy:** Como o `HTTPBuilder-ng` foi construído sobre a linguagem Groovy, o compilador do Java gerou erros por não reconhecer classes como `groovy.lang.Closure`. Foi necessário adicionar não só o núcleo do Groovy, mas também os módulos `groovy-xml` e `groovy-json` no `pom.xml` para evitar exceções de `NoClassDefFoundError` em tempo de execução.
* **Tipagem Inteligente (ClassCastException):** Ao receber a resposta da requisição HTTP, tentei forçar a conversão (cast) direta para `String`. Ocorreu um erro fatal porque o `HTTPBuilder` identificou que o retorno era um HTML, acionou o Jsoup por debaixo dos panos e retornou um objeto `Document`. A dificuldade transformou-se numa vantagem, pois passei a utilizar o `Document` diretamente na função, otimizando o código.

## 📂 Estrutura de Saída
O bot organiza os resultados no diretório:
`./docs/Arquivos_padrao_TISS/`
- `Componente_Comunicacao.zip`
- `Historico_Versoes.csv`
- `Tabela_Erros_ANS.xlsx`

## ⚙️ Como Executar
1. Instalar o **JDK 21**.
2. Clonar o repositório e abrir na sua IDE de preferência.
3. Executar o Main.ava ou rodar pelo Maven ('./mvnw exec:java')
3. Garantir que o Maven carregou todas as dependências do `pom.xml` (HTTPBuilder, JSoup, Groovy base, XML e JSON).
4. Executar a classe `Main` (`Task1CrawlerTISS`, etc.).
5. Verificar os ficheiros gerados na pasta de downloads configurada.

#### ⚙️ Como Testar
- Rode ´mvn test´

---
**Autor:** Gustavo  
*Desenvolvido com o objetivo de aprender e aplicar conceitos modernos de Java e automação.*