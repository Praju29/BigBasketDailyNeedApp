const express = require("express");
const cors = require("cors");

const multer = require("multer");
const path = require("path");

const storage = multer.diskStorage({
  destination: "./uploads/",
  filename: (req, file, cb) => {
    return cb(
      null,
      `${file.fieldname}_${Date.now()}${path.extname(file.originalname)}`
    );
  },
});

const upload = multer({
  storage: storage,
});

const routerUser = require("./router/user");
const routerCategory = require("./router/category");
const routerSubCategory = require("./router/subCategory");
const routerProduct = require("./router/product");
const routerOrder = require("./router/order");
const routerTotal = require("./router/total");

const app = express();
app.use(express.json());
app.use(cors("*"));

app.use(upload.single("image"));
app.use("/uploads", express.static("uploads"));

app.use("/user", routerUser);
app.use("/category", routerCategory);
app.use("/subCategory", routerSubCategory);
app.use("/product", routerProduct);
app.use("/order", routerOrder);
app.use("/total", routerTotal);

app.listen(4000, "0.0.0.0", () => {
  console.log("Server is running at port 4000");
});
