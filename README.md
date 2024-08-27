# Calculadora de Empréstimo
Este projeto é uma aplicação de gerenciamento e visualização de cronogramas de empréstimos. A aplicação permite acompanhar detalhadamente o progresso de pagamento de empréstimos através de uma tabela informativa.

**Detalhes da Tabela**
- Data Competência: Data do cálculo do período.
- Valor de Empréstimo: Valor do empréstimo.
- Saldo Devedor: Saldo restante do empréstimo após cada pagamento.
- Consolidada: Percentual da parcela consolidada.
- Total: Total acumulado de amortização e juros pagos.
- Amortização: Valor da amortização para o período.
- Saldo: Saldo do empréstimo após amortização.
- Provisão: Provisão acumulada até a data.
- Acumulado: Total acumulado até a data.
- Pago: Total pago até a data.

**Preview**


## Executando a Aplicação Localmente:

- Clone o repositório
   ```sh
   git clone https://github.com/kleberaluizio/Teste.git
   ```
### Back-end
1. Navegue até o diretório
```sh
   cd calculadoradeemprestimo
 ```
2. Compile e execute
 ```sh
   ./mvnw spring-boot:run
 ```
Se preferir, você pode abrir o diretório calculadoradeemprestimo em sua IDE de preferência e executar a aplicação por lá.
### Front-end
1. Instale os pacotes npm:
   ```sh
   npm install
   ```
2. Crie a build da aplicação:
   ```sh
   npm run build
   ```
3. Inicie o servidor que simula o ambiente de produção:
   ```sh
   npm run preview -- --port 3040
   ```
Se preferir, você pode iniciar a aplicação em modo desenvolvedor:
```sh
   npm run dev
   ```
## Tecnologias Utilizadas
### Back-end
- [x] Java 21,
- [x] Spring Boot
- [x] Maven
- [x] Tomcat (embedded server)
- [x] JUnit

### Front-end
- [x] Vite, ferramenta de build para acelerar o desenvolvimento do projeto, simplificando a configuração inicial.
- [x] React (Javascript, Typescript)
	* **react-number-format:** Para formatar entradas em campos de texto, garantindo que o usuário só possa inserir dados numéricos.
	* **react-hot-toast:** Para exibir notificações toast, informando aos usuários sobre erros ou dados inválidos.
  	* **react-hook-form:**  Para gerenciar formulários.
  	* **styled-components:** Para estilizar componentes utilizando a sintaxe de template literals do JavaScript.
- [x] Bootstrap

## Endpoints
**POST /loan/financial-summary**

Retorna um cronograma/resumo de pagamentos e datas relavantes.
  
**Payload esperado (exemplo):**

```json
{
  "initialDate": "YYYY-MM-DD",
  "finalDate": "YYYY-MM-DD",
  "firstPayment": "YYYY-MM-DD",
  "loanAmount": 10000.00,
  "interestRate": 5.5
}
```

**Retornos:**
  * 200 (OK): Sucesso. Retorna o cronograma/resumo de pagamentos e datas relavantes.
  * 400 (Bad Request): Erro de validação. Datas inválidas ou payload incorreto.
  * 500 (Internal Server Error): Falha interna no servidor.
	* Retorna status 200 (OK) em caso de sucesso, 400 (Bad Request) para datas inválidas ou 500 (Internal Server Error) em falha.

