let getUsersUrl = 'http://localhost:8080/admin/api/v1/users'
let getCurrentUserUrl = 'http://localhost:8080/admin/api/v1/me'
let getRolesUrl = 'http://localhost:8080/admin/api/v1/roles'
let postAddUserUrl = 'http://localhost:8080/admin/api/v1/users'
let patchEditUserUrl = 'http://localhost:8080/admin/api/v1/users/'
let deleteUserUrl = 'http://localhost:8080/admin/api/v1/users/'

let currentUserId

fillNavbar(getUserPromise())
fillUsersTable(getUsersPromise())
fillCurrentUserTable(getUserPromise())
fillNavPills(getUserPromise())
fillRolesSelectInAddUser(getRolesPromise())
fillRolesSelectInModals(getRolesPromise())

document.addUserForm.addEventListener('submit', sendCreatedUser);
document.editForm.addEventListener('submit', sendEditedUser);
document.deleteForm.addEventListener('submit', sendDeletedUser);

async function fillUsersTable(usersPromise) {
  let users = await usersPromise
  let rows = document.querySelector('tbody')
  users.forEach(user => {
      rows.innerHTML += `
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${getUserRolesUpperCase(user)}</td>
                <td>
                    <button class="btn btn-info btn-sm"  onclick="fillEditModal(${user.id})" data-bs-target="#modalEdit" data-bs-toggle="modal" type="submit">Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger btn-sm" onclick="fillDeleteModal(${user.id})" data-bs-target="#modalDelete" data-bs-toggle="modal" type="submit">Delete</button>
                </td>
            </tr>`
    }
  )
}

async function fillCurrentUserTable(userPromise) {
  let user = await userPromise
  let rows = document.querySelector('#currentUserTable').querySelector('tbody')
  rows.innerHTML += `
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${getUserRolesUpperCase(user)}</td>
            </tr>`
}

async function fillNavbar(userPromise) {
  let user = await userPromise
  let currentUserInfoNavbar = document.querySelector('.navbar-brand')
  currentUserInfoNavbar.innerHTML += `
            <strong>${user.email}</strong>
            &nbspwith roles:&nbsp
            <span>${getUserRolesUpperCase(user)}</span>`

}

async function fillNavPills(userPromise) {
  let user = await userPromise
  let currentUserRoles = document.querySelector('.nav-item')
  user.roles.forEach(role => {
    if (role.name === 'ROLE_ADMIN') {
      currentUserRoles.innerHTML += `
            <a class="nav-link active text-start" id="nav-pills-admin-tab" data-bs-toggle="pill"
                        href="#nav-pills-admin"> Admin </a>
    `
    }
    if (role.name === 'ROLE_USER') {
      currentUserRoles.innerHTML += `
            <a class="nav-link text-start" id="nav-pills-user-tab" data-bs-toggle="pill"
                        href="#nav-pills-user"> User </a>
    `
    }
  })
}

async function fillRolesSelectInAddUser(rolesPromise) {
  let roles = await rolesPromise
  let select = document.getElementById('addUserSelect')
  roles.forEach(role => {
    select.innerHTML += `<option value="${role.name}">${role.name = role.name.substring(5, 6) + role.name.substring(6).toLowerCase()}</option>`
  })
}

async function fillEditModal(id) {
  let response = await fetch('http://localhost:8080/admin/api/v1/users/' + id)
  let user = await response.json()
  currentUserId = user.id;
  let modalForm = document.editForm
  modalForm.email.value = user.email
  modalForm.password.value = user.password
  modalForm.username.value = user.username
  modalForm.firstName.value = user.firstName
  modalForm.lastName.value = user.lastName

  let userRoleNames = user.roles.map(role => role.name)

  modalForm.roles.options[0].selected = !!userRoleNames.includes('ROLE_ADMIN');
  modalForm.roles.options[1].selected = !!userRoleNames.includes('ROLE_USER');

}

async function fillDeleteModal(id) {
  let response = await fetch('http://localhost:8080/admin/api/v1/users/' + id)
  let user = await response.json()
  let modalForm = document.deleteForm
  modalForm.idShowed.value = user.id
  modalForm.emailShowed.value = user.email
  modalForm.passwordShowed.value = user.password
  modalForm.usernameShowed.value = user.username
  modalForm.firstNameShowed.value = user.firstName
  modalForm.lastNameShowed.value = user.lastName

  let userRoleNames = user.roles.map(role => role.name)

  modalForm.rolesShowed.options[0].selected = !!userRoleNames.includes('ROLE_ADMIN');
  modalForm.rolesShowed.options[1].selected = !!userRoleNames.includes('ROLE_USER');

}

async function fillRolesSelectInModals(rolesPromise) {
  let roles = await rolesPromise
  let editSelect = document.editForm.roles
  let deleteSelect = document.deleteForm.rolesShowed
  roles.forEach(role => {
    editSelect.innerHTML += `<option value="${role.name}">${role.name.substring(5, 6) + role.name.substring(6).toLowerCase()}</option>`
    deleteSelect.innerHTML += `<option value="${role.name}">${role.name.substring(5, 6) + role.name.substring(6).toLowerCase()}</option>`
  })
}

function sendCreatedUser(event) {
  event.preventDefault()
  let formData = document.addUserForm
  let selectedRoles = [...document.getElementById('addUserSelect').selectedOptions].map(option => option.value)
  let roles = []
  for (let roleName of selectedRoles) {
    let role = {
      name: roleName
    }
    roles.push(role)
  }
  let user = {
    email: formData.email.value,
    password: formData.password.value,
    username: formData.username.value,
    firstName: formData.firstName.value,
    lastName: formData.lastName.value,
    roles: roles
  };

  if (validate(user)) {
    fetch(postAddUserUrl, {
      method: 'POST',
      body: JSON.stringify(user),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then(() => location.href = '/admin')
  }
}

function validate(user) {
  const EMAIL_REGEXP = /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/iu;

  if (user.email === '') {
    alert('Enter email')
    return false
  } else if (!isEmailValid(user.email)) {
    alert('Not valid email')
    return false
  }
  if (user.password === '') {
    alert('Enter password')
    return false
  }
  if (user.username === '') {
    alert('Enter username')
    return false
  }
  if (user.roles.length === 0) {
    alert('Choose user role')
    return false
  }

  function isEmailValid(value) {
    return EMAIL_REGEXP.test(value);
  }

  return true
}

function sendEditedUser(event) {
  event.preventDefault()
  let formData = document.editForm
  let selectedRoles = [...document.getElementById('editUserSelect').selectedOptions].map(option => option.value)
  let roles = []
  for (let roleName of selectedRoles) {
    let role = {
      name: roleName
    }
    roles.push(role)
  }
  let user = {
    id: currentUserId,
    email: formData.email.value,
    password: formData.password.value,
    username: formData.username.value,
    firstName: formData.firstName.value,
    lastName: formData.lastName.value,
    roles: roles
  };

  if (validate(user)) {
    fetch(patchEditUserUrl + user.id, {
      method: 'PATCH',
      body: JSON.stringify(user),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then(() => location.href = '/admin')
  }
}

function sendDeletedUser(event) {
  event.preventDefault()
  let formData = document.deleteForm
  let selectedRoles = [...document.getElementById('deleteUserSelect').selectedOptions].map(option => option.value)
  let roles = []
  for (let roleName of selectedRoles) {
    let role = {
      name: roleName
    }
    roles.push(role)
  }
  let user = {
    id: formData.idShowed.value,
    email: formData.emailShowed.value,
    password: formData.passwordShowed.value,
    username: formData.usernameShowed.value,
    firstName: formData.firstNameShowed.value,
    lastName: formData.lastNameShowed.value,
    roles: roles
  };

  if (validate(user)) {
    fetch(deleteUserUrl + user.id, {
        method: 'DELETE'
      }
    ).then(() => location.href = '/admin')
  }
}

function getUserRolesUpperCase(user) {
  let shortRole = ''
  user.roles.forEach(role => {
    shortRole += role.name.substring(5) + ' '
  })
  return shortRole.trim()
}

function getUsersPromise() {
  return fetch(getUsersUrl).then(response => response.json())
}

function getUserPromise() {
  return fetch(getCurrentUserUrl).then(response => response.json())
}

function getRolesPromise() {
  return fetch(getRolesUrl).then(response => response.json());
}
