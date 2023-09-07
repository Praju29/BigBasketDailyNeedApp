const express = require("express");
const db = require("../db");
const utils = require("../utils");

const router = express.Router();

router.post("/addProducts", (req, res) => {
  const { category_id, subcategory_id, name, price, description } = req.body;
  const imageFile = req.file;
  // const imagePath = `/uploads/${imageFile.filename}`;

  // Construct the image URL
  const imageURL = `${imageFile.filename}`; // Update with your server URL and upload directory
  // Insert the product data into the products table
  const product = {
    category_id,
    subcategory_id,
    name,
    price,
    description,
    image: imageURL, // Assuming you are storing the filename in the database
  };

  db.query("INSERT INTO products SET ?", product, (err, result) => {
    res.send(utils.createResult(err, result));
    //     console.log(req.file)
    //     if (err) {
    //     console.error('Error inserting product:', err);
    //     res.status(500).json({ error: 'An error occurred' });
    //     return;
    //   }

    //   res.status(201).json({ message: 'Product created successfully',imageURL });
  });
});

router.get("/getAllProduct", (request, response) => {
  const statement = "select * from products";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});
router.get("/updateProduct/:id", (request, response) => {
  const id = request.params.id;
  //const { name, price, description } = request.body;
  db.query("select * from  products where id = ?", [id], (error, data) => {
    response.send(utils.createResult(error, data));
  });
});

router.get("/getAllProduct/:subcategory_id", (request, response) => {
  const subcategory_id = request.params.subcategory_id;
  //const { name, price, description } = request.body;
  db.query(
    "select * from  products where subcategory_id = ?",
    [subcategory_id],
    (error, data) => {
      response.send(utils.createResult(error, data));
    }
  );
});

router.get("/getAllProductByCid/:category_id", (request, response) => {
  const category_id = request.params.category_id;
  //const { name, price, description } = request.body;
  db.query(
    "select * from  products where category_id = ?",
    [category_id],
    (error, data) => {
      response.send(utils.createResult(error, data));
    }
  );
});

router.put("/updateProduct/:id", (request, response) => {
  const id = request.params.id;
  const { name, price, description } = request.body;
  db.query(
    "update products set name = ?,price = ?,description = ? where id = ?",
    [name, price, description, id],
    (error, data) => {
      response.send(utils.createResult(error, data));
    }
  );
});

router.delete("/deleteProduct/:id", (request, response) => {
  const id = request.params.id;
  const statement = "delete from products where id = ?";
  db.query(statement, [id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/:id", (request, response) => {
  const id = request.params.id;
  const statement = "select * from products where id=?";
  db.query(statement, [id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/searchByName/:name", (request, response) => {
  const name = request.params.name;
  const statement = "select * from products where name=?";
  db.query(statement, [name], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.post("/addReview", (request, response) => {
  const { product_id, user_id, rating, review_text } = request.body;
  db.query(
    "insert into ratings(product_id, user_id, rating,review_text) values (?,?,?,?)",
    [product_id, user_id, rating, review_text],
    (error, result) => {
      response.send(utils.createResult(error, result));
    }
  );
});

router.get("/getAllReview/:product_id", (request, response) => {
  const product_id = request.params.product_id;
  const statement = "select * from ratings where product_id=?";
  db.query(statement, [product_id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

module.exports = router;
