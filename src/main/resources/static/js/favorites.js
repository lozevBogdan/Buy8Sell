window.addEventListener("load",loadFavorites)
//
// window.addEventListener('load', (event) => {
//     console.log('The page has fully loaded');
// });

const id = document.getElementById('idPrincipal').value;

const favorites = document.getElementById('loadBtn');

favorites.addEventListener("click", loadFavorites)


async function loadFavorites(event) {
    const favorCtr = document.getElementById('favorCtr');
    // event.preventDefault()

    fetch(`http://localhost:8080/api/users/${id}/favorites`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res => res.json())
        .then(data => {
           // favorCtr.appendChild(productAsHtml(data));
           console.log(data);
        })

}

// const productAsHtml = (product) => html`
//     <section class="panel">
//         <div id="imageHolder" class="pro-img-box text-center">
//             <img id="image" th:src="${product.pictureUrl}"
//                  width="250" height="220" />
//
//             <th:block th:if="*{isProductIsFavorInCurrentUser()}">
//                 <form th:method="post"
//                       th:action="@{/users/remove/favorites/${product.id}/favorites(id=*{id},favorites ='favorites')}">
//                     <button  type="submit" class="btn btn-danger bi bi-heart" >
//                         <i class="bi bi-heart"></i>Remove from favorites
//                     </button>
//                 </form>
//             </th:block>
//         </div>
//         <div class="panel-body text-center">
//             <h4>
//                 <a th:href="@{/products/info/{id}(id=*{id})}" th:text="${product.title}" class="pro-title">
//                     tittle
//                 </a>
//             </h4>
//             <h5 th:if="${product.isPromo}"><span class="badge badge-warning">PROMO</span></h5>
//             <p class="price" th:text="|${product.price} BGN|">$300.00</p>
//         </div>
//     </section>`