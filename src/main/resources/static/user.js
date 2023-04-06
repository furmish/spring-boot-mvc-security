let getCurrentUserUrl = 'http://localhost:8080/user/api/v1/me'

fillCurrentUserTable(getUserPromise())
fillNavbar(getUserPromise())
fillNavPills(getUserPromise())

async function fillCurrentUserTable(userPromise) {
    let user = await userPromise
    let rows = document.querySelector("tbody")
    rows.innerHTML += `
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${getUserRoles(user)}</td>
            </tr>`
}

async function fillNavbar(userPromise) {
    let user = await userPromise
    let currentUserInfoNavbar = document.querySelector(".navbar-brand")
    currentUserInfoNavbar.innerHTML += `
            <strong>${user.email}</strong>
            &nbspwith roles:&nbsp
            <span>${getUserRoles(user)}</span>`

}

async function fillNavPills(userPromise) {
    let user = await userPromise
    let currentUserRoles = document.querySelector(".nav-item")
    user.roles.forEach(role => {
        if (role.name == "ROLE_USER") {
            currentUserRoles.innerHTML += `
            <a class="nav-link active text-start" id="nav-pills-user-tab" data-bs-toggle="pill"
                        href="#nav-pills-user"> User </a>
    `
        }
    })
}

function getUserPromise() {
    return fetch(getCurrentUserUrl).then(response => response.json())
}

function getUserRoles(user) {
    let shortRole = ''
    user.roles.forEach(role => {
        shortRole += getShortName(role) + ' '
    })

    function getShortName(role) {
        return role.name.substring(5).toUpperCase()
    }

    return shortRole.trim()
}