# accenture-desafio project

Projeto criado para o desafio tecnico da Accenture

Aplicação desenvolvida utilizando Quarkus com java 11.

Documentação da aplicação vai estar disponível no http://localhost:8080/documentation/

Todos os endpoints estão protegidos por token utilizando keycloak.

A configuração do keycloak pode ser feita accesando o http://localhost:8180/auth/

Foi utilizado docker-compose para parte de banco de dados e keycloak

Segue uma print do coverage atual do projeto, lembrando que não foi adicionado NADA ao exclude, o coverage está analisando TODOS os arquivos do projeto, as partes que se refere ao fluxo da aplicação está com coverage acima de 95% (Services e Helpers).

![Screenshot_1](https://user-images.githubusercontent.com/15441558/120019730-2c6e0a80-bfbf-11eb-9037-3c765ad11c20.png)

Para rodar a aplicação Quarkus é necessário configurar um Run/Debug configuration para Quarkus, segue uma print de uma configuração básica que irá rodar a aplicação

![Screenshot_2](https://user-images.githubusercontent.com/15441558/120019942-722ad300-bfbf-11eb-995c-492931d8dda5.png)

É necessário escolher o JRE 11 e adicionar o working directory e já está pronto para rodar a aplicação.

Por conta do pouco tempo que tenho atualmente para desenvolver este desafio, esse foi o melhor que consegui nesse prazo ;)
