# 🚀 Exemplo de Integração Java + Jira

Este projeto demonstra como integrar uma aplicação Java com o Jira Cloud usando a API REST para criar issues automaticamente.

## 📋 Sobre a Autenticação via API Token

A autenticação no Jira utiliza um **token de API** combinado com o endereço de e-mail do usuário. Este método seguro permite que aplicações externas façam requisições autenticadas sem precisar armazenar senhas. O token funciona como uma credencial especial que pode ser revogada ou deletada a qualquer momento.

Para mais informações, consulte a [documentação oficial da API do Jira](https://developer.atlassian.com/cloud/jira/platform/rest/v3/).

## 🔐 Como Gerar um Token de API no Jira

Siga os passos abaixo para criar um token de API e integrá-lo com sua aplicação Java:

### Passo 1: Acessar o Site do Jira
- Acesse [jira.atlassian.com](https://jira.atlassian.com)
- Clique em "Criar conta" ou faça login se já possuir uma conta
- Preencha os dados solicitados

### Passo 2: Criar um Site (Workspace)
- Após criar a conta, você será redirecionado para criar um novo site
- Escolha um nome para seu site
- Clique em "Criar site"

### Passo 3: Criar um Projeto
- Dentro do seu site, clique em "Criar projeto"
- Selecione o tipo de projeto (ex: "Software" para desenvolvimento)
- Escolha um modelo de projeto (ex: "Kanban")
- Defina um nome para seu projeto
- Clique em "Criar"

### Passo 4: Copiar a URL Base do Projeto
- A URL do seu projeto terá este formato: `https://seu-dominio.atlassian.net`
- Copie esta URL base para usar no código Java

### Passo 5: Acessar as Configurações da Conta
- Clique na sua foto de perfil no canto superior direito
- Selecione "Configurações de perfil" ou "Account settings"

### Passo 6: Gerar um Token de API
- No menu lateral, clique em "Segurança" (Security)
- Localize a seção "API tokens"
- Clique em "Criar token de API" (Create API token)

### Passo 7: Configurar Expiração (Opcional)
- Na tela de criação, você pode definir uma expiração para o token
- É recomendado definir uma expiração por questões de segurança

### Passo 8: Copiar o Token
- Após gerar o token, uma URL será exibida com o valor do token
- **Copie e guarde em um local seguro** - você não conseguirá ver o valor novamente

### Passo 9: Integrar com Seu Código Java
- Abra o arquivo `src/main/java/school/sptech/app/App.java`
- Substitua os valores das seguintes variáveis:

```java
String baseUrl = "https://seu-dominio.atlassian.net";
String email = "seu-email@example.com";
String apiToken = "seu-token-aqui";
String projectKey = "key-do-projeto";
```

### Passo 10: Executar o Código
- Compile e execute seu projeto Java
- Uma nova issue será criada no backlog do seu projeto Jira
- Você deve ver a resposta da API no console com o ID da issue criada

## 📝 Exemplo de Uso

Para criar uma issue no Jira usando este código:

```java
import school.sptech.config.Jira;

public class App {
    public static void main(String[] args) throws Exception {
        String baseUrl = "https://seu-dominio.atlassian.net";
        String email = "seu-email@example.com";
        String apiToken = "seu-token-aqui";
        
        Jira jira = new Jira(baseUrl, email, apiToken);
        
        String resultado = jira.createIssue(
            "key-do-projeto",           // Project Key
            "Nova tarefa",     // Resumo
            "Task"             // Tipo de issue
        );
        
        System.out.println(resultado);
    }
}
```

## 💡 Identificando a Chave do Projeto

A **chave do projeto** (project key) aparece na URL:

- Exemplo: `https://java-integration.atlassian.net/jira/software/projects/SCRUM/boards/1/backlog`
- Nesse exemplo, a chave do projeto é **`SCRUM`**

Você também pode encontrá-la nas configurações do projeto dentro do Jira.

## ⚠️ Segurança

- **Nunca compartilhe seu token de API** publicamente ou suba no GitHub
- **Nunca commite suas credenciais** no repositório
- Se o token vazar, acesse imediatamente o Jira e delete/revogue o token
- Crie um novo token e atualize suas configurações

## 🛠️ Requisitos

- Java 21 ou superior
- Conta no Jira Cloud (gratuita)
- Maven (opcional, para build via terminal)

## 📚 Recursos Úteis

- [Documentação da API REST do Jira Cloud](https://developer.atlassian.com/cloud/jira/platform/rest/v3/)
- [Autenticação no Jira Cloud](https://developer.atlassian.com/cloud/jira/platform/basic-auth-for-rest-apis/)
- [Referência da API de Issues](https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/)
- [Criando Tokens de API](https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/)

