import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Button } from "bootstrap";
import { useNavigate } from "react-router-dom";
import "./Css_user/productlist.css";

function ProductList() {
  const [products, setProducts] = useState([]);
  const [productId, setProductId] = useState("");
  const [productData, setProductData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/product/getAllProduct"
      ); // Update with your API endpoint
      setProducts(response.data.data);
      console.log(sessionStorage.getItem("id"));
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const handleSearch = async () => {
    try {
      const response = await axios.get(
        `http://localhost:4000/product/searchByName/${productId}`
      );
      console.log(response.data.data[0].name);
      setProductData(response.data.data[0]);
      setProducts(response.data.data);
    } catch (error) {
      console.error("Error fetching product data:", error);
    }
  };
  const goToProductDetails = async (id) => {
    console.log(id);
    navigate(`/topbar/prodDetails/${id}`);
  };
  return (
    <>
      <div className="home">
        <div className="body1">
          <div className="pro-search-bar">
            <input
              type="text"
              placeholder="Enter Product Name"
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
            />
            <button className="pro-searchbtn" onClick={handleSearch}>
              Search
            </button>
          </div>
        </div>
        <div className="row">
          {products.map((item) => (
            <div key={item.id} className="col-md-3">
              <div className="pro-card">
                <div>
                  <img
                    src={`http://localhost:4000/uploads/` + item.image}
                    alt=""
                  />
                </div>
                <div>
                  <div className="pro-card-body">
                    <h5 className="pro-card-title">{item.name}</h5>
                    <div className="pro-card-text">
                      <div>₹ {item.price}</div>
                    </div>
                  </div>
                  <div>
                    <button
                      className="pro-btn"
                      onClick={() => goToProductDetails(item.id)}>
                      View Details
                    </button>
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

export default ProductList;
