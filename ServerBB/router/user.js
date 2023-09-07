const express = require("express");
const db = require("../db");
const utils = require("../utils");

const router = express.Router();

router.post("/register", async (req, res) => {
  try {
    const {
      name,
      email,
      mobile,
      password,
      address_line1,
      city,
      state,
      postal_code,
      country,
    } = req.body;

    // Insert user details into the users table
    const user = { name, email, mobile, password, role: "user" };
    const userResult = await db.query("INSERT INTO users SET ?", user);
    const userId = userResult.insertId;

    // Insert address details into the addresses table
    const address = {
      user_id: userId,
      address_line1,
      city,
      state,
      postal_code,
      country,
    };
    await db.query("INSERT INTO addresses SET ?", address);

    res.status(201).json({ message: "Registration successful" });
  } catch (err) {
    console.error("Error during registration:", err);
    res.status(500).json({ error: "An error occurred" });
  }
});

const loginQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
const roleLoginQuery =
  "SELECT * FROM users WHERE email = ? AND password = ? AND role = ?";

router.post("/loginUser", (req, res) => {
  const { email, password } = req.body;
  db.query(loginQuery, [email, password], (error, result) => {
    res.send(utils.createResult(error, result));
  });
});

function loginWithRole(req, res, role) {
  const { email, password } = req.body;
  db.query(roleLoginQuery, [email, password, role], (error, result) => {
    res.send(utils.createResult(error, result));
  });
}

router.post("/login", (req, res) => {
  loginWithRole(req, res, "user");
});

router.post("/adminlogin", (req, res) => {
  loginWithRole(req, res, "admin");
});

router.get("/:id", (req, res) => {
  const id = req.params.id;
  const statement = "SELECT * FROM users WHERE id=?";
  db.query(statement, [id], (error, result) => {
    res.send(utils.createResult(error, result));
  });
});

router.get("/address/:id", (req, res) => {
  const id = req.params.id;
  const statement = "SELECT * FROM addresses WHERE user_id=?";
  db.query(statement, [id], (error, result) => {
    res.send(utils.createResult(error, result));
  });
});

// get all users
router.get("/", (request, response) => {
  const statement = "select * from users";
  db.query(statement, (error, result) => {
    response.send(utils.createResult(error, result));
  });
});

router.put("/updateUser", (req, res) => {
  const { name, email, mobile, id } = req.body;
  db.query(
    "UPDATE users SET name = ?, email = ?, mobile = ? WHERE id = ?",
    [name, email, mobile, id],
    (error, data) => {
      res.send(utils.createResult(error, data));
    }
  );
});

// router.post("/updateUserProfile", async (req, res) => {
//   try {
//     const {
//       id,
//       name,
//       email,
//       mobile,
//       address_line1,
//       city,
//       state,
//       postal_code,
//       country,
//     } = req.body;

//     // Insert user details into the users table
//     const user = { name, email, mobile, id };
//     const userResult = await db.query(
//       "UPDATE users SET name = ?, email = ?, mobile = ? WHERE id = ?",
//       user
//     );
//     const userId = userResult.insertId;

//     // Insert address details into the addresses table
//     const address = {
//       user_id: userId,
//       address_line1,
//       city,
//       state,
//       postal_code,
//       country,
//     };
//     await db.query(
//       "UPDATE addresses SET address_line1 = ?, city = ?, state = ?,postal_code = ?, country = ? WHERE user_id = ?",
//       address
//     );

//     res.status(201).json({ message: "Updated successful" });
//   } catch (err) {
//     console.error("Error during updation:", err);
//     res.status(500).json({ error: "An error occurred" });
//   }
// });

router.put("/updateUserAddress", (req, res) => {
  const { address_line1, city, state, postal_code, country, user_id } =
    req.body;
  db.query(
    "UPDATE addresses SET address_line1 = ?, city = ?, state = ?, postal_code = ?, country = ? WHERE user_id = ?",
    [address_line1, city, state, postal_code, country, user_id],
    (error, data) => {
      res.send(utils.createResult(error, data));
    }
  );
});

router.put("/updateUserProfile", (req, res) => {
  const {
    name,
    email,
    mobile,
    address_line1,
    city,
    state,
    postal_code,
    country,
    id,
  } = req.body;
  db.query(
    "UPDATE users JOIN addresses ON users.id = addresses.user_id SET users.name = ?, users.email = ?,users.mobile = ?,addresses.address_line1 = ?, addresses.city = ?,addresses.state = ?,addresses.postal_code = ?,addresses.country = ? WHERE users.id = ?;",
    [name, email, mobile, address_line1, city, state, postal_code, country, id],
    (error, data) => {
      res.send(utils.createResult(error, data));
    }
  );
});

module.exports = router;
