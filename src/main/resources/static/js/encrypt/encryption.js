/**
 * Encryption.js
 */

const ENDPOINTS = {
    api: '/api/',
    encrypt: '/encrypt',
    decrypt: '/decrypt',
    matches: '/matches',
    encode: '/encode'
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

            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
        });
}

function decrypt(zone) {
    const form = document.getElementById(zone+'Form');

    if ( !validity(form) )
        return;

    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPostData(`${ENDPOINTS.api}${zone}${ENDPOINTS.decrypt}`, data)
        .then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    $('#'+zone+'Result').text(data.data);
                }
            } else {
                $('#'+zone+'Result').text('');
            }

            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
        });
}

function matche(zone) {

    $('#'+zone+'SecretKey').attr('required', 'required');
    const form = document.getElementById(zone+'Form');

    let flag = validity(form);
    $('#'+zone+'SecretKey').removeAttr('required');
    if ( !flag ) 
        return;
    
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPostData(`${ENDPOINTS.api}${zone}${ENDPOINTS.matches}`, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
        });
}

function encode(zone) {
    const form = document.getElementById(zone+'Form');

    if ( !validity(form) )
        return;

    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPostData(`${ENDPOINTS.api}${zone}${ENDPOINTS.encode}`, data)
        .then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    $('#'+zone+'Result').text(data.data);
                }
            } else {
                $('#'+zone+'Result').text('');
            }

            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
        });
}

function copyResult(zone) {

    const text = $('#'+zone+'Result').text();

    navigator.clipboard.writeText(text)
        .then(() => console.log('복사 성공: ' + zone))
        .catch(err => console.log('복사 실패:' + err));
}