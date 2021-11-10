function addToCartBtnHandler(e) {
    let productId = e.currentTarget.dataset.prodid
    fetch(`/cart/add?productId=${productId}`)
    let cartNumber = document.querySelector("#lblCartCount");
    cartNumber.textContent = (parseInt(cartNumber.textContent) + 1).toString()
}

let addToCartBtn = document.querySelectorAll(".add");
console.log(addToCartBtn)
for (let button of addToCartBtn){
    button.addEventListener("click", addToCartBtnHandler);
}