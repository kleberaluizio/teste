import React, { createContext, useContext, useState, ReactNode } from 'react';
import { LoanScheduleEntry } from '../types/LoanScheduleEntry';

interface FinancialSummaryContextType {
  summary: LoanScheduleEntry[];
  setSummary: React.Dispatch<React.SetStateAction<LoanScheduleEntry[]>>;
}

const FinancialSummaryContext = createContext<FinancialSummaryContextType | undefined>(undefined);

export const FinancialSummaryProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [summary, setSummary] = useState<LoanScheduleEntry[]>([]);
  
  return (
    <FinancialSummaryContext.Provider value={{ summary, setSummary }}>
      {children}
    </FinancialSummaryContext.Provider>
  );
};

export const useFinancialSummary = (): FinancialSummaryContextType => {
  const context = useContext(FinancialSummaryContext);
  if (!context) {
    throw new Error('LoanInputInfo and FinancialSummary must be within FinancialSummaryProvider');
  }
  return context;
};
