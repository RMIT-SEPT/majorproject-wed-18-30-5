import React from 'react';
import logo from './logo.svg';
import './App.css';
import Login from "./components/Login";
import Signup from "./components/Signup";
import { Router, Redirect } from "@reach/router"


const NotFound = (props) => <h2>404</h2>

function App() {
  return (


    <Router>
      <Signup path="signup" />
      <Login path="login" />

      <NotFound path="**" />

    </Router>


  );
}

export default App;
