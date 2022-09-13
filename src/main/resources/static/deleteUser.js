$(async function() {

    deleteUser();
});
function deleteUser(){
    const deleteForm = document.forms["formDeleteUser"];
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("http://localhost:8080/adminRest/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteFormCloseButton').click();
                allUsers();
            })
    })
}
$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function showDeleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["formDeleteUser"];
    form.id.value = user.id;
    form.lastname.value = user.lastname;
    form.username.value = user.username;


    $('#rolesDeleteUser').empty();

    await fetch("http://localhost:8080/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.text = role.role.substring(5);
                el.value = role.id;
                $('#rolesDeleteUser')[0].appendChild(el);
            })
        });
}
async function getUser(id) {
    let url = "http://localhost:8080/adminRest/" + id;
    let response = await fetch(url);
    return await response.json();
}