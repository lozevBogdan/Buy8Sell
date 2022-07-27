window.addEventListener("load", loadFavorites)


const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

const id = document.getElementById('idPrincipal').value;

async function loadFavorites() {

    const favorCtr = document.getElementById('favorCtr');

    fetch(`http://localhost:8080/api/users/${id}/favorites`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        }
    }).then(res => res.json())
        .then(data => {
            data.forEach(e => {
                if(e.promo){
                    favorCtr.innerHTML += (productAsHtmlPromo(e));
                }else {
                    favorCtr.innerHTML += (productAsHtmlNotPromo(e));
                }

            })
        })
}

const productAsHtmlPromo = (product) => `
   <div class="col-md-4">
    <section class="panel">
        <div id="imageHolder" class="pro-img-box text-center">
            <img id="image" src = ${product.pictureUrl}
                 width="250" height="220" />
                <form method="post"
                      action="/users/remove/favorites/${product.id}/${'favorites'}">
                      <input type="hidden" name="_csrf" value=${csrfHeaderValue}>
                    <button  type="submit" class="btn btn-success bi bi-heart" >
                        <i class="bi bi-heart"></i>Remove from favorites
                    </button>
                </form>
        </div>
        <div class="panel-body text-center">
            <h4>
                <a href="/products/info/${product.id}" th:text="${product.title}" class="pro-title">
                    ${product.title}
                </a>
            </h4>
             <h5  ><span class="badge badge-warning">PROMO</span></h5>  
            <p class="price" th:text="|${product.price} BGN|"> ${product.price} BGN</p>
        </div>
    </section>
</div>
`

const productAsHtmlNotPromo = (product) => `
   <div class="col-md-4">
    <section class="panel">
        <div id="imageHolder" class="pro-img-box text-center">
            <img id="image" src = ${product.pictureUrl}
                 width="250" height="220" />
                <form method="post"
                      action="/users/remove/favorites/${product.id}/${'favorites'}">
                      <input type="hidden" name="_csrf" value=${csrfHeaderValue}>
                    <button  type="submit" class="btn btn-success bi bi-heart" >
                        <i class="bi bi-heart"></i>Remove from favorites
                    </button>
                </form>
        </div>
        <div class="panel-body text-center">
            <h4>
                <a href="/products/info/${product.id}" th:text="${product.title}" class="pro-title">
                    ${product.title}
                </a>
            </h4>
            <p class="price" th:text="|${product.price} BGN|"> ${product.price} BGN</p>
        </div>
    </section>
</div>
`