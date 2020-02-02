import React, { useState } from 'react';

import api from '../../../services/api';

import PersonForm from '../../../components/PersonForm/'

export default function CreateRegister({ history }) {
  const [loading, setLoading] = useState(false);
  const [serverErrors, setServerErrors] = useState([]);

  const token = localStorage.getItem('user_authorization');
  if (!token) {
    redirectToLogin();
  }

  function redirectToLogin() {
    history.push("/");
  }

  function redirectToDashboard() {
    history.push("/dashboard");
  }

  const header = {
    'Authorization': `Bearer ${token}`
  }

  function createBodyRequest(person) {
    return {
      name: person.name,
      gender: person.gender ? person.gender : null,
      email: person.email ? person.email : null,
      nationality: person.nationality ? person.nationality : null,
      citizenship: person.citizenship ? person.citizenship : null,
      cpf: person.cpf,
      birth_date: person.birthDate ? person.birthDate : null
    }
  }

  function submitSuccessCheckStatus(response) {
    setLoading(false);
    if (response && response.status === 200) {
      redirectToDashboard();
    }
  }

  function handleRegisterSubmit(person) {
    const body = createBodyRequest(person);

    setLoading(true);
    api.post("/people", body, {
      headers: header
    })
      .then(response => {
        submitSuccessCheckStatus(response);
      })
      .catch(({ response }) => {
        setLoading(false);
        setServerErrors((response && response.data && response.data.errors) || []);
      });
  }

  return (
    <PersonForm
      serverErrors={serverErrors}
      loading={loading}
      submit={handleRegisterSubmit}
      cancelButtonAction={redirectToDashboard}
      headerText={"Cadastrar"}
      submitButtonText={"Cadastrar"}
    />
  )
}