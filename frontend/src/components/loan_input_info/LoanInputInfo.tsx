import React from 'react'
import styled from 'styled-components';
import { toast, Toaster} from 'react-hot-toast';
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

  var shouldNotProceedSubmit: Boolean = false;

  const validateFields = (data: LoanRequest) => {
    
    const requiredFields = ['initialDate', 'finalDate', 'firstPaymentDate','loanAmount', 'interestRate'];
    let hasError :Boolean = false;
    requiredFields.forEach(field => {
      if (!data[field]) {
        shouldNotProceedSubmit = true;
        hasError = true;
      }
    });

    if (hasError) {
      toast.error('Todos os campos são obrigatórios!')
    }

    if (new Date(data.finalDate) <= new Date(data.initialDate)) {
      toast.error('Data final deve ser após a data inicial');
      hasError = true;
      shouldNotProceedSubmit = true;
    }

    if (new Date(data.initialDate) >= new Date(data.firstPaymentDate) || new Date(data.finalDate) <= new Date(data.firstPaymentDate)) {
      toast.error('Primeiro pagamento deve ser após a data inicial e antes da data final');
      hasError = true;
      shouldNotProceedSubmit = true;
    }
   
    if (!hasError) {
      shouldNotProceedSubmit = false;
    }
  };

  const onSubmit = async (data: LoanRequest) => {
    console.log("executei")
    try {
      validateFields(data);
      
      if (shouldNotProceedSubmit) {
        setSummary([])
        return;
      }
      const result: LoanFinancialRecord[] = await loanService.getFinancialSummary(data);
      setSummary(result);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <FORM onSubmit={handleSubmit(onSubmit)} className="row">
      <div className="form-group col-xl-2 col-6 px-3">
        <label htmlFor="initialDate">Data Inicial</label>
        <input type="date" className='form-control' id="initialDate" {...register('initialDate')} />
      </div>
      <div className="form-group col-xl-2 col-6 px-3">
        <label htmlFor="finalDate">Data Final</label>
        <input type="date" className="form-control" id="finalDate" {...register('finalDate')} />
      </div>
      <div className="form-group col-xl-2 col-6 px-3">
        <label htmlFor="firstPaymentDate">Primeiro Pagamento</label>
        <input type="date" className="form-control" id="firstPaymentDate" {...register('firstPaymentDate')}/>
      </div>
      <div className="form-group col-xl-2 col-6 px-3">
        <label htmlFor="loanAmount">Valor do Empréstimo</label>
        <NumericFormat
          className="form-control"
          id="loanAmount"
          prefix="R$ "
          decimalScale={2}
          fixedDecimalScale={true}
          placeholder="Digite um valor"
          thousandSeparator="."
          decimalSeparator={','}
          value={getValues('loanAmount')}
          onValueChange={(values) => setValue('loanAmount', values.value ? parseFloat(values.value) : 0)}
        />
      </div>
      <div className="form-group col-xl-2 col-6 px-3">
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
          onValueChange={(values) => setValue('interestRate', values.value ? parseFloat(values.value) : 0)}
        />
      </div>
      <div className="col-xl-2 col-6 px-3 button-container">
        <button type="submit" className="btn btn-primary col-12">Calcular</button>
      </div>
      <Toaster
        position="bottom-right"
        reverseOrder={false}
        toastOptions={{
          duration: 4000, 
          style: {
            color: 'black',
            border: '2px solid black',
          },
        }} />
    </FORM>
    
  )
}

const FORM = styled.form`
  display: flex;
  flex-direction: row;
  margin: 0 30px 30px 30px;
`;

export default LoanInputInfo