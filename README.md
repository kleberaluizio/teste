# Calculadora de Empréstimo

## Tecnologias Utilizadas
### Back-end
* Java 21
* Maven
* Tomcat (embedded server)
* JUnit

### Front-end
* Vite, ferramenta de build para acelerar o desenvolvimento do projeto, simplificando a configuração inicial.
* React (Javascript, Typescript)
	* **react-number-format** para formatar entradas em campos de texto, garantindo que o usuário só possa inserir textos que atendam a padrões numéricos.
	* **react-hot-toast** para exibir notificações toast, informando aos usuários sobre ações de erro na ausência de informações ou dados inválidos.
  * **react-hook-form**  para gerenciar formulários.
  * **styled-components** para estilizar componentes utilizando a sintaxe de template literals do JavaScript e acoplando o estilo direto ao componente.
* Bootstrap

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

