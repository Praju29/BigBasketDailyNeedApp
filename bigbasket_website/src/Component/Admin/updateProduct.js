import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../../../node_modules/bootstrap/dist/css/bootstrap.css";

function UpdateProduct() {
  const { id } = useParams();

  const [values, setValues] = useState({
    id: id,
    name: "",
    price: "",
    description: "",
  });
  useEffect(() => {
    axios
      .get("http://localhost:4000/product/updateProduct/" + id)
      .then((res) => {
        console.log(res);
        console.log(res.data.data[0].name);
        setValues({
          ...values,
          name: res.data.data[0].name,
          price: res.data.data[0].price,
          description: res.data.data[0].description,
        });
      })
      .catch((err) => console.log(err));
  }, []);

  const navigate = useNavigate();
  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .put("http://localhost:4000/product/updateProduct/" + id, values)
      .then((res) => {
        console.log(res);
        navigate("/sidebaradmin/products");
      })
      .catch((err) => console.log(err));
  };
  return (
    <div className="d-flex w-100 vh-100 justify-content-center align-items-center">
      <div className="w-50 border bg-secondary text-white p-5">
        <form onSubmit={handleSubmit}>
          <div>
            <input
              className="form-control"
              name="name"
              type="text"
              placeholder="Name"
              value={values.name}
              onChange={(e) => setValues({ ...values, name: e.target.value })}
            />
          </div>
          <div>
            {" "}
            <input
              className="form-control"
              name="price"
              type="text"
              placeholder="Price"
              value={values.price}
              onChange={(e) => setValues({ ...values, price: e.target.value })}
            />
          </div>
          <div>
            <input
              className="form-control"
              name="description"
              type="text"
              placeholder="Description"
              value={values.description}
              onChange={(e) =>
                setValues({ ...values, description: e.target.value })
              }
            />
          </div>
          <button className="btn btn-info">Update</button>
        </form>
      </div>
    </div>
  );
}

export default UpdateProduct;
