# GerenciarFuncionario — versão simples

Java 21, Maven, Spring Boot, PostgreSQL e Thymeleaf. Apenas MVC com HTML (sem API REST duplicada).

1. Abra o pom.xml como projeto Maven no IntelliJ.
2. Crie o banco `GerenciarFuncionario` caso não exista (respeite maiúsculas).
3. Em src/main/resources/application.properties, substitua `${DB_PASSWORD:}` pela senha local ou defina a variável DB_PASSWORD. Não publique a senha.
4. Execute GerenciarFuncionarioApplication.java. Acesse http://localhost:8080/.
5. Para criar tabelas manualmente, execute sql/criacao.sql no banco e altere ddl-auto para none. IF NOT EXISTS não altera tabelas existentes.

Rotas: /funcionario, /tarefa. Cada página tem cadastro, pesquisa, edição e exclusão. Tarefas têm status e responsável. O painel mostra contagens.

Mantidos os nomes antigos emailFuncionairo e enumStatus (inclusive Comcluida), para compatibilidade com o banco existente. Se renomear, altere também DTO, SQL, Repository, Service e HTML.

Para adaptar a outro tema, altere nesta ordem: Model → Repository → DTO → Service → Controller → HTML → SQL. As buscas simples usam métodos derivados do Spring Data. Os filtros combinados de tarefa usam um laço em memória, adequado a um projeto pequeno.

Testes manuais: cadastrar funcionário; pesquisar; editar; tentar e-mail duplicado; cadastrar tarefa; pesquisar e alterar status; tentar excluir funcionário vinculado; excluir tarefa e depois funcionário. Não foi executado teste de integração neste ambiente.
