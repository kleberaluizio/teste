import React from 'react'
import styled from 'styled-components';

const Header = styled.header`
    font-size: 40px;
    font-family: Arial;
    color: black;
    margin: 30px 0 20px 40px;

    @media (max-width: 576px) {
    font-size: 30px;
    }
`;

const Title = () => {
  return (
    <Header>Calculadora de Empréstimos</Header>
  )
}

export default Title