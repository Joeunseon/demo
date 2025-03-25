/**
 * Encryption.js
 */

const ENDPOINTS = {
    api: '/api/',
    encrypt: "/encrypt",
    decrypt: "/decrypt"
};

function validity(form) {

    if (!form.checkValidity()) {
        form.reportValidity();
        return false;
    }

    return true;
}

function encrypt(zone) {
    const form = document.getElementById(zone+'Form');

    if ( !validity(form) )
        return;

    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPostData(`${ENDPOINTS.api}${zone}${ENDPOINTS.encrypt}`, data)
        .then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    $('#'+zone+'Result').text(data.data);
                }
            }
        });
}

function decrypt(zone) {
    
}

function copyResult(zone) {

    const text = $('#'+zone+'Result').text();

    navigator.clipboard.writeText(text)
        .then(() => console.log('복사 성공: ' + zone))
        .catch(err => console.log('복사 실패:' + err));
}