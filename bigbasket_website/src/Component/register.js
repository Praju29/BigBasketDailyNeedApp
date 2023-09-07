import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../Component/register.css";

function Register(props) {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    email: "",
    password: "",
    name: "",
    mobile: "",
    address_line1: "",
    city: "",
    state: "",
    postal_code: "",
    country: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post("http://localhost:4000/user/register", user)
      .then((res) => {
        console.log(res);
        navigate("/");
      })
      .catch((err) => console.log(err));
  };

  const TextChanged = (args) => {
    const copyOfUser = { ...user };
    copyOfUser[args.target.name] = args.target.value;
    setUser(copyOfUser);
  };

  return (
    <div className="register-container">
      <div className="register-form">
        <form onSubmit={handleSubmit}>
          <table>
            <tbody>
              <tr>
                <td colSpan={2}>
                  {" "}
                  <h2 className="text-center">Create Account</h2>
                </td>
              </tr>
              <tr>
                <td>
                  {" "}
                  <label htmlFor="name">Full Name :</label>
                </td>
                <td>
                  <input
                    value={user.name}
                    name="name"
                    onChange={TextChanged}
                    id="name"
                    placeholder="full Name"
                    type="text"
                  />
                </td>
              </tr>
              <tr>
                <td>
                  <label htmlFor="email">Email :</label>
                </td>
                <td>
                  <input
                    value={user.email}
                    onChange={TextChanged}
                    type="email"
                    placeholder="youremail@gmail.com"
                    id="email"
                    name="email"
                  />
                </td>
              </tr>
              <tr>
                <td>
                  <label htmlFor="mobile">Mobile No. :</label>
                </td>
                <td>
                  <input
                    value={user.mobile}
                    onChange={TextChanged}
                    type="number"
                    placeholder="yourmobilenumber"
                    id="mobile"
                    name="mobile"
                  />
                </td>
              </tr>

              <tr>
                <td>
                  <label htmlFor="password">Password :</label>
                </td>
                <td>
                  {" "}
                  <input
                    value={user.password}
                    onChange={TextChanged}
                    type="password"
                    placeholder="********"
                    id="password"
                    name="password"
                  />
                </td>
              </tr>
              <tr>
                <td>
                  {" "}
                  <label htmlFor="address_line1">Address :</label>
                </td>
                <td>
                  <input
                    value={user.address_line1}
                    name="address_line1"
                    onChange={TextChanged}
                    id="address_line1"
                    placeholder="address_line1"
                    type="text"
                  />
                </td>
              </tr>

              <tr>
                <td>
                  {" "}
                  <label htmlFor="city">City :</label>
                </td>
                <td>
                  <input
                    value={user.city}
                    name="city"
                    onChange={TextChanged}
                    id="city"
                    placeholder="city"
                    type="text"
                  />
                </td>
              </tr>

              <tr>
                <td>
                  {" "}
                  <label htmlFor="state">State :</label>
                </td>
                <td>
                  <input
                    value={user.state}
                    name="state"
                    onChange={TextChanged}
                    id="state"
                    placeholder="state"
                    type="text"
                  />
                </td>
              </tr>

              <tr>
                <td>
                  {" "}
                  <label htmlFor="postal_code">Postal Code :</label>
                </td>
                <td>
                  {" "}
                  <input
                    value={user.postal_code}
                    name="postal_code"
                    onChange={TextChanged}
                    id="postal_code"
                    placeholder="postal_code"
                    type="text"
                  />
                </td>
              </tr>

              <tr>
                <td>
                  {" "}
                  <label htmlFor="country">Country :</label>
                </td>
                <td>
                  <input
                    value={user.country}
                    name="country"
                    onChange={TextChanged}
                    id="country"
                    placeholder="country"
                    type="text"
                  />
                </td>
              </tr>

              <tr>
                <td colSpan={2}>
                  <button
                    type="submit"
                    className="btnRegister"

                    // onClick={() => {
                    //   Insert();
                    // }}
                  >
                    Register
                  </button>
                </td>
              </tr>

              <tr>
                <td colSpan={2}>
                  <Link
                    to="/"
                    className="link-btn"
                    // onClick={() => props.onFormSwitch("login")}
                  >
                    <h6
                      className="text-center"
                      style={{
                        marginTop: 10,
                      }}>
                      {" "}
                      Already have an account? Login here.
                    </h6>
                  </Link>
                </td>
              </tr>
            </tbody>
          </table>
        </form>
      </div>
    </div>
  );
}

export default Register;
