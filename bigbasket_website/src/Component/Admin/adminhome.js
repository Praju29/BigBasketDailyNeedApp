import React, { useState, useEffect } from "react";
import axios from "axios";
import "../../../node_modules/bootstrap/dist/css/bootstrap.css";
import "../Admin/CSS/adminhome.css"; // Import your CSS file

function AdminHome() {
  const [orders, setOrders] = useState([]);
  const [userData, setUserData] = useState(null);
  const [productData, setProductData] = useState(null);
  const [orderData, setOrderData] = useState(null);

  useEffect(() => {
    getFewOrdersDetails();

    axios
      .get("http://localhost:4000/total/gettotalcountuser")
      .then((response) => {
        setUserData(response.data.data[0].uid);
      })
      .catch((error) => {
        console.error("Error fetching user data:", error);
      });

    axios
      .get("http://localhost:4000/total/gettotalcountproduct")
      .then((response) => {
        setProductData(response.data.data[0].pid);
      })
      .catch((error) => {
        console.error("Error fetching product data:", error);
      });

    axios
      .get("http://localhost:4000/total/gettotalcountorder")
      .then((response) => {
        setOrderData(response.data.data[0].oid);
      })
      .catch((error) => {
        console.error("Error fetching order data:", error);
      });
  }, []);

  const getFewOrdersDetails = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/order/getFewOrdersDetails"
      ); // Update the endpoint based on your server setup
      const ordersData = response.data.data;
      setOrders(ordersData);
    } catch (error) {
      console.error("Error fetching orders:", error);
    }
  };

  return (
    <>
      <div className="container1">
        <div className="imgdiv"></div>
        <div className="boxdiv">
          <div className="div1">
            <h4>User Count </h4>
            <h1> {userData}</h1>
          </div>
          <div className="subboxdiv">
            <div className="div2">
              <h4>Product Count</h4>
              <h1> {productData}</h1>
            </div>
            <div className="div3">
              <h4>Order Count</h4>
              <h1>{orderData}</h1>
            </div>
          </div>
        </div>
      </div>

      <div className="tbl-orders">
        <h2>All Orders</h2>
        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>Product Id</th>
              <th>Name</th>
              <th>Price</th>
              <th>Delivery Status</th>
              <th>Order Date</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((item) => {
              // Convert order_date to a JavaScript Date object
              const orderDate = new Date(item.order_date);

              // Extract day, month, and year components
              const day = orderDate.getDate().toString().padStart(2, "0");
              const month = (orderDate.getMonth() + 1)
                .toString()
                .padStart(2, "0");
              const year = orderDate.getFullYear();

              // Format the date as dd/mm/yyyy
              const formattedDate = `${day}/${month}/${year}`;

              return (
                <tr key={item.product_id}>
                  <td>{item.product_id}</td>
                  <td>{item.name}</td>
                  <td>₹ {item.price}</td>
                  <td>{item.delivery_status}</td>
                  <td>{formattedDate}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default AdminHome;
