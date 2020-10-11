import React, { useState, useEffect } from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import { Api } from "../api/Index";
import { useHistory } from "react-router-dom";
import HomeNavBar from "./HomeNav";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright © "}
      AGME
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%",
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

function SendLoginData({ setResult, username, password }) {
  const [data] = useState(() => {
    const formData = new URLSearchParams();
    formData.append("username", username);
    formData.append("password", password);
    return formData;
  });

  const { response, error, loading } = Api.login.post(data)();
  useEffect(() => {
    if (response) {
      console.log(response);
      setResult(response);
    } else if (error) {
      console.log(error);
      alert("error check console");
      setResult(null);
    }
  }, [response, error, setResult]);
  return <>{loading && <p> Loading...Please wait...</p>}</>;
}

export default function SignIn() {
  const classes = useStyles();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [submit, setSubmit] = useState(false);
  const [loginResult, setLoginResult] = useState("");

  const history = useHistory();
  useEffect(() => {
    if (!loginResult) {
      setSubmit(false);
    } else if (loginResult) {
      if (loginResult.message === "admin") {
        history.push("/dasboardadmin");
      } else if (loginResult.message === "customer") {
        history.push("/home");
      } else if (loginResult.message === "employee") {
        history.push("/empbookings");
      }
    }
  }, [loginResult, setSubmit, history]);

  return (
    <>
      <header>
        <HomeNavBar />
      </header>
      <Container component="main" maxWidth="xs">
        {/* <div>{JSON.stringify(loginResult)}</div> */}
        <CssBaseline />
        <div className={classes.paper}>
          <Avatar className={classes.avatar}></Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <form className={classes.form} noValidate>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="email"
              label="Username"
              name="email"
              autoComplete="username"
              autoFocus
              onChange={(e) => setUsername(e.target.value)}
              value={username}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            {submit ? (
              <SendLoginData
                setResult={setLoginResult}
                username={username}
                password={password}
              />
            ) : (
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={() => setSubmit(true)}
              >
                Sign In
              </Button>
            )}
            <Grid container>
              <Grid item xs>
                <Link href="forgetpassword" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link href="/Signup" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </form>
        </div>
        <Box mt={8}>
          <Copyright />
        </Box>
      </Container>
    </>
  );
}
