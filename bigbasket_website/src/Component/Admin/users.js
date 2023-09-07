import React, { useState, useEffect } from "react";
import axios from "axios";

function Users() {
  var [data, setData] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = function () {
    axios
      .get("http://localhost:4000/user/")
      .then((response) => {
        console.log(response);
        setData(response.data.data);
      })
      .catch((error) => {
        console.log("Error fetching data:", error);
      });
  };
  return (
    <>
      <div className="tbl-orders">
        <h2>Users</h2>
        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Mobile</th>

              {/* Add more table headers */}
            </tr>
          </thead>
          <tbody>
            {data.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.name}</td>
                <td>{item.email}</td>
                <td>{item.mobile}</td>

                {/* Add more table data cells */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default Users;
