import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useFormik } from "formik";
import * as Yup from 'yup';
import api from '../../services/api';
import './register.css';

import CircularProgress from '@material-ui/core/CircularProgress';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import { KeyboardDatePicker } from '@material-ui/pickers';
import MaskedInput from 'react-text-mask'

const validationSchema = Yup.object({
  name: Yup.string().required("Nome da pessoa é obrigatório."),
  email: Yup.string().email("Formato do e-mail é inválido."),
  cpf: Yup.string().required("Cpf é obrigatório.").matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, 'Formato do cpf é inválido')
});

export default function Register({ history }) {
  const [loading, setLoading] = useState(false);
  const [serverErrors, setServerErrors] = useState([]);
  const [birthDate, setBirthDate] = useState(new Date());

  const { handleSubmit, handleChange, handleBlur, values, errors, touched } = useFormik({
    initialValues: {
      name: '',
      gender: '',
      email: '',
      nationality: '',
      citizenship: '',
      cpf: ''
    },
    validationSchema,
    onSubmit: () => handleRegisterSubmit()
  });

  function handleRegisterSubmit() {
    const token = localStorage.getItem('user_authorization');
    if (!token) {
      redirectToLogin();
    }
    function redirectToLogin() {
      history.push("/");
    }

    const header = {
      'Authorization': `Bearer ${token}`
    }
    const body = {
      name: values.name,
      gender: values.gender ? values.gender : null,
      email: values.email ? values.email : null,
      nationality: values.nationality ? values.nationality : null,
      citizenship: values.citizenship ? values.citizenship : null,
      cpf: values.cpf,
      birth_date: birthDate ? birthDate.toLocaleString().split(" ")[0].split("/").reverse().join("-") : null
    }

    setLoading(true);
    api.post("/people", body, {
      headers: header
    })
      .then(response => {
        setLoading(false);
        if (response && response.status === 200) {
          history.push("/dashboard");
        }
      })
      .catch(({ response }) => {
        setLoading(false);
        setServerErrors(response && response.data && response.data.errors || []);
      });
  }

  const handleDateChange = date => {
    setBirthDate(date);
  };


  return (
    <div className="container">
      <div className="logo">Cadastrar</div>
      <div className="content">
        {serverErrors.map(error => (
          <p className="error-message">
            {error}
          </p>
        )
        )}
        <form onSubmit={handleSubmit}>
          <label htmlFor="name">Nome <span className="required-field">*{errors.name && touched.name ? ` ${errors.name}` : ''}</span></label>
          <input
            type="text"
            id="name"
            name="name"
            placeholder="Nome da pessoa"
            className="custom-input"
            value={values.name}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          <label htmlFor="name">Sexo</label>
          <Select id="gender" name="gender" value={values.gender} onChange={handleChange} displayEmpty className="selectEmpty">
            <MenuItem value="">
              <em>Nenhum</em>
            </MenuItem>
            <MenuItem value={"MALE"}>Masculino</MenuItem>
            <MenuItem value={"FEMALE"}>Feminino</MenuItem>
            <MenuItem value={"OTHER"}>Outros</MenuItem>
          </Select>
          <label htmlFor="email">E-mail <span className="required-field">{errors.email && touched.email ? ` ${errors.email}` : ''}</span></label>
          <input
            type="email"
            id="email"
            name="email"
            placeholder="email@example.com"
            className="custom-input"
            value={values.email}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          {!birthDate && (
            <p className="error-message">
              Data de nascimento é obrigatória.
            </p>
          )}
          <KeyboardDatePicker
            margin="normal"
            id="birthDate"
            label="Data de nascimento*"
            format="dd/MM/yyyy"
            value={birthDate}
            onChange={handleDateChange}
            KeyboardButtonProps={{ 'aria-label': 'change date' }}
            invalidDateMessage="Formato da data inválida"
          />
          <label htmlFor="nationality">Naturalidade</label>
          <input
            type="text"
            id="nationality"
            name="nationality"
            placeholder="Brasil"
            className="custom-input"
            value={values.nationality}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          <label htmlFor="citizenship">Nacionalidade</label>
          <input
            type="text"
            id="citizenship"
            name="citizenship"
            placeholder="brasileiro"
            className="custom-input"
            value={values.citizenship}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          <label htmlFor="cpf">Cpf <span className="required-field">*{errors.cpf && touched.cpf ? ` ${errors.cpf}` : ''}</span></label>
          <MaskedInput
            mask={[/\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '-', /\d/, /\d/]}
            className="custom-input"
            id="cpf"
            name="cpf"
            guide={true}
            onChange={handleChange}
            value={values.cpf}
            onBlur={handleBlur}
          />
          <button className="btn" type="submit" disabled={loading}>{loading ? (<CircularProgress disableShrink color="secondary" />) : 'Cadastrar'}</button>

        </form>
        <Link to="/dashboard"><button className="cancel-button" disabled={loading}>Cancelar</button></Link>
      </div>
    </div>
  )
}