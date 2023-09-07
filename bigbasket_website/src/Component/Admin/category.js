import React, { useState, useEffect } from "react";
import axios from "axios";
import "../Admin/CSS/category.css";

function Category() {
  const [categories, setCategories] = useState([]);
  const [name, setName] = useState("");
  const [image, setSelectedFile] = useState(null);

  const fetchCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:4000/category/getAllCategories"
      ); // Update with your API endpoint
      setCategories(response.data.data);
      console.log(response);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleAddCategory = async () => {
    const formData = new FormData();
    formData.append("name", name);
    formData.append("image", image);

    try {
      await axios.post("http://localhost:4000/category/addCategory", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setName("");
      setSelectedFile(null);
      fetchCategories();
    } catch (error) {
      console.error("Error adding category:", error);
    }
  };

  const Delete = function (id) {
    axios
      .delete("http://localhost:4000/category/deleteCategory/" + id)
      .then((response) => {
        console.log(response);
        fetchCategories();
        //setCategories(response.data.data);
      })
      .catch((error) => {
        console.log("Error fetching data:", error);
      });
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const openPopup = () => {
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  return (
    <div className=".container4">
      <h1 className="page">Categories</h1>

      <button className="add" onClick={openPopup}>
        Add Category
      </button>
      {isPopupOpen && (
        <div className="popup-overlay">
          <div className="popup-content">
            <div>
              <h1>Add New Category</h1>
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Category Name"
              />
              <input
                type="file"
                onChange={handleFileChange}
                style={{ marginTop: 20 }}
              />
            </div>
            <div className="addCatCont">
              {
                <button className="addbtn1" onClick={handleAddCategory}>
                  Add Category
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
          {categories.map((item) => (
            <tr key={item.id}>
              <td>
                {" "}
                <img
                  // src={`/uploads/${img.img.path}`}
                  src={`http://localhost:4000/uploads/` + item.image}
                  alt={item.image}></img>
              </td>
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

export default Category;
