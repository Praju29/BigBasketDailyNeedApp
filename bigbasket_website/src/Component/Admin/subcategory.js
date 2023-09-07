import React, { useState, useEffect } from "react";
import axios from "axios";

function SubCategory() {
  const [categories, setCategories] = useState([]);
  const [subcategories, setSubCategories] = useState([]);
  const [name, setName] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");

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

  const fetchSubCategoriesById = async (id) => {
    try {
      const response = await axios.get(
        "http://localhost:4000/subCategory/getAllSubCategories/" + id
      ); // Update with your API endpoint
      setSubCategories(response.data.data);
      console.log(response);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };
  const handleAddSubCategory = async () => {
    const formData = new FormData();
    formData.append("category_id", selectedCategory);
    formData.append("name", name);
    console.log(selectedCategory);
    try {
      await axios.post(
        "http://localhost:4000/subCategory/addSubCategory",
        formData
      );
      setName("");
      fetchSubCategories();
      closePopup();
    } catch (error) {
      console.error("Error adding category:", error);
    }
  };

  const Delete = function (id) {
    axios
      .delete("http://localhost:4000/subCategory/deleteSubCategory/" + id)
      .then((response) => {
        console.log(response);
        fetchSubCategories();
        //setCategories(response.data.data);
      })
      .catch((error) => {
        console.log("Error fetching data:", error);
      });
  };

  useEffect(() => {
    fetchCategories();
    fetchSubCategories();
  }, []);

  const handleCategoryChange = (event) => {
    setSelectedCategory(event.target.value);
    console.log(event.target.value);
    fetchSubCategoriesById(event.target.value);
  };

  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const openPopup = () => {
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  return (
    <div className=".container5">
      <h1 className="page">Sub Categories</h1>

      <button className="addsub" onClick={openPopup}>
        Add SubCategories
      </button>
      {isPopupOpen && (
        <div className="popup-overlay">
          <div className="popup-content">
            <h1>Category Dropdown</h1>
            <select value={selectedCategory} onChange={handleCategoryChange}>
              <option value="">Select a category</option>
              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.name}
                </option>
              ))}
            </select>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="Category Name"
              style={{ marginBottom: 40 }}
            />
            <div class="addSubCatCont">
              {
                <button className="addbtn1" onClick={handleAddSubCategory}>
                  Add SubCategory
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

      <table className="table1">
        <thead>
          <tr>
            <th>Image</th>
            <th>Category Name</th>
            <th>Delete</th>

            {/* Add more table headers */}
          </tr>
        </thead>
        <tbody>
          {subcategories.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.name}</td>
              <td>
                {" "}
                <button
                  className="delt"
                  onClick={() => {
                    console.log(item.id);
                    Delete(item.id);
                  }}>
                  Delete
                </button>
              </td>

              {/* Add more table data cells */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
export default SubCategory;
