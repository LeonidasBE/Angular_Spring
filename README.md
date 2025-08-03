## 🧪 Projeto de Aprendizado com Angular & Spring Boot

Este repositório é voltado para estudos e experimentações com diversas tecnologias, com foco principal em **Angular** e **Spring Boot**.

---

## 🚀 Objetivo

Este projeto surgiu como um laboratório pessoal para aprendizado contínuo e testes com diferentes stacks de desenvolvimento.  
Originalmente, ele utilizava:

- **Angular 5**
- **Spring Boot 1.5.10**
- **Java 8**

Como um **autodesafio**, decidi atualizar todo o stack para versões mais modernas:

- **Angular 19.1.5**
- **Spring Boot 3.4.1**
- **Java 17**

---

## 🔮 Futuras Implementações

A ideia é seguir evoluindo o projeto com o uso de novas tecnologias e boas práticas. Entre os planos futuros, estão:

- Integração com **Redis**
- Configuração de **CI/CD** (GitHub Actions, GitLab CI ou similar)
- Utilização do **Spring Cloud** para arquitetura de microsserviços
- Testes com bancos de dados **NoSQL**
- Monitoramento com ferramentas como **Prometheus** e **Grafana**
- Aplicação de testes automatizados (unitários e de integração)

---

## 📁 Estrutura do Projeto

```bash
/
├── helpdesk/         # Código principal do Spring Boot
├── angular/helpdesk  # Aplicação principal Angular
└── README.md
```

---

## ⚙️ Como Executar Localmente

### Pré-requisitos

- Node.js (versão recomendada: 18+)
- Angular CLI instalado globalmente
- Java 17
- Maven
- MongoDB 8.0.12

### Backend (Spring Boot)

```bash
# Acesse a pasta do backend
cd helpdesk/

# Execute a aplicação
./mvnw spring-boot:run
```

### Frontend (Angular)

```bash
# Acesse a pasta do frontend
cd angular/helpdesk

# Instale as dependências
npm install

# Inicie o servidor de desenvolvimento
ng serve
```

---

## ⚠️ Aviso

Este projeto **não** é uma aplicação de produção.  
Funciona como um **"Frankenstein de testes"** — um ambiente totalmente livre para **experimentar, quebrar e reconstruir**.

---

## 🤝 Contribuições

Mesmo sendo um projeto pessoal, feedbacks, sugestões ou ideias são sempre bem-vindos!  
Sinta-se à vontade para abrir issues ou pull requests. 😊

---
