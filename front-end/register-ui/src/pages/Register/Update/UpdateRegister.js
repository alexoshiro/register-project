import React, { useState, useEffect } from 'react';

import api from '../../../services/api';

import PersonForm from '../../../components/PersonForm/'

export default function UpdateRegister({ history, match }) {
  const { personId } = match.params;
  const [loading, setLoading] = useState(false);
  const [serverErrors, setServerErrors] = useState([]);
  const [person, setPerson] = useState({});
  useEffect(() => {
    const token = localStorage.getItem('user_authorization');
    const header = {
      'Authorization': `Bearer ${token}`
    }
    if (!token) {
      redirectToLogin();
    }
    function redirectToLogin() {
      history.push("/");
    }
    if (!personId) {
      history.push("/dashboard");
    }
    setLoading(true);
    api.get(`/people/${personId}`, {
      headers: header
    })
      .then(response => {
        setLoading(false);
        if (response && response.status === 200) {
          setPerson((response.data) || {});
        }
      })
      .catch(({ response }) => {
        setLoading(false);
        if (response.status === 401 || response.status === 403) {
          localStorage.removeItem('user_authorization');
          redirectToLogin();
        }
      });

  }, [history, personId])

  const token = localStorage.getItem('user_authorization');

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

  function handleUpdateSubmit(person) {
    const body = createBodyRequest(person);
    setLoading(true);
    api.patch(`/people/${personId}`, body, {
      headers: header
    })
      .then(response => {
        setLoading(false);
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
      person={person}
      loading={loading}
      submit={handleUpdateSubmit}
      cancelButtonAction={redirectToDashboard}
      headerText="Editar"
      submitButtonText="Editar"
    />
  )
}