import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';

import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import CreateRegister from './pages/Register/Create/'
import UpdateRegister from './pages/Register/Update/'

export default function Routes() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact component={Login} />
        <Route path="/dashboard" component={Dashboard} />
        <Route path="/register" component={CreateRegister} />
        <Route path="/edit/:personId" component={UpdateRegister} />
      </Switch>
    </BrowserRouter>
  )
}