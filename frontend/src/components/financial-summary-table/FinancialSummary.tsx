import React from 'react'
import styled from 'styled-components';
import SummaryHead from './SummaryHead';
import { useFinancialSummary } from '../../context/FinancialSummaryContext';
import { LoanScheduleEntry } from '../../types/LoanScheduleEntry';

const FinancialSummary: React.FC = () => {
  const { summary } = useFinancialSummary();
  const isDataAvailable = summary.length > 0;

  const formatDate = (dateString: string) => {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}/${year}`;
  };

  const formatCurrency = (number: number) => {
    return Math.abs(number).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  };

  return (
    <Summary className='table-responsive'>
      {isDataAvailable ? (
      <table className="table table-bordered table-sm" border={1}>
        <SummaryHead></SummaryHead>
        <tbody className='table-body'>
          {summary.map((record: LoanScheduleEntry, index: number) => (
            <tr key={index}>
              <td className='align-center'>{formatDate(record.competenceDate)}</td>
              <td className='record'>{formatCurrency(record.loanAmount)}</td>
              <td className='record'>{formatCurrency(record.outstandingAmount)}</td>
              <td className='align-center'>{record.consolidated}</td>
              <td className='record'>{formatCurrency(record.totalPayment)}</td>
              <td className='record'>{formatCurrency(record.amortization)}</td>
              <td className='record'>{formatCurrency(record.balance)}</td>
              <td className='record'>{formatCurrency(record.provision)}</td>
              <td className='record'>{formatCurrency(record.accumulated)}</td>
              <td className='record'>{formatCurrency(record.paid)}</td>
            </tr>
          ))}
        </tbody>
      </table>
      ) : (
        <NoDataMessage>
          <h3>Não há dados disponíveis. Por favor, preencha o formulário para gerar o relatório.</h3>
          <h3>Se você já preencheu o formulário, certifique-se de enviá-lo para ver as informações aqui.</h3>
        </NoDataMessage>
      )}
    </Summary>
  )
}

const Summary = styled.section`
  margin: 0 45px 0 45px;
  max-height: 400px;
  overflow-y: auto;
    
  @media (max-width: 1200px) {
    font-size: 13px;
    max-height: 300px;
  }
`;

const NoDataMessage = styled.div`
  margin-top: 45px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h3 {
    font-size: 20px;
  }
`;

export default FinancialSummary