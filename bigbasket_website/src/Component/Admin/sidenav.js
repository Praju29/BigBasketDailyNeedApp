import { Outlet, useNavigate } from "react-router-dom";
import "../../../node_modules/bootstrap/dist/css/bootstrap.css";
import SideNav, { NavItem, NavIcon, NavText } from "@trendmicro/react-sidenav";
import "@trendmicro/react-sidenav/dist/react-sidenav.css";
import "../Admin/CSS/sidenav.css";

function Sidenav() {
  const navigate = useNavigate();
  const handleLogout = () => {
    // You can perform any logout logic here, such as clearing tokens or user data.
    // For this example, we'll just navigate the user back to the login page.
    navigate("/"); // Replace "/login" with your actual login route
  };
  return (
    <>
      <div className="page-container">
        <SideNav
          onSelect={(selected) => {
            console.log(selected);
            navigate("/" + selected);
          }}
          className="mysidenav">
          {/* <SideNav.Toggle /> */}
          <SideNav.Nav defaultSelected="sidebaradmin/adminhome">
            <NavItem eventKey="sidebaradmin/adminhome">
              <NavIcon>
                <i className="fas fa-home" style={{ fontSize: "1.5em" }}></i>
              </NavIcon>
              <NavText>Home</NavText>
            </NavItem>
            <NavItem eventKey="sidebaradmin/users">
              <NavIcon>
                <i
                  className="fa-solid fa-user"
                  style={{ fontSize: "1.5em" }}></i>
              </NavIcon>
              <NavText>Users</NavText>
            </NavItem>
            <NavItem eventKey="sidebaradmin/products">
              <NavIcon>
                <i
                  className="fa-sharp fa-solid fa-box-open"
                  style={{ fontSize: "1.5em" }}></i>
              </NavIcon>
              <NavText>Products</NavText>
            </NavItem>
            <NavItem eventKey="sidebaradmin/orders">
              <NavIcon>
                <i
                  className="fa-solid fa-cart-shopping"
                  style={{ fontSize: "1.5em" }}></i>
              </NavIcon>
              <NavText>Orders</NavText>
            </NavItem>
            <NavItem>
              <NavIcon>
                <i
                  className="fa-solid fa-list"
                  style={{ fontSize: "1.5em" }}></i>
              </NavIcon>
              <NavText>Category</NavText>

              <NavItem eventKey="sidebaradmin/category">
                <NavText>Categories</NavText>
              </NavItem>
              <NavItem eventKey="sidebaradmin/subcategory">
                <NavText>Sub Categories</NavText>
              </NavItem>
            </NavItem>
          </SideNav.Nav>
        </SideNav>
        <div>
          <button className="logout-button" onClick={handleLogout}>
            Logout
          </button>
          <Outlet />
        </div>
      </div>
    </>
  );
}

export default Sidenav;
