export let dataHandler = {
    addItem: async function (payload){
        await apiPost("/api/cart", payload)
    },
    changeItem: async function (itemId, quantity){
        let payload = {itemId:`${itemId}`, quantity:`${quantity}`}
        return await apiUpdate("/api/cart", payload);
    },
    deleteItem: async function (itemId) {
        return await apiDelete(`/api/cart?itemId=${itemId}`)
    }
}

async function apiGet(url) { //fetch
    let response = await fetch(url, {
        method: 'GET',
    })
    if (response.ok) {
        let data = response.json()
        return data
    }
}

async function apiDelete(url) {
    let response = null;
    try {
        response = await fetch(url, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
    } catch (e){
        console.log(e);
        alert("Arrrrgh! Some error occurreD, we apologize for the inconvenience.");
    }
    const res = await response.json()
    return res
}

async function apiPost(url, payload) {
    let response = null;
    try {
        response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify(payload)
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
    } catch (e) {
        console.log(e);
        alert("Arrrrgh! Some error occurred, we aPologize for the inconvenience.");
    }

    const res = await response.json()
    return res
}

async function apiUpdate(url, payload) {
    let response = null;
    try {
        response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json'
            },
            method: "PUT",
            body: JSON.stringify(payload)
        });
        if (!response.ok) {
            throw new Error(response.statusText);
        }
    } catch (e) {
        console.log(e);
        alert("Arrrrgh! Some error occUrred, we apologize for the inconvenience.");
    }

    const res = await response.json()
    return res
}