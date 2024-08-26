export interface LoanRequest {
    initialDate: string;
    finalDate: string;
    firstPaymentDate: string;  
    loanAmount: number;
    interestRate: number;
}