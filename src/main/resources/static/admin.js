$(async function () {
    await allUsers();
    await newUser();
    deleteUser();
    editCurrentUser();
});

async function allUsers() {
    const table = $('#bodyAllUserTable');
    table.empty()
    fetch("http://localhost:8080/api/admin")
        .then(r => r.json())
        .then(data => {
            data.forEach(user => {
                let users = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.position}</td>
                            <td>${user.salary}</td>
                            <td>${user.username}</td>
                            <td>${user.password}</td>                            
                            <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                            <td>
                                <button type="button" class="btn btn-sm btn-info" data-toggle="modal" id="buttonEdit" data-action="edit" data-id="${user.id}" data-target="#edit">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" id="buttonDelete" data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
                            </td>
                        </tr>)`;
                table.append(users);
            })
        })
        .catch((error) => {
            alert(error);
        })
}

async function newUser() {
    const rolesResponse = await fetch("http://localhost:8080/api/admin/roles");
    const roles = await rolesResponse.json();

    roles.forEach((role) => {
        let element = document.createElement("option");
        element.text = role.name.substring(5);
        element.value = role.id;
        document.getElementById('rolesNewUser').appendChild(element);
    });

    const formAddNewUser = document.forms["formAddNewUser"];

    formAddNewUser.addEventListener("submit", async function (event) {
        event.preventDefault();
        let rolesNewUser = [];
        for (let i = 0; i < formAddNewUser.roles.options.length; i++) {
            if (formAddNewUser.roles.options[i].selected) {
                try {
                    const roleResponse = await fetch("http://localhost:8080/api/admin/roles/" + formAddNewUser.roles.options[i].value);
                    const role = await roleResponse.json();
                    rolesNewUser.push(role);
                } catch (error) {
                    alert("Error retrieving role: " + error);
                    return;
                }
            }
        }

        try {
            const response = await fetch("http://localhost:8080/api/admin/addUser", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    name: formAddNewUser.name.value,
                    surname: formAddNewUser.surname.value,
                    position: formAddNewUser.position.value,
                    salary: formAddNewUser.salary.value,
                    username: formAddNewUser.username.value,
                    password: formAddNewUser.password.value,
                    roles: rolesNewUser,
                }),
            });

            if (response.ok) {

                window.location.assign("http://localhost:8080/admin");
                formAddNewUser.reset();
            } else {
                throw new Error("Error adding user");
            }
        } catch (error) {
            alert(error);
        }
    });
}

function deleteUser() {
    const deleteForm = document.forms["formDeleteUser"];
    deleteForm.addEventListener("submit", function (event) {
        event.preventDefault();
        fetch("http://localhost:8080/api/admin/deleteUser/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteFormCloseButton').click();
                return allUsers();
            })
            .catch((error) => {
                alert(error);
            });
    })
}

$(document).ready(function () {
    $('#delete').on("show.bs.modal", function (event) {
        const button = $(event.relatedTarget);
        const id = button.data("id");
        return viewDeleteModal(id);
    })
})

async function viewDeleteModal(id) {
    let userDelete = await getUser(id);
    let formDelete = document.forms["formDeleteUser"];
    formDelete.id.value = userDelete.id;
    formDelete.name.value = userDelete.name;
    formDelete.surname.value = userDelete.surname;
    formDelete.position.value = userDelete.position;
    formDelete.salary.value = userDelete.salary;
    formDelete.username.value = userDelete.username;
    formDelete.password.value = userDelete.password;


    $('#deleteRolesUser').empty();

    await fetch("http://localhost:8080/api/admin/roles")
        .then(r => r.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < userDelete.roles.length; i++) {
                    if (userDelete.roles[i].name === role.name) {
                        selectedRole = true;
                        break;
                    }
                }
                let element = document.createElement("option");
                element.text = role.name.substring(5);
                element.value = role.id;
                if (selectedRole) element.selected = true;
                $('#deleteRolesUser')[0].appendChild(element);
            })
        })
        .catch((error) => {
            alert(error);
        })
}

async function getUser(id) {

    let url = "http://localhost:8080/api/admin/" + id;
    let response = await fetch(url);
    return await response.json();
}

function editCurrentUser() {
    const editForm = document.forms["formEditUser"];
    editForm.addEventListener("submit", function (event) {
        event.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.roles.options.length; i++) {
            if (editForm.roles.options[i].selected)
                editUserRoles.push({
                    id: editForm.roles.options[i].value,
                    name: editForm.roles.options[i].name,
                });
        }

        fetch("http://localhost:8080/api/admin/updateUser", {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                name: editForm.name.value,
                surname: editForm.surname.value,
                position: editForm.position.value,
                salary: editForm.salary.value,
                username: editForm.username.value,
                password: editForm.password.value,
                roles: editUserRoles
            })
        }).then(() => {
            $('#editFormCloseButton').click();
            return allUsers();
        })
            .catch((error) => {
                alert(error);
            });
    });
}

$(document).ready(function () {
    $('#edit').on("show.bs.modal", function (event) {
        const button = $(event.relatedTarget);
        const id = button.data("id");
        return viewEditModal(id);
    });
});

async function viewEditModal(id) {
    let userEdit = await getUser(id);
    let form = document.forms["formEditUser"];
    form.id.value = userEdit.id;
    form.name.value = userEdit.name;
    form.surname.value = userEdit.surname;
    form.position.value = userEdit.position;
    form.salary.value = userEdit.salary;
    form.username.value = userEdit.username;


    $('#editRolesUser').empty();

    await fetch("http://localhost:8080/api/admin/roles")
        .then(r => r.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < userEdit.roles.length; i++) {
                    if (userEdit.roles[i].name === role.name) {
                        selectedRole = true;
                        break;
                    }
                }
                let element = document.createElement("option");
                element.text = role.name.substring(5);
                element.value = role.id;
                if (selectedRole) element.selected = true;
                $('#editRolesUser')[0].appendChild(element);
            });
        })
        .catch((error) => {
            alert(error);
        });
}