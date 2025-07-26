package com.gabriel.gerenciadordetarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GerenciadordetarefasApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("H:/Gabriel/Projetos/Gerenciador de Tarefas/CodigoFonte/back/gerenciadordetarefas").ignoreIfMissing().load();

		String dbUrl = dotenv.get("DB_URL");
		String dbUser = dotenv.get("DB_USER");
		String dbPassword = dotenv.get("DB_PASSWORD");
		String jwtSecret = dotenv.get("MINHA_CHAVE_JWT");

		if (dbUrl == null || dbUser == null || dbPassword == null) {
			System.err.println("Erro: Variáveis de ambiente DB_URL, DB_USER ou DB_PASSWORD não encontradas no arquivo .env");
			System.exit(1);
		}

		System.setProperty("DB_URL", dbUrl);
		System.setProperty("DB_USER", dbUser);
		System.setProperty("DB_PASSWORD", dbPassword);
		System.setProperty("MINHA_CHAVE_JWT", jwtSecret);

		SpringApplication.run(GerenciadordetarefasApplication.class, args);
	}

}
