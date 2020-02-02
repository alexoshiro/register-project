import React from 'react';

import * as Yup from 'yup';
import { useFormik } from "formik";

import CircularProgress from '@material-ui/core/CircularProgress';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import { KeyboardDatePicker } from '@material-ui/pickers';
import MaskedInput from 'react-text-mask'

import './PersonForm.css';

const validationSchema = Yup.object({
  name: Yup.string().required("Nome da pessoa é obrigatório."),
  email: Yup.string().email("Formato do e-mail é inválido."),
  cpf: Yup.string().required("Cpf é obrigatório.").matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, 'Formato do cpf é inválido'),
  birthDate: Yup.string().required("Data de nascimento é obrigatória.")
});

export default function PersonForm({ person = {}, submit, serverErrors, loading, cancelButtonAction, headerText = "Cadastrar", submitButtonText = "Cadastrar" }) {
  const { handleSubmit, handleChange, handleBlur, values, errors, touched, setFieldValue } = useFormik({
    initialValues: {
      name: person.name || '',
      gender: person.gender || '',
      email: person.email || '',
      nationality: person.nationality || '',
      citizenship: person.citizenship || '',
      cpf: person.cpf || '',
      birthDate: person.birth_date || ''
    },
    enableReinitialize: true,
    validationSchema,
    onSubmit: (values) => submit(values)
  });
  console.log(values);
  const handleDateChange = date => {
    const birthDate = date ? date.toLocaleString().split(" ")[0].split("/").reverse().join("-") : '';
    setFieldValue("birthDate", birthDate);
  };

  return (
    <div className="container">
      <div className="logo">{headerText}</div>
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
          {errors.birthDate && touched.birthDate && (
            <p className="error-message">
              {errors.birthDate}
            </p>
          )}
          <KeyboardDatePicker
            margin="normal"
            id="birthDate"
            label="Data de nascimento*"
            format="dd/MM/yyyy"
            value={values.birthDate && values.birthDate !== "" ? new Date(values.birthDate.split("-")[0], Number(values.birthDate.split("-")[1]) - 1, values.birthDate.split("-")[2]) : null}
            onChange={handleDateChange}
            onBlur={handleBlur}
            KeyboardButtonProps={{ 'aria-label': 'change date' }}
            invalidDateMessage="Formato da data inválida"
            maxDate={new Date()}
            maxDateMessage="A data de nascimento não pode ser maior a data atual"
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
          <button className="custom-button" type="submit" disabled={loading}>{loading ? (<CircularProgress disableShrink color="secondary" />) : submitButtonText}</button>
        </form>
        <button className="cancel-button" disabled={loading} onClick={cancelButtonAction}>Cancelar</button>
      </div>
    </div>
  )
}
