const express = require("express");
const db = require("../db");
const utils = require("../utils");

const router = express.Router();

router.post("/placeOrder", (request, response) => {
  const { user_id, product_id, quantity } = request.body;

  const orderDetails = {
    user_id,
    product_id,
    quantity,
    order_date: new Date(),
    delivery_status: "Not Yet Delivered",
  };

  db.query("INSERT INTO orders SET ?", orderDetails, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/getOrdersById/:user_id", (request, response) => {
  const user_id = request.params.user_id;
  const statement =
    "select * from products join orders on products.id = orders.product_id where orders.user_id =?";
  db.query(statement, [user_id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});
router.get("/getOrdersByOrderId/:id", (request, response) => {
  const id = request.params.id;
  const statement =
    "select * from products join orders on products.id = orders.product_id where orders.id =?";
  db.query(statement, [id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

// router.delete("/cancleOrder/:id/:user_id", (request, response) => {
//   const id = request.params.id;
//   const user_id = request.params.user_id;

//   const statement = "delete from orders where id = ? and user_id = ?";
//   db.query(statement, [id, user_id], (error, result) => {
//     response.send(utils.createResult(error, result));
//   });
// });
router.delete("/cancleOrder/:id", (request, response) => {
  const id = request.params.id;

  const statement = "delete from orders where id = ?";
  db.query(statement, [id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/getAllOrders", (request, response) => {
  const statement = "select * from orders";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/getOrdersDetails", (request, response) => {
  const statement =
    "select orders.id as order_id,product_id,products.name, price, delivery_status,order_date,users.email,quantity from products join  orders on products.id = orders.product_id join users on users.id = orders.user_id ";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/getFewOrdersDetails", (request, response) => {
  const statement =
    "select orders.id as order_id,product_id,name, price, delivery_status,order_date from products join  orders on products.id = orders.product_id ";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

// router.get("/getMoreOrdersDetails/:product_id", (request, response) => {
//   const product_id = request.params.product_id;
//   const statement =
//     //"select orders.id,orders.product_id,email, payment_mode, city, products.name, price,quantity  from users join payment_info on payment_info.user_id = users.id join addresses on addresses.user_id = payment_info.user_id join orders on orders.user_id = addresses.user_id join products on products.id = orders.product_id  where orders.product_id=? LIMIT 1";
//     "SELECT orders.id AS order_id, orders.product_id, email, payment_mode, city, products.name, price, quantity FROM users JOIN payment_info ON payment_info.user_id = users.id JOIN addresses ON addresses.user_id = payment_info.user_id JOIN orders ON orders.user_id = addresses.user_id JOIN products ON products.id = orders.product_id WHERE orders.product_id = ? LIMIT 1;";
//   db.query(statement, [product_id], (error, result) => {
//     response.send(utils.createResult(error, result));
//   });
// });

router.put("/updateDeliveryStatus/:id", (request, response) => {
  const id = request.params.id;
  db.query(
    "update orders set delivery_status = 'Delivered' where id = ?",
    [id],
    (error, data) => {
      response.send(utils.createResult(error, data));
    }
  );
});

router.post("/payment", (request, response) => {
  const { user_id, payment_mode } = request.body;
  db.query(
    "insert into payment_info(user_id, payment_mode) values (?,?)",
    [user_id, payment_mode],
    (error, result) => {
      response.send(utils.createResult(error, result));
    }
  );
});

module.exports = router;
