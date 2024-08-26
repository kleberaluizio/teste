import React from 'react'
import styled from 'styled-components';
import toast, { Toaster } from 'react-hot-toast';
import CurrencyInput from 'react-currency-input-field';
import { NumericFormat } from 'react-number-format';
import { useForm } from 'react-hook-form';
import { LoanService } from '../../services/loanService';
import { LoanRequest } from '../../types/loanRequest';
import { LoanFinancialRecord } from '../../types/loanFinancialRecord';
import { useFinancialSummary } from '../../context/FinancialSummaryContext';

const loanService = new LoanService();


const LoanInputInfo: React.FC = () => {
  const { setSummary } = useFinancialSummary();
  const { register, handleSubmit, setValue, getValues, setError, clearErrors,formState: { errors } } = useForm<LoanRequest>();

  const validateFields = (data: LoanRequest) => {
    
    const requiredFields = ['initialDate', 'finalDate', 'firstPaymentDate','loanAmount', 'interestRate'];
    requiredFields.forEach(field => {
      if (!data[field]) {
        setError(field as keyof LoanRequest, {
          type: 'manual',
          message: 'Este campo é obrigatório',
        });
      }
    });
    if (new Date(data.finalDate) <= new Date(data.initialDate)) {
      setError('finalDate', { type: 'manual', message: 'Data final deve ser após a data inicial' });
      toast.error('Data final deve ser após a data inicial');
    }

    if (new Date(data.firstPaymentDate) <= new Date(data.initialDate)) {
      setError('firstPaymentDate', { type: 'manual', message: 'Data do primeiro pagamento deve ser após a data inicial' });
      toast.error('Data do primeiro pagamento deve ser após a data inicial');
    }
  };

  const onSubmit = async (data: LoanRequest) => {
    console.log("executei")
    try {
      clearErrors();
      validateFields(data);
      
      if (Object.keys(errors).length > 0) {
        return;
      }
      const result: LoanFinancialRecord[] = await loanService.getFinancialSummary(data);
      setSummary(result);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleLoanAmountChange = (value: string | undefined) => {
    setValue('loanAmount', value ? parseFloat(value) : 0)
    clearErrors('loanAmount'); 
  };

  const handleInterestRateChange = (values) => {
    setValue('interestRate', values.value ? parseFloat(values.value) : 0)
    clearErrors('interestRate'); 
  };

  return (
    <FORM onSubmit={handleSubmit(onSubmit)}>
      <div className="form-group col-md-2 px-3">
        <label htmlFor="initialDate">Data Inicial</label>
        <input type="date" className='form-control' id="initialDate" {...register('initialDate')} />
        {errors.initialDate && <span className='error-container'>{errors.initialDate.message}</span>}
      </div>
      <div className="form-group col-md-2 px-3">
        <label htmlFor="finalDate">Data Final</label>
        <input type="date" className="form-control" id="finalDate" {...register('finalDate')} />
        {errors.finalDate && <span className='error-container'>{errors.finalDate.message}</span>}
      </div>
      <div className="form-group col-md-2 px-3">
        <label htmlFor="firstPaymentDate">Primeiro Pagamento</label>
        <input type="date" className="form-control" id="firstPaymentDate" {...register('firstPaymentDate')}/>
        {errors.firstPaymentDate && <span className='error-container'>{errors.firstPaymentDate.message}</span>}
      </div>
      <div className="form-group col-md-2 px-3">
        <label htmlFor="loanAmount">Valor do Empréstimo</label>
        <CurrencyInput
          className="form-control"
          id="loanAmount"
          placeholder="Digite um valor"
          decimalsLimit={2}
          prefix="R$ "
          decimalSeparator=","
          groupSeparator="."
          value={getValues('loanAmount')}
          onValueChange={handleLoanAmountChange}
        />
        {errors.loanAmount && <span className='error-container'>Este campo é obrigatório</span>}
      </div>
      <div className="form-group col-md-2 px-3">
        <label htmlFor="interestRate">Taxa de Juros</label>
        <NumericFormat
          className="form-control"
          id="interestRate"
          thousandSeparator={false}
          suffix=" %"
          decimalScale={1}
          fixedDecimalScale={true}
          placeholder="Digite um valor"
          decimalSeparator={','}
          value={getValues('interestRate')}
          onValueChange={handleInterestRateChange}
        />
        {errors.interestRate && <span className='error-container'>Este campo é obrigatório</span>}
      </div>
      <div className="form-group col-md-2 px-3 button-container">
        <button type="submit" className="btn btn-primary form-control">Calcular</button>
      </div>
      <Toaster position="top-right" reverseOrder={false} toastOptions={{duration: 2000}}/>
    </FORM>
    
  )
}

const FORM = styled.form`
  display: flex;
  flex-direction: row;
  margin: 0 30px 30px 30px;
`;

export default LoanInputInfo