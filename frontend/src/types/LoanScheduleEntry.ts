export interface LoanScheduleEntry {
    competenceDate: string;     // Data de competência
    loanAmount: number;         // Valor do empréstimo
    outstandingAmount: number;  // Valor pendente
    consolidated: string;       // Consolidado
    totalPayment: number;       // Pagamento total
    amortization: number;       // Amortização
    balance: number;            // Saldo
    provision: number;          // Provisão
    accumulated: number;        // Acumulado
    paid: number;               // Pago
}
