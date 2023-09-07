const express = require("express");
const db = require("../db");
const utils = require("../utils");

const router = express.Router();

router.post("/addCategory", (req, res) => {
  const { name } = req.body;
  const imageFile = req.file;
  // Construct the image URL
  const imageURL = `${imageFile.filename}`; // Update with your server URL and upload directory
  // Insert the product data into the products table
  const product = {
    name,
    image: imageURL, // Assuming you are storing the filename in the database
  };

  db.query("INSERT INTO categories SET ?", product, (err, result) => {
    res.send(utils.createResult(err, result));
  });
});

router.get("/getAllCategories", (request, response) => {
  const statement = "select * from categories";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.delete("/deleteCategory/:id", (request, response) => {
  const id = request.params.id;
  const statement = "delete from categories where id = ?";
  db.query(statement, [id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

module.exports = router;
