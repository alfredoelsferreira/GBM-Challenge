import React from 'react';
import SkyLight from 'react-skylight';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';

function validate(name, description, price, quantity) {
  // true means invalid, so our conditions got reversed
  return {
    name: name.length === 0,
    description: description.length === 0,
    price: price.length === 0,
    quantity: quantity.length === 0
  };
}

class AddProduct extends React.Component {
  constructor(props) {
      super(props);
      this.state = {name: '', description: '',  price: '', quantity: '',
      touched: {
          name: false,
          description: false,
          price: false,
          quantity: false
        },};
  }

  handleChange = (event) => {
      this.setState(
          {[event.target.name]: event.target.value}
      );
  }

  handleBlur = (field) => (evt) => {
    this.setState({
      touched: { ...this.state.touched, [field]: true },
    });
  }

  // Save product and close modal form
  handleSubmit = (event) => {
      event.preventDefault();

      if (!this.canBeSubmitted()) {
        return;
      }

      var newProduct = {name: this.state.name, description: this.state.description,
        price: this.state.price, quantity: this.state.quantity};

      this.props.addProduct(newProduct);
      this.refs.addDialog.hide();
  }

  canBeSubmitted() {
    const errors = validate(this.state.name, this.state.description, this.state.price, this.state.quantity);
    const isDisabled = Object.keys(errors).some(x => errors[x]);
    return !isDisabled;
  }

  cancelSubmit = (event) => {
    event.preventDefault();
    this.refs.addDialog.hide();
  }

  render() {
    const errors = validate(this.state.name, this.state.description, this.state.price, this.state.quantity);
    const isDisabled = Object.keys(errors).some(x => errors[x]);

    const shouldMarkError = (field) => {
      const hasError = errors[field];
      const shouldShow = this.state.touched[field];
      return hasError ? shouldShow : false;
    };

    return (
      <div>
        <SkyLight hideOnOverlayClicked ref="addDialog">
          <h3>New Product</h3>
          <form>
            <TextField label="Name" placeholder="Name"  name="name" onChange={this.handleChange} onBlur={this.handleBlur('name')}/><br/>
            <TextField label="Description" placeholder="Description" name="description" onChange={this.handleChange} onBlur={this.handleBlur('description')}/><br/>
            <TextField type="number" label="Price" placeholder="Price" name="price" onChange={this.handleChange} onBlur={this.handleBlur('price')}/><br/>
            <TextField type="number" label="Quantity" placeholder="Quantity" name="quantity" onChange={this.handleChange} onBlur={this.handleBlur('quantity')}/><br/>
            <Button variant="outlined" disabled={isDisabled} style={{marginRight: 10}} color="primary" onClick={this.handleSubmit}>Save</Button>
            <Button variant="outlined" color="secondary" onClick={this.cancelSubmit}>Cancel</Button>
          </form>
        </SkyLight>
        <div>
            <Button variant="raised" color="primary" style={{'margin': '10px'}} onClick={() => this.refs.addDialog.show()}>New Product</Button>
        </div>
      </div>
    );
  }
}

export default AddProduct;
