import { LoanScheduleEntry } from "../types/LoanScheduleEntry";
import { LoanRequest } from "../types/loanRequest";

const API_URL = 'http://localhost:8080/loan';

export class LoanService {

  public async getFinancialSummary(request: LoanRequest): Promise<LoanScheduleEntry[]> {
    try {
      const response = await fetch(`${API_URL}/financial-summary`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(request)
      });
      if (!response.ok) {
        throw new Error(`HTTP error!`);
      }
      const data: LoanScheduleEntry[] = await response.json();
      return data;
    } catch (error) {
      console.error('Error fetching financial summary:', error);
      throw error;
    }
  }
}