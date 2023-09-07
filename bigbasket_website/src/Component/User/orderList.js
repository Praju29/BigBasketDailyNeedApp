import React, { useState, useEffect } from "react";
import axios from "axios";
import "../User/Css_user/orderlist.css";
import RatingStars from "react-rating-stars-component";

function OrderList() {
  const [orders, setOrders] = useState([]);
  const [rating, setRating] = useState(0);
  const [reviewText, setReviewText] = useState("");
  const [selectedProductId, setSelectedProductId] = useState(null); // Add selectedProductId state
  const [isPopupOpen, setIsPopupOpen] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const ordersResponse = await axios.get(
        `http://localhost:4000/order/getOrdersById/${sessionStorage.getItem(
          "id"
        )}`
      );
      const ordersData = ordersResponse.data.data;
      setOrders(ordersData);
      console.log(ordersResponse);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const cancleOrder = async (oid) => {
    console.log(oid);
    try {
      const response = await axios.delete(
        `http://localhost:4000/order/cancleOrder/${oid}`
      );
      fetchData();
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const openPopup = (productId) => {
    setSelectedProductId(productId);
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  const handleRatingChange = (newRating) => {
    setRating(newRating);
  };

  const handleReviewTextChange = (event) => {
    setReviewText(event.target.value);
  };

  const addReview = async () => {
    const formData = new FormData();

    formData.append("product_id", selectedProductId);
    formData.append("user_id", sessionStorage.getItem("id"));
    formData.append("rating", rating);
    formData.append("review_text", reviewText);

    try {
      await axios.post("http://localhost:4000/product/addReview", formData);

      // Reset selectedProductId and close the popup
      setSelectedProductId(null);
      setIsPopupOpen(false);

      // Refetch the orders to update their review status.
      fetchData();
    } catch (error) {
      console.error("Error adding category:", error);
    }
  };

  return (
    <div className="order-list-container">
      <h1 className="page">My Orders</h1>
      <div className="order-list">
        {orders.map((item) => {
          const orderDate = new Date(item.order_date);
          const day = orderDate.getDate().toString().padStart(2, "0");
          const month = (orderDate.getMonth() + 1).toString().padStart(2, "0");
          const year = orderDate.getFullYear();
          const formattedDate = `${day}/${month}/${year}`;

          return (
            <div key={item.product_id} className="order-card">
              <div className="odiv-img">
                <img
                  src={`http://localhost:4000/uploads/` + item.image}
                  alt=""
                  className="order-image"
                />
              </div>
              <div className="oorder-details">
                <h3 className="oproduct-name">{item.name}</h3>
                <p className="oproduct-price">₹ {item.price}</p>
                <p className="oproduct-quantity">Quantity: {item.quantity}</p>
                <p className="oproduct-total-price">
                  Total: ₹ {item.price * item.quantity}
                </p>
                <p className="odelivery-status">
                  Delivery Status: {item.delivery_status}
                </p>
                <p className="order-date">Order Date: {formattedDate}</p>
              </div>
              <div className="button-container">
                <div>
                  {item.delivery_status === "Delivered" && (
                    <button
                      className="addReview"
                      onClick={() => openPopup(item.product_id)}>
                      Add Review
                    </button>
                  )}
                </div>
                <div>
                  <button className="obtn" onClick={() => cancleOrder(item.id)}>
                    Cancel Order
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {isPopupOpen && (
        <div className="popup-overlay">
          <div className="popup-content">
            <h2>Add Review</h2>
            <div className="rating-container">
              <label>Rating:</label>
              <RatingStars
                count={5}
                value={rating}
                size={24}
                onChange={handleRatingChange}
                activeColor="#007bff"
              />
            </div>
            <div>
              <label>Review:</label>
              <textarea value={reviewText} onChange={handleReviewTextChange} />
            </div>
            <div className="review-container">
              <button onClick={addReview}>Add Review</button>
              <button onClick={closePopup}>Close</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default OrderList;
