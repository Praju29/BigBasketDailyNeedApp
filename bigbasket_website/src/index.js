import ReactDOM from "react-dom";
import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Component/login";
import Register from "./Component/register";
import AdminHome from "./Component/Admin/adminhome";
import Orders from "./Component/Admin/orders";
import Products from "./Component/Admin/products";
import Users from "./Component/Admin/users";
import Category from "./Component/Admin/category";
import Sidenav from "./Component/Admin/sidenav";
import SubCategory from "./Component/Admin/subcategory";
import UpdateProduct from "./Component/Admin/updateProduct";
import TopBar from "./Component/User/topBar";
import Home from "./Component/User/home";
import ProductList from "./Component/User/productList";
import CategoryList from "./Component/User/categotyList";
import OrderList from "./Component/User/orderList";
import ProductDetails from "./Component/User/productDetails";

import image1 from "./Component/User/img/BigBasket.jpg";
import image2 from "./Component/User/img/img3.jpg";
import image3 from "./Component/User/img/logo.jpg";
import image4 from "./Component/User/img/image.png";
function App() {
  const [pId, setpId] = useState(0);
  const imageUrls = [image4, image1, image2, image3];
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/topbar" element={<TopBar />}>
          <Route path="home" element={<Home images={imageUrls} />} />
          <Route path="category" element={<CategoryList />} />
          <Route path="products" element={<ProductList />} />
          <Route path="orders" element={<OrderList />} />
          <Route path="prodDetails/:pId" element={<ProductDetails />} />
        </Route>
        <Route path="/sidebaradmin" element={<Sidenav />}>
          <Route path="adminhome" element={<AdminHome />} />
          <Route path="orders" element={<Orders />} />
          <Route path="products" element={<Products />} />
          <Route path="users" element={<Users />} />
          <Route path="category" element={<Category />} />
          <Route path="subcategory" element={<SubCategory />} />
        </Route>
        <Route path="/update/:id" element={<UpdateProduct />} />
        {/* <Route path="/orderDetails/:id" element={<OrdersDetails />} /> */}
      </Routes>
    </BrowserRouter>
  );
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);
