import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../../services/api';
import './dashboard.css';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import CircularProgress from '@material-ui/core/CircularProgress';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';

const columns = [
  { id: 'name', label: 'Nome', minWidth: 170 },
  { id: 'gender', label: 'Sexo', minWidth: 100, format: value => convertGenderToReadable(value) },
  { id: 'email', label: 'E-mail', minWidth: 170 },
  { id: 'birth_date', label: 'Data de nascimento', minWidth: 170, format: value => value.split("-").reverse().join("/") },
  { id: 'nationality', label: 'Naturalidade', minWidth: 150 },
  { id: 'citizenship', label: 'Nacionalidade', minWidth: 150 },
  { id: 'cpf', label: 'Cpf', minWidth: 170 },
  { id: 'actions', label: 'Ações', minWidth: 100, align: "center" }
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
  const [rowsRemoved, setRowsRemoved] = useState(0);
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
          setRows((response.data && response.data.payload) || []);
          setTotalItems((response.data && response.data.links && response.data.links.total_items) || 0);
        }
      })
      .catch(({ response }) => {
        setLoading(false);
        if (response.status === 401 || response.status === 403) {
          localStorage.removeItem('user_authorization');
          redirectToLogin();
        }
      });

  }, [history, page, rowsPerPage, rowsRemoved])

  function handleChangePage(event, newPage) {
    setPage(newPage);
  };

  function handleChangeRowsPerPage(event) {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  function handleDeleteRow(id) {
    const token = localStorage.getItem('user_authorization');
    const header = {
      'Authorization': `Bearer ${token}`
    }
    setLoading(true);
    api.delete(`/people/${id}`, {
      headers: header
    })
      .then(response => {
        if (response && response.status === 204) {
          setPage(0);
          setRowsRemoved(rowsRemoved + 1);
        } else {
          setLoading(false);
        }
      })
      .catch(({ response }) => {
        setLoading(false);
        alert(response.data);
      });
  }

  return (
      <div className="dashboard-container">
        <div className="register-button-container">
          <Link to="/register">
            <button className="custom-button">Cadastrar nova pessoa</button>
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
                      if (column.id === "actions") {
                        return (
                          <TableCell key={column.id} align={column.align}>
                            <EditIcon onClick={() => history.push(`/edit/${row.id}`)} />
                            <DeleteIcon className="table-icon-spacing" onClick={() => handleDeleteRow(row.id)} />
                          </TableCell>
                        )
                      } else {
                        const value = row[column.id];
                        return (
                          <TableCell key={column.id} align={column.align}>
                            {column.format && value ? column.format(value) : value}
                          </TableCell>
                        );
                      }

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