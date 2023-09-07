import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../User/Css_user/home.css";

const Home = ({ images }) => {
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
  const [products, setProducts] = useState([]);
  const [productId, setProductId] = useState("");
  const [productData, setProductData] = useState(null);
  const navigate = useNavigate();

  const nextImage = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
  };

  const prevImage = () => {
    setCurrentImageIndex((prevIndex) =>
      prevIndex === 0 ? images.length - 1 : prevIndex - 1
    );
  };
  // Auto-scroll functionality
  useEffect(() => {
    fetchProducts();

    const interval = setInterval(nextImage, 3000); // Change interval as needed (in milliseconds)

    return () => {
      clearInterval(interval);
    };
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
        <div className="image-slider">
          {/* <button onClick={prevImage}>Previous</button> */}
          <img
            src={images[currentImageIndex]}
            alt={`Image ${currentImageIndex + 1}`}
            style={{ width: "100%", height: 250 }}
          />
          {/* <button onClick={nextImage}>Next</button> */}
        </div>
        <div className="divsearch1">
          <div className="search-bar1">
            <input
              type="text"
              placeholder="Enter Product Name"
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
            />
            <button className="searchbtn1" onClick={handleSearch}>
              Search
            </button>
          </div>
        </div>
        <div className="row">
          {products.map((item) => (
            <div key={item.id} className="col-md-3">
              <div className="card">
                <div>
                  <img
                    src={`http://localhost:4000/uploads/` + item.image}
                    alt=""
                  />
                </div>
                <div>
                  <div className="card-body">
                    <h5 className="card-title">{item.name}</h5>
                    <div className="card-text">
                      <div>₹ {item.price}</div>
                    </div>
                  </div>
                  <div>
                    <button
                      className="viewbtn"
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
};

export default Home;
