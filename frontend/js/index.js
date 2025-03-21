const API_URL = `http://localhost:8081/api/products`
const TOKEN = `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MjU4NDQzMSwiZXhwIjoxNzQyNTg4MDMxfQ.3D1iJtChFsbhpUYl5CluiG8OoGUiUs5B-wjFqh23teQ`

function getProducts() {
    fetch(
        API_URL,
        {
            headers: { 'Authorization': `Bearer ${TOKEN}` }
        }
    )
    .then(res => res.json())
    .then(data => {
        const list = document.getElementById("products_list")
        list.innerHTML = ``
        data.forEach(product => {
            const li = document.createElement("li")
            li.textContent = `${product.name}`
            list.appendChild(li)
        });
    })
    //Додати метод
    //getProductById
}