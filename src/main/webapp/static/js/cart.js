import {dataHandler} from "./dataHandler.js";

const shoppingCart ={
    removeButtons: document.querySelectorAll(".remove"),
    quantitySelects: document.querySelectorAll("#quantity"),
    changeSubtotal: (e) => {
        let quantity = e.target.options.selectedIndex;
        let itemId = e.target.dataset.id;
        let unitPrice = parseFloat(document.querySelector(`#product_${itemId} > .list-group-item-text > .unit-price`).innerHTML);
        let subtotal = document.querySelector(`#product_${itemId} > .subtotal > p`);
        subtotal.innerHTML = `Subtotal: ${quantity * unitPrice} USD`;
    },
    changeTotal: () => {
        let subtotals = document.querySelectorAll(".subtotal > p")
        let total = 0
        for(let subtotal of subtotals){
            total += parseFloat(subtotal.innerHTML.split(" ")[1]);
        }
        document.querySelector(".total > p").innerHTML = `Total: ${total} USD`
    },
    removeButtonHandler: async (e) => {
        let itemId = e.target.dataset.id;
        let deleteResponse = await dataHandler.deleteItem(itemId);
        if (deleteResponse.ok) {
            let container = document.querySelector("#cart");
            let domElement = document.querySelector(`#product_${itemId}`);
            container.removeChild(domElement);
            shoppingCart.changeTotal();
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
            console.log(updateResponse);
            if (updateResponse.ok && updateResponse.productId === parseFloat(itemId)) {
                const newQuantity = updateResponse.quantity;
                const subtotal = updateResponse.subtotal;
                const defaultCurrency = updateResponse.defaultCurrency;
                const total = updateResponse.total;
                shoppingCart.changeSubtotal(e);     // use subtotal instead
                shoppingCart.changeTotal();     // use total instead
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