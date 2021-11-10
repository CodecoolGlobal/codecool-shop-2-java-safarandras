import {dataHandler} from "./dataHandler.js";

const shoppingCart ={
    removeButtons: document.querySelectorAll(".remove"),
    quantitySelects: document.querySelectorAll("#quantity"),
    changeSubtotal: (e) => {
        let quantity = e.target.options.selectedIndex;
        let itemId = e.target.dataset.id;
        let unitPrice = parseInt(document.querySelector(`#product_${itemId} > .list-group-item-text > .unit-price`).innerHTML);
        let subtotal = document.querySelector(`#product_${itemId} > .subtotal > p`);
        subtotal.innerHTML = `Subtotal: ${quantity * unitPrice} USD`;
    },
    changeTotal: (e) => {
        let subtotals = document.querySelectorAll(".subtotal > p")
        let total = 0
        for(let subtotal of subtotals){
            total += parseInt(subtotal.innerHTML.split(" ")[1]);
        }
        document.querySelector(".total > p").innerHTML = `Total: ${total} USD`
    },
    removeButtonHandler: (e) => {
        let itemId = e.target.dataset.id;
        dataHandler.deleteItem(itemId).then();
        let container = document.querySelector(".container");
        let domElement = document.querySelector(`#product_${itemId}`);
        container.removeChild(domElement);
    },
    selectChangeHandler: (e) => {
        if(e.target.options.selectedIndex === 0){
            shoppingCart.removeButtonHandler(e);
        }
        else{
            let quantity = e.target.options.selectedIndex;
            let itemId = e.target.dataset.id;
            dataHandler.changeItem(itemId, quantity).then();
            shoppingCart.changeSubtotal(e);
            shoppingCart.changeTotal(e);
        }
        console.log(e.target.options.selectedIndex);
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