import React, { createContext, useContext, useState, ReactNode } from 'react';
import { LoanFinancialRecord } from '../types/loanFinancialRecord';

interface FinancialSummaryContextType {
  summary: LoanFinancialRecord[];
  setSummary: React.Dispatch<React.SetStateAction<LoanFinancialRecord[]>>;
}

const FinancialSummaryContext = createContext<FinancialSummaryContextType | undefined>(undefined);

export const FinancialSummaryProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [summary, setSummary] = useState<LoanFinancialRecord[]>([]);
  
  return (
    <FinancialSummaryContext.Provider value={{ summary, setSummary }}>
      {children}
    </FinancialSummaryContext.Provider>
  );
};

export const useFinancialSummary = (): FinancialSummaryContextType => {
  const context = useContext(FinancialSummaryContext);
  if (!context) {
    throw new Error('useFinancialSummary must be used within a FinancialSummaryProvider');
  }
  return context;
};
