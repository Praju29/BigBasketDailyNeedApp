const express = require("express");
const db = require("../db");
const utils = require("../utils");

const router = express.Router();

router.get("/gettotalcountuser", (request, response) => {
  const statement = "SELECT COUNT(id) as uid FROM users";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/gettotalcountproduct", (request, response) => {
  const statement = "SELECT COUNT(*) as pid FROM products";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/gettotalcountorder", (request, response) => {
  const statement = "SELECT COUNT(*) as oid FROM orders";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

// router.get("/totalp", (request, response) => {
//   const statement = "SELECT COUNT(*) as pid FROM products";

//   db.query(statement, (error, result) => {
//     if (error) {
//       console.error("Database error:", error);
//       response.status(500).json({ error: "Internal server error" });
//     } else {
//       response.send(utils.createResult(error, result));
//     }
//   });
// });

router.get("/getMoreOrdersDetails/:product_id", (request, response) => {
  const product_id = request.params.product_id;
  const statement =
    "SELECT orders.id AS order_id, orders.product_id, email, payment_mode, city, products.name, price, quantity FROM users JOIN payment_info ON payment_info.user_id = users.id JOIN addresses ON addresses.user_id = payment_info.user_id JOIN orders ON orders.user_id = addresses.user_id JOIN products ON products.id = orders.product_id WHERE orders.id = ? LIMIT 1;";
  db.query(statement, [product_id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

module.exports = router;
