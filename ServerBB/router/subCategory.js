const express = require("express");
const db = require("../db");
const utils = require("../utils");

const router = express.Router();

router.post("/addSubCategory", (request, response) => {
  const { category_id, name } = request.body;
  db.query(
    "insert into subcategories(category_id, name) values (?,?)",
    [category_id, name],
    (error, result) => {
      response.send(utils.createResult(error, result));
    }
  );
});

router.get("/getAllSubCategories", (request, response) => {
  const statement = "select * from subcategories";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.get("/getAllSubCategories/:category_id", (request, response) => {
  const category_id = request.params.category_id;
  const statement = "select * from subcategories where category_id=?";
  db.query(statement, [category_id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.delete("/deleteSubCategory/:id", (request, response) => {
  const id = request.params.id;
  const statement = "delete from subcategories where id = ?";
  db.query(statement, [id], (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

module.exports = router;
