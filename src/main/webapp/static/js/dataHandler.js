export let dataHandler = {
    addItem: async function (payload){
        await apiPost("/api/cart", payload)
    },
    changeItem: async function (itemId, quantity){
        let payload = {itemId:`${itemId}`, quantity:`${quantity}`}
        await apiUpdate("/api/cart", payload)
    },
    deleteItem: async function (itemId) {
        await apiDelete(`/api/cart?itemId=${itemId}`)
    }
}

async function apiGet(url) { //fetch
    let response = await fetch(url, {
        method: 'GET',
    })
    if (response.status === 200) {
        let data = response.json()
        return data
    }
}

async function apiDelete(url) {
    let response = await fetch(url, {
        method: 'DELETE'
    })
}

async function apiPost(url, payload) {
    let response = await fetch(url, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: "POST" ,
        body : JSON.stringify(payload)
    })
    const res = await response.json()
    return res
}

async function apiUpdate(url, payload) {
    let response = await fetch(url, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: "PUT",
        body : JSON.stringify(payload)
    })
    const res = await response.json()
    return res
}