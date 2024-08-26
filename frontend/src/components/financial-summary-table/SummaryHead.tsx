import React from 'react'
import styled from 'styled-components';

const SummaryHead = () => {
    return (
        <thead>
            <tr>
                <th className='thead' colSpan={3}>Empréstimo</th>
                <th className='thead' colSpan={2}>Parcela</th>
                <th className='thead' colSpan={2}>Principal</th>
                <th className='thead' colSpan={3}>Juros</th>
            </tr>
            <tr >
                <th className='thead' >Data Competência</th>
                <th className='thead' >Valor de Empréstimo</th>
                <th className='thead' >Saldo Devedor</th>
                <th className='thead' >Consolidada</th>
                <th className='thead' >Total</th>
                <th className='thead' >Amortização</th>
                <th className='thead' >Saldo</th>
                <th className='thead' >Provisão</th>
                <th className='thead' >Acumulado</th>
                <th className='thead' >Pago</th>
            </tr>
        </thead>
    )
}


export default SummaryHead