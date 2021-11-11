function addToCartBtnHandler(element) {
    let productId = element.dataset.prodid;
    const data = {"productId": productId};

    fetch(`/cart`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    }).then(function (response) {                      // first then()
        if (response.ok) {
            return response.text();
        }

        throw new Error('Something went wrong.');
    })
        .then(function (text) {                          // second then()
            console.log('Request successful', text);
            let cartNumber = document.querySelector("#lblCartCount");
            cartNumber.textContent = (parseInt(cartNumber.textContent) + 1).toString();
        })
        .catch(function (error) {                        // catch
            console.log('Request failed', error);
        });
}