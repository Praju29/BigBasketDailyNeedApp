import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../Component/login.css";

function LoginPage() {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();

    axios
      .post("http://localhost:4000/user/loginUser", user)
      .then((res) => {
        if (res.data.status === "success") {
          if (res.data.data[0].role === "user") {
            const userId = res.data.data[0].id;
            sessionStorage["name"] = res.data.data[0].name;
            sessionStorage["id"] = userId;

            navigate("/topbar/home");
          } else if (res.data.data[0].role === "admin") {
            navigate("/sidebaradmin/adminhome");
          } else {
            alert("No such record");
          }
        } else {
          alert("No such record");
        }
      })
      .catch((err) => console.log(err));
  };

  const handleTextChanged = (e) => {
    const { name, value } = e.target;
    setUser((prevUser) => ({ ...prevUser, [name]: value }));
  };

  return (
    <div className="container-login">
      <div className="login-box">
        <div className="table-login">
          <center>
            <form onSubmit={handleSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td colSpan={2}>
                      <h2 className="text-center">Welcome</h2>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <label htmlFor="email" className="txt">
                        Email
                      </label>
                    </td>
                    <td>
                      <input
                        value={user.email}
                        onChange={handleTextChanged}
                        type="email"
                        placeholder="youremail@gmail.com"
                        id="email"
                        name="email"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <label htmlFor="password" className="txt">
                        Password
                      </label>
                    </td>
                    <td>
                      <input
                        value={user.password}
                        onChange={handleTextChanged}
                        type="password"
                        placeholder="********"
                        id="password"
                        name="password"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td colSpan={2}>
                      <button type="submit" className="login">
                        Log In
                      </button>
                    </td>
                  </tr>
                  <tr>
                    <td colSpan={2}>
                      <Link to="/register">
                        <h6 className="text-center">
                          Don't have an account? Register here.
                        </h6>
                      </Link>
                    </td>
                  </tr>
                </tbody>
              </table>
            </form>
          </center>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
