# Tech Challenge - Hackaton - Video para Imagens

# Índice

* [Índice](#índice)
* [Breve Descrição](#Breve-Descrição)
* [Tecnologias Utilizadas](#Tecnologias-Utilizadas)
* [Estrutura do Projeto](#Estrutura-do-Projeto)
* [Rodando o Projeto Local](#Rodando-o-Projeto-Local)
* [Sonar Coverage](#Sonar-Coverage)

## Breve Descrição

Aplicação se trata de um Projeto Fiap Tech Challenge (Hackathon) - Software Architecture, simulando um projeto de uma
empresa que recebe videos e transforma em Imagens.

Este repositório é referente ao microserviço responsável pelo processamento dos videos (Transforma em imagem e zippar).

### Demais repositórios
- https://github.com/leodelmiro/fiap-hackaton-video-usuario
- https://github.com/leodelmiro/fiap-hackaton-video-recebe
- https://github.com/leodelmiro/fiap-hackaton-video-gerencia
- https://github.com/leodelmiro/fiap-hackaton-video-notifica
- https://github.com/leodelmiro/fiap-hackaton-video-gateway
- https://github.com/leodelmiro/fiap-hackaton-video-db
- https://github.com/leodelmiro/fiap-hackaton-video-infra

## Tecnologias Utilizadas

- Kotlin
- Spring 3
- Swagger
- Docker
- Docker Compose
- AWS EKS
- Kubernetes
- S3

## Estrutura do Projeto

- Entrypoint: Entrada de acesso externo para a aplicação
    - Queue: Entrada de acesso via Consumo de Fila.
- Core: Sem acesso ao mundo externo, livre de frameworks e isolado.
    - Domain: Pode ser acesso por qualquer um.
    - Usecase: Regras de negócio.
    - Dataprovider: Interfaces para o dados do mundo externo.
- Dataprovider: Implmentações para o mundo externo
    - Gateway: Implementações das Interfaces de acesso do Core.
- Config: Configurações do Projeto

## Rodando o Projeto Local

### 1. Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- Docker
- Git
- JDK 21 ou superior

### 2. Clonar o Repositório

Clone o repositório do projeto:

```sh
git clone https://github.com/leodelmiro/fiap-hackaton-video-processa
```

### 3. Executar o Script de Setup

O projeto inclui um script de setup (`setup.sh`) que automatiza o processo de construção e execução do projeto. O script
realiza as seguintes operações:

- Para e remove os contêineres Docker, juntamente com seus volumes.
- Executa a construção do projeto Maven.
- Inicia os contêineres Docker em modo destacável e reconstrói as imagens se necessário.

Para executar o script, siga os passos abaixo:

#### macOS e Linux

1. **Tornar o Script Executável**:

    ```sh
    chmod +x setup.sh
    ```

2. **Executar o Script**:

    ```sh
    ./setup.sh
    ```

#### Windows

1. **Executar o Script**:

   No PowerShell ou Git Bash:

    ```sh
    ./setup.sh
    ```"

## Sonar Coverage
![Sonar Coverage](https://github.com/user-attachments/assets/b22bc36b-327a-41e5-a169-92c65e2c20e6)
https://sonarcloud.io/summary/overall?id=leodelmiro_fiap-hackaton-video-processa&branch=main
