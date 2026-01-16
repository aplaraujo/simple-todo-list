# Imagem base
FROM ubuntu:latest AS build

# Comando para atualização dos pacotes
RUN apt-get update

# Comando para instalação e configuração do Java
RUN apt-get install openjdk-17-jdk -y

# Comando para copiar tudo que está dentro do projeto para o conteiner
COPY . .

# Instalação do Maven
RUN apt-get install maven -y

# Comando para limpar e rodar o Maven
RUN mvn clean install

# Comando para rodar uma imagem no projeto
FROM openjdk:17-jdk-slim

# Expor a porta 8080
EXPOSE 8080

# Comando para copiar o que há dentro da pasta target para o conteiner
COPY --from=build /target/nome-do-projeto-0.0.1-SNAPSHOT.jar app.jar

# Comando para executar o jar do projeto
ENTRYPOINT ["java", "-jar", "app.jar"]