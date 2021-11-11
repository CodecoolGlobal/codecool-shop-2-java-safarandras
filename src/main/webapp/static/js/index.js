function addToCartBtnHandler(element) {
    let productId = element.dataset.prodid;
    const data = {"productId" : productId};
    fetch(`/cart`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(data)
    } );
    let cartNumber = document.querySelector("#lblCartCount");
    cartNumber.textContent = (parseInt(cartNumber.textContent) + 1).toString();
}