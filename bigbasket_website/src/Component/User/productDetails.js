import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "./Css_user/prodetails.css";

function ProductDetails() {
  const { pId } = useParams();
  const [products, setProducts] = useState([]);
  const [qty, setQty] = useState({});
  const [selectedPaymentMode, setSelectedPaymentMode] = useState("");
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    fetchProducts(pId);
  }, [pId]);

  const fetchProducts = async (id) => {
    try {
      const response = await axios.get(`http://localhost:4000/product/${id}`); // Update with your API endpoint
      setProducts(response.data.data);
      console.log(sessionStorage.getItem("id"));
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  // Function to handle quantity increment
  const handleIncrement = (productId) => {
    setQty((prevQty) => ({
      ...prevQty,
      [productId]: (prevQty[productId] || 1) + 1,
    }));
  };

  // Function to handle quantity decrement
  const handleDecrement = (productId) => {
    setQty((prevQty) => ({
      ...prevQty,
      [productId]: Math.max((prevQty[productId] || 1) - 1, 1),
    }));
  };

  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const openPopup = () => {
    setIsPopupOpen(true);
    PlaceOrder();
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  const [isReviewPopupOpen, setIsReviewPopupOpen] = useState(false);
  const openReviewPopup = (id) => {
    setIsReviewPopupOpen(true);
    fetchAllReview(id);
  };

  const closeReviewPopup = () => {
    setIsReviewPopupOpen(false);
  };

  const handleModeChange = (event) => {
    //category_id = event.target.value;
    setSelectedPaymentMode(event.target.value);
    console.log(event.target.value);
  };

  const PlaceOrder = async () => {
    // const formData = new FormData();
    // formData.append("user_id", sessionStorage.getItem("id"));
    // formData.append("product_id", pId);
    // formData.append("quantity", qty);

    const orderData = {
      user_id: sessionStorage.getItem("id"),
      product_id: pId,
      quantity: qty[pId] || 1, // Use quantity for the specific product
    };
    try {
      await axios.post("http://localhost:4000/order/placeOrder", orderData); // Update with your API endpoint
      // Clear form inputs
      console.error("Products", orderData);
    } catch (error) {
      console.error("Error adding product:", error);
    }
  };

  const BuyNow = async () => {
    const orderData = {
      user_id: sessionStorage.getItem("id"),
      payment_mode: selectedPaymentMode,
    };
    try {
      await axios.post("http://localhost:4000/order/payment", orderData); // Update with your API endpoint
      // Clear form inputs
      console.error("Products", orderData);
      closePopup();
    } catch (error) {
      console.error("Error adding product:", error);
    }
  };

  const fetchAllReview = async (id) => {
    try {
      const response = await axios.get(
        `http://localhost:4000/product/getAllReview/${id}`
      ); // Update with your API endpoint
      // setProducts(response.data.data);
      setReviews(response.data.data);
      console.log(sessionStorage.getItem("id"));
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  return (
    <>
      <div className="details-home">
        <div className="details-row">
          {products.map((item) => (
            <div key={item.id}>
              <div className="details-card">
                <div className="details-card-body">
                  <div className="details-image-section">
                    <img
                      src={`http://localhost:4000/uploads/` + item.image}
                      alt=""
                    />
                  </div>
                  <div className="detaildiv">
                    <h5 className="details-card-title">{item.name}</h5>
                    <div className="details-card-text">
                      <div>₹ {item.price}</div>
                      <div>{item.description}</div>
                    </div>
                    <div>
                      <button
                        className="details-minus"
                        onClick={() => handleDecrement(item.id)}>
                        -
                      </button>
                      <h3>{qty[item.id] || 1}</h3>
                      <button
                        className="details-plus"
                        onClick={() => handleIncrement(item.id)}>
                        +
                      </button>
                    </div>
                    <div>
                      <div>
                        <p>
                          {" "}
                          Total Price: ₹ {item.price * (qty[item.id] || 1)}
                        </p>
                      </div>
                    </div>
                    <div>
                      <button
                        className="details-placeOrder"
                        onClick={openPopup}>
                        Place Order
                      </button>
                      <button
                        className="viewReview"
                        onClick={() => openReviewPopup(item.id)}>
                        View Review
                      </button>
                    </div>
                    {isPopupOpen && (
                      <div className="popup-overlay">
                        <div className="popup-content">
                          <div>
                            <select
                              value={selectedPaymentMode}
                              onChange={handleModeChange}>
                              <option value="">Select Payment Mode</option>
                              <option value="Cash">Cash On Delivery</option>
                            </select>
                          </div>
                          <div className="buycontainer">
                            {
                              <button
                                className="details-addbtn1"
                                onClick={BuyNow}>
                                Buy Now
                              </button>
                            }
                            {
                              <button
                                className="details-closebtn1"
                                onClick={closePopup}>
                                Close
                              </button>
                            }
                          </div>
                        </div>
                      </div>
                    )}
                    {isReviewPopupOpen && (
                      <div className="popup-overlay">
                        <div className="popup-content">
                          <h2>Product Reviews</h2>
                          <ul className="review-list">
                            {reviews.map((review, index) => (
                              <li key={index}>
                                <p>Rating: {review.rating}</p>
                                <p>{review.review_text}</p>
                              </li>
                            ))}
                          </ul>
                          <button
                            className="reviewClose"
                            onClick={closeReviewPopup}>
                            Close
                          </button>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default ProductDetails;
