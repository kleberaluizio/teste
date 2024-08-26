import React from 'react'
import styled from 'styled-components';
import SummaryHead from './SummaryHead';
import { useFinancialSummary } from '../../context/FinancialSummaryContext';
import { LoanFinancialRecord } from '../../types/loanFinancialRecord';

const FinancialSummary: React.FC = () => {
  const { summary } = useFinancialSummary();

  const formatDate = (dateString: string) => {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}/${year}`;
  };

  const formatNumber = (number: number) => {
    return Math.abs(number).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  };

  return (
    <SECTION className='table-responsive'>
      <table className="table table-bordered table-sm" border={1}>
        <SummaryHead></SummaryHead>
        <tbody className='table-body'>
          {summary.map((record: LoanFinancialRecord, index: number) => (
            <tr key={index}>
              <td className='align-center'>{formatDate(record.competenceDate)}</td>
              <td className='record'>{formatNumber(record.loanAmount)}</td>
              <td className='record'>{formatNumber(record.outstandingAmount)}</td>
              <td className='align-center'>{record.consolidated}</td>
              <td className='record'>{formatNumber(record.totalPayment)}</td>
              <td className='record'>{formatNumber(record.amortization)}</td>
              <td className='record'>{formatNumber(record.balance)}</td>
              <td className='record'>{formatNumber(record.provision)}</td>
              <td className='record'>{formatNumber(record.accumulated)}</td>
              <td className='record'>{formatNumber(record.paid)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </SECTION>
  )
}

const SECTION = styled.div`
    margin: 0 45px 0 45px;
    max-height: 400px;
    overflow-y: auto;
    
    @media (max-width: 1200px) {
    font-size: 13px;
    max-height: 300px;
    }
`;

export default FinancialSummary