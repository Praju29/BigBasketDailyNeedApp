import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "../Admin/CSS/products.css";

function Products() {
  const [products, setProducts] = useState([]);
  const [productIdd, setProductIdd] = useState("");

  const [productId, setProductId] = useState(""); //productName
  const [productData, setProductData] = useState(null);

  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState("");
  const [image, setImage] = useState(null);

  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedSubCategory, setSelectedSubCategory] = useState("");
  const [categories, setCategories] = useState([]);
  const [subcategories, setSubCategories] = useState([]);

  useEffect(() => {
    fetchProducts();
    fetchCategories();
    fetchSubCategories();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/product/getAllProduct"
      ); // Update with your API endpoint
      setProducts(response.data.data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const Delete = function (id) {
    axios
      .delete("http://localhost:4000/product/deleteProduct/" + id)
      .then((response) => {
        console.log(response);
        fetchProducts();
        //setCategories(response.data.data);
      })
      .catch((error) => {
        console.log("Error fetching data:", error);
      });
  };

  const handleUpdateProduct = async () => {
    const requestData = {
      name,
      price,
      description,
      id: productIdd,
    };

    try {
      const response = await axios.put(
        "http://localhost:4000/product/updateProduct",
        requestData
      ); // Update with your API endpoint
      console.log("Update response:", response.data.data);
    } catch (error) {
      console.error("Error updating product:", error);
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
  const fetchCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/category/getAllCategories"
      ); // Update with your API endpoint
      setCategories(response.data.data);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const fetchSubCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/subCategory/getAllSubCategories"
      ); // Update with your API endpoint
      setSubCategories(response.data.data);
      console.log(response);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const handleCategoryChange = (event) => {
    //category_id = event.target.value;
    setSelectedCategory(event.target.value);
    console.log(event.target.value);
  };
  const handleSubCategoryChange = (event) => {
    // subcategory_id = event.target.value;
    setSelectedSubCategory(event.target.value);
    console.log(event.target.value);
  };
  const handleAddProduct = async () => {
    const formData = new FormData();
    formData.append("name", name);
    formData.append("price", price);
    formData.append("description", description);
    formData.append("category_id", selectedCategory);
    formData.append("subcategory_id", selectedSubCategory);
    formData.append("image", image);

    try {
      await axios.post("http://localhost:4000/product/addProducts", formData); // Update with your API endpoint
      fetchProducts();
      // Clear form inputs
      setName("");
      setPrice("");
      setDescription("");
      setSelectedCategory("");
      setSelectedSubCategory("");
      setImage(null);
    } catch (error) {
      console.error("Error adding product:", error);
    }
    closePopup();
  };

  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const openPopup = () => {
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };
  return (
    <>
      <h1 className="page">Products</h1>
      <div className="container2">
        <div className="divsearch">
          <div className="search-bar">
            <input
              type="text"
              placeholder="Enter Product Name"
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
            />
            <button className="searchbtn" onClick={handleSearch}>
              Search
            </button>
          </div>
        </div>

        <div>
          <button className="addp" onClick={openPopup}>
            Add Product
          </button>
        </div>
        {isPopupOpen && (
          <div className="popup-overlay1">
            <div className="popup-content">
              <div>
                <h3>Add Product</h3>
                <select
                  value={selectedCategory}
                  onChange={handleCategoryChange}>
                  <option value="">Select a category</option>
                  {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
                <select
                  value={selectedSubCategory}
                  onChange={handleSubCategoryChange}>
                  <option value="">Select a category</option>
                  {subcategories.map((subcategory) => (
                    <option key={subcategory.id} value={subcategory.id}>
                      {subcategory.name}
                    </option>
                  ))}
                </select>
                <input
                  type="text"
                  placeholder="Name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                />
                <input
                  type="text"
                  placeholder="Price"
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                />
                <input
                  type="text"
                  placeholder="Description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />
                <input
                  type="file"
                  onChange={(e) => setImage(e.target.files[0])}
                />
              </div>
              <div class="addProdCont">
                {
                  <button className="addbtn1" onClick={handleAddProduct}>
                    Add Product
                  </button>
                }
                {
                  <button className="closebtn1" onClick={closePopup}>
                    Close
                  </button>
                }
              </div>
            </div>
          </div>
        )}
      </div>
      <div className="prductList">
        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>Id</th>
              <th>Name</th>
              <th>Price</th>
              <th>Description</th>
              <th>Image</th>

              {/* Add more table headers */}
            </tr>
          </thead>
          <tbody>
            {products.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.name}</td>
                <td>₹ {item.price}</td>
                <td>{item.description}</td>
                <td>
                  {" "}
                  <img
                    // src={`/uploads/${img.img.path}`}
                    src={`http://localhost:4000/uploads/` + item.image}
                    alt={item.image}
                    width="200"
                    height="200"></img>
                </td>

                <td>
                  {" "}
                  <button
                    className="delete"
                    style={{ backgroundColor: "#dc3545" }}
                    onClick={() => {
                      console.log(item.id);
                      Delete(item.id);
                    }}>
                    Delete
                  </button>
                </td>
                <td>
                  {" "}
                  <Link to={`/update/${item.id}`} onClick={handleUpdateProduct}>
                    Update Product
                  </Link>
                </td>

                {/* Add more table data cells */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default Products;
