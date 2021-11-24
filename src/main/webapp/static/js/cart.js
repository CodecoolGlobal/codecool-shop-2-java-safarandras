import {dataHandler} from "./dataHandler.js";

const shoppingCart ={
    removeButtons: document.querySelectorAll(".remove"),
    quantitySelects: document.querySelectorAll("#quantity"),
    changeSubtotal: (newQuantity, subtotal, defaultCurrency, e) => {
        let itemId = e.target.dataset.id;
        let subtotalElement = document.querySelector(`#product_${itemId} > .subtotal > p`);
        subtotalElement.innerHTML = `Subtotal: ${subtotal} ${defaultCurrency}`;
    },
    changeTotal: (newQuantity, total, defaultCurrency) => {
        document.querySelector(".total > p").innerHTML = `Total: ${total} ${defaultCurrency}`;
    },
    removeButtonHandler: async (e) => {
        let itemId = e.target.dataset.id;
        let deleteResponse = await dataHandler.deleteItem(itemId);
        if (deleteResponse.productId === parseInt(itemId)) {
            const defaultCurrency = deleteResponse.defaultCurrency;
            const total = deleteResponse.total;
            shoppingCart.changeTotal(0, total, defaultCurrency);
            let container = document.querySelector("#cart");
            let domElement = document.querySelector(`#product_${itemId}`);
            container.removeChild(domElement);
            if(container.querySelectorAll(`.list-group-item`).length === 0){
                container.innerHTML =
                    `<div class="container">
                    <div class="card">
                        <h4 class="row justify-content-md-center">Your cart is empty!</h4>
                    </div>
                </div>`
            }
        }
    },
    selectChangeHandler: async (e) => {
        if(e.target.options.selectedIndex === 0){
            shoppingCart.removeButtonHandler(e);
        }
        else{
            let quantity = e.target.options.selectedIndex;
            let itemId = e.target.dataset.id;
            let updateResponse = await dataHandler.changeItem(itemId, quantity);
            if (updateResponse.productId === parseInt(itemId)) {
                const newQuantity = updateResponse.quantity;
                const subtotal = updateResponse.subtotal;
                const defaultCurrency = updateResponse.defaultCurrency;
                const total = updateResponse.total;
                shoppingCart.changeSubtotal(newQuantity, subtotal, defaultCurrency, e);
                shoppingCart.changeTotal(newQuantity, total, defaultCurrency);
            }
        }
    },
    init: () => {
        for(let button of shoppingCart.removeButtons){
            button.addEventListener("click", shoppingCart.removeButtonHandler)
        }
        for(let select of shoppingCart.quantitySelects){
            select.addEventListener("change", shoppingCart.selectChangeHandler)
        }
    }
}
shoppingCart.init();