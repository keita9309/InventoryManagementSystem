let usernameInput = document.querySelector('#userInput');

function editAuthority() {
    usernameInput.disabled = false;
    document.querySelector('#edit-form').submit();
}