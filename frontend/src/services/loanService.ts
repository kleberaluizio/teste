import { LoanFinancialRecord } from "../types/loanFinancialRecord";
import { LoanRequest } from "../types/loanRequest";

const API_URL = 'http://localhost:8080/loan';

export class LoanService {

  public async getFinancialSummary(request: LoanRequest): Promise<LoanFinancialRecord[]> {
    try {
      const response = await fetch(`${API_URL}/financial-summaries`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(request)
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data: LoanFinancialRecord[] = await response.json();
      return data;
    } catch (error) {
      console.error('Error fetching loan records:', error);
      throw error;
    }
  }
}