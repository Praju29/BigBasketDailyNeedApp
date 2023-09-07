// Update TopBar component
import React, { useEffect } from "react";
import { NavLink, Outlet, useNavigate } from "react-router-dom";
import $ from "jquery";
import "./Css_user/Navbar.css";

function TopBar() {
  const navigate = useNavigate();

  function animation() {
    var tabsNewAnim = $("#navbarSupportedContent");
    var activeItemNewAnim = tabsNewAnim.find(".active");
    var activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
    var activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
    var itemPosNewAnimTop = activeItemNewAnim.position();
    var itemPosNewAnimLeft = activeItemNewAnim.position();
    $(".hori-selector").css({
      top: itemPosNewAnimTop.top + "px",
      left: itemPosNewAnimLeft.left + "px",
      height: activeWidthNewAnimHeight + "px",
      width: activeWidthNewAnimWidth + "px",
    });
    $("#navbarSupportedContent").on("click", "li", function (e) {
      $("#navbarSupportedContent ul li").removeClass("active");
      $(this).addClass("active");
      var activeWidthNewAnimHeight = $(this).innerHeight();
      var activeWidthNewAnimWidth = $(this).innerWidth();
      var itemPosNewAnimTop = $(this).position();
      var itemPosNewAnimLeft = $(this).position();
      $(".hori-selector").css({
        top: itemPosNewAnimTop.top + "px",
        left: itemPosNewAnimLeft.left + "px",
        height: activeWidthNewAnimHeight + "px",
        width: activeWidthNewAnimWidth + "px",
      });
    });
  }
  useEffect(() => {
    animation();
    $(window).on("resize", function () {
      setTimeout(function () {
        animation();
      }, 500);
    });
  }, []);

  const handleLogout = () => {
    sessionStorage.removeItem("name");
    sessionStorage.removeItem("id");

    // You can perform any logout logic here, such as clearing tokens or user data.
    // For this example, we'll just navigate the user back to the login page.
    navigate("/"); // Replace "/login" with your actual login route
  };

  return (
    <>
      <div>
        <nav className="navbar navbar-expand-lg navbar-mainbg">
          <div className="container-fluid">
            <NavLink className="navbar-brand navbar-logo" to="/topbar/home">
              BigBasket
            </NavLink>
            {/* <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation">
          <i className="fas fa-bars text-white"></i>
        </button> */}
            <div
              className="collapse navbar-collapse"
              id="navbarSupportedContent">
              <ul className="navbar-nav ml-auto">
                <div className="hori-selector">
                  <div className="left"></div>
                  <div className="right"></div>
                </div>
                <li className="nav-item active">
                  <NavLink className="nav-link" to="/topbar/home">
                    <i className="fas fa-tachometer-alt"> Home</i>
                  </NavLink>
                </li>
                <li className="nav-item active">
                  <NavLink className="nav-link" to="/topbar/category">
                    <i className="fa fa-th-large"> Category</i>
                  </NavLink>
                </li>
                <li className="nav-item active">
                  <NavLink className="nav-link" to="/topbar/products">
                    <i className="fa fa-bars"> Products</i>
                  </NavLink>
                </li>
                <li className="nav-item active">
                  <NavLink className="nav-link" to="/topbar/orders">
                    <i className="fa fa-shopping-cart"> Orders</i>
                  </NavLink>
                </li>
                <li className="navbar-nav ml-auto" style={{ marginLeft: 400 }}>
                  <button onClick={handleLogout} className="btn">
                    Logout
                  </button>
                </li>
              </ul>
            </div>
          </div>
        </nav>

        <Outlet />
      </div>
    </>
  );
}

export default TopBar;
