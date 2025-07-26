# Sistema de Gestão de Academia

Este é um projeto Java desenvolvido como parte da disciplina Laboratório de Programação 2 (LAB2). O sistema simula uma plataforma de gestão para academias, incluindo funcionalidades como cadastro de alunos, criação de treinos personalizados e atribuição de instrutores.

# Estrutura de Pacotes

- `model`: Contém as classes principais do domínio (Aluno, Instrutor, Pessoa, Treino, Exercicio).
- `repository`: Repositórios de dados, implementados com coleções em memória (`Map`, `List`).
- `service`: Camada de negócio com as regras de manipulação e criação de objetos.
- `main`: Classe principal contendo o método `main()` para testes em prompt.
- 'mainGUI': Para rodar a interface.
- 
# Conceitos Aplicados

- **Herança**: Classe `Pessoa` é a superclasse de `Aluno` e `Instrutor`.
- **Interface**: `UsuarioSistema` define contrato básico para autenticação de usuários.
- **Classe Abstrata**: `Pessoa` serve como base abstrata para herança.
- **Teste com `main()`**: Simula o cadastro de aluno, criação de treino e exibição dos dados.
- Singleton: Repositórios e Fachada com getInstancia()
- Polimorfismo: Interface UsuarioSistema para login
- Encapsulamento: Atributos privados com métodos getters*"


# Como Executar

1. Clone o repositório:
   ```
   git clone https://github.com/seu-usuario/sistema-academia-lab2.git
   ```

2. Abra a pasta main e avance para a pasta java e rode o Main.java para o teste em prompt ou a MainGUI.java para rodar a interface

# Funcionalidades Implementadas

- Cadastro e listagem do aluno
- Cadastro e listagem do instrutor
- Criação de treinos personalizados
- Associação de treinos a alunos
- Definição e edição de exercícios

# Equipe

- **Lucas Augusto** – 




