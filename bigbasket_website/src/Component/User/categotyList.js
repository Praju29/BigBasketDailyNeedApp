import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Css_user/categoty.css";

function CategoryList() {
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedSubCategory, setSelectedSubCategory] = useState("");
  const [categories, setCategories] = useState([]);
  const [subcategories, setSubCategories] = useState([]);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetchCategories();
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/product/getAllProduct"
      );
      setProducts(response.data.data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/category/getAllCategories"
      );
      setCategories(response.data.data);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const fetchSubCategories = async (categoryId) => {
    try {
      const response = await axios.get(
        `http://localhost:4000/subCategory/getAllSubCategories/${categoryId}`
      );
      setSubCategories(response.data.data);
    } catch (error) {
      console.error("Error fetching subcategories:", error);
    }
  };

  const fetchProductsBySubCategories = async (subcategoryId) => {
    try {
      const response = await axios.get(
        `http://localhost:4000/product/getAllProduct/${subcategoryId}`
      );
      setProducts(response.data.data);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const fetchProductsByCategories = async (categoryId) => {
    try {
      const response = await axios.get(
        `http://localhost:4000/product/getAllProductByCid/${categoryId}`
      );
      setProducts(response.data.data);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const handleCategoryChange = (event) => {
    const categoryId = event.target.value;
    setSelectedCategory(categoryId);
    fetchSubCategories(categoryId);
    setSelectedSubCategory("");
    fetchProductsByCategories(categoryId);
  };

  const handleSubCategoryChange = (event) => {
    const subcategoryId = event.target.value;
    setSelectedSubCategory(event.target.value);
    fetchProductsBySubCategories(subcategoryId);
  };

  return (
    <div className="cat-body">
      <div className="cat-dd">
        <select value={selectedCategory} onChange={handleCategoryChange}>
          <option value="">Select a Category</option>
          {categories.map((category) => (
            <option key={category.id} value={category.id}>
              {category.name}
            </option>
          ))}
        </select>
        {selectedCategory && (
          <select
            value={selectedSubCategory}
            onChange={handleSubCategoryChange}>
            <option value="">Select a Subcategory</option>
            {subcategories.map((subcategory) => (
              <option key={subcategory.id} value={subcategory.id}>
                {subcategory.name}
              </option>
            ))}
          </select>
        )}
      </div>

      <div className="cat-list">
        {products.map((item) => (
          <div key={item.id} className="cat-card">
            <img
              src={`http://localhost:4000/uploads/` + item.image}
              alt={item.name}
              width="200"
              height="200"
            />
            <div className="cat-details">
              <h3>{item.name}</h3>
              <p>₹ {item.price}</p>
              <p>{item.description}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default CategoryList;
