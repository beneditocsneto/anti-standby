# anti-standby

Utilitário Java simples que impede o sistema de entrar em modo de espera (standby/suspensão), simulando um pequeno movimento do mouse em intervalos regulares.

## Como funciona

A aplicação move o cursor do mouse 10 pixels para o lado e o retorna à posição original a cada **25 segundos**, o que é suficiente para impedir que o sistema operacional considere a máquina ociosa.

Opcionalmente, é possível informar um horário-limite (`HH:mm`) como argumento. Enquanto o horário atual for **anterior** ao horário informado, o programa continua rodando; ao atingir ou ultrapassar esse horário, ele se encerra automaticamente.

## Requisitos

- Java 25 (JDK)
- Maven

## Build

```bash
./mvnw clean package
```

O plugin `maven-jar-plugin` já gera o `.jar` com a `Main-Class` configurada (`org.mb.Main`), então o artefato pode ser executado diretamente.

## Uso

Executar sem limite de horário (roda indefinidamente enquanto o processo estiver ativo):

```bash
java -jar target/anti-standby-1.0-SNAPSHOT.jar
```

Executar até um horário específico (formato 24h `HH:mm`):

```bash
java -jar target/anti-standby-1.0-SNAPSHOT.jar 18:30
```

Nesse exemplo, o programa mantém o sistema ativo até as 18:30 e depois encerra sozinho.

### Validação do argumento

Se o argumento informado não estiver no formato `HH:mm` (ou não for fornecido), o programa assume que deve rodar indefinidamente e exibe uma mensagem informativa no console.

## Observações

- A aplicação usa `java.awt.Robot`, portanto requer um ambiente gráfico (não funciona em modo headless).
- Em alguns sistemas operacionais, pode ser necessário conceder permissão de acessibilidade/controle do mouse para o processo Java.
