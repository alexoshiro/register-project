import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../../services/api';
import './dashboard.css';

import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import CircularProgress from '@material-ui/core/CircularProgress';

const columns = [
  { id: 'name', label: 'Nome', minWidth: 170 },
  { id: 'gender', label: 'Sexo', minWidth: 100, format: value => convertGenderToReadable(value) },
  { id: 'email', label: 'E-mail', minWidth: 170 },
  { id: 'birth_date', label: 'Data de nascimento', minWidth: 170, format: value => value.split("-").reverse().join("/") },
  { id: 'nationality', label: 'Naturalidade', minWidth: 170 },
  { id: 'citizenship', label: 'Nacionalidade', minWidth: 170 },
  { id: 'cpf', label: 'Cpf', minWidth: 170 }
];

function convertGenderToReadable(value) {
  switch (value) {
    case 'MALE':
      return "masculino";
    case 'FEMALE':
      return "feminino";
    default:
      return "outros";
  }
}

export default function Dashboard({ history }) {
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(50);
  const [rows, setRows] = useState([]);
  const [totalItems, setTotalItems] = useState(0);
  useEffect(() => {

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
    setLoading(true);
    api.get("/people", {
      params: {
        page: page + 1,
        page_items: rowsPerPage
      },
      headers: header
    })
      .then(response => {
        setLoading(false);
        if (response && response.status === 200) {
          setRows(response.data && response.data.payload || []);
          setTotalItems(response.data && response.data.links && response.data.links.total_items || 0);

        }
      })
      .catch(({ response }) => {
        setLoading(false);
        if (response.status === 401) {
          localStorage.removeItem('user_authorization');
          redirectToLogin();
        }
      });

  }, [history, page, rowsPerPage])

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = event => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  return (
    <div className="dashboard-container">
      <div className="register-button-container">
        <Link to="/register">
          <Button variant="contained" color="primary">Cadastrar pessoa</Button>
        </Link>
      </div>

      <TableContainer className="table-container">
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              {columns.map(column => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow hover role="checkbox" tabIndex={-1}>
                <TableCell align="center" colSpan={columns.length}>
                  <CircularProgress disableShrink />
                </TableCell>
              </TableRow>
            ) : rows.map(row => {
              return (
                <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                  {columns.map(column => {
                    const value = row[column.id];
                    return (
                      <TableCell key={column.id} align={column.align}>
                        {column.format && value ? column.format(value) : value}
                      </TableCell>
                    );
                  })}
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[50, 100]}
        component="div"
        count={totalItems}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />
    </div>
  )
}