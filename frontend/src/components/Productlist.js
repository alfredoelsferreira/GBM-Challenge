import React, { Component } from 'react';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import { SERVER_URL } from '../constants.js';
import AddProduct from './AddProduct.js';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css'
import { CSVLink } from 'react-csv';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Snackbar from '@material-ui/core/Snackbar';

class Productlist extends Component {
    constructor(props) {
        super(props);
        this.state = { products: [], open: false, message: '' };
    }

    componentDidMount() {
        this.fetchProducts();
    }

    // Fetch all products
    fetchProducts = () => {
        const token = sessionStorage.getItem("jwt");
        fetch(SERVER_URL + 'v1/products', {
                headers: { 'X-GBM-TOKEN': token }
            })
            .then((response) => response.json())
            .then((responseData) => {
                console.log("responseData: " + JSON.stringify(responseData));
                this.setState({
                    products: responseData.data,
                });
            })
            .catch(err => console.error(err));
    }

    confirmDelete = (id) => {
        confirmAlert({
            message: 'Are you sure to delete?',
            buttons: [{
                    label: 'Yes',
                    onClick: () => this.onDelClick(id)
                },
                {
                    label: 'No',
                }
            ]
        })
    }

    // Delete Product
    onDelClick = (id) => {
        const token = sessionStorage.getItem("jwt");
        fetch(SERVER_URL + 'v1/products/' + id, {
                method: 'DELETE',
                headers: { 'X-GBM-TOKEN': token }
            })
            .then(res => {
                this.setState({ open: true, message: 'Product deleted' });
                this.fetchProducts();
            })
            .catch(err => {
                this.setState({ open: true, message: 'Error when deleting' });
                console.error(err)
            })
    }


    renderEditable = (cellInfo) => {
        return ( <
            div style = {
                { backgroundColor: "#fafafa" }
            }
            contentEditable suppressContentEditableWarning onBlur = {
                e => {
                    const data = [...this.state.products];
                    data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
                    this.setState({ products: data });
                }
            }
            dangerouslySetInnerHTML = {
                {
                    __html: this.state.products[cellInfo.index][cellInfo.column.id]
                }
            }
            />
        );
    }

    // Add new product
    addProduct(product) {
        const token = sessionStorage.getItem("jwt");
        fetch(SERVER_URL + 'v1/products', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-GBM-TOKEN': token
                },
                body: JSON.stringify(product)
            })
            .then(res => this.fetchProducts())
            .catch(err => console.error(err))
    }

    // Update Product
    updateProduct(product, id) {
        var newProduct = {
            name: product.name,
            description: product.description,
            price: parseFloat(product.price),
            quantity: parseInt(product.quantity)
        };
        const token = sessionStorage.getItem("jwt");
        fetch(SERVER_URL + 'v1/products/' + id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'X-GBM-TOKEN': token
                },
                body: JSON.stringify(newProduct)
            })
            .then(res =>
                this.setState({ open: true, message: 'Changes saved' })
            )
            .catch(err =>
                this.setState({ open: true, message: 'Error when saving' })
            )
    }

    handleClose = (event, reason) => {
        this.setState({ open: false });
    };


    render() {
            const columns = [{
                    Header: 'Name',
                    accessor: 'name',
                    Cell: this.renderEditable
                }, {
                    Header: 'Description',
                    accessor: 'description',
                    Cell: this.renderEditable
                }, {
                    Header: 'Price $',
                    accessor: 'price',
                    Cell: this.renderEditable
                }, {
                    Header: 'Quantity',
                    accessor: 'quantity',
                    Cell: this.renderEditable
                }, {
                    id: 'savebutton',
                    sortable: false,
                    filterable: false,
                    width: 100,
                    accessor: 'id',
                    Cell: ({ value, row }) => ( < Button size = "small"
                        variant = "flat"
                        color = "primary"
                        onClick = {
                            () => { this.updateProduct(row, value) }
                        } > Save < /Button>)
                    },
                    {
                        id: 'delbutton',
                        sortable: false,
                        filterable: false,
                        width: 100,
                        accessor: 'id',
                        Cell: ({ value }) => ( < Button size = "small"
                            variant = "flat"
                            color = "secondary"
                            onClick = {
                                () => { this.confirmDelete(value) }
                            } > Delete < /Button>)
                        }
                    ]

                    return (
                      <div className = "App" >
                        <Grid container >
                          <Grid item >
                            <AddProduct addProduct = { this.addProduct }fetchProducts = { this.fetchProducts }/>
                          < /Grid >
                          <Grid item style = {{ padding: 20 }} >
                            <CSVLink data = { this.state.products } separator = ";" > Export CSV < /CSVLink>
                          < /Grid >
                        </Grid>

                        <ReactTable data = { this.state.products } columns = { columns } filterable = { true } pageSize = { 10 }/>
                        <Snackbar open = { this.state.open } onClose = { this.handleClose } autoHideDuration = { 1500 } message = { this.state.message }/>
                      < /div >
                    );
                }

            }


            export default Productlist;
