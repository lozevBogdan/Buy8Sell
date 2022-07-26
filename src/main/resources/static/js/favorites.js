window.addEventListener("load",() => {

    fetch(`http://localhost:8080/api/users/${id}/favorites`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res => res.json())
        .then(data => {
            console.log(data);
        })

})

// async function loadFavorites(event) {
//
//     // event.preventDefault()
//     fetch(`http://localhost:8080/api/users/${id}/favorites`, {
//         method: 'GET',
//         headers: {
//             'Accept': 'application/json'
//         }
//     }).then(res => res.json())
//         .then(data => {
//             console.log(data);
//         })
// }