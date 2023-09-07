import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { useNavigate, useParams } from "react-router-dom";
import "../Admin/CSS/orders.css";

function Orders() {
  const [orders, setOrders] = useState([]);
  const [status, setStatus] = useState([]);
  const navigate = useNavigate();
  const { id } = useParams();
  const [order, setOrder] = useState([]);
  const [selectedOrderId, setSelectedOrderId] = useState(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const ordersResponse = await axios.get(
        "http://localhost:4000/order/getOrdersDetails"
      );
      const ordersData = ordersResponse.data.data;
      setOrders(ordersData);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const UpdateDeliveryStatus = async (orderId) => {
    try {
      const response = await axios.put(
        `http://localhost:4000/order/updateDeliveryStatus/${orderId}`
      );
      const ordersStatus = response.data.data;
      setStatus(ordersStatus);
      console.log(response);
      fetchData();
    } catch (error) {
      console.error("Error updating delivery status:", error);
    }
  };

  return (
    <div className="container3">
      <h1 className="page">All Orders</h1>
      <table className="table table-bordered table-striped">
        <thead>
          <tr>
            <th>Product Id</th>
            <th>Name</th>
            <th>Email</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total Price</th>
            <th>Delivery Status</th>
            <th>Order Date</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((item) => {
            const orderDate = new Date(item.order_date);
            const day = orderDate.getDate().toString().padStart(2, "0");
            const month = (orderDate.getMonth() + 1)
              .toString()
              .padStart(2, "0");
            const year = orderDate.getFullYear();
            const formattedDate = `${day}/${month}/${year}`;

            return (
              <tr key={item.product_id}>
                <td>{item.product_id}</td>
                <td>{item.name}</td>
                <td>{item.email}</td>
                <td>₹ {item.price}</td>
                <td>{item.quantity}</td>
                <td>₹ {item.price * item.quantity}</td>
                <td>{item.delivery_status}</td>
                <td>{formattedDate}</td>
                <td>
                  <button
                    className="mark"
                    onClick={() => UpdateDeliveryStatus(item.order_id)}>
                    Mark As Delivered
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

export default Orders;
