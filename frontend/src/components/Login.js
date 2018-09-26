import React, { Component } from 'react';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Snackbar from '@material-ui/core/Snackbar';
import Productlist from './Productlist';
import { SERVER_URL } from '../constants.js';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = { email: '', password: '', isAuthenticated: false, open: false };
    }

    login = () => {
        const user = { email: this.state.email, password: this.state.password };
        fetch(SERVER_URL + 'v1/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            })
            .then((response) => response.json())
            .then((responseData) => {
                console.log("responseData" : responseData);
                const jwtToken = responseData.data.token;
                if (jwtToken !== null) {
                    sessionStorage.setItem("jwt", jwtToken);
                    this.setState({ isAuthenticated: true });
                } else {
                    this.setState({ open: true });
                }
            })
            .catch(err => {
                this.setState({ open: true});
                console.error(err)
            })
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value });
    }

    handleClose = (event) => {
        this.setState({ open: false });
    }

    render() {
    if (this.state.isAuthenticated === true) {
      return (<Productlist />)
    }
    else {
      return (
        <div>
          <br/>
          <TextField tpye="text" name="email" placeholder="Email" onChange={this.handleChange} /><br/>
          <TextField type="password" name="password" placeholder="Password" onChange={this.handleChange} /><br /><br/>
          <Button variant="raised" color="primary" onClick={this.login}>Login</Button>
          <Snackbar open={this.state.open}  onClose={this.handleClose} autoHideDuration={1500} message='Check your email and password' />
        </div>
      );
    }
  }
}

export default Login;
