import React, { useState } from 'react';
import { useFormik } from "formik";
import * as Yup from 'yup';
import api from '../../services/api'

import CircularProgress from '@material-ui/core/CircularProgress';

const validationSchema = Yup.object({
  username: Yup.string().required("Nome de usuário é obrigatório."),
  password: Yup.string().required("Senha é obrigatória.")
});

export default function Login({ history }) {
  const [loading, setLoading] = useState(false);
  const [serverErrors, setServerErrors] = useState([]);

  const { handleSubmit, handleChange, handleBlur, values, errors, touched } = useFormik({
    initialValues: {
      username: '',
      password: ''
    },
    validationSchema,
    onSubmit: () => handlerLoginSubmit()
  });

  function handlerLoginSubmit() {
    const header = {
      'Authorization': `Basic ${btoa(`${values.username}:${values.password}`)}`
    }
    setLoading(true);
    api.post("/login", "", {
      headers: header
    })
      .then(response => {
        setLoading(false);
        if (response && response.status === 200) {
          localStorage.setItem("user_authorization", response.headers['x-token']);
          history.push("/dashboard");
        }
      })
      .catch(({ response }) => {
        setLoading(false);
        setServerErrors((response && response.data && response.data.errors) || []);
      });
  }

  return (
    <div className="container">
      <div className="logo">Sistema de cadastro</div>
      <div className="content">
        <p className="login-header"><strong>Login</strong></p>
        {serverErrors.map(error => (
          <p className="error-message">
            {error}
          </p>
        )
        )}
        <form onSubmit={handleSubmit}>
          <label htmlFor="username">Usuário <span className="required-field">*{errors.username && touched.username ? ` ${errors.username}` : ''}</span></label>
          <input
            type="text"
            id="username"
            name="username"
            placeholder="nome de usuário"
            className="custom-input"
            value={values.username}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          <label htmlFor="password">Senha <span className="required-field">*{errors.password && touched.password ? ` ${errors.password}` : ''}</span></label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="senha"
            className="custom-input"
            value={values.password}
            onChange={handleChange}
            onBlur={handleBlur}
          />

          <button className="custom-button" type="submit" disabled={loading}>{loading ? (<CircularProgress disableShrink color="secondary" />) : 'Entrar'}</button>
        </form>
      </div>
    </div>
  )
}